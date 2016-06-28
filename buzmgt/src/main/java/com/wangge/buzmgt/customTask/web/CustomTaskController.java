package com.wangge.buzmgt.customTask.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.WebUtils;

import com.wangge.buzmgt.customTask.entity.CustomMessages;
import com.wangge.buzmgt.customTask.entity.CustomTask;
import com.wangge.buzmgt.customTask.server.CustomTaskServer;
import com.wangge.buzmgt.customTask.server.ImplCustomTaskServe;
import com.wangge.buzmgt.monthTask.service.MonthTaskService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;

///customTask/list
@Controller
@RequestMapping(value = "/customTask")
public class CustomTaskController {
	@Autowired
	CustomTaskServer customServ;
	@Resource
	MonthTaskService monthTaskService;
	@Autowired
	private SalesManRepository salesRep;
	private static final String SEARCH_OPERTOR = "SC_";
	private Log log = LogFactory.getLog(this.getClass());

	// 查询自定义任务列表
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String findAllTask(HttpServletRequest request, Model model) {
		return "customTask/index";
	}

	/**
	 * 查询数据
	 * 
	 * @param request
	 * @param pageRequest
	 * @return
	 */
	@RequestMapping("/all")
	public ResponseEntity<Map<String, Object>> findAll(HttpServletRequest request, Pageable pageRequest) {
		Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
		Map<String, Object> repMap = new HashMap<String, Object>();
		repMap.put("code", "1");
		try {
			repMap.putAll(customServ.findAll(pageRequest, searchParams));
		} catch (Exception e) {
			e.printStackTrace();
			repMap.put("code", "0");
			repMap.put("msg", e.getMessage());
			log.debug(e.getStackTrace());
			return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);

	}

	/**
	 * 查询单个任务
	 * 
	 * @param customTask
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/messages/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> pointdetail(@PathVariable("id") CustomTask customTask, Pageable pageReq,
			HttpServletRequest request) {
		Map<String, Object> repMap = new HashMap<String, Object>();
		try {
			repMap = customServ.getMessage(customTask, pageReq);
			return new ResponseEntity<Object>(repMap, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			repMap.put("code", "0");
			repMap.put("msg", "数据服务器错误");
			log.debug(e.getStackTrace());
			return new ResponseEntity<Object>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 跳转单条记录详情
	 * 
	 * @param customTask
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/details/{id}", method = RequestMethod.GET)
	public String detail(@PathVariable("id") CustomTask customTask,
			HttpServletRequest request, Model model) {
		model.addAttribute("task", customTask);
		customServ.getSaleSet(customTask, model);
		model.addAttribute("taskType", ImplCustomTaskServe.TASKTYPEARR[customTask.getType()]);
		return "customTask/detail";
	}

	/**
	 * 跳转到新建消息页面
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletRequest request, Model model) {
		Set<SalesMan> salesSet = new HashSet<SalesMan>();
		Region region = monthTaskService.getRegion(null);
		salesSet.addAll(salesRep.readAllByRegionId(region.getId()));
		model.addAttribute("salesList", salesSet);
		return "customTask/add";
	}

	/**
	 * 保存新建的消息记录
	 * 
	 * @param customTask
	 * @return
	 */
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createSub(@RequestBody CustomTask customTask) {
		Map<String, Object> repMap = new HashMap<String, Object>();
		repMap.put("code", "1");
		try {
			customServ.save(customTask);
		} catch (Exception e) {
			e.printStackTrace();
			repMap.put("code", "0");
			repMap.put("msg", e.getMessage());
			log.debug(e.getStackTrace());
			return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);

	}

	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> createSub(@RequestBody Map<String, Object> messages) {
		Map<String, Object> repMap = new HashMap<String, Object>();
		repMap.put("code", "1");
		try {
			customServ.saveMessage(messages);
		} catch (Exception e) {
			e.printStackTrace();
			repMap.put("code", "0");
			repMap.put("msg", e.getMessage());
			log.debug(e.getStackTrace());
			return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);

	}

	@RequestMapping(value = "/checkUpdate/{taskId}", method = RequestMethod.GET)
	public ResponseEntity<Object> checkUpdate(@PathVariable("taskId") Long taskId) {
		Object id = customServ.findlastId(taskId);
		return new ResponseEntity<Object>(id, HttpStatus.OK);
	}
}
