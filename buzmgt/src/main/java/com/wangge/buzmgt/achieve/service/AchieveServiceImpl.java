package com.wangge.buzmgt.achieve.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.achieve.entity.Achieve;
import com.wangge.buzmgt.achieve.entity.Achieve.AchieveStatusEnum;
import com.wangge.buzmgt.achieve.repository.AchieveRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;
import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.SearchFilter;
@Service
public class AchieveServiceImpl implements AchieveService {

  @Autowired
  private AchieveRepository achieveRepository;
  @Autowired
  private LogService logService;
  
  public List<Achieve> findAll(Map<String,Object> searchParams){
    return this.findAll(searchParams, new Sort(Direction.DESC, "createDate"));
  }
  /**
   * 处理条件参数
   */
  public Specification<Achieve> dispose(Map<String, Object> searchParams){
    //过滤删除
    searchParams.put("EQ_flag", "NORMAL");
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<Achieve> spec = achieveSearchFilter(filters.values(), Achieve.class);
    return spec;
  }
  @Override
  public List<Achieve> findAll(Map<String, Object> searchParams, Sort sort) {
    if(ObjectUtils.equals(sort, null)){
      sort =  new Sort(Direction.DESC, "createDate");
    }
    Specification<Achieve> spec = dispose(searchParams);
    return achieveRepository.findAll(spec, sort);
  }

  @Override
  public Page<Achieve> findAll(Map<String, Object> searchParams, Pageable pageable) {
    Specification<Achieve> spec = dispose(searchParams);
    return achieveRepository.findAll(spec, pageable);
  }

  @Override
  public List<Achieve> findByMachineTypeAndPlanId(String machineType,String planId) {
    Map<String, Object> searchParams = new HashMap<>();
    searchParams.put("EQ_machineType", machineType);
    searchParams.put("EQ_planId", planId);
    return this.findAll(searchParams);
  }
  @Override
  @Transactional
  public void save(Achieve achieve) {
    try {
      achieveRepository.save(achieve);
    } catch (Exception e) {
      LogUtil.error(e.getMessage(), e);
      throw e;
    }
  }
  
  public static Specification<Achieve> achieveSearchFilter(final Collection<SearchFilter> filters,
      final Class<Achieve> entityClazz){
    return new Specification<Achieve>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_PLAN_TYPE = "com.wangge.buzmgt.common.PlanTypeEnum";

      private final static String TYPE_FlAG_TYPE = "com.wangge.buzmgt.common.FlagEnum";
      
      private final static String TYPE_ACHIEVE_STATUS = "com.wangge.buzmgt.achieve.entity.Achieve$AchieveStatusEnum";
      
      private final static String TYPE_DATE = "java.util.Date";
      
      @SuppressWarnings({"unchecked","rawtypes"})
      @Override
      public Predicate toPredicate(Root<Achieve> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if(CollectionUtils.isNotEmpty(filters)){
          List<Predicate> predicates = new ArrayList<>();
          for(SearchFilter filter : filters){
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
              } else if (javaTypeName.equals(TYPE_PLAN_TYPE)) {
                /**
                 * PlanTypeEnum格式转换
                 */
                try {
                  String status = filter.value.toString();
                  PlanTypeEnum planTypeEnum = PlanTypeEnum.valueOf(status);
                  filter.value = planTypeEnum;
                } catch (Exception e) {
                  LogUtil.info(e.getMessage(), e);
                  break ;
                }
                predicates.add(cb.equal(expression, filter.value));
              } else if(javaTypeName.equals(TYPE_FlAG_TYPE)){
                /**
                 * FlagEnum格式转换
                 */
                try {
                  String status = filter.value.toString();
                  FlagEnum flagEnum = FlagEnum.valueOf(status);
                  filter.value = flagEnum;
                } catch (Exception e) {
                  LogUtil.error(e.getMessage(), e);
                  break;
                }
                predicates.add(cb.equal(expression, filter.value));
              } else if(javaTypeName.equals(TYPE_ACHIEVE_STATUS)){
                /**
                 * FlagEnum格式转换
                 */
                try {
                  String status = filter.value.toString();
                  AchieveStatusEnum flagEnum = AchieveStatusEnum.valueOf(status);
                  filter.value = flagEnum;
                } catch (Exception e) {
                  LogUtil.error(e.getMessage(), e);
                  break;
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
              boolean value = Boolean.parseBoolean("true");
              if (value)
                predicates.add(cb.isNull(expression));
              else
                predicates.add(cb.isNotNull(expression));

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
