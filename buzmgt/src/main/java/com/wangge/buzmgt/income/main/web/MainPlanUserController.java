package com.wangge.buzmgt.income.main.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;
import com.wangge.buzmgt.income.main.entity.MainIncomePlan;
import com.wangge.buzmgt.income.main.entity.PlanUserVo;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.log.util.LogUtil;

@RestController
@RequestMapping("/mainPlanUsers")
public class MainPlanUserController {
  @Autowired
  MainPlanService mainPlanService;
  private static final String SEARCH_OPERTOR = "SC_";
  
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public Page<PlanUserVo> getUser(HttpServletRequest request, HttpServletResponse response, Pageable pageReq) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<PlanUserVo> page = mainPlanService.getUserpage(pageReq, searchParams);
    return page;
  }
  
  @RequestMapping(value = "/addUsers/{id}", method = RequestMethod.POST)
  public @ResponseBody Map<String, Object> saveUser(@PathVariable("id") MainIncomePlan plan,
       @RequestBody List<IncomeMainplanUsers> ulist, HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> remap = new HashMap<>();
    try {
      remap.putAll(mainPlanService.saveUser(plan, ulist));
    } catch (Exception e) {
      remap.put("code", "1");
      LogUtil.error("保存收益主计划人员出错", e);
    }
    return remap;
  }
  
  @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> deletePlanUser(@RequestBody IncomeMainplanUsers user, HttpServletRequest request) {
    Map<String, Object> repMap = new HashMap<String, Object>();
    repMap.put("code", "1");
    try {
      mainPlanService.deleteUser(user);
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Map<String, Object>>(repMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
