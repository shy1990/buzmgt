package com.wangge.buzmgt.receipt.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;
import com.wangge.buzmgt.receipt.service.OrderReceiptService;

@Controller
@RequestMapping(value="/receiptRemark")
public class ReceiptRemarkController {
  
  private static final String SEARCH_OPERTOR = "sc_";
  @Resource
  private OrderReceiptService orderReceiptService; 
  
  @RequestMapping(value="/show",method=RequestMethod.GET)
  public String showReceiptRemark(){
    
    return "receipt/receipt_abnormal";
  }
  
  @RequestMapping(value="/remarkList",method=RequestMethod.POST)
  @ResponseBody
  public String getReceiptRemarkList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<ReceiptRemark> receiptRemarkList=orderReceiptService.getReceiptRemarkList(searchParams, pageRequest);
    receiptRemarkList.forEach(list->{
      list.getSalesMan().setUser(null);
      list.getSalesMan().setRegion(null);
      
    });
    String json="";
    try { 
      json=JSON.toJSONString(receiptRemarkList, SerializerFeature.DisableCircularReferenceDetect);
    }
     catch(Exception e){
       System.out.println(e.getMessage());
     }
    return json;
  }
  @RequestMapping(value="notRemarkList",method=RequestMethod.POST)
  @ResponseBody
  public String getReceiptNotRemark(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"creatTime"},direction=Direction.DESC) Pageable pageRequest ){
    String json="";
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<OrderSignfor> receiptRemarkList=orderReceiptService.getReceiptNotRemark(searchParams, pageRequest);
    
    try { 
      json=JSON.toJSONString(receiptRemarkList, SerializerFeature.DisableCircularReferenceDetect);
    }
     catch(Exception e){
       System.out.println(e.getMessage());
     }
    return json;
  }
}
