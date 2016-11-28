package com.wangge.buzmgt.receipt.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.wangge.buzmgt.cash.entity.Cash;
import com.wangge.buzmgt.cash.service.CashService;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderItemService;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;
import com.wangge.buzmgt.receipt.service.OrderReceiptService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.rejection.entity.Rejection;
import com.wangge.buzmgt.rejection.service.RejectionServive;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.excel.ExcelExport;
import com.wangge.json.JSONFormat;

@Controller
@RequestMapping(value="/receiptRemark")
public class ReceiptRemarkController {
  
  private static final String SEARCH_OPERTOR = "sc_";
  
  private static final Logger logger = Logger.getLogger(ReceiptRemarkController.class);
  @Autowired
  private OrderReceiptService orderReceiptService; 
  @Autowired
  private OrderSignforService orderSignforService;
  @Autowired
  private ManagerService managerService;
  @Autowired
  private SalesManService salesManService;
  @Autowired
  private OrderItemService itemService;
  @Autowired
  private RejectionServive rejectionServive;
  @Autowired
  private CashService cashService;
  
  
  @RequestMapping(value="/show",method=RequestMethod.GET)
  public String showReceiptRemark(Model model){
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    model.addAttribute("regionName", manager.getRegion().getName());
    model.addAttribute("regionId", manager.getRegion().getId());
    model.addAttribute("regionType", manager.getRegion().getType());
    return "receipt/receipt_remark";
  }
  /**
   * 选择区域后回调方法
   * @param model
   * @param region
   * @return
   */
  @RequestMapping("getRemarkList/{regionId}")
  public String getRemarkByRegion(Model model,@PathVariable(value="regionId")Region region){
    model.addAttribute("regionName",region.getName());
    model.addAttribute("regionId",region.getId());
    model.addAttribute("regionType",region.getType());
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
  @JSONFormat(filterField={"SalesMan.user","SalesMan.region"},nonnull=true)
  public Page<ReceiptRemark> getReceiptRemarkList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<ReceiptRemark> receiptRemarkList=orderReceiptService.getReceiptRemarkList(searchParams, pageRequest);
    return receiptRemarkList;
  }
  /**
   * 收现金列表
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value="/cash")
  @JSONFormat(filterField = {"SalesMan.user","SalesMan.region"},nonnull=true,dateFormat="yyyy-MM-dd")  
  public String getCashList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_orderPayType", "CASH");
    List<OrderSignfor> cashListAll=orderReceiptService.getCashList(searchParams);
    List<OrderSignfor> cashList = new ArrayList<>();
    cashList.forEach(list->{
      list.getSalesMan().setUser(null);
      list.getSalesMan().setRegion(null);
    });
    int total=0;
    int number=pageRequest.getPageNumber();
    int size=pageRequest.getPageSize();
    for(OrderSignfor cash:cashListAll){
      if(number*size <= total && total < (number+1)*size){
        cashList.add(cash);
      }
      total++;
    };
    PageImpl<OrderSignfor> page = new PageImpl<OrderSignfor>(cashList,pageRequest,total);
    String json="";
    try {
      json=JSON.toJSONString(page, SerializerFeature.DisableCircularReferenceDetect);
    }
    catch(Exception e){
      logger.error(e.getMessage());
    }
    return json;
  }
  /**
   * s收现金详情
   * @param request
   * @param orderSignfor
   * @return
   */
  @RequestMapping(value="/cash/{orderId}")
  public String getCashById(Model model ,@PathVariable("orderId") OrderSignfor orderSignfor , 
      HttpServletRequest request){
    //绑定数据订单详情
    itemService.disposeOrderSignfor(orderSignfor);
    model.addAttribute("order", orderSignfor);
    return "receipt/receipt_order_det";
  }
  /**
   * 报备列表
   * @param request
   * @param receiptRemark
   * @return
   */
  @RequestMapping(value="/remarkList/{orderId}")
  public String getReceiptRemarkList(Model model ,@PathVariable("orderId") ReceiptRemark receiptRemark , HttpServletRequest request){
    if(receiptRemark.getSalesMan()==null){
      receiptRemark.setSalesMan(new SalesMan());
    }
    String orderNo=receiptRemark.getOrderno();
    OrderSignfor order= orderSignforService.findByOrderNo(orderNo);
    //绑定数据订单详情
    itemService.disposeOrderSignfor(order);
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
    String[] gridTitles = { "业务名称","店铺名称","订单号","理由","报备时间"};
    String[] coloumsKey = { "salesMan.truename","shopName", "orderno",
          "remark","createTime"};
//    String[] gridTitles = { "业务名称","店铺名称","订单号", "金额","理由","报备时间","付款时间"};
//    String[] coloumsKey = { "salesMan.truename","shopName", "orderno", "order.orderPrice",
//        "remark","createTime", "order.yewuSignforTime"};

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
        String phoneNum= list.getPhoneCount().toString();
        String partsNum= String.valueOf(list.getPartsCount());
        list.setGoodNum("手机 "+phoneNum+"部，配件"+partsNum+"件");
      });
      String[] gridTitles_1 = {"订单号", "店铺名称","业务名称", "商品数量","下单时间","订单金额","实际金额","订单状态","订单流转时间","客户付款状态","客户付款时间","代理商付款状态","代理商付款时间"};
      String[] coloumsKey_1 = { "orderNo", "shopName", "salesMan.truename", "goodNum","createTime",
              "orderPrice","actualPayNum","orderRoamStatus","roamTime","orderPayType","overTime","agentPayStatus","agentPayTime"};
      ExcelExport.doExcelExport("订单导出"+(searchParams.get("GTE_createTime")+"~"+searchParams.get("LTE_createTime"))+".xls", 
          allOrderList, gridTitles_1, coloumsKey_1, request, response);
      
      break;
    case "cash":
      String[] gridTitles_2 = { "业务名称","店铺名称","订单号", "金额","收现金时间","发货时间","业务签收时间","打款时间"};
      String[] coloumsKey_2 = { "order.salesMan.truename","order.shopName", "order.orderNo", "order.orderPrice","createDate",
          "order.fastmailTime","order.yewuSignforTime","payDate"};
      String startTime=(String) searchParams.get("GTE_createTime");
      String endTime=(String) searchParams.get("LTE_createTime");
      searchParams.remove("GTE_createTime");
      searchParams.remove("LTE_createTime");
      if(!"".equals(startTime) || !"".equals(endTime)){
        searchParams.put("GTE_createDate", startTime);
        searchParams.put("LTE_createDate", endTime);
      }
      List<Cash> cashListAll=cashService.findAll(searchParams);
      
      ExcelExport.doExcelExport("收现金导出"+(startTime+"~"+endTime)+".xls", 
          cashListAll, gridTitles_2, coloumsKey_2, request, response);
      break;
    //拒收导出
    case "rejected":
        List<Rejection> rejectionList = rejectionServive.findAll(searchParams);
      String[] rejectTitles_ = { "业务名称","商家名称","订单号","预计到达时间", "寄回物流单号","拒收原因","拒收时间"};
      String[] rejectKey_ = { "salesMan.truename","shopName", "orderno", "arriveTime","trackingno","remark","createTime"};
      ExcelExport.doExcelExport("拒收导出.xls", rejectionList, rejectTitles_, rejectKey_, request, response);
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
        action.getSalesMan().setRegion(null);
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
  @RequestMapping(value="/notRemarkList/{orderNo}",method=RequestMethod.GET)
  public String getNotRemarkByOrderNo(@PathVariable("orderNo") String orderNo,Model model,
      HttpServletRequest request){
    Map<String, Object> searchParams = new HashMap<String, Object>();
    searchParams.put("EQ_orderNo", orderNo);
    try {
      List<OrderSignfor> notRemarkList = orderReceiptService.getReceiptNotRemark(searchParams);
      OrderSignfor notRemark=notRemarkList.get(0);
      //绑定数据订单详情
      itemService.disposeOrderSignfor(notRemark);
      String userId=notRemark.getUserId();
      SalesMan s=salesManService.findByUserId(userId);
      notRemark.setSalesMan(s);
      model.addAttribute("order", notRemark);
    }
    catch(Exception e){
      logger.error(e.getMessage());
    }
    return "receipt/receipt_order_det";
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
