package com.wangge.buzmgt.pushmoney.service;

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

import com.wangge.buzmgt.pushmoney.entity.Category;
import com.wangge.buzmgt.pushmoney.entity.PriceScope;
import com.wangge.buzmgt.pushmoney.entity.PushMoney;
import com.wangge.buzmgt.pushmoney.entity.PushMoneyRegion;
import com.wangge.buzmgt.pushmoney.repository.CategoryRepository;
import com.wangge.buzmgt.pushmoney.repository.PriceScopeRepository;
import com.wangge.buzmgt.pushmoney.repository.PushMoneyRegionRepository;
import com.wangge.buzmgt.pushmoney.repository.PushMoneyRepository;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class PushMoneyServiceImpl implements PushMoneyService {

  private Logger logger = Logger.getLogger(PushMoneyServiceImpl.class);
  @Resource
  private PushMoneyRepository pushMoneyRepository;
  
  @Resource
  private PushMoneyRegionRepository pushMoneyRegionRepository;

  @Resource
  private PriceScopeRepository priceScopeRepository;

  @Resource
  private CategoryRepository categoryRepository;

  @Override
  public List<PriceScope> findPriceScopeAll() {
    return priceScopeRepository.findAll();
  }

  @Override
  public List<Category> findCategoryAll() {
    Sort sort=new Sort("name");
    return categoryRepository.findAll(sort);
  }

  @Override
  public Page<PushMoney> findAll(Map<String, Object> searchParams, Pageable pageable) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<PushMoney> spec = orderSignforSearchFilter(filters.values(), PushMoney.class);
    return pushMoneyRepository.findAll(spec, pageable);
  }

  @Override
  public List<PushMoney> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<PushMoney> spec = orderSignforSearchFilter(filters.values(), PushMoney.class);
    return pushMoneyRepository.findAll(spec);
  }

  @Override
  @Transactional
  public PushMoney save(PushMoney pushMoney) {
    pushMoney.setCreateDate(new Date());
    return pushMoneyRepository.save(pushMoney);
  }

  @Override
  @Transactional
  public void delete(Integer id) {
    pushMoneyRepository.delete(id);
  }

  private static Specification<PushMoney> orderSignforSearchFilter(final Collection<SearchFilter> filters,
      final Class<PushMoney> entityClazz) {

    return new Specification<PushMoney>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<PushMoney> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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

              predicates.add(cb.isNull(expression));

              break;
            case NOTNULL:

              predicates.add(cb.isNotNull(expression));

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
  public PushMoneyRegion save(PushMoneyRegion pushMoneyRegion) {
    pushMoneyRegion.setCreateDate(new Date());
    
    return pushMoneyRegionRepository.save(pushMoneyRegion);
  }

}
