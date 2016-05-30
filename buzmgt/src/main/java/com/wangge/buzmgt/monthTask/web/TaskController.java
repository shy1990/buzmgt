package com.wangge.buzmgt.monthTask.web;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.io.nativeio.Errno;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.wangge.buzmgt.monthTask.entity.MonthOdersData;
import com.wangge.buzmgt.monthTask.entity.MonthTask;
import com.wangge.buzmgt.monthTask.entity.MonthTaskPunish;
import com.wangge.buzmgt.monthTask.entity.MonthTaskSub;
import com.wangge.buzmgt.monthTask.repository.MonthOrdersDataRepository;
import com.wangge.buzmgt.monthTask.repository.MonthTaskPunishRepository;
import com.wangge.buzmgt.monthTask.repository.MonthTaskRepository;
import com.wangge.buzmgt.monthTask.service.MonthTaskService;
import com.wangge.buzmgt.oilcost.entity.OilCost;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.ExcelExport;

@Controller
@RequestMapping("/monthTask")
public class TaskController {
	@Resource
	MonthTaskService monthTaskService;
	@Resource
	RegionRepository regoinRep;
	@Resource
	MonthTaskRepository monthTaskRep;
	@Autowired
	private SalesManRepository salesRep;
	@Resource
	MonthOrdersDataRepository monthDataRep;
	@Autowired
	private ManagerService managerService;
	@Autowired
	MonthTaskPunishRepository monthPunishRep;
	@Autowired
	MonthTaskPunishRepository monthTakPuRep;
	private Log log = LogFactory.getLog(TaskController.class);



	/**
	 * 任务查看页面
	 * 
	 * @param task
	 * @param model
	 * @return
	 */
	@RequestMapping("/lookup/set/{id}")
	public String lookeTaskSet(@PathVariable("id") MonthTask task, Model model) {
		monthTaskService.findSalesMan(task, model);
		model.addAttribute("taskId", task.getId());
		return "month/mainTask_set_detail";
	}



	/**
	 * 月任务查看页面->子任务设置->子任务执行情况 月任务查看页面->扣罚设置页面
	 * 
	 * @param regoinId
	 * @param page
	 * @return
	 */
	@RequestMapping("/lookup/mainTasks")
	public String findAllMainTasks(String regoinId, Model model) {
		model.addAttribute("region", monthTaskService.getRegion(regoinId));

		return "month/mainTask";
	}
	/**
	 * 查看任务数据
	 * 
	 * @param task
	 * @param page
	 * @return
	 */
	@RequestMapping("/lookup/setData/{id}")
	public ResponseEntity<Map<String, Object>> lookeTaskSetData(@PathVariable("id") MonthTask task, Pageable page) {
		Map<String, Object> smap = new HashMap<String, Object>();
		smap = monthTaskService.findSetData(task, page);
		return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.OK);
	}

	@RequestMapping("/export/{id}")
	public void excelExport(@PathVariable("id") MonthTask task, @RequestParam("salesId") SalesMan salesman,
			HttpServletRequest request, HttpServletResponse response) {
		monthTaskService.ExportSetExcel(task,
				salesman.getTruename().trim().replace("/n", "").replace("/r", "").replace("/t", ""), request, response);

	}
	
	/**
	 * 月任务派发页面->修改,发布 月任务派发页面->添加月任务 月任务派发页面->查看月任务设置情况
	 * 
	 * @param regoinId
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	public String init(Model model) {
		//本月已派发的任务
		model.addAttribute("doneCount", monthTaskService.getIssueTaskCount());
		return "month/task_month";
	}
	
	/**
	 * 月任务派发情况查询;主任务数据查询
	 * 
	 * @param month
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> handle(String month, Pageable page,
			@RequestParam MultiValueMap<String, String> parameters) {
		try {
			return new ResponseEntity<Map<String, Object>>(monthTaskService.getMainTaskList(page, month, parameters),
					HttpStatus.OK);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
			log.debug(e);
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("code", "1");
			return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	// 子任务数据查询
	@RequestMapping(value = "/mainTasks/{id}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
		Map<String, Object> smap = new HashMap<String, Object>();
		try {
			smap = monthTaskService.getMainTaskForUpdate(id);

			return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
			smap.put("code", "1");
			return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * 主任务添加
	 * 
	 * @param regionId
	 * @param taskId
	 * @param model
	 * @return
	 */
	@RequestMapping("/addTask")
	public String addTask(String regionId, String taskId, Model model) {

		if (regionId == null || regionId.isEmpty()) {
			regionId = "0";
		}
		Set<SalesMan> salesSet = new HashSet<SalesMan>();
		Region region = monthTaskService.getRegion(regionId);
		salesSet.addAll(salesRep.readAllByRegionId(region.getId()));
		model.addAttribute("salesList", salesSet);
		model.addAttribute("region", region);
		model.addAttribute("taskId", taskId);
		return "month/month_task_add";
	}

	/**
	 * 子任务数据查询页面
	 * 
	 * @param task
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/findupTask/{id}", method = RequestMethod.GET)
	public String findupTask(@PathVariable("id") MonthTask task, Model model) {
		try {
			model.addAttribute("taskId", task.getId());
			monthTaskService.findSalesMan(task, model);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
		}

		return "month/month_task_findup";
	}

	/**
	 * 具体的子任务数据查询
	 * 
	 * @param parentId
	 * @param goals
	 * @param pageable
	 * @param parameters
	 * @return
	 */
	@RequestMapping(value = "/subTasks", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> findSubtask(@RequestParam("parentId") Long parentId, Integer goals,
			Pageable pageable, @RequestParam MultiValueMap<String, String> parameters) {
		Map<String, Object> smap = new HashMap<String, Object>();
		try {
			smap = monthTaskService.findTaskSub(parentId, goals, pageable, parameters);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
		}
		return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.OK);

	}

	/**
	 * 子任务执行情况查询
	 * 
	 * @param shopId
	 * @param month
	 * @param subTaskId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public String getTaskDetail(@RequestParam Long shopId, @RequestParam String month, @RequestParam Long subTaskId,
			Model model) {
		try {
			monthTaskService.findTaskExecut(shopId, month, subTaskId, model);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
		}
		return "month/month_task_detail";

	}

	/**
	 * 月任务扣罚页面
	 * 
	 * @param subTaskId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/punish", method = RequestMethod.GET)
	public String punish(Long subTaskId, Model model) {
		try {
			Subject subject = SecurityUtils.getSubject();
			User user = (User) subject.getPrincipal();
			Manager manager = managerService.getById(user.getId());
			model.addAttribute("regionId", manager.getRegion().getId());
			model.addAttribute("punishObj", monthPunishRep.findById(0));
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
		}
		return "month/punish_set";
	}

	/**月任务扣罚数据查询
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "/punish/data", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> punishData(Pageable page) {
		Map<String, Object> smap = new HashMap<String, Object>();
		try {
			smap = monthTaskService.getPunishData(page);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
		}
		return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.OK);
	}

	/**月任务扣罚数据新建
	 * @param newObj
	 * @return
	 */
	@RequestMapping(value = "/punish", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createPunish(MonthTaskPunish newObj) {
		Map<String, Object> smap = new HashMap<String, Object>();
		try {
			newObj.setRegionName(monthTaskService.getAllName(regoinRep.findById(newObj.getRegionId()), ""));
			monthTakPuRep.save(newObj);
		} catch (Exception e) {
			e.printStackTrace();
			log.debug(e);
			return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(smap, HttpStatus.OK);

	}
}
