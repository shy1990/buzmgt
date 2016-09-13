package com.wangge.buzmgt.income.main.web;

import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.excel.ExcelExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mainIncome")
public class MainIncomeController {
  @Autowired
  private MainIncomeService incomeService;
  @Autowired
  RegionService regionService;
  private static final String SEARCH_OPERTOR = "SC_";
  
  @RequestMapping("/index")
  public String goIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
    model.addAttribute("regions", regionService.findByTypeOrderById(regionService.findByRegionTypeName("省")));
    return "/income/main/income";
  }
  
  @RequestMapping("/getVoPage")
  public @ResponseBody Page<MainIncomeVo> getIncomePage(HttpServletRequest request, HttpServletResponse response,
      Pageable pageReq) throws Exception {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<MainIncomeVo> page = incomeService.getVopage(pageReq, searchParams);
    return page;
  }
  
  @RequestMapping(value = "/export", method = RequestMethod.GET)
  public void export(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
      String month = searchParams.get("EQ_month") + "";
      month = month.equals("") ? DateUtil.getPreMonth(new Date(), -1) : month;
      searchParams.put("EQ_month", month);
      String[] gridTitles = { "业务员", "负责区域", "业务角色", "月份", " 基本工资", "业务佣金",
          "油补佣金", "扣罚金额", "达量收入", "叠加收入", "总收入","是否审核" };
      String[] coloumsKey = { "truename", "namepath", "rolename", "month", "basicSalary", 
          "busiIncome", "oilIncome",
          "punish", "reachIncome", "overlyingIncome", "allresult","state" };
      List<MainIncomeVo> list = incomeService.findAll(searchParams);
      ExcelExport.doExcelExport(month + "业务员工资表.xls", list, gridTitles, coloumsKey, request, response);
    } catch (Exception e) {
      LogUtil.error("导出工资表出错", e);
      throw new Exception("导出工资表出错");
    }
  }
}
