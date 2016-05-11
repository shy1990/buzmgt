package com.wangge.buzmgt.cash.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wangge.buzmgt.cash.service.WaterOrderCashService;
import com.wangge.buzmgt.receipt.web.ReceiptRemarkController;

@Controller
@RequestMapping("/waterOrder")
public class WaterOrderCashController {

  private static final String SEARCH_OPERTOR = "sc_";
  
  private static final Logger logger = Logger.getLogger(WaterOrderCashController.class);
  @Autowired
  private WaterOrderCashService waterOrderCashService;
  
  @RequestMapping(value="",method=RequestMethod.GET)
  public String getWaterOrderCashList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    
    return "";
  }
  
  
  
}
