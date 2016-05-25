package com.wangge.buzmgt.cash.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.Cash.CashStatusEnum;
import com.wangge.buzmgt.cash.repository.CheckCashRepository;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;

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
  private WaterOrderCashService cashService;
  @Resource
  private BankTradeService bankTradeService;
  @Resource
  private MonthPunishService monthPunishService;
  

  @Override
  public List<CheckCash> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<CheckCash> spec = checkCashSearchFilter(filters.values(), CheckCash.class);
    List<CheckCash> checkCashs = checkCashRepository.findAll(spec);
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
          
          
          //TODO 查询是否扣罚
          secp.put("EQ_status", 0);
          List<MonthPunish> monthPunishs=monthPunishService.findAll(secp);
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
    for(BankTrade woc:bankTrades){
      incomeMoney+=woc.getMoney();
    }
    cc.setIncomeMoney(incomeMoney);
    
  }
  @Override
  public List<CheckCash> findByCreateDate(String createDate) {
    return null;
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

}
