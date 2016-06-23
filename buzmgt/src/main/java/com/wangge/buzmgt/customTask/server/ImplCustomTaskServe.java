package com.wangge.buzmgt.customTask.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.customTask.entity.CustomTask;
import com.wangge.buzmgt.customTask.repository.CustomMessagesRepository;
import com.wangge.buzmgt.customTask.repository.CustomTaskRepository;
import com.wangge.buzmgt.customTask.util.PredicateUtil;
import com.wangge.buzmgt.monthTask.entity.AppServer;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.HttpUtil;

@Service
public class ImplCustomTaskServe implements CustomTaskServer {
	@Autowired
	CustomTaskRepository customRep;
	@Autowired
	CustomMessagesRepository messageRep;
	@Autowired
	SalesManRepository salesmanRep;
	private static final String[] TASKTYPEARR = new String[] { "注册", "售后", "扣罚" };

	@Override
	public Map<String, Object> getList(String salesmanId, Pageable page) {
		return null;
	}

	@Override
	public void save(CustomTask customTask) throws Exception {
		Collection<SalesMan> oldSet = customTask.getSalesmanSet();
		List<String> idList = new ArrayList<String>();
		for (SalesMan old : oldSet) {
			idList.add(old.getId());
		}
		List<SalesMan> newlist = salesmanRep.findAll(idList);
		customTask.setSalesmanSet(new HashSet<SalesMan>(newlist));
		customRep.save(customTask);
		String phone = "";
		for (SalesMan salesman : newlist) {
			phone += salesman.getMobile() + ",";
		}
		if (phone.length() > 3)
			phone = phone.substring(0, phone.length() - 1);
		Map<String, Object> talMap = new HashMap<String, Object>();
		talMap.put("mobiles", phone);
		talMap.put("msg", customTask.getTitle());
		talMap.put("Id", customTask.getId());
		HttpUtil.sendPostJson(AppServer.URL + "customTask", talMap);
	}

	@Override
	public List<Map<String, Object>> findAll(Pageable page, Map<String, Object> searchParams) {
		String salesName = searchParams.get("salesName") == null ? "" : searchParams.get("salesName").toString();
		searchParams.remove("salesName");
		Page<CustomTask> cpage = customRep.findAll(new Specification<CustomTask>() {

			@Override
			public Predicate toPredicate(Root<CustomTask> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if (!salesName.isEmpty()) {
					SetJoin<CustomTask, SalesMan> setJion = root
							.join(root.getModel().getDeclaredSet("salesmanSet", SalesMan.class), JoinType.LEFT);
					predicates.add(cb.like(setJion.get("truename"), "%" + salesName + "%"));
				}
				PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);

				return cb.and(predicates.toArray(new Predicate[] {}));
			}
		}, page);
		List<CustomTask> cList = cpage.getContent();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (CustomTask task : cList) {
			Map<String, Object> datamap = new HashMap<String, Object>();
			datamap.put("title", task.getTitle());
			datamap.put("type", task.getType());
			datamap.put("typeName", TASKTYPEARR[task.getType()]);			
			datamap.put("content", task.getContent());
			datamap.put("time", task.getCreateTime());
			 Set<SalesMan> saleList = task.getSalesmanSet();
			String saleNames = "";
			for (SalesMan man : saleList) {
				saleNames += man.getTruename() + " ";
			}
			if (saleNames.length() > 1)
				saleNames = saleNames.substring(0, saleNames.length() - 1);
			datamap.put("salesMan", saleNames);
			int status=task.getStatus();
			if(status==0){
				datamap.put("recieve","业务员未读" );
			}else{
				datamap.put("recieve","有回执" );
			}
			if(saleList.size()>0&&status==1){
				Object[] sum= (Object[]) messageRep.countByRoleType(task.getId());
				int allsum= Integer.parseInt(sum[1].toString());
				int unsum=Integer.parseInt(sum[0].toString());
				if(allsum==1&&saleList.size()==1&&unsum==0){
					datamap.put("recieve","回执已读");	
				}
				else if(allsum>1){
					datamap.put("recieve",unsum+"/"+allsum );
				}
			}
			mapList.add(datamap);
		}
		return mapList;
	}

}
