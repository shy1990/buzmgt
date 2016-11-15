package com.wangge.buzmgt.importExcel.web;

import com.wangge.buzmgt.importExcel.service.CacelOrderService;
import com.wangge.buzmgt.importExcel.entity.CacelOrder;
import com.wangge.buzmgt.util.excel.ExcelExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 取消订单商家
 */
@Controller
@RequestMapping("/export")
public class CacelOrderController {
  @Autowired
  private CacelOrderService cacelOrderService;

  @RequestMapping(value = "/toCacelOrder")
  public String toCacelOrder(){
    return "excel/cacel_order_Ex";
  }

  /**
   * @param request
   * @param response
   * @param @return  设定文件
   * @return void
   * @throws
   * @Title: exportCacelOrder
   * @Description: 导出取消订单的商家到excel
   */
  @RequestMapping("/cacelOrder")
  public void exportCacelOrder(HttpServletRequest request, HttpServletResponse response) {
    List<CacelOrder> list = cacelOrderService.findAll();
    String[] gridTitles_1 = {"订单号", "店铺名称", "真实姓名", "订单创建日期", "取消订单数量(次)", "再次下单数量(次)"};
    String[] coloumsKey_1 = {"orderNum", "userName", "trueName", "createTime", "cacelSum", "orderSum"};
    ExcelExport.doExcelExport("昨天取消订单商家导出表.xls", list, gridTitles_1, coloumsKey_1, request, response);
  }
}