package com.wangge.buzmgt.cash.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.service.BankTradeService;
import com.wangge.buzmgt.cash.service.CheckCashService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.excel.MapedExcelExport;
import com.wangge.json.JSONFormat;

@Controller
@RequestMapping("/checkCash")
public class CheckCashController {

  private static final String SEARCH_OPERTOR = "sc_";

  private static final Logger logger = Logger.getLogger(CheckCashController.class);

  @Autowired
  private CheckCashService checkCashService;
  @Autowired
  private BankTradeService bankTradeService;
  @Autowired
  private SalesManService salesManService;

  @RequestMapping(value = "/toCheckPending")
  public String toBankTrades() {
    return "cash/check_pending";
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  @JSONFormat(filterField = { "OrderSignfor.salesMan" }, nonnull = true, dateFormat = "yyyy-MM-dd")
  public Page<CheckCash> getCashList(HttpServletRequest request, @PageableDefault(page = 0, size = 10, sort = {
      "createDate", "rnid" }, direction = Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<CheckCash> page = checkCashService.findAll(searchParams, pageable);
    return page;
  }

  @RequestMapping(value = "/salesmanName", method = RequestMethod.GET)
  @JSONFormat(filterField = { "OrderSignfor.salesMan" }, nonnull = true, dateFormat = "yyyy-MM-dd")
  public Page<CheckCash> findBySalesManName(@RequestParam String salesmanName, @RequestParam String createDate) {
    Page<CheckCash> page = null;
    try {

      List<String> userIds = salesManService.findByTruename(salesmanName);
      if(userIds.isEmpty()){
        return new PageImpl(new ArrayList<CheckCash>());
      }
      Map<String, Object> secp = new HashMap<>();
      secp.put("IN_userId", userIds);
      secp.put("EQ_createDate", createDate);
      List<CheckCash> list = checkCashService.findAll(secp);
      page = new PageImpl<>(list,null,list.size());
      
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return page;
  }

  /**
   * 查询 未匹配银行交易记录
   */
  @RequestMapping(value = "/unCheck", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getUnCheckBankTrades(HttpServletRequest request) {
    JSONObject json = new JSONObject();
    List<BankTrade> bankTrades = checkCashService.getUnCheckBankTrades();
    json.put("content", bankTrades);
    return json;
  }

  /**
   * 么有流失单号的扣罚审核
   * 
   * @param createDate
   * @return
   */
  @RequestMapping(value = "/debtCheck", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getDebtCheck(String createDate) {
    if ("".equals(createDate)) {
      createDate = DateUtil.date2String(new Date());
    }
    JSONObject json = new JSONObject();
    List<CheckCash> debtCheckList = checkCashService.getDebtChecks(createDate);
    json.put("content", debtCheckList);
    return json;
  }

  @RequestMapping(value = "/debtCheck/{userId}", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject auditDebtCheck(@PathVariable("userId") String userId, String createDate) {
    return checkCashService.auditDebtCheck(userId, createDate);
  }

  /**
   * 审核账单（打款和流水单号）
   * 
   * @return
   */
  @RequestMapping(value = "/{userId}", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject auditByUserIdAndCreateDate(@PathVariable("userId") String userId,
      @RequestParam(value = "createDate") String createDate) {

    return checkCashService.checkPendingByUserIdAndCreateDate(userId, createDate);
  }

  /**
   * 删除未匹配记录
   * 
   * @param bankTrade
   * @return
   */
  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject deleteUnCheckBankTrade(@PathVariable("id") BankTrade bankTrade) {

    return checkCashService.deleteUnCheckBankTrade(bankTrade);
  }

  @RequestMapping(value = "/export", method = RequestMethod.GET)
  public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<CheckCash> checkCashs = checkCashService.findAll(searchParams);

    List<Map<String, Object>> alList = new ArrayList<Map<String, Object>>();
    Map<String, Integer> sumMap = new HashMap<String, Integer>();
    checkCashs.forEach(checkCash -> {
      List<BankTrade> bankTrades = checkCash.getBankTrades();
      String userId = checkCash.getUserId();
      String cardName = checkCash.getCardName();
      Date createDate = checkCash.getCreateDate();
      String cradNo = "";
      String incomeMoney = "";
      for (BankTrade bankTrade : bankTrades) {
        cradNo += bankTrade.getCardNo() + "    ";
        incomeMoney += bankTrade.getMoney().toString() + "    ";
      }
      List<WaterOrderCash> orderCashs = checkCash.getCashs();
      for (WaterOrderCash orderCash : orderCashs) {
        Map<String, Object> objMap = new HashMap<>();
        objMap.put("userId", userId);
        objMap.put("cardName", cardName);
        objMap.put("cradNo", cradNo);
        objMap.put("incomeMoney", incomeMoney);
        objMap.put("serialNo", orderCash.getSerialNo());
        objMap.put("cashMoney", orderCash.getCashMoney());
        objMap.put("cashMoneyTotal", checkCash.getCashMoney());
        objMap.put("debtMoney", checkCash.getDebtMoney());
        objMap.put("shouldPayMoney", checkCash.getShouldPayMoney());
        objMap.put("incomeMoneyTotal", checkCash.getIncomeMoney());
        objMap.put("stayMoney", checkCash.getStayMoney());
        objMap.put("createDate", createDate);
        alList.add(objMap);

        Integer sum = sumMap.get(userId);
        if (null == sum) {
          sumMap.put(userId, 1);
        } else {
          sumMap.put(userId, sum + 1);
        }
      }

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
        obMap1.put("firstCol", 1);
        obMap1.put("lastCol", 1);
        marginList.add(obMap1);

        Map<String, Object> obMap2 = new HashMap<String, Object>();
        obMap2.put("firstRow", start + 1);
        obMap2.put("lastRow", end);
        obMap2.put("firstCol", 2);
        obMap2.put("lastCol", 2);
        marginList.add(obMap2);

        Map<String, Object> obMap3 = new HashMap<String, Object>();
        obMap3.put("firstRow", start + 1);
        obMap3.put("lastRow", end);
        obMap3.put("firstCol", 3);
        obMap3.put("lastCol", 3);
        marginList.add(obMap3);

        Map<String, Object> obMap5 = new HashMap<String, Object>();
        obMap5.put("firstRow", start + 1);
        obMap5.put("lastRow", end);
        obMap5.put("firstCol", 6);
        obMap5.put("lastCol", 6);
        marginList.add(obMap5);

        Map<String, Object> obMap6 = new HashMap<String, Object>();
        obMap6.put("firstRow", start + 1);
        obMap6.put("lastRow", end);
        obMap6.put("firstCol", 7);
        obMap6.put("lastCol", 7);
        marginList.add(obMap6);

        Map<String, Object> obMap7 = new HashMap<String, Object>();
        obMap7.put("firstRow", start + 1);
        obMap7.put("lastRow", end);
        obMap7.put("firstCol", 8);
        obMap7.put("lastCol", 8);
        marginList.add(obMap7);

        Map<String, Object> obMap8 = new HashMap<String, Object>();
        obMap8.put("firstRow", start + 1);
        obMap8.put("lastRow", end);
        obMap8.put("firstCol", 9);
        obMap8.put("lastCol", 9);
        marginList.add(obMap8);

        Map<String, Object> obMap9 = new HashMap<String, Object>();
        obMap9.put("firstRow", start + 1);
        obMap9.put("lastRow", end);
        obMap9.put("firstCol", 10);
        obMap9.put("lastCol", 10);
        marginList.add(obMap9);

        Map<String, Object> obMap4 = new HashMap<String, Object>();
        obMap4.put("firstRow", start + 1);
        obMap4.put("lastRow", end);
        obMap4.put("firstCol", 11);
        obMap4.put("lastCol", 11);
        marginList.add(obMap4);

      }
      start = end;
    }
    String[] gridTitles_ = { "业务ID", "姓名", "付款卡号", "打款金额", "流水单号", "当日收现", "收现总额", "昨日累加", "业务应付", "业务实付", "业务待付",
        "操作日期" };
    String[] coloumsKey_ = { "userId", "cardName", "cradNo", "incomeMoney", "serialNo", "cashMoney", "cashMoneyTotal",
        "debtMoney", "shouldPayMoney", "incomeMoneyTotal", "stayMoney", "createDate" };
    logger.info(alList);
    logger.info(marginList);
    MapedExcelExport.doExcelExport("待审核账单.xls", alList, gridTitles_, coloumsKey_, request, response, marginList);
  }

  /**
   * 归档修改银行交易记录归档表示符
   * 
   * @param archivingDate
   * @return
   */
  @RequestMapping(value = "/archiving", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject archiving(@RequestParam String archivingDate) {
    JSONObject json = new JSONObject();
    try {
      Map<String, Object> spec = new HashMap<>();
      spec.put("LTE_payDate", archivingDate);
      spec.put("EQ_isArchive", 0);
      List<BankTrade> bankTrades = bankTradeService.findAll(spec);
      if (bankTrades.size() > 0) {
        bankTrades.forEach(bankTrade -> {
          bankTrade.setIsArchive(1);
        });
        bankTradeService.save(bankTrades);
        json.put("result", "success");
        json.put("message", "操作成功");
        return json;
      }
      json.put("result", "failure");
      json.put("message", "没有交易记录或已经归档");

    } catch (Exception e) {
      json.put("result", "failure");
      json.put("message", "");
      return json;
    }
    return json;
  }

}
