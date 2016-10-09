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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.Cash;
import com.wangge.buzmgt.cash.entity.Cash.CashStatusEnum;
import com.wangge.buzmgt.cash.entity.CheckCash;
import com.wangge.buzmgt.cash.entity.MonthPunish;
import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderCash.WaterPayStatusEnum;
import com.wangge.buzmgt.cash.repository.CheckCashRepository;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.entity.PunishSet;
import com.wangge.buzmgt.salesman.service.PunishSetService;
import com.wangge.buzmgt.teammember.entity.SalesMan;
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
    List<CheckCash> checkCashs = null;
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
    Page<CheckCash> checkCashPage = null;
    try {
      checkCashPage = checkCashRepository.findAll(spec, pageRequest);
      disposeCheckCash(checkCashPage.getContent());

    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return checkCashPage;
  }

  public void disposeCheckCash(List<CheckCash> checkCashs) {
    try {

      checkCashs.forEach(checkCash -> {
        String userId = checkCash.getUserId().trim();
        if (StringUtils.isNotEmpty(userId)) {

          String payDate = DateUtil.date2String(checkCash.getCreateDate());
          //查询银行导入数据
          Map<String, Object> secp = new HashMap<>();

          secp.put("EQ_userId", userId);
          secp.put("EQ_importDate", payDate);
          List<BankTrade> bankTrades = bankTradeService.findAll(secp);
          secp.remove("EQ_importDate");
          checkCash.setBankTrades(bankTrades);
          disposeBankTrade(bankTrades, checkCash);

          // 查询流水单号
          secp.put("EQ_createDate", payDate);// 划分时间
          List<WaterOrderCash> wocs = cashService.findAll(secp);
          secp.remove("EQ_createDate");
          checkCash.setCashs(wocs);
          checkCash.setIsCheck(wocs.get(0).getPayStatus());

          /*
           * -->查询是否扣罚 查询前一天是否有扣罚
           */
          secp.put("EQ_createDate", DateUtil.date2String(DateUtil.moveDate(checkCash.getCreateDate(), -1)));
          List<MonthPunish> monthPunishs = monthPunishService.findAll(secp);
          checkCash.setMonthPunishs(monthPunishs);
          disposeMonthPunish(monthPunishs, checkCash);

        }
      });
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }

  // 计算扣罚金额
  private void disposeMonthPunish(List<MonthPunish> monthPunishs, CheckCash cc) {
    BigDecimal debtMoney = new BigDecimal(0);
    for (MonthPunish wp : monthPunishs) {
      debtMoney = debtMoney.add(new BigDecimal(Float.toString(wp.getDebt()))).add(new BigDecimal(Float.toString(wp.getAmerce()))) ;
    }
    cc.setDebtMoney(debtMoney.floatValue());
  }

  // 计算打款总金额
  public void disposeBankTrade(List<BankTrade> bankTrades, CheckCash cc) {
    BigDecimal incomeMoney = new BigDecimal(0);
    String cardName = "";
    if (bankTrades == null || bankTrades.size() == 0) {
      String userId = cc.getUserId();
      SalesMan salesMan = salesManService.findByUserId(userId);

      cc.setCardName(salesMan.getTruename());
    } else {

      for (BankTrade woc : bankTrades) {
        incomeMoney =incomeMoney.add(new BigDecimal(Float.toString(woc.getMoney())));
        cardName = woc.getCardName();
      }
      cc.setCardName(cardName);
    }
    cc.setIncomeMoney(incomeMoney.floatValue());

  }

  /**
   * 审核过程梳理 1.查询数据userId+createDate 2.
   */
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public JSONObject checkPendingByUserIdAndCreateDate(String userId, String createDate) {
    Map<String, Object> secp = new HashMap<>();
    JSONObject json = new JSONObject();
    secp.put("EQ_userId", userId);
    secp.put("EQ_createDate", createDate);
    try {
      List<CheckCash> list = this.findAll(secp);
      if (list.size() > 0) {
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

  public void checkWaterOrderCash(CheckCash cc) {
    try {
      List<WaterOrderCash> waterOrders = cc.getCashs();

      if (waterOrders.size() == 0)
        return;
      Float stayMoney = cc.getStayMoney();// 待付金额
      Float incomeMoney = cc.getIncomeMoney();// 支付金额
	    BigDecimal incomeBigDecimal = new BigDecimal(Float.toString(incomeMoney));
      for (WaterOrderCash order : waterOrders) {
        // 总支付金额大于流水单金额
        Float cashMoney = order.getCashMoney();
	      BigDecimal cashBigDecimal = new BigDecimal(Float.toString(cashMoney));
        if (incomeMoney > cashMoney) {
          order.setPaymentMoney(cashMoney);
        } else {
          if (incomeMoney < 0)
            order.setPaymentMoney(0f);
          else
            order.setPaymentMoney(incomeMoney);
        }
//        incomeMoney -= cashMoney;
	      incomeBigDecimal = incomeBigDecimal.subtract(cashBigDecimal) ;
	      incomeMoney = incomeBigDecimal.floatValue();
        order.setPayStatus(WaterPayStatusEnum.OverPay);
        order.setPayDate(cc.getCreateDate());

        // 修改收现金列表数据
        order.getOrderDetails().forEach(detail -> {
          Cash cash = detail.getCash();
          cash.setPayDate(cc.getCreateDate());
          cash.setStatus(CashStatusEnum.OverPay);
        });
      }
      // 修改原有扣罚状态 放在app-server接口产生流水单号时进行修改
      /*
       * List<MonthPunish> monthPunishs = cc.getMonthPunishs(); if
       * (monthPunishs.size() > 0) { monthPunishs.forEach(mp -> {
       * mp.setStatus(1); }); monthPunishService.save(monthPunishs); }
       */

      // 是否产生扣罚
      if (stayMoney != 0) {
        // 产生扣罚，修改流水单号状态
        WaterOrderCash order = waterOrders.get(0);
        // order.setIsPunish(1);//修改扣罚位置-->在产生流水订单号时进行

        MonthPunish mp = new MonthPunish();
        String userId = order.getUserId();
        mp.setDebt(stayMoney);
        if (stayMoney > 0) {
          PunishSet punishSet = punishSetService.findByUserId(userId);
	        BigDecimal stayBigDecimal = new BigDecimal(Float.toString(stayMoney));
	        BigDecimal punishBigDecimal = new BigDecimal(Float.toString(punishSet.getPunishNumber()));
//          mp.setAmerce(stayMoney * punishSet.getPunishNumber());// 扣罚
	        mp.setAmerce(stayBigDecimal.multiply(punishBigDecimal).floatValue());// 扣罚
        } else {
          mp.setAmerce(0.0f);// 扣罚
        }
        mp.setStatus(0);//
        mp.setCreateDate(order.getCreateDate());
        mp.setSeriaNo(order.getSerialNo());
        mp.setUserId(userId);
        monthPunishService.save(mp);
      }
      cashService.save(waterOrders);

    } catch (Exception e) {
      logger.info(e.getMessage());
      throw e;
    }

  }

  @Override
  public List<BankTrade> getUnCheckBankTrades() {
    Map<String, Object> spec = new HashMap<>();
    spec.put("ISNULL_userId", "true");
    return bankTradeService.findAll(spec);
  }

  @Override
  @Transactional(rollbackForClassName = "Exception")
  public JSONObject deleteUnCheckBankTrade(BankTrade bankTrade) {
    JSONObject json = new JSONObject();
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
            case IN:
              predicates.add(cb.in(expression).value(filter.value));

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

  /**
   * 查询没有流水单号的交易
   *
   * @param createDate
   *          正常的查询日期
   * @return
   */
  @Override
  public List<CheckCash> getDebtChecks(String createDate) {
    Map<String, Object> spec = new HashMap<>();
    List<CheckCash> checkCashs = null;
    try {
      // 正常搜索日期
      spec.put("EQ_createDate", createDate);
      spec.put("NOTEQ_status", 1);// 扣罚状态 0-没有流水单匹配，1-有流水单匹配，2-没有流水单匹配审核后的状态
      checkCashs = findCheckCashByDebt(spec);

    } catch (Exception e) {
      e.printStackTrace();
      logger.info(e.getMessage());
      return checkCashs;
    }
    return checkCashs;
  }

  /**
   * 查询核对信息， 拼装信息
   *
   * @param spec
   * @return
   */
  public List<CheckCash> findCheckCashByDebt(Map<String, Object> spec) {

    List<CheckCash> checkCashs = new ArrayList<>();
    // 查询前一天是否有扣罚记录
    String createDate = (String) spec.get("EQ_createDate");
    spec.put("EQ_createDate", DateUtil.moveDate(createDate, -1));
    List<MonthPunish> monthPunishs = monthPunishService.findAll(spec);

    spec.remove("EQ_status");
    spec.remove("NOTEQ_status");
    spec.remove("EQ_createDate");
    if (monthPunishs.size() > 0) {
      for (MonthPunish monthPunish : monthPunishs) {
        CheckCash cash = new CheckCash();
        String userId = monthPunish.getUserId();

        cash.setUserId(userId);
        cash.setDebtMoney(monthPunish.getDebt() + monthPunish.getAmerce());
        cash.setMonthPunishs(monthPunishs);
        cash.setCashMoney(0.0f);
        cash.setCreateDate(DateUtil.string2Date(createDate));
        if (monthPunish.getStatus() == 0) {
          cash.setIsCheck("未审核");
        } else {
          cash.setIsCheck("已审核");
        }
        spec.put("EQ_userId", userId);
        spec.put("EQ_importDate", createDate);
        List<BankTrade> bankTrades = bankTradeService.findAll(spec);
        if (bankTrades.size() > 0) {
          Float incomeMoney = 0.0f;
          for (BankTrade bankTrade : bankTrades) {
          	BigDecimal incomeBigDecimal = new BigDecimal(Float.toString(incomeMoney));
//            incomeMoney += bankTrade.getMoney();
	          incomeBigDecimal = incomeBigDecimal.add(new BigDecimal(Float.toString(bankTrade.getMoney())));
	          incomeMoney = incomeBigDecimal.floatValue();
          }
          cash.setIncomeMoney(incomeMoney);

          cash.setBankTrades(bankTrades);
          cash.setCardName(bankTrades.get(0).getCardName());
        } else {
          // 查询truename
          SalesMan s = salesManService.findByUserId(userId);
          cash.setCardName(s.getTruename());

        }
        checkCashs.add(cash);
      }
      return checkCashs;
    }
    return null;
  }

  @Override
  @Transactional(rollbackForClassName = "Exception")
  public JSONObject auditDebtCheck(String userId, String createDate) {
    Map<String, Object> spec = new HashMap<>();
    JSONObject json = new JSONObject();

    spec.put("EQ_userId", userId);
    spec.put("EQ_createDate", createDate);
    spec.put("EQ_status", 0);

    try {
      // 修改原有扣罚状态
      List<CheckCash> list = this.findCheckCashByDebt(spec);
      if (CollectionUtils.isNotEmpty(list)) {
        checkDebtOrder(list.get(0));

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

  public void checkDebtOrder(CheckCash cash) {
    List<MonthPunish> monthPunishs = cash.getMonthPunishs();
    if (monthPunishs.size() > 0) {

      monthPunishs.forEach(mp -> {
        mp.setStatus(2);// 修改状态没有流水单审核后状态
      });
      monthPunishService.save(monthPunishs);
    }
    Float stayMoney = cash.getStayMoney();
    // 是否产生扣罚
    if (stayMoney != 0) {
      // 产生扣罚，修改流水单号状态

      MonthPunish mp = new MonthPunish();
      String userId = cash.getUserId();
      mp.setDebt(stayMoney);
      if (stayMoney > 0) {
        PunishSet punishSet = punishSetService.findByUserId(userId);
//        mp.setAmerce(stayMoney * punishSet.getPunishNumber());// 扣罚
	      mp.setAmerce(new BigDecimal(Float.toString(stayMoney)).multiply(new BigDecimal(Float.toString(punishSet.getPunishNumber()))).floatValue());// 扣罚
      } else {
        mp.setAmerce(0.0f);// 扣罚
      }
      mp.setStatus(0);//
      mp.setCreateDate(cash.getCreateDate());
      mp.setUserId(userId);
      monthPunishService.save(mp);
    }
  }

}
