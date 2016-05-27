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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.CheckCash;
import com.wangge.buzmgt.cash.service.BankTradeService;
import com.wangge.buzmgt.cash.service.CheckCashService;

@Controller
@RequestMapping("/checkCash")
public class CheckCashController {

  private static final String SEARCH_OPERTOR = "sc_";

  private static final Logger logger = Logger.getLogger(CheckCashController.class);

  @Autowired
  private CheckCashService checkCashService;

  @RequestMapping(value="/toCheckPending")
  public String toBankTrades(){
    return "cash/check_pending";
  }
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public Page<CheckCash> getCashList(HttpServletRequest request,
      @PageableDefault(page = 0, size = 10, sort = { "createDate","rnid" }, direction = Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<CheckCash> page= checkCashService.findAll(searchParams, pageable);
//    String json = "";
//    try {
//      json = JSON.toJSONString(page, SerializerFeature.DisableCircularReferenceDetect);
//    } catch (Exception e) {
//      e.printStackTrace();
//      logger.error(e.getMessage());
//    }
    return page;
  }
  
  /**
   * 查询 未匹配银行交易记录
   */
  @RequestMapping(value="unCheck",method=RequestMethod.GET)
  public JSONObject getUnCheckBankTrades(HttpServletRequest request){
//    return checkCashService.getUnCheckBankTrades();
    return null;
  }
  /**
   * 审核账单（打款和流水单号）
   * @return
   */
  @RequestMapping(value="/{userId}",method=RequestMethod.POST)
  @ResponseBody
  public JSONObject auditByUserIdAndCreateDate(@PathVariable("userId") String userId,
      @RequestParam(value="createDate") String createDate){
    
    return checkCashService.checkPendingByUserIdAndCreateDate(userId, createDate);
  }


}
