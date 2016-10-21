package com.wangge.buzmgt.cash.service;

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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.cash.entity.Cash.CashStatusEnum;
import com.wangge.buzmgt.cash.entity.MonthPunish;
import com.wangge.buzmgt.cash.repository.MonthPunishRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.service.SalesmanDataService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class MonthPunishServiceImpl implements MonthPunishService {

  @Value("${buzmgt.file.fileUploadPath}")
  private String fileUploadPath;

  private static final Logger logger = Logger.getLogger(MonthPunishServiceImpl.class);
  @Resource
  private MonthPunishRepository monthPunishRepository;
  @Resource
  private SalesmanDataService dataService;
  @Resource
  private RegionService regionService;

  @Override
  public List<MonthPunish> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<MonthPunish> spec = monthPunishSearchFilter(filters.values(), MonthPunish.class);
    List<MonthPunish> list = monthPunishRepository.findAll(spec);
    return list;
  }

  @Override
  public Page<MonthPunish> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<MonthPunish> spec = monthPunishSearchFilter(filters.values(), MonthPunish.class);
    Page<MonthPunish> page = monthPunishRepository.findAll(spec, pageRequest);
    return page;
  }

  /**
   * 
   * @param searchParams
   * @param pageable
   * @return
   */
  @Override
  public List<MonthPunish> findByUserIdAndCreateDate(String userId,String createDate) {
    Map<String, Object> spec= new HashMap<>();
    spec.put("EQ_userId", userId);
    spec.put("GTE_createDate", createDate);
    spec.put("LTE_createDate", createDate);
    
    List<MonthPunish> monthPunishList=new ArrayList<>();
    try {
      
      monthPunishList = this.findAll(spec);
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return monthPunishList;
  }

  @Override
  public void save(MonthPunish mp) {
    monthPunishRepository.save(mp);
  }

  @Override
  public void save(List<MonthPunish> monthPunishs) {
    monthPunishRepository.save(monthPunishs);
    
  }

  private static Specification<MonthPunish> monthPunishSearchFilter(final Collection<SearchFilter> filters,
      final Class<MonthPunish> entityClazz) {

    return new Specification<MonthPunish>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_ORDERSIGNFOR_TYPE = "com.wangge.buzmgt.cash.entity.Cash$CashStatusEnum";
      
      private final static String TYPE_FLAG_TYPE = "com.wangge.buzmgt.common.FlagEnum";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<MonthPunish> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
              } else if (javaTypeName.equals(TYPE_FLAG_TYPE)) {
                
                String status = filter.value.toString();
                if (FlagEnum.NORMAL.toString().equals(status)) {
                  filter.value = FlagEnum.NORMAL;
                }
                if (FlagEnum.DEL.toString().equals(status)) {
                  filter.value = FlagEnum.DEL;
                }
                predicates.add(cb.equal(expression, filter.value));
                
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
              boolean value = Boolean.parseBoolean((String) filter.value);
              if (value) {
                predicates.add(cb.isNull(expression));
              } else {
                predicates.add(cb.isNotNull(expression));
              }

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
  public List<MonthPunish> findAllISNotCash(Map<String, Object> spec) {
    String createDate=(String) spec.get("EQ_createDate");
    Date minDate=DateUtil.string2Date(createDate+" 00:00:00 000");
    Date maxDate=DateUtil.string2Date(createDate+" 23:59:59 999");
//    return monthPunishRepository.findAllISNotCash(minDate,maxDate);
    return null;
  }



}
