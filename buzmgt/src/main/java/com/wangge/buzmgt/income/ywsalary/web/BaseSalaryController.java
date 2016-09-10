package com.wangge.buzmgt.income.ywsalary.web;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalaryUser;
import com.wangge.buzmgt.income.ywsalary.service.BaseSalaryService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.MapedExcelExport;

@Controller
@RequestMapping("/baseSalary")
public class BaseSalaryController {
  
  @Resource
  private BaseSalaryService baseSalaryService;
  @Resource
  private ManagerService managerService;
  @Resource
  private SalesManService salesManService;
  @Autowired
  RegionService regionService;
  private static final String SEARCH_OPERTOR = "sc_";
  
  /**
   * 列表展示页面+regionId
   * 
   * @param region
   * @param model
   * @return
   */
  @RequestMapping("/show")
  public String toBaseSalary(HttpServletRequest request, Model model) {
    List<BaseSalaryUser> salaryUsers = baseSalaryService.getStaySetSalesMan();
    model.addAttribute("salaryUsers", salaryUsers);
    model.addAttribute("salesId", request.getParameter("salesmanId"));
    model.addAttribute("month", request.getParameter("month"));
    model.addAttribute("regions", regionService.findByTypeOrderById(RegionType.PROVINCE));
    return "ywsalary/base_salary";
  }
  
  /**
   * 获取数据
   * 
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<Map<String, Object>> getBaseSalarys(HttpServletRequest request,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    return new ResponseEntity<Map<String, Object>>(baseSalaryService.findAll(searchParams, pageRequest), HttpStatus.OK);
  }
  
  /**
   * 导出列表
   * 
   * @param request
   * @param response
   */
  @RequestMapping(value = "/export", method = RequestMethod.GET)
  public void export(HttpServletRequest request, HttpServletResponse response, Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    String[] gridTitles = { "业务名称", "负责区域", "基础薪资", "日工资(元/天)", "新增日期", "是否有效" };
    String[] coloumsKey = { "userName", "region", "salary", "daySalary", "newdate", "state" };
    List<Map<String, Object>> list = baseSalaryService.findAll1(searchParams, pageRequest);
    MapedExcelExport.doExcelExport("业务员基础" + DateUtil.getPreMonth(new Date(), 0) + "薪资表.xls", list, gridTitles,
        coloumsKey, request, response, null);
  }
  
  /**
   * 添加数据
   * 
   * @param baseSalary
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject addSalary(@RequestBody List<BaseSalary> baseList) {
    JSONObject json = new JSONObject();
    baseList.forEach((baseSalary) -> {
      try {
        baseSalaryService.save(baseSalary);
        json.put("status", "success");
        json.put("successMsg", "操作成功！");
        
      } catch (Exception e) {
        LogUtil.info(e.getMessage());
        json.put("status", "failure");
        json.put("errorMsg", "操作失败！");
      }
    });
    return json;
  }
  
  /**
   * 修改薪资
   * 
   * @param baseSalary
   * @param salary
   * @return
   */
  @RequestMapping(value = "/{Id}", method = RequestMethod.PUT)
  @ResponseBody
  public JSONObject updateSalary(@PathVariable("Id") BaseSalary baseSalary, Double salary,String upDate) {
    JSONObject json = new JSONObject();
    
    try {
      
      baseSalaryService.update(baseSalary, salary,upDate);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  
}
