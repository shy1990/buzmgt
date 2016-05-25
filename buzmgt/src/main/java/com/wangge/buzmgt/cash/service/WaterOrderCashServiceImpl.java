package com.wangge.buzmgt.cash.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;
import com.wangge.buzmgt.cash.repository.WaterOrderCashRepository;
import com.wangge.buzmgt.cash.repository.WaterOrderDetailRepository;
import com.wangge.buzmgt.receipt.entity.RemarkStatusEnum;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class WaterOrderCashServiceImpl implements WaterOrderCashService {

  @Autowired
  private WaterOrderCashRepository waterOrderCashRepository;
  @Autowired
  private WaterOrderDetailRepository waterOrderDetailRepository;

  @Autowired
  private RegionService regionService;


  @Override
  public List<WaterOrderCash> findAll() {
    return waterOrderCashRepository.findAll();
  }


  @Override
  public List<WaterOrderCash> findAll(Map<String, Object> searchParams) {
    regionService.disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<WaterOrderCash> spec = WaterOrderCashSearchFilter(filters.values(), WaterOrderCash.class);
    List<WaterOrderCash> waterOrderList = waterOrderCashRepository.findAll(spec);
    return waterOrderList;
  }

  @Override
  public Page<WaterOrderCash> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    regionService.disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<WaterOrderCash> spec = WaterOrderCashSearchFilter(filters.values(), WaterOrderCash.class);
    Page<WaterOrderCash> waterOrderPage = waterOrderCashRepository.findAll(spec, pageRequest);
    waterOrderPage.getContent().forEach(WaterOrder -> {
    });

    return waterOrderPage;
  }

  
  @Override
  public WaterOrderDetail findByOrderNo(String cashId) {
    return waterOrderDetailRepository.findByCashId(cashId);
  }
  

  @Override
  public WaterOrderCash findBySerialNo(String serialNo) {
    // TODO Auto-generated method stub
    return waterOrderCashRepository.findBySerialNo(serialNo);
  }
  
  private static Specification<WaterOrderCash> WaterOrderCashSearchFilter(final Collection<SearchFilter> filters,
      final Class<WaterOrderCash> entityClazz) {

    return new Specification<WaterOrderCash>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_ORDERSIGNFOR_TYPE = "com.wangge.buzmgt.receipt.entity.RemarkStatusEnum";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<WaterOrderCash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
                if (RemarkStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.UnPay;
                }
                if (RemarkStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.OverPay;
                }
                if (RemarkStatusEnum.UnPayLate.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.UnPayLate;
                }
                if (RemarkStatusEnum.OverPayLate.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.OverPayLate;
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
                if (RemarkStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.UnPay;
                }
                if (RemarkStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.OverPay;
                }
                if (RemarkStatusEnum.UnPayLate.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.UnPayLate;
                }
                if (RemarkStatusEnum.OverPayLate.toString().equals(status)) {
                  filter.value = RemarkStatusEnum.OverPayLate;
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
               * sc_ORMLK_userId = 370105,3701050,3701051
               * 用于区域选择
               */
              String[] parameterValue = ((String) filter.value).split(",");
              Predicate[] pl=new Predicate[parameterValue.length];
              
              for(int n=0;n<parameterValue.length;n++){
                pl[n]=(cb.like(expression, "%"+parameterValue[n]+"%"));
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
