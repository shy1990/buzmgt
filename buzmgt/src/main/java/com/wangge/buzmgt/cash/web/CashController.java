package com.wangge.buzmgt.cash.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
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
import com.wangge.buzmgt.cash.entity.Cash;
import com.wangge.buzmgt.cash.service.CashService;

@Controller
@RequestMapping("/cash")
public class CashController {

  private static final String SEARCH_OPERTOR = "sc_";
  
  private static final Logger logger = Logger.getLogger(CashController.class);
  @Autowired
  private CashService cashService;
  
  @RequestMapping(value="",method=RequestMethod.GET)
  @ResponseBody
  public String getCashList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createDate"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<Cash> cashListAll=cashService.findAllByParams(searchParams);
    List<Cash> cashList=new ArrayList<>();
    int total=0;
    int number=pageRequest.getPageNumber();
    int size=pageRequest.getPageSize();
    for(Cash cash:cashListAll){
      if(number*size <= total && total < (number+1)*size){
        cashList.add(cash);
      }
      total++;
    };
    PageImpl<Cash> page = new PageImpl<Cash>(cashList,pageRequest,total);
    String json="";
    try { 
      json=JSON.toJSONString(page, SerializerFeature.DisableCircularReferenceDetect);
    }
     catch(Exception e){
       logger.error(e.getMessage());
     }
    return json;
  }

  @RequestMapping("/send")
  @ResponseBody
  public boolean send(String searchDate){
    boolean msg = false;
    try {
      logger.info("-----------------开启定时结算");
      List<String> userIds=cashService.findByStatusGroupByUserIdForSceduled(searchDate);
      userIds.forEach(userId->{
        logger.info("userId:"+userId);
        boolean msg_ = cashService.createWaterOrderByCash(userId);
        logger.info("返回结果:" + msg_);
      });
      logger.info("-----------------结束定时结算");
      return true;
    } catch (Exception e) {
      return msg;
    }
  }
  
  
  
}
