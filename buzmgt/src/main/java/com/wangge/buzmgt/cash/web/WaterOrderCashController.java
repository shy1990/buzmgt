package com.wangge.buzmgt.cash.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;
import com.wangge.buzmgt.cash.service.WaterOrderCashService;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.util.excel.MapedExcelExport;
import com.wangge.json.JSONFormat;

@Controller
@RequestMapping("/waterOrder")
public class WaterOrderCashController {

  private static final String SEARCH_OPERTOR = "sc_";

  private static final Logger logger = Logger.getLogger(WaterOrderCashController.class);
  @Autowired
  private WaterOrderCashService waterOrderCashService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  // @ResponseBody
  @JSONFormat(filterField = { "OrderSignfor.salesMan" }, nonnull = true, dateFormat = "yyyy-MM-dd HH:mm")
  public Page<WaterOrderCash> getWaterOrderCashList(HttpServletRequest request,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<WaterOrderCash> waterOrderPage = waterOrderCashService.findAll(searchParams, pageRequest);
    return waterOrderPage;
  }

  @RequestMapping("/show")
  public String showWaterList(Model model, HttpServletRequest request) {
    String serialNo = request.getParameter("serialNo");
    model.addAttribute("serialNo", serialNo);
    return "waterorder/list";
  }

  @RequestMapping("/export")
  public void exportWaterOrderCash(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<WaterOrderCash> waterOrders = waterOrderCashService.findAll(searchParams);

    List<Map<String, Object>> alList = new ArrayList<Map<String, Object>>();
    // 有序插入
    Map<String, Integer> sumMap = new LinkedHashMap<>();
    waterOrders.forEach(waterOrder -> {
      String serialNo = waterOrder.getSerialNo();
      List<WaterOrderDetail> detail = new ArrayList<>();
      detail = waterOrder.getOrderDetails();
      detail.forEach(item -> {
        Map<String, Object> objMap = new HashMap<>();
        OrderSignfor order = new OrderSignfor();
        order = item.getCash().getOrder();
        objMap.put("serialNo", serialNo);
        objMap.put("orderNo", order.getOrderNo());
        objMap.put("orderPrice", order.getOrderPrice());
        objMap.put("actualPayNum", order.getActualPayNum());
        objMap.put("cashMoney", waterOrder.getCashMoney());
        objMap.put("status", waterOrder.getPayStatus());
        objMap.put("createDate", waterOrder.getCreateDate());
        alList.add(objMap);

        Integer sum = sumMap.get(serialNo);
        if (null == sum) {
          sumMap.put(serialNo, 1);
        } else {
          sumMap.put(serialNo, sum + 1);
        }

      });

    });
    List<Map<String, Object>> marginList = new ArrayList<Map<String, Object>>();
    int start = 0;
    int end = 0;
    for (Map.Entry<String, Integer> entry : sumMap.entrySet()) {
      // 流水单号合并单元格
      Map<String, Object> obMap = new HashMap<String, Object>();
      /*
       * int firstRow, int lastRow, int firstCol, int lastCol)
       */
      end = start + entry.getValue();
      if (entry.getValue() > 1) {
        obMap.put("firstRow", start + 1);
        obMap.put("lastRow", end);
        obMap.put("firstCol", 0);
        obMap.put("lastCol", 0);
        marginList.add(obMap);
        // 总金额合并
        Map<String, Object> obMap1 = new HashMap<String, Object>();
        obMap1.put("firstRow", start + 1);
        obMap1.put("lastRow", end);
        obMap1.put("firstCol", 4);
        obMap1.put("lastCol", 4);
        marginList.add(obMap1);

        Map<String, Object> obMap2 = new HashMap<String, Object>();
        obMap2.put("firstRow", start + 1);
        obMap2.put("lastRow", end);
        obMap2.put("firstCol", 5);
        obMap2.put("lastCol", 5);
        marginList.add(obMap2);

        Map<String, Object> obMap3 = new HashMap<String, Object>();
        obMap3.put("firstRow", start + 1);
        obMap3.put("lastRow", end);
        obMap3.put("firstCol", 6);
        obMap3.put("lastCol", 6);
        marginList.add(obMap3);
      }
      start = end;
    }
	  String[] gridTitles_ = { "流水单号", "订单编号", "订单金额", "实际金额", "总金额",  "状态", "日期" };
	  String[] coloumsKey_ = { "serialNo", "orderNo", "orderPrice", "actualPayNum", "cashMoney", "status", "createDate" };
    MapedExcelExport.doExcelExport("流水单号.xls", alList, gridTitles_, coloumsKey_, request, response, marginList);
  }

}
