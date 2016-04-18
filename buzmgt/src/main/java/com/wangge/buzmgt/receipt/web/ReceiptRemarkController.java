package com.wangge.buzmgt.receipt.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;
import com.wangge.buzmgt.receipt.service.OrderReceiptService;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.util.ExcelExport;

@Controller
@RequestMapping(value="/receiptRemark")
public class ReceiptRemarkController {
  
  private static final String SEARCH_OPERTOR = "sc_";
  
  private static final Logger logger = Logger.getLogger(ReceiptRemarkController.class);
  @Resource
  private OrderReceiptService orderReceiptService; 
  
  @Resource
  private OrderSignforService orderSignforService;
  
  @RequestMapping(value="/show",method=RequestMethod.GET)
  public String showReceiptRemark(){
    
    return "receipt/receipt_remark";
  }
  @RequestMapping(value="/allOrderList",method=RequestMethod.GET)
  public String showAllOrderList(){
    return "receipt/all_order_list";
  }
  /**
   * 报备列表
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value="/remarkList")
  @ResponseBody
  public String getReceiptRemarkList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<ReceiptRemark> receiptRemarkList=orderReceiptService.getReceiptRemarkList(searchParams, pageRequest);
    receiptRemarkList.getContent().forEach(list->{
      list.getSalesMan().setUser(null);
      list.getSalesMan().setRegion(null);
    });
    String json="";
    try { 
      json=JSON.toJSONString(receiptRemarkList, SerializerFeature.DisableCircularReferenceDetect);
    }
     catch(Exception e){
       logger.error(e.getMessage());
     }
    return json;
  }
  /**
   * 报备列表
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value="/remarkList/{orderId}")
  public String getReceiptRemarkList(Model model ,@PathVariable("orderId") ReceiptRemark receiptRemark , HttpServletRequest request){
    if(receiptRemark.getSalesMan()==null){
      receiptRemark.setSalesMan(new SalesMan());
    }
    String orderNo=receiptRemark.getOrderno();
    OrderSignfor order= orderSignforService.findByOrderNo(orderNo);
    receiptRemark.setOrder(order);
    model.addAttribute("receiptRemark", receiptRemark);
    return "receipt/receipt_remark_det";
  }
  /**
   * 导出
   * 
   * @param request
   * @param response
   */
  @RequestMapping("/export")
  public void excelExport(HttpServletRequest request, HttpServletResponse response) {
    String[] gridTitles = { "业务名称","店铺名称","订单号", "金额","理由","报备时间","付款时间"};
    String[] coloumsKey = { "salesMan.truename","shopName", "orderno", "order.orderPrice",
          "remark","createTime", "order.yewuSignforTime"};

    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    String type=(String) request.getParameter("type");
    switch (type) {
    //报备导出
    case "reported":
      
      List<ReceiptRemark> receiptRemarkList=orderReceiptService.findAll(searchParams);
      receiptRemarkList.forEach(list->{
        if(list.getSalesMan()==null){
          list.setSalesMan(new SalesMan());
        }
        list.getSalesMan().setUser(null);
        list.getSalesMan().setRegion(null);
      });
      ExcelExport.doExcelExport("报备导出.xls", receiptRemarkList, gridTitles, coloumsKey, request, response);
      
      break;
    //未报备导出
    case "notreported":
      List<OrderSignfor> receiptNotRemarkList=orderReceiptService.getReceiptNotRemark(searchParams);
      String[] gridTitles_ = { "业务名称","店铺名称","订单号", "金额","下单时间","发货时间","业务签收时间","打款时间"};
      String[] coloumsKey_ = { "salesMan.truename","shopName", "orderNo", "orderPrice","createTime",
              "fastmailTime","yewuSignforTime","customSignforTime"};
      ExcelExport.doExcelExport("未报备导出.xls", receiptNotRemarkList, gridTitles_, coloumsKey_, request, response);
      
      break;
    
    case "allOrder":
      List<OrderSignfor> allOrderList = orderSignforService.findAll(searchParams);
      allOrderList.forEach(list->{
        if(list.getSalesMan()==null){
          list.setSalesMan(new SalesMan());
        }
        list.getSalesMan().setUser(null);
        list.getSalesMan().setRegion(null);
      });
      String[] gridTitles_1 = { "业务名称","店铺名称","订单号", "金额","下单时间","发货时间","业务签收时间","打款时间"};
      String[] coloumsKey_1 = { "salesMan.truename","shopName", "orderNo", "orderPrice","createTime",
              "fastmailTime","yewuSignforTime","customSignforTime"};
      ExcelExport.doExcelExport("订单导出("+searchParams.get("GTE_createTime")+"-"+searchParams.get("LTE_createTime")+").xls", allOrderList, gridTitles_1, coloumsKey_1, request, response);
      
      break;
    case "cash":
      //TODO 收现金导出
      break;

    default:
      break;
    }
    

  }
  
  @RequestMapping(value="/notRemarkList",method=RequestMethod.GET)
  @ResponseBody
  public String getReceiptNotRemark(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    String json="";
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<OrderSignfor> receiptRemarkList = orderReceiptService.getReceiptNotRemark(searchParams);
    List<OrderSignfor> notRemarkList = new ArrayList<OrderSignfor>();
    int total=0;
    int number=pageRequest.getPageNumber();
    int size=pageRequest.getPageSize();
    for(OrderSignfor action:receiptRemarkList){
      if(number*size <= total && total < (number+1)*size){
        notRemarkList.add(action);
      }
      total++;
    };
    PageImpl<OrderSignfor> page = new PageImpl<OrderSignfor>(notRemarkList,pageRequest,total);
    try {
      json=JSON.toJSONString(page, SerializerFeature.DisableCircularReferenceDetect);
    }
     catch(Exception e){
       logger.error(e.getMessage());
     }
    return json;
  }
  /**
   * 获取全部订单列表
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value="/getAllOrderList",method=RequestMethod.GET)
  @ResponseBody
  public String getAllOrderList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    String json="";
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<OrderSignfor> allOrderPage = orderSignforService.getOrderSingforList(searchParams, pageRequest);
    allOrderPage.getContent().forEach(osfl->{
      if(osfl.getSalesMan()==null){
        osfl.setSalesMan(new SalesMan());
      }
      osfl.getSalesMan().setUser(null);
      osfl.getSalesMan().setRegion(null);
    });
    try {
      json=JSON.toJSONString(allOrderPage, SerializerFeature.DisableCircularReferenceDetect);
    }
    catch(Exception e){
      logger.error(e.getMessage());
    }
    return json;
  }
}
