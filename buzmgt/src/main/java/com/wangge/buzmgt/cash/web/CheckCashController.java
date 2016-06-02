package com.wangge.buzmgt.cash.web;

import java.util.HashMap;
import java.util.List;
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
import com.wangge.buzmgt.cash.service.CheckCashService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.json.JSONFormat;

@Controller
@RequestMapping("/checkCash")
public class CheckCashController {

  private static final String SEARCH_OPERTOR = "sc_";

  private static final Logger logger = Logger.getLogger(CheckCashController.class);

  @Autowired
  private CheckCashService checkCashService;
  @Autowired
  private SalesManService salesManService ;

  @RequestMapping(value="/toCheckPending")
  public String toBankTrades(){
    return "cash/check_pending";
  }
  @RequestMapping(value = "", method = RequestMethod.GET)
  @JSONFormat(filterField = {"OrderSignfor.salesMan"},nonnull=true,dateFormat="yyyy-MM-dd")
  public  Page<CheckCash> getCashList(HttpServletRequest request,
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
  @RequestMapping(value="/salesmanName",method=RequestMethod.GET)
  @ResponseBody
  public String findBySalesManName(@RequestParam String salesmanName,@RequestParam String createDate){
    JSONObject json=new JSONObject();
    String jsonStr="";
    try {
      
      String userId= salesManService.findByTruename(salesmanName);
      Map<String, Object> secp=new HashMap<>();
      secp.put("EQ_userId", userId);
      secp.put("EQ_createDate", createDate);
      List<CheckCash> list = checkCashService.findAll(secp);
      json.put("content", list);
      json.put("status", "success");
      json.put("successMsg", "操作成功");
      jsonStr=JSON.toJSONString(json, SerializerFeature.DisableCircularReferenceDetect);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return jsonStr;
  }
  
  /**
   * 查询 未匹配银行交易记录
   */
  @RequestMapping(value="/unCheck",method=RequestMethod.GET)
  @ResponseBody
  public JSONObject getUnCheckBankTrades(HttpServletRequest request){
    JSONObject json=new JSONObject();
    List<BankTrade> bankTrades = checkCashService.getUnCheckBankTrades();
    json.put("content", bankTrades);
    return json;
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
  /**
   * 删除未匹配记录
   * @param bankTrade
   * @return
   */
  @RequestMapping(value="/delete/{id}",method=RequestMethod.GET)
  @ResponseBody
  public JSONObject deleteUnCheckBankTrade(@PathVariable("id") BankTrade bankTrade){
    
    return checkCashService.deleteUnCheckBankTrade(bankTrade);
  }
//  @RequestMapping(value="/export",method=RequestMethod.GET)
//  public void exportExcel(HttpServletRequest request,HttpServletResponse response,
//      @PageableDefault(page = 0, size = 10, sort = { "createDate","rnid" }, direction = Direction.DESC) Pageable pageable){
//    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
//    Page<CheckCash> page= checkCashService.findAll(searchParams, pageable);
//    checkCashService.exportSetExecl(page.getContent(),request,response);
//  }
  


}
