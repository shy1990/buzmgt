package com.wangge.buzmgt.customtask.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

public class PredicateUtil {
  
  private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";
  
  private final static String TIME_MIN = " 00:00:00 000";
  
  private final static String TIME_MAX = " 23:59:59 999";
  
  private final static String TYPE_DATE = "java.util.Date";
  
  /**
   * predicate的通用处理方法
   * 
   * @param params
   * @param root
   * @param cb
   * @param predicates
   * @throws NumberFormatException
   */
  @SuppressWarnings("unchecked")
  public static void createPedicateByMap(Map<String, Object> params, Root<?> root, CriteriaBuilder cb,
      List<Predicate> predicates) throws NumberFormatException {
    
    for (Map.Entry<String, Object> search : params.entrySet()) {
      String keyAndFilter = search.getKey();
      Object value = search.getValue();
      if (!keyAndFilter.contains("_")) {
        continue;
      }
      String key = keyAndFilter.split("_")[1];
      String filter = keyAndFilter.split("_")[0];
      Path expression = root.get(key);
      if (null == value || StringUtils.isBlank(value.toString())) {
        continue;
      }
      String javaTypeName = expression.getJavaType().getName();
      try {
        switch (filter) {
          case "EQ":
            if (javaTypeName.equals(TYPE_DATE)) {
              predicates.add(cb.equal(root.get(key), new SimpleDateFormat(DATE_FORMAT).parse(value.toString())));
              
            } else {
              predicates.add(cb.equal(root.get(key), value));
            }
            break;
          case "LK":
            predicates.add(cb.like(root.get(key), "%" + value + "%"));
            break;
          case "GT":
            if (javaTypeName.equals(TYPE_DATE)) {
              // 大于最大值
              predicates.add(
                  cb.greaterThan(expression, new SimpleDateFormat(DATE_FORMAT).parse(value.toString() + TIME_MIN)));
              
            } else {
              predicates.add(cb.greaterThan(expression, (Comparable) value));
            }
            
            break;
          case "LT":
            if (javaTypeName.equals(TYPE_DATE)) {
              try {
                // 小于最小值
                predicates
                    .add(cb.lessThan(expression, new SimpleDateFormat(DATE_FORMAT).parse(value.toString() + TIME_MAX)));
              } catch (ParseException e) {
                throw new RuntimeException("日期格式化失败!");
              }
            } else {
              predicates.add(cb.lessThan(expression, (Comparable) value));
            }
            
            break;
        }
      } catch (ParseException e) {
        throw new RuntimeException("日期格式化失败!");
      }
    }
  }
}
