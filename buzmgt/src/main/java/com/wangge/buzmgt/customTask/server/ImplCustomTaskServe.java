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
import org.springframework.ui.Model;

import com.wangge.buzmgt.customTask.entity.CustomMessages;
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
	public static final String[] TASKTYPEARR = new String[] { "注册", "售后", "扣罚" };

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
	public Map<String,Object> findAll(Pageable page, Map<String, Object> searchParams) {
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
		Map<String,Object> allMap=new HashMap<String,Object>();
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		for (CustomTask task : cList) {
			Map<String, Object> datamap = new HashMap<String, Object>();
			datamap.put("id", task.getId());
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
			getCustomRecieve(task, datamap, saleList);
			mapList.add(datamap);
		}
		allMap.put("content", mapList);
		allMap.put("size", cpage.getSize());
		allMap.put("totalElements", cpage.getTotalElements());
		return allMap;
	}

	/**获取单个customtask的recieve数据
	 * @param task
	 * @param datamap
	 * @param saleList 业务员列表
	 * @throws NumberFormatException
	 */
	private Object getCustomRecieve(CustomTask task, Map<String, Object> datamap, Set<SalesMan> saleList)
			throws NumberFormatException {
		int status = task.getStatus();
		if (status == 0) {
			datamap.put("recieve", "业务员未读");
		} else {
			datamap.put("recieve", "有回执");
		}
		if (saleList.size() > 0 && status == 1) {
			Object[] sum = (Object[]) messageRep.countByRoleType(task.getId());
			int allsum = Integer.parseInt(sum[1].toString());
			int unsum = Integer.parseInt(sum[0].toString());
			if (allsum == 1 && saleList.size() == 1 && unsum == 0) {
				datamap.put("recieve", "回执已读");
			} else if (allsum > 1) {
				datamap.put("recieve", unsum + "/" + allsum);
			}
		}
		return datamap.get("recieve");
	}

	@Override
	public void getSaleSet(CustomTask customTask, Model model) {
		Set<String> reSet = messageRep.findByCustomtaskId(customTask.getId());
		Set<SalesMan> salesmanSet = customTask.getSalesmanSet();
		List<SalesMan> reList = salesmanRep.findAll(reSet);
		salesmanSet.removeAll(reList);
		Set<SalesMan> unreSet = salesmanSet;
	
		model.addAttribute("reSet", reList);
		model.addAttribute("unreSet", unreSet);
		if (reSet.size() > 0) {
			String ids = "";
			for (String s : reSet) {
				ids += s + ",";
			}
			model.addAttribute("resalSet", ids.substring(0, ids.length() - 1));
		} else {
			model.addAttribute("resalSet", "null");
		}
		if (salesmanSet.size() > 0) {
			String ids = "";
			for (SalesMan s : salesmanSet) {
				ids += s.getId() + ",";
			}
			model.addAttribute("unresalSet", ids.substring(0, ids.length() - 1));
		} else {
			model.addAttribute("unresalSet", "null");
		}
	}

	@Override
	public Map<String, Object> getMessage(CustomTask customTask, Pageable pageReq) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		getCustomRecieve(customTask, returnMap, customTask.getSalesmanSet());
		
		Long customId = customTask.getId();
		Set<String> reSet = messageRep.findbyRoleType(customId);
		int size = pageReq.getPageSize();
		int page = pageReq.getPageNumber();
		int startNum = size * page;
		int ArrSize = reSet.size() > size * (page + 1) ? size : reSet.size() - startNum;
		String[] subSet = reSet.toArray(new String[] {});
		String[] idArr = new String[ArrSize];
		for (int i = 0; i < ArrSize; i++) {
			idArr[i] = subSet[startNum + i];
		}
		List<CustomMessages> msList = messageRep.findByCustomtaskIdAndSalesmanIdInOrderByTimeAsc(customId, idArr);
		List<Map<String, Object>> contList = new ArrayList<Map<String, Object>>();
		Map<String, List<CustomMessages>> remap = new HashMap<String, List<CustomMessages>>();
		// 聚合分组消息列表
		for (CustomMessages message : msList) {
			message.setCtime();
			String saleid = message.getSalesmanId();
			List<CustomMessages> mList = remap.get(saleid);
			if (null == mList) {
				mList = new ArrayList<CustomMessages>();
			}
			mList.add(message);
			remap.put(saleid, mList);
		}
		// 处理分组
		for (Map.Entry<String, List<CustomMessages>> entry : remap.entrySet()) {
			Map<String, Object> singleSaleMap = new HashMap<String, Object>();
			SalesMan man = salesmanRep.findById(entry.getKey());
			List<CustomMessages> mesageList = entry.getValue();
			singleSaleMap.put("name", man.getTruename());
			singleSaleMap.put("salesId", entry.getKey());
			singleSaleMap.put("size", mesageList.size());
			singleSaleMap.put("unsize", countUnreadSize(mesageList));
			singleSaleMap.put("mesList", mesageList);
			contList.add(singleSaleMap);
		}
		returnMap.put("content", contList);
		returnMap.put("number", reSet.size());
		returnMap.put("newId", findlastId(customId));
		returnMap.put("size", size);
		return returnMap;
	}

	/**
	 * 统计每个list中有多少条没有阅读的信息
	 * 
	 * @param mesageList
	 * @return
	 */
	private Object countUnreadSize(List<CustomMessages> mesageList) {
		List<Long> idList = new ArrayList<Long>();
		int i = 0;
		for (CustomMessages c : mesageList) {
			if (c.getRoletype() == 1 && c.getStatus() == 0) {
				i++;
				idList.add(c.getId());
			}
		}
		if (idList.size() > 0) {
			messageRep.updateStatusById(idList);
		}
		return i;
	}

	@Override
	public void saveMessage(Map<String,Object> messages) {
		/*"customtaskId" : taskId,
		"salesmanId" : salesmanIds,
		"content" : content*/
		Long customtaskId=Long.parseLong(messages.get("customtaskId").toString());
		String content=messages.get("content").toString();
		@SuppressWarnings("unchecked")
		List<String> salesids=(List<String>) messages.get("salesmanId");
		List<CustomMessages> mlist=new ArrayList<CustomMessages>();
		for(String saleid:salesids){
			CustomMessages message=new CustomMessages(customtaskId,saleid,content);
			mlist.add(message);
		}
		messageRep.save(mlist);
		Map<String, Object> talMap = new HashMap<String, Object>();
		List<SalesMan> salesList=salesmanRep.findAll(salesids);
		String phone="";
		for(SalesMan man:salesList){
			phone+=man.getMobile()+",";
		}
		talMap.put("mobiles", phone.substring(0,phone.length()-1));
		talMap.put("msg", "您有新的自定义回复消息");
		talMap.put("Id", customtaskId);
		
		HttpUtil.sendPostJson(AppServer.URL + "customTask", talMap);
	}

	@Override
	public Object findlastId(Long taskId) {
		
		return messageRep.CountbyCustomtaskId(taskId);
	}

}
