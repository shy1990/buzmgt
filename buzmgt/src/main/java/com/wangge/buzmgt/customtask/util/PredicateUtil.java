package com.wangge.buzmgt.customtask.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

public class PredicateUtil {
  
  private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";
  private final static String YEAR_MONTH_DAY = "yyyy-MM-dd";
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
      Class<?> clssz = expression.getJavaType();
      String javaTypeName = clssz.getName();
      try {
        switch (filter) {
          case "EQ":
            if (javaTypeName.equals(TYPE_DATE)) {
              predicates.add(cb.equal(root.get(key), new SimpleDateFormat(YEAR_MONTH_DAY).parse(value.toString())));
            } else {
              // 如果是枚举类型,则转化为枚举实例
              if (clssz.isEnum()) {
                Object[] enums = clssz.getEnumConstants();
                predicates.add(cb.equal(root.get(key), enums[Integer.valueOf(value + "")]));
              } else {
                predicates.add(cb.equal(root.get(key), value));
              }
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
          case "ORE":
            /**
             * 查询HedgeVo 一个值查两个字段 'sc_ORE_shopName = orderNo_A37010506130
             */
            String[] parameter = ((String) value).split("_");
            Path<?> expression_ = root.get(parameter[0]);
            String value_ = parameter[1];
            Predicate p = cb.or(cb.equal(expression_, value_), cb.like(expression, "%" + value_ + "%"));
            predicates.add(p);
            
            break;
          case "ORLK":
            /**只支持字符串适配
             * sc_ORLK_userId-region = A37010506130
             */
            String[] keys = keyAndFilter.split("-");
            List<Predicate> orList = new ArrayList<>();
            for (int i = 0; i < keys.length; i++) {
              orList.add(cb.like(root.get(keys[i]), "%" + value + "%"));
            }
            Predicate p_LK = cb.or(orList.toArray(new Predicate[] {}));
            predicates.add(p_LK);
            
            break;
        }
      } catch (ParseException e) {
        throw new RuntimeException("日期格式化失败!");
      }
    }
  }
}
