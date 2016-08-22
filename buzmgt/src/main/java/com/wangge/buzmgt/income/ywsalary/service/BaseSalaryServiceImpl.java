package com.wangge.buzmgt.income.ywsalary.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalaryUser;
import com.wangge.buzmgt.income.ywsalary.repository.BaseSalaryRepository;
import com.wangge.buzmgt.income.ywsalary.repository.BaseSalaryUserRepository;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.OrderPayType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class BaseSalaryServiceImpl implements BaseSalaryService {

 

  private static final Logger logger = Logger.getLogger(BaseSalaryServiceImpl.class);
  
  @Resource
  private BaseSalaryRepository baseSalaryRepository;

  @Resource
  private BaseSalaryUserRepository salaryUserRepository;
  
  @Resource
  private RegionService regionService;
  
  @Override
  public List<BaseSalary> findAll(Map<String, Object> searchParams) {
    regionService.disposeSearchParams("userId", searchParams);
    searchParams.put("EQ_flag", "NORMAL");
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<BaseSalary> spec = baseSalarySearchFilter(filters.values(), BaseSalary.class);
    List<BaseSalary> baseSalarys = baseSalaryRepository.findAll(spec);
    return baseSalarys;

  }

  @Override
  public Page<BaseSalary> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    Page<BaseSalary> baseSalaryPage=null;
    try {
      regionService.disposeSearchParams("userId", searchParams);
      Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
      Specification<BaseSalary> spec = baseSalarySearchFilter(filters.values(), BaseSalary.class);
      baseSalaryPage = baseSalaryRepository.findAll(spec, pageRequest);
      
    } catch (Exception e) {
      logger.info(e.getMessage());
    }
    return baseSalaryPage;
  }


  @Override
  @Transactional
  public BaseSalary save(BaseSalary baseSalary) {
    baseSalary.setUpdateDate(new Date());
    return baseSalaryRepository.save(baseSalary);
  }

  @Override
  @Transactional
  public void delete(BaseSalary baseSalary) {
    baseSalaryRepository.delete(baseSalary);
  }
  

  @Override
  public List<BaseSalaryUser> getStaySetSalesMan() {
    
    return salaryUserRepository.findAll();
  }

  private static Specification<BaseSalary> baseSalarySearchFilter(final Collection<SearchFilter> filters,
      final Class<BaseSalary> entityClazz) {

    return new Specification<BaseSalary>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_DATE = "java.util.Date";

      private final static String TYPE_BASE_SALARY_FLAG_TYPE = "com.wangge.buzmgt.ywsalary.entity.FlagEnum";
      
      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<BaseSalary> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
              }else if(javaTypeName.equals(TYPE_BASE_SALARY_FLAG_TYPE)){
                String type = filter.value.toString();
                if(FlagEnum.NORMAL.toString().equals(type)){
                  filter.value = FlagEnum.NORMAL;
                }else{
                  filter.value = FlagEnum.DEL;
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
