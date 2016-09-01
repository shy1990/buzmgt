package com.wangge.buzmgt.income.main.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.wangge.buzmgt.income.main.entity.PlanUserVo;
import com.wangge.buzmgt.income.main.service.MainPlanService;

@RestController
@RequestMapping("/mainPlanUsers")
public class MainPlanUserController {
  @Autowired
  MainPlanService mainPlanService;
  private static final String SEARCH_OPERTOR = "SC_";
  
  @RequestMapping(value="/",method=RequestMethod.GET)
  public Page<PlanUserVo> getUser(HttpServletRequest request, HttpServletResponse response, Pageable pageReq) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<PlanUserVo> page = mainPlanService.getUserpage(pageReq, searchParams);
    return page;
  }
  
}
