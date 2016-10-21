package com.wangge.buzmgt.income.main.web;

import com.wangge.buzmgt.income.main.entity.MainIncomePlan;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mainPlan")
public class MainPlanController {
  @Autowired
  RegionService regionService;
  @Autowired
  MainPlanService mainPlanService;
  @Resource
  private UserService userService;
  
  @RequestMapping("/index")
  public String init(Model model) {
    model.addAttribute("regions", regionService.findByTypeOrderById(regionService.findByRegionTypeName("省")));
    return "/income/main/index";
  }
  
  @RequestMapping(value = "/queryPlan")
  public ResponseEntity<?> queryUsers(@RequestParam(name = "regionId", required = false) String regionId,
      HttpServletRequest request, HttpServletResponse response, Pageable pageReq) {
    
    return new ResponseEntity<Map<String, Object>>(mainPlanService.findAll(regionId, pageReq), HttpStatus.OK);
  }
  
  @RequestMapping(value = "/newPlan")
  public String initNew(Model model) {
    mainPlanService.assembleBeforeUpdate(model);
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    user = userService.getById(user.getId());
    model.addAttribute("organizations", user.getOrganization().getChildren());
    model.addAttribute("machineTypes", mainPlanService.getAllMachineType());
    model.addAttribute("allBrands", mainPlanService.getAllBrandType());
    return "/income/main/newPlan";
  }
  
  @RequestMapping(value = "/newPlan", method = RequestMethod.POST)
  public ResponseEntity<?> newPlan(@RequestBody MainIncomePlan plan, HttpServletRequest request,
      HttpServletResponse response) {
    Map<String, Object> repMap = new HashMap<String, Object>();
    repMap.put("code", "1");
    try {
      repMap.putAll(mainPlanService.save(plan));
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
  }
  
  @RequestMapping("update/{id}")
  public String updatePlan(@PathVariable("id") MainIncomePlan plan, HttpServletRequest request, Model model) {
    model.addAttribute("plan", plan);
    mainPlanService.assembleBeforeUpdate(model);
    
    // model.addAttribute("userList", plan.getUsers());
    return "/income/main/modifyPlan";
    
  }
  
  /**
   * getUserList:获取方案下的人员列表. <br/>
   * 
   * @author yangqc
   * @param pid
   * @param request
   * @param model
   * @return
   * @since JDK 1.8
   */
  @RequestMapping("getUserList/{id}")
  public ResponseEntity<Map<String, Object>> getUserList(@PathVariable("id") Long pid, HttpServletRequest request,
      Model model) {
    Map<String, Object> repMap = new HashMap<String, Object>();
    repMap.put("code", "1");
    try {
      List<Map<String, Object>> uList = mainPlanService.findUserList(pid);
      repMap.put("data", uList);
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);
    } catch (Exception e) {
      LogUtil.error("查询出错任务", e);
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
  }
  
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> deletePlan(@PathVariable("id") MainIncomePlan plan,
      HttpServletRequest request) {
    Map<String, Object> repMap = new HashMap<String, Object>();
    repMap.put("code", "1");
    try {
      mainPlanService.delete(plan);
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> getPlan(@PathVariable("id") MainIncomePlan plan,
      HttpServletRequest request) {
    Map<String, Object> repMap = new HashMap<String, Object>();
    repMap.put("code", "1");
    try {
      List<Map<String, Object>> uList = mainPlanService.findUserList(plan.getId());
      repMap.put("data", uList);
      repMap.put("id", plan.getId());
      repMap.put("mainTitle", plan.getMaintitle());
      repMap.put("subTitle", plan.getSubtitle());
      repMap.put("regionName", plan.getRegionname());
      
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
   * 根据id获取主方案
   *
   * @param plan
   * @return MainIncomePlan
   *//*
     * @RequestMapping(value = "/dateRange/{id}")
     * 
     * @ResponseBody public MainIncomePlan getCashById(@PathVariable("id")
     * MainIncomePlan plan) { return plan; }
     */
}
