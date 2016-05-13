package com.wangge.buzmgt.cash.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.service.WaterOrderCashService;

@Controller
@RequestMapping("/waterOrder")
public class WaterOrderCashController {

  private static final String SEARCH_OPERTOR = "sc_";
  
  private static final Logger logger = Logger.getLogger(WaterOrderCashController.class);
  @Autowired
  private WaterOrderCashService waterOrderCashService;
  
  @RequestMapping(value="",method=RequestMethod.GET)
  @ResponseBody
  public String getWaterOrderCashList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createDate"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<WaterOrderCash> waterOrderPage=waterOrderCashService.findAll(searchParams, pageRequest);
    String json="";
    try { 
      json=JSON.toJSONString(waterOrderPage, SerializerFeature.DisableCircularReferenceDetect);
    }
     catch(Exception e){
       logger.error(e.getMessage());
     }
    return json;
  }
  
  @RequestMapping("/show")
  public String showWaterList(Model model,HttpServletRequest request){
    return "waterorder/list";
  }
  
  
}
