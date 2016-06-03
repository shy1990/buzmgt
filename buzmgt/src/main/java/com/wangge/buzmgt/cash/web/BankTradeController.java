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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.service.BankTradeService;

@Controller
@RequestMapping("/bankTrade")
public class BankTradeController {

  private static final String SEARCH_OPERTOR = "sc_";

  private static final Logger logger = Logger.getLogger(BankTradeController.class);

  @Autowired
  private BankTradeService bankTradeService;

  @RequestMapping(value="/toBankTrades")
  public String toBankTrades(){
    return "cash/bank_import";
  }
  @RequestMapping(value = "", method = RequestMethod.GET)
  @ResponseBody
  public String getCashList(HttpServletRequest request,
      @PageableDefault(page = 0, size = 10, sort = { "createDate","id" }, direction = Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<BankTrade> bankTrades = bankTradeService.findAll(searchParams, pageable);
    String json = "";
    try {
      json = JSON.toJSONString(bankTrades, SerializerFeature.DisableCircularReferenceDetect);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return json;
  }

  /**
   * excel文件上传
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject upload(HttpServletRequest request,@RequestParam String importDate) {

    JSONObject json=bankTradeService.importExcel(request,importDate);
    
    return json;
  }

}
