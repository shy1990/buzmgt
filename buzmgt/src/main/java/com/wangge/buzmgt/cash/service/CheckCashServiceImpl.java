package com.wangge.buzmgt.cash.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.cash.entity.CheckCash;
import com.wangge.buzmgt.cash.entity.MonthPunish;
import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.Cash;
import com.wangge.buzmgt.cash.entity.Cash.CashStatusEnum;
import com.wangge.buzmgt.cash.entity.WaterOrderCash.WaterPayStatusEnum;
import com.wangge.buzmgt.cash.repository.CheckCashRepository;
import com.wangge.buzmgt.cash.web.CashController;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.entity.PunishSet;
import com.wangge.buzmgt.salesman.service.PunishSetService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;
import com.wangge.buzmgt.util.excel.MapedExcelExport;

@Service
public class CheckCashServiceImpl implements CheckCashService {

  @Value("${buzmgt.file.fileUploadPath}")
  private String fileUploadPath;

  private static final Logger logger = Logger.getLogger(CheckCashServiceImpl.class);
  @Resource
  private CheckCashRepository checkCashRepository;
  @Resource
  private RegionService regionService;
  @Resource
  private SalesManService salesManService;
  @Resource
  private WaterOrderCashService cashService;
  @Resource
  private BankTradeService bankTradeService;
  @Resource
  private MonthPunishService monthPunishService;
  @Resource
  private PunishSetService punishSetService;
  

  @Override
  public List<CheckCash> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<CheckCash> spec = checkCashSearchFilter(filters.values(), CheckCash.class);
    List<CheckCash> checkCashs =null;
    try {
      checkCashs = checkCashRepository.findAll(spec);
      disposeCheckCash(checkCashs);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return checkCashs;

  }

  @Override
  public Page<CheckCash> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<CheckCash> spec = checkCashSearchFilter(filters.values(), CheckCash.class);
    Page<CheckCash> checkCashPage=null;
    try {
      checkCashPage= checkCashRepository.findAll(spec, pageRequest);
      disposeCheckCash(checkCashPage.getContent());
      
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return checkCashPage;
  }

  public void disposeCheckCash( List<CheckCash> checkCashs){
    try {
      
      checkCashs.forEach(checkCash->{
        String userId=checkCash.getUserId();
        if(StringUtils.isNotEmpty(userId)){
          
          String payDate=DateUtil.date2String(checkCash.getCreateDate());
          //TODO 查询银行导入数据
          Map<String, Object> secp=new HashMap<>();
          
          secp.put("EQ_userId", userId);
          secp.put("EQ_payDate", payDate);
          List<BankTrade> bankTrades =bankTradeService.findAll(secp);
          secp.remove("EQ_payDate");
          checkCash.setBankTrades(bankTrades);
          disposeBankTrade(bankTrades,checkCash);
          
          //TODO 查询流水单号
          secp.put("EQ_createDate", payDate);//划分时间
          List<WaterOrderCash> wocs =cashService.findAll(secp);
          secp.remove("EQ_createDate");
          checkCash.setCashs(wocs);
          checkCash.setIsCheck(wocs.get(0).getPayStatus());
          
          
          /*
           * -->查询是否扣罚
           * 查询前一天是否有扣罚
           */
          secp.put("EQ_createDate", DateUtil.date2String(DateUtil.moveDate(checkCash.getCreateDate(),-1)));
          List<MonthPunish> monthPunishs=monthPunishService.findAll(secp);
          checkCash.setMonthPunishs(monthPunishs);
          disposeMonthPunish(monthPunishs,checkCash);
          
          
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }
  //计算扣罚金额
  private void disposeMonthPunish(List<MonthPunish> monthPunishs, CheckCash cc) {
    Float debtMoney=new Float(0);
    for(MonthPunish wp:monthPunishs){
      debtMoney+=wp.getDebt()+wp.getAmerce();
    }
    cc.setDebtMoney(debtMoney);
  }

  //计算打款总金额
  public void disposeBankTrade(List<BankTrade> bankTrades,CheckCash cc){
    Float incomeMoney=new Float(0);
    String cardName="";
    for(BankTrade woc:bankTrades){
      incomeMoney+=woc.getMoney();
      cardName=woc.getCardName();
    }
    cc.setIncomeMoney(incomeMoney);
    cc.setCardName(cardName);
    
    
  }
  
  /**
   * 审核过程梳理
   * 1.查询数据userId+createDate
   * 2.
   */
  @Override
  @Transactional
  public JSONObject checkPendingByUserIdAndCreateDate(String userId, String createDate) {
    Map<String, Object> secp=new HashMap<>();
    JSONObject json=new JSONObject();
    secp.put("EQ_userId", userId);
    secp.put("EQ_createDate", createDate);
    try {
      List<CheckCash> list = this.findAll(secp);
      if(list.size()>0){
        checkWaterOrderCash(list.get(0));
        json.put("status", "success");
        json.put("successMsg", "操作成功");
        return json;
      }
      json.put("status", "error");
      json.put("errorMsg", "未查到该数据");
      
    } catch (Exception e) {
      json.put("status", "error");
      json.put("errorMsg", "操作失败");
      logger.info(e.getMessage());
      return json;
    }
    
    return json;
  }
  @Transactional
  public void checkWaterOrderCash(CheckCash cc){
    try {
      List<WaterOrderCash> waterOrders = cc.getCashs();
      
      if(waterOrders.size()==0)
        return ;
      Float stayMoney=cc.getStayMoney();//待付金额
      Float incomeMoney=cc.getIncomeMoney();//支付金额
      for(WaterOrderCash order:waterOrders){
        //总支付金额大于流水单金额
        Float cashMoney=order.getCashMoney();
        if(incomeMoney>cashMoney){
          order.setPaymentMoney(cashMoney);
        }else{
          if(incomeMoney<0)
            order.setPaymentMoney(0f);
          else
            order.setPaymentMoney(incomeMoney);
        }
        incomeMoney-=cashMoney;
        
        
        order.setPayStatus(WaterPayStatusEnum.OverPay);
        order.setPayDate(cc.getCreateDate());
        
        
        //修改收现金列表数据
        order.getOrderDetails().forEach(detail->{
          Cash cash = detail.getCash();
          cash.setPayDate(cc.getCreateDate());
          cash.setStatus(CashStatusEnum.OverPay);
        });
      }
      //修改原有扣罚状态
      List<MonthPunish> monthPunishs=cc.getMonthPunishs();
      if(monthPunishs.size()>0){
        monthPunishs.forEach(mp->{
          mp.setStatus(1);
        });
        monthPunishService.save(monthPunishs);
      }
      
      //是否产生扣罚
      if(stayMoney>0){
        //产生扣罚，修改流水单号状态
        WaterOrderCash order= waterOrders.get(0);
        order.setIsPunish(1);
        
        MonthPunish mp=new MonthPunish();
        String userId=order.getUserId();
        mp.setDebt(stayMoney);
        PunishSet punishSet=punishSetService.findByUserId(userId);
        mp.setAmerce(stayMoney*punishSet.getPunishNumber());//扣罚
        mp.setStatus(0);//
        mp.setCreateDate(order.getCreateDate());
        mp.setSeriaNo(order.getSerialNo());
        mp.setUserId(userId);
        monthPunishService.save(mp);
      }
      cashService.save(waterOrders);
      
    } catch (Exception e) {
      e.printStackTrace();
      logger.info(e.getMessage());
    }
      
  }
  @Override
  public List<BankTrade> getUnCheckBankTrades() {
    Map<String, Object> spec=new HashMap<>();
    spec.put("ISNULL_userId","true");
    return bankTradeService.findAll(spec);
  }



  @Override
  @Transactional
  public JSONObject deleteUnCheckBankTrade(BankTrade bankTrade) {
    JSONObject json=new JSONObject();
    try {
      bankTradeService.delete(bankTrade);
      json.put("status", "success");
      json.put("successMsg", "操作成功");
    } catch (Exception e) {
      json.put("status", "error");
      json.put("errorMsg", "操作失败");
      logger.info(e.getMessage());
      return json;
    }
    return json;
  }

  
  private static Specification<CheckCash> checkCashSearchFilter(final Collection<SearchFilter> filters,
      final Class<CheckCash> entityClazz) {

    return new Specification<CheckCash>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_ORDERSIGNFOR_TYPE = "com.wangge.buzmgt.cash.entity.Cash$CashStatusEnum";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<CheckCash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (CollectionUtils.isNotEmpty(filters)) {
          List<Predicate> predicates = new ArrayList<Predicate>();
          for (SearchFilter filter : filters) {
            // nested path translate, 如Task的名为"user.name"的filedName,
            // 转换为Task.user.name属性
            String[] names = StringUtils.split(filter.fieldName, ".");
            Path expression = root.get(names[0]);
            for (int i = 1; i < names.length; i++) {
              expression = expression.get(names[i]);
            }

            String javaTypeName = expression.getJavaType().getName();

            // logic operator
            switch (filter.operator) {
            case EQ:
              // 日期相等,小于等于最大值,大于等于最小值
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  predicates.add(cb.greaterThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                  predicates.add(cb.lessThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {
                String status = filter.value.toString();
                if (CashStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.UnPay;
                }
                if (CashStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.OverPay;
                }
                predicates.add(cb.equal(expression, filter.value));
              } else {
                predicates.add(cb.equal(expression, filter.value));
              }

              break;
            case LIKE:
              predicates.add(cb.like(expression, "%" + filter.value + "%"));

              break;
            case GT:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 大于最大值
                  predicates.add(cb.greaterThan(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.greaterThan(expression, (Comparable) filter.value));
              }

              break;
            case LT:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 小于最小值
                  predicates.add(cb.lessThan(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.lessThan(expression, (Comparable) filter.value));
              }

              break;
            case GTE:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 大于等于最小值
                  predicates.add(cb.greaterThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
                } catch (ParseException e) {
                  e.printStackTrace();
                }
              } else if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {
                String status = filter.value.toString();
                if (CashStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.UnPay;
                }
                if (CashStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.OverPay;
                }
                predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.value));

              } else {
                predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.value));
              }

              break;
            case LTE:
              if (javaTypeName.equals(TYPE_DATE)) {
                try {
                  // 小于等于最大值
                  predicates.add(cb.lessThanOrEqualTo(expression,
                      new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
                } catch (ParseException e) {
                  throw new RuntimeException("日期格式化失败!");
                }
              } else {
                predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.value));
              }

              break;
            case NOTEQ:

              predicates.add(cb.notEqual(expression, filter.value));

              break;
            case ISNULL:

              predicates.add(cb.isNull(expression));
              break;
            case NOTNULL:
              predicates.add(cb.isNull(expression));

              break;
            case ORMLK:
              /**
               * sc_ORMLK_userId = 370105,3701050,3701051 用于区域选择
               */
              String[] parameterValue = ((String) filter.value).split(",");
              Predicate[] pl = new Predicate[parameterValue.length];

              for (int n = 0; n < parameterValue.length; n++) {
                pl[n] = (cb.like(expression, "%" + parameterValue[n] + "%"));
              }

              Predicate p_ = cb.or(pl);
              predicates.add(p_);
              break;

            default:
              break;

            }
          }

          // 将所有条件用 and 联合起来
          if (!predicates.isEmpty()) {
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
          }
        }

        return cb.conjunction();
      }
    };
  }

  @Override
  public void exportSetExecl(List<CheckCash> checkCashs, HttpServletRequest request, HttpServletResponse response) {
    List<Map<String, Object>> alList = new ArrayList<Map<String, Object>>();
    Map<String, Integer> sumMap = new HashMap<String, Integer>();
    checkCashs.forEach(checkCash->{
      List<BankTrade> bankTrades=checkCash.getBankTrades();
      String userId = checkCash.getUserId();
      String cardName = checkCash.getCardName();
      Date createDate = checkCash.getCreateDate();
      String cradNo="";
      String incomeMoney="";
      for(BankTrade bankTrade:bankTrades){
        cradNo+=bankTrade.getCardNo()+"    ";
        incomeMoney+=bankTrade.getMoney().toString()+"    ";
      }
      List<WaterOrderCash> orderCashs=checkCash.getCashs();
      for(WaterOrderCash orderCash:orderCashs){
        Map<String,Object> objMap=new HashMap<>();
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
    String[] gridTitles_ = { "业务ID", "姓名", "付款卡号", "打款金额", "流水单号", "当日收现", "收现总额", "昨日累加", "业务应付",  "业务实付", "业务待付", "操作日期"};
    String[] coloumsKey_ = { "userId", "cardName", "cradNo", "incomeMoney", "serialNo", "cashMoney", "cashMoneyTotal", 
        "debtMoney", "shouldPayMoney", "incomeMoneyTotal", "stayMoney", "createDate" };
   logger.info(alList);
   logger.info(marginList);
    MapedExcelExport.doExcelExport("待审核账单.xls", alList, gridTitles_, coloumsKey_, request, response, marginList);
  }


}
