package com.wangge.buzmgt.monthTask.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.Predicate;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.assess.repository.AssessRepository;
import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.monthTask.entity.MonthOdersData;
import com.wangge.buzmgt.monthTask.entity.MonthTask;
import com.wangge.buzmgt.monthTask.entity.MonthTaskExecution;
import com.wangge.buzmgt.monthTask.entity.MonthTaskSub;
import com.wangge.buzmgt.monthTask.entity.MonthshopBasData;
import com.wangge.buzmgt.monthTask.repository.MonthOrdersDataRepository;
import com.wangge.buzmgt.monthTask.repository.MonthTaskExecutionRepository;
import com.wangge.buzmgt.monthTask.repository.MonthTaskRepository;
import com.wangge.buzmgt.monthTask.repository.MonthTaskSubRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.DateUtil;

@Service

public class MonthTaskServiceImpl implements MonthTaskService {
	@PersistenceContext
	private EntityManager em;

	@Autowired
	private MonthTaskRepository mtaskRep;
	@Resource
	MonthOrdersDataRepository monthDataRep;
	@Resource
	MonthTaskRepository monthTaskRep;
	@Resource
	MonthTaskSubRepository taskSubrep;
	@Autowired
	MonthTaskExecutionRepository mtExecRepository;
	@Resource
	RegionRepository regoinRep;
	@Autowired
	private ManagerService managerService;
	@Resource
	private AssessService assessService;
	private static Integer[] levels = new Integer[] { 20, 15, 10, 7, 4 };

	// 订单支付状态为1,计算上个月的订单情况
	public static final String lsdatasql = "select  r.registdata_id, r.region_id , o3.days, r.shop_name, '1' month \n"
			+ "  from (select count(1) days, o2.member_id\n" + "          from (select o1.member_id, o1.day\n"
			+ "                  from (select t.member_id,\n" + "                               t.CREATETIME,\n"
			+ "                               to_char(t.CREATETIME, 'yyyy-mm-dd') day,\n"
			+ "                               t.ship_Name\n"
			+ "                          from SJZAIXIAN.SJ_TB_ORDER t\n"
			+ "                         where to_char(t.createTime, 'yyyy-mm') =\n"
			+ "                               to_char(sysdate - interval '1' month, 'yyyy-mm')\n"
			+ "                           and t.pay_status = '1') o1\n"
			+ "                 group by o1.member_id, o1.day) o2\n" + "         group by o2.member_id\n"
			+ "         order by days desc) o3\n" + "  left join SJZAIXIAN.SJ_TB_members m on o3.member_id = m.id \n"
			+ "   inner join  sys_registdata r on r.member_id=m.id \n" + " where  r.region_id like '$town%' ";
	// 上月拜访访情况
	public static final String lsVisitSql = "select count(1), tmp.month, tmp.registdata_id, tmp.region_id \n"
			+ "  from (select substr(g.finish_time, 0, 7) month,\n" + "               g.registdata_id,\n"
			+ "               g.region_id\n"
			+ "          from (select to_char(v.finish_time, 'yyyy-mm-dd') finish_time,\n"
			+ "                       r.registdata_id,\n" + "                       r.region_id\n"
			+ "                  from sys_visit v\n"
			+ "                  left join sys_registdata r on v.registdata_id =\n"
			+ "                                                r.registdata_id\n"
			+ "                 where v.finish_time is not null\n"
			+ "                   and r.region_id like '$town%'\n"
			+ "                   and (to_char(v.finish_time, 'yyyy-mm') =\n"
			+ "                       to_char(sysdate - interval '1' month, 'yyyy-mm'))) g\n"
			+ "         group by g.finish_time, g.registdata_id, g.region_id) tmp\n"
			+ " group by tmp.month, tmp.registdata_id, tmp.region_id";

	@Override
	public Map<String, Object> getMainTaskList(Pageable page, String month, MultiValueMap<String, String> parameters)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException,
			SecurityException {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		Object flag = parameters.getFirst("flag");
		String saleManName = parameters.getFirst("salesManName") == null ? null : parameters.getFirst("salesManName") + "";
		if (null == month || "".equals(month))
			month = DateUtil.getPreMonth(new Date(), 1);
		Page<MonthTask> result = null;
		if (null == flag) {
			result = mtaskRep.findByMonth(month, page);
		} else {
			if (null == saleManName||"".equals(saleManName)) {
				result = mtaskRep.findByMonthAndStatus(month, 1, page);
			} else {
				result = mtaskRep.findByMonthAndStatusAndMonthData_Salesman_TruenameLike(month, 1,
						"%" + saleManName + "%", page);
			}
		}
		List<MonthTask> taskList = result.getContent();
		List<Map<String, Object>> vList = new ArrayList<Map<String, Object>>();
		for (MonthTask mt : taskList) {
			if (mt.getMonthData() == null) {
				continue;
			}
			SalesMan salesman = mt.getMonthData().getSalesman();
			Region r = salesman.getRegion();
			Map<String, Object> taskMap = new HashMap<String, Object>();
			String address = getAllName(r, "");
			taskMap.put("region", address);
			taskMap.put("status", mt.getStatus());
			taskMap.put("taskId", mt.getId());
			// #saojie.salesman.user.organization.name
			taskMap.put("name", salesman.getTruename());
			taskMap.put("role", salesman.getUser().getOrganization().getName());
			Class<? extends MonthTask> mclass = mt.getClass();
			List<Map<String, Object>> dList = new ArrayList<Map<String, Object>>();
			for (Integer i : levels) {
				Map<String, Object> datamap = new HashMap<String, Object>();
				datamap.put("level", i + "");
				datamap.put("goal", getReflectInt(mclass.getDeclaredMethod("getTal" + i + "goal").invoke(mt)));
				datamap.put("set", getReflectInt(mclass.getDeclaredMethod("getTal" + i + "set").invoke(mt)));
				dList.add(datamap);
			}
			taskMap.put("detail", dList);
			vList.add(taskMap);
		}
		pageMap.put("content", vList);
		pageMap.put("number", result.getTotalPages());
		pageMap.put("totalElements", result.getTotalElements());
		pageMap.put("size", result.getSize());
		return pageMap;
	}

	// 获取反射的int
	private int getReflectInt(Object o) {
		return o == null ? 0 : Integer.parseInt(o + "");
	}

	// 获得县级之上的所有地名
	private String getAllName(Region r, String name) {
		if (!r.getName().equals("中国")) {
			name = r.getName() + name;
		}
		Region father = r.getParent();
		if (null != father) {
			return getAllName(father, name);
		}
		return name;
	}

	@Override
	public Map<String, Object> getMainTaskForUpdate(Long id) {
		Map<String, Object> smap = new HashMap<String, Object>();
		MonthTask mtsk = monthTaskRep.findById(id);
		MonthOdersData data = mtsk.getMonthData();
		data.setSalesman(null);
		smap.put("task", mtsk);
		smap.put("data", data);

		return smap;
	}

	@Override
	public Map<String, Object> findTaskSub(Long parentId, Integer goal, Pageable page,
			MultiValueMap<String, String> parameters) throws Exception {
		Map<String, Object> pageMap = new HashMap<String, Object>();
		Page<MonthTaskSub> pageRe = null;
		if (null != goal) {
			pageRe = taskSubrep.findByMonthTask_IdAndGoalOrderByGoalDesc(parentId, goal, page);
		} else {
			pageRe = taskSubrep.findByMonthTask_IdOrderByGoalDesc(parentId, page);
		}
		List<MonthTaskSub> taskList = pageRe.getContent();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		// goal,shopName,done,region,shopId,month,
		for (MonthTaskSub mt : taskList) {
			Map<String, Object> mtMap = new HashMap<String, Object>();

			mtMap.put("subTaskId", mt.getId());
			mtMap.put("goal", mt.getGoal());
			mtMap.put("done", mt.getDone());
			MonthshopBasData sdata = mt.getMonthsd();
			mtMap.put("month", sdata.getMonth());
			RegistData regist = sdata.getRegistData();
			mtMap.put("shopId", regist.getId());
			mtMap.put("shopName", regist.getShopName());
			mtMap.put("region", regist.getRegion().getName());
			dataList.add(mtMap);
		}
		pageMap.put("content", dataList);
		pageMap.put("number", pageRe.getTotalPages());
		pageMap.put("totalElements", pageRe.getTotalElements());
		pageMap.put("size", pageRe.getSize());
		return pageMap;
	}

	@Override
	public void findTaskExecut(Long shopId, String month, Long subTaskId, Model model) {
		MonthTaskSub mtaskSub = taskSubrep.findOne(subTaskId);
		List<MonthTaskExecution> dlist = mtExecRepository.findByTaskmonthAndRegistData_idOrderByTimeDesc(month, shopId);
		model.addAttribute("goal", mtaskSub.getGoal());
		model.addAttribute("done", mtaskSub.getDone());
		SalesMan salesman = mtaskSub.getMonthsd().getSalesman();
		Assess assess = findAssessBySalesMan(salesman);
		model.addAttribute("salesMan", salesman);
		model.addAttribute("assess", assess);
		if (dlist.size() > 0) {
			model.addAttribute("shopName", dlist.get(0).getRegistData().getShopName());
		} else {
			model.addAttribute("shopName", mtaskSub.getMonthsd().getRegistData().getShopName());
		}
		List<Map<String, String>> vlist = new ArrayList<Map<String, String>>();

		for (MonthTaskExecution mt : dlist) {
			Map<String, String> mtMap = new HashMap<String, String>();
			String sd = DateUtil.date2String(mt.getTime(), "yyyy-MM月dd日 HH时mm:dd");
			String[] timeArr = sd.split(" ");
			String day = timeArr[0].substring(5);
			String time = timeArr[1].substring(0, 5);
			mtMap.put("day", day);
			mtMap.put("time", time);
			mtMap.put("action", mt.getAction());
			mtMap.put("shopName", mt.getRegistData().getShopName());
			vlist.add(mtMap);
		}
		model.addAttribute("visitList", vlist);

	}

	/**
	 * 通过salesMan查询Assess考核阶段
	 * 
	 * @param salesMan
	 * @return
	 */
	public Assess findAssessBySalesMan(SalesMan salesMan) {
		List<Assess> assesseList = assessService.findBysalesman(salesMan);
		Assess assess = new Assess();
		Map<String, Assess> assessMaxStage = new HashMap<String, Assess>();
		assessMaxStage.put("max", assess);
		try {
			if (assesseList.size() > 0) {
				assesseList.forEach(assess_ -> {
					int max = Integer.parseInt(assessMaxStage.get("max").getAssessStage() == null ? "0"
							: assessMaxStage.get("max").getAssessStage());
					if (Integer.parseInt(assess_.getAssessStage()) > max) {
						assessMaxStage.put("max", assess_);
						System.out.println(assess_.getAssessStage());
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assessMaxStage.get("max");
	}

	@Override
	public void findSalesMan(MonthTask task, Model model) {
		SalesMan salesman = task.getMonthData().getSalesman();
		Assess assess = findAssessBySalesMan(salesman);
		model.addAttribute("salesMan", salesman);
		model.addAttribute("assess", assess);

	}

	@Override
	public Region getRegion(String regoinId) {
		if (null == regoinId) {
			// 获取user
			Subject subject = SecurityUtils.getSubject();
			User user = (User) subject.getPrincipal();
			Manager manager = managerService.getById(user.getId());
			String regionId = manager.getRegion().getId();
			return regoinRep.findOne(regionId);
		} else {
			return regoinRep.findOne(regoinId);
		}
	}
}
