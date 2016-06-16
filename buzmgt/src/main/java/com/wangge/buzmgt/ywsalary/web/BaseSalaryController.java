package com.wangge.buzmgt.ywsalary.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.excel.ExcelExport;
import com.wangge.buzmgt.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.ywsalary.entity.BaseSalaryUser;
import com.wangge.buzmgt.ywsalary.service.BaseSalaryService;
import com.wangge.json.JSONFormat;

@Controller
@RequestMapping("/baseSalary")
public class BaseSalaryController {

  @Resource
  private BaseSalaryService baseSalaryService;
  @Resource
  private ManagerService managerService;

  private static final String SEARCH_OPERTOR = "sc_";

  private static final Logger logger = Logger.getLogger(BaseSalaryController.class);

  /**
   * 列表展示页面+regionId
   * @param region
   * @param model
   * @return
   */
  @RequestMapping("/show")
  public String toBaseSalary(@RequestParam(value = "regionId", required = false) Region region, Model model) {
    List<BaseSalaryUser> salaryUsers = baseSalaryService.getStaySetSalesMan();
    model.addAttribute("salaryUsers", salaryUsers);
    if (region != null) {
      model.addAttribute("regionName", region.getName());
      model.addAttribute("regionId", region.getId());
      model.addAttribute("regionType", region.getType());
    }else{
      Subject subject = SecurityUtils.getSubject();
      User user=(User) subject.getPrincipal();
      Manager manager = managerService.getById(user.getId());
      model.addAttribute("regionName", manager.getRegion().getName());
      model.addAttribute("regionId", manager.getRegion().getId());
      model.addAttribute("regionType", manager.getRegion().getType());
    }
    return "ywsalary/base_salary";
  }

  /**
   * 获取数据
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.GET)
  @JSONFormat(filterField = { "Region.children", "Region.parent",
      "SalesMan.user" }, nonnull = true, dateFormat = "yyyy-MM-dd HH:mm")
  public Page<BaseSalary> getBaseSalarys(HttpServletRequest request,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<BaseSalary> page = baseSalaryService.findAll(searchParams, pageRequest);

    return page;
  }
  /**
   * 导出列表
   * @param request
   * @param response
   */
  @RequestMapping(value = "/export", method = RequestMethod.GET)
  @JSONFormat(filterField = { "Region.children", "Region.parent",
  "SalesMan.user" }, nonnull = true, dateFormat = "yyyy-MM-dd HH:mm")
  public void export(HttpServletRequest request , HttpServletResponse response) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    String[] gridTitles = { "业务名称","负责区域","基础薪资", "日期"};
    String[] coloumsKey = { "user.truename","user.region.name", "salary", "updateDate"};
    List<BaseSalary> list= baseSalaryService.findAll(searchParams);
    ExcelExport.doExcelExport("业务员基础薪资表.xls", list, gridTitles, coloumsKey, request, response);
  }

  /**
   * 添加数据
   * @param baseSalary
   * @return
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject addSalary(BaseSalary baseSalary) {
    JSONObject json = new JSONObject();
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

  /**
   * 修改薪资
   * @param baseSalary
   * @param salary
   * @return
   */
  @RequestMapping(value = "/{Id}", method = RequestMethod.PUT)
  @ResponseBody
  public JSONObject updateSalary(@PathVariable("Id") BaseSalary baseSalary, Float salary) {
    // Float salary = Float.valueOf(request.getParameter("salary"));
    JSONObject json = new JSONObject();
    if (baseSalary == null) {
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    try {
      if (salary != null) {
        baseSalary.setSalary(salary);
      }
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

  /**
   * 
   * @param baseSalary
   * @param request
   * @return
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  @ResponseBody
  public JSONObject deleteSalary(@PathVariable("Id") BaseSalary baseSalary, HttpServletRequest request) {
    JSONObject json = new JSONObject();
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
