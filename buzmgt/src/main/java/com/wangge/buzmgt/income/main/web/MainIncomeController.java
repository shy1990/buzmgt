package com.wangge.buzmgt.income.main.web;

import java.util.Date;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.vo.BusinessSalaryVo;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.income.schedule.service.JobService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.monthtask.service.MonthTaskService;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.superposition.service.SuperpositonService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.excel.ExcelExport;

@Controller
@RequestMapping("/mainIncome")
public class MainIncomeController {
  @Autowired
  private MainIncomeService incomeService;
  @Autowired
  RegionService regionService;
  @Autowired
  SuperpositonService superService;
  @Autowired
  JobService jobService;
  @Autowired
  AchieveIncomeService achieveService;
  @Autowired
  MonthTaskService monthService;
  
  private static final String SEARCH_OPERTOR = "SC_";
  
  @RequestMapping("/index")
  public String goIndex(Model model, HttpServletRequest request, HttpServletResponse response) {
    model.addAttribute("regions", regionService.findByTypeOrderById(regionService.findByRegionTypeName("省")));
    return "/income/main/income";
  }
  
  @RequestMapping("/index/check")
  public String initCheck(Model model, HttpServletRequest request, HttpServletResponse response) {
    model.addAttribute("regions", regionService.findByTypeOrderById(regionService.findByRegionTypeName("省")));
    model.addAttribute("check", 1);
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
      String[] gridTitles = { "业务员", "负责区域", "业务角色", "月份", " 基本工资", "业务佣金", "油补佣金", "扣罚金额", "达量收入", "叠加收入", "总收入",
          "是否审核" };
      String[] coloumsKey = { "truename", "namepath", "rolename", "month", "basicSalary", "rbusiSal", "oilIncome",
          "punish", "reachIncome", "overlyingIncome", "allresult", "state" };
      List<MainIncomeVo> list = incomeService.findAll(searchParams);
      ExcelExport.doExcelExport(month + "业务员工资表.xls", list, gridTitles, coloumsKey, request, response);
    } catch (Exception e) {
      LogUtil.error("导出工资表出错", e);
      throw new Exception("导出工资表出错");
    }
  }
  
  @RequestMapping(value = "/fh", method = RequestMethod.POST)
  public ResponseEntity<Map<String, Object>> fh(@RequestParam("id") Long id) {
    Map<String, Object> hmap = new HashMap<>();
    try {
      incomeService.check(id);
      return new ResponseEntity<Map<String, Object>>(hmap, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<Map<String, Object>>(hmap, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
  
  /**
   * 实例url http://192.168.2.179:8080/mainIncome/calcuPayed?payDate=2016-09-29
   * 15:30:16&orderNo=20160620174259875&userId=asdad&regionId=370184168
   * 
   * @param payDate
   *          支付时间
   * @param orderNo
   *          订单号
   * @param userId
   *          用户id
   * @param regionId
   *          区域id
   * @return
   * @throws Exception
   * @since JDK 1.8
   */
  @RequestMapping("/calcuPayed")
  public @ResponseBody String calPayedOrder(@RequestParam String orderNo, @RequestParam String userId,
      @RequestParam String regionId) throws Exception {
    incomeService.caculatePayedOrder(orderNo, userId, regionId);
    return "ok";
  }
  
  @RequestMapping("/test")
  public @ResponseBody void test() {
    try {
      // superService.compute(20L,2L);
      // achieveService.calculateAchieveIncomeTotal(20L, 12L);
      // jobService.doTask();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @RequestMapping("/businessList")
  public String InitBusiPage(@RequestParam("id") MainIncome mainIncome, String userId, String month, Model model) {
    model.addAttribute("mainIncome", mainIncome);
    model.addAttribute("regions", regionService.getListByIds(mainIncome.getSalesman()));
    monthService.findSalaryAndAcess(mainIncome.getSalesman(), model);
    return "/income/sub/businessSalary";
  }
  
  @RequestMapping("/businessList/findVolist")
  public @ResponseBody Page<BusinessSalaryVo> findVoList(HttpServletRequest request, HttpServletResponse response,
      Pageable pageReq) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    return incomeService.findBusinessSalaryVo(searchParams, pageReq);
  }
  
  @RequestMapping(value = "/exportBusiness", method = RequestMethod.GET)
  public void exportBusiness(HttpServletRequest request, HttpServletResponse response) throws Exception {
    try {
      Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
      String month = searchParams.get("EQ_cmonth") + "";
      String[] gridTitles = { "业务员", "月份", "负责区域", "店铺名称", "订单编号", "手机总数", "订单状态", "支付状态", "佣金", "下单日期" };
      String[] coloumsKey = { "truename", "cmonth", "namepath", "shopName", "orderno", "phoneCount", "orderStatus",
          "payStatus", "income", "createTime" };
      List<BusinessSalaryVo> list = incomeService.findAllBusines(searchParams);
      String title = month + "业务员业务佣金表.xls";
      if (null != list && list.size() > 0)
        title = list.get(0).getTruename() + title;
      ExcelExport.doExcelExport(title, list, gridTitles, coloumsKey, request, response);
    } catch (Exception e) {
      LogUtil.error("导出业务佣金表出错", e);
    }
  }
  
  /**
   * 订单详情的收益
   * 
   * @param model
   * @param orderSignfor
   * @param ruleId
   * @return
   */
  @RequestMapping(value = "/detail/{orderNo}", method = RequestMethod.GET)
  public String incomeOrderDetail(Model model, @PathVariable("orderNo") String orderNo) {
    OrderSignfor orderSignfor = incomeService.disposeIncomeForOrderItem(orderNo);
    model.addAttribute("order", orderSignfor);
    return "achieve/income_order_detial";
  }
}
