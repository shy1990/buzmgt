package com.wangge.buzmgt.brandincome.service;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.entity.BrandIncome.BrandIncomeStatus;
import com.wangge.buzmgt.brandincome.repository.BrandIncomeRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.goods.entity.Brand;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.SearchFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 16-8-31.
 * 品牌型号收益service
 */

@Service
public class BrandIncomeServiceImpl implements BrandIncomeService {
  @Resource
  private BrandIncomeRepository brandIncomeRepository;

  @Override
  public BrandIncome findById(Long id) {
    return brandIncomeRepository.findOne(id);
  }

  @Override
  public BrandIncome save(BrandIncome brandIncome) {
    return brandIncomeRepository.save(brandIncome);
  }

  @Override
  public Page<BrandIncome> findAll(Map<String, Object> searchParams, Pageable pageable) {
    Specification<BrandIncome> spec = dispose(searchParams);
    return brandIncomeRepository.findAll(spec,pageable);
  }

  /**
   * 处理条件参数
   */
  public Specification<BrandIncome> dispose(Map<String, Object> searchParams){
    //过滤删除
    searchParams.put("EQ_flag", "NORMAL");
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<BrandIncome> spec = brandIncomeSearchFilter(filters.values(), BrandIncome.class);
    return spec;
  }

  @Override
  public List<BrandIncome> findAll(Map<String, Object> searchParams, Sort sort) {
    if(ObjectUtils.equals(sort, null)){
      sort =  new Sort(Sort.Direction.DESC, "createDate");
    }
    Specification<BrandIncome> spec = dispose(searchParams);
    return brandIncomeRepository.findAll(spec, sort);
  }

  public static Specification<BrandIncome> brandIncomeSearchFilter(final Collection<SearchFilter> filters,
                                                                   final Class<BrandIncome> entityClazz){
    return new Specification<BrandIncome>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_FlAG_TYPE = "com.wangge.buzmgt.common.FlagEnum";

      private final static String TYPE_BRANDINCOME_STATUS = "com.wangge.buzmgt.brandincome.entity.BrandIncome$BrandIncomeStatus";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({"unchecked","rawtypes"})
      @Override
      public Predicate toPredicate(Root<BrandIncome> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
                } else if(javaTypeName.equals(TYPE_BRANDINCOME_STATUS)){
                  /**
                   * BrandIncomeStatus格式转换
                   */
                  try {
                    String status = filter.value.toString();
                    BrandIncomeStatus flagEnum = BrandIncomeStatus.valueOf(status);
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
