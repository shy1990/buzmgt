package com.wangge.buzmgt.ywsalary.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.ywsalary.entity.BaseSalaryUser;
import com.wangge.buzmgt.ywsalary.service.BaseSalaryService;
import com.wangge.json.JSONFormat;

@Controller
@RequestMapping("/baseSalary")
public class BaseSalaryController {

  @Resource
  private BaseSalaryService baseSalaryService;

  private static final String SEARCH_OPERTOR = "sc_";
  
  private static final Logger logger=Logger.getLogger(BaseSalaryController.class);

  @RequestMapping("show")
  public String toBaseSalary(Model model) {
    List<BaseSalaryUser> salaryUsers=baseSalaryService.getStaySetSalesMan();
    model.addAttribute("salaryUsers", salaryUsers);
    return "ywsalary/base_salary";
  }

  @RequestMapping(name = "", method = RequestMethod.GET)
  @JSONFormat(filterField = {"SalesMan.region","SalesMan.user"},nonnull=true,dateFormat="yyyy-MM-dd HH:mm")
  public Page<BaseSalary> getBaseSalarys(HttpServletRequest request,
      @PageableDefault(page = 0, size = 10) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<BaseSalary> page = baseSalaryService.findAll(searchParams, pageRequest);

    return page;
  }
  @RequestMapping(name="",method=RequestMethod.POST)
  public JSONObject addSalary(BaseSalary baseSalary){
    JSONObject json=new JSONObject();
    try {
      baseSalaryService.save(baseSalary);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      logger.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  @RequestMapping(name="/{Id}",method=RequestMethod.PUT)
  public JSONObject updateSalary(@PathVariable("Id") BaseSalary baseSalary,HttpServletRequest request){
    
    JSONObject json=new JSONObject();
    if(baseSalary==null){
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    try {
      baseSalaryService.save(baseSalary);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      logger.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  
  
  @RequestMapping(name="/{id}",method=RequestMethod.DELETE)
  public JSONObject deleteSalary(@PathVariable("Id") BaseSalary baseSalary,HttpServletRequest request){
    JSONObject json=new JSONObject();
    try {
      baseSalaryService.delete(baseSalary);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      logger.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  
  

}
