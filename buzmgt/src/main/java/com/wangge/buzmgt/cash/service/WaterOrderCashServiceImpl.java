package com.wangge.buzmgt.cash.service;

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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderCash.WaterPayStatusEnum;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;
import com.wangge.buzmgt.cash.repository.WaterOrderCashRepository;
import com.wangge.buzmgt.cash.repository.WaterOrderDetailRepository;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.receipt.entity.RemarkStatusEnum;
import com.wangge.buzmgt.util.SearchFilter;
import com.wangge.buzmgt.util.excel.MapedExcelExport;

@Service
public class WaterOrderCashServiceImpl implements WaterOrderCashService {

  @Autowired
  private WaterOrderCashRepository waterOrderCashRepository;
  @Autowired
  private WaterOrderDetailRepository waterOrderDetailRepository;


  @Override
  public List<WaterOrderCash> findAll() {
    return waterOrderCashRepository.findAll();
  }

  @Override
  public List<WaterOrderCash> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<WaterOrderCash> spec = WaterOrderCashSearchFilter(filters.values(), WaterOrderCash.class);
    List<WaterOrderCash> waterOrderList = waterOrderCashRepository.findAll(spec);
    return waterOrderList;
  }

  @Override
  public Page<WaterOrderCash> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<WaterOrderCash> spec = WaterOrderCashSearchFilter(filters.values(), WaterOrderCash.class);
    Page<WaterOrderCash> waterOrderPage = waterOrderCashRepository.findAll(spec, pageRequest);

    return waterOrderPage;
  }

  @Override
  public WaterOrderDetail findByOrderNo(String cashId) {
    return waterOrderDetailRepository.findByCashId(cashId);
  }

  @Override
  public WaterOrderCash findBySerialNo(String serialNo) {
    return waterOrderCashRepository.findBySerialNo(serialNo);
  }

  @Override
  public void save(List<WaterOrderCash> waterOrders) {

    waterOrderCashRepository.save(waterOrders);
  }

  private static Specification<WaterOrderCash> WaterOrderCashSearchFilter(final Collection<SearchFilter> filters,
      final Class<WaterOrderCash> entityClazz) {

    return new Specification<WaterOrderCash>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_WATERPAYSTATUS_TYPE = "com.wangge.buzmgt.cash.entity.WaterOrderCash$WaterPayStatusEnum";

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
              } else if (javaTypeName.equals(TYPE_WATERPAYSTATUS_TYPE)) {
                String status = filter.value.toString();
                if (WaterPayStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = WaterPayStatusEnum.UnPay;
                }
                if (WaterPayStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = WaterPayStatusEnum.OverPay;
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

  
  @Override
  public void ExportSetExcel(List<WaterOrderCash> waterOrders, HttpServletRequest request,
      HttpServletResponse response) {
    List<Map<String, Object>> alList = new ArrayList<Map<String, Object>>();
    Map<String, Integer> sumMap = new HashMap<String, Integer>();
    waterOrders.forEach(waterOrder -> {
      String serialNo = waterOrder.getSerialNo();
      List<WaterOrderDetail> detail = new ArrayList<>();
      detail = waterOrder.getOrderDetails();
      detail.forEach(item->{
        Map<String, Object> objMap = new HashMap<>();
        OrderSignfor order = new OrderSignfor();
        order=item.getCash().getOrder();
        objMap.put("serialNo", serialNo);
        objMap.put("orderNo", order.getOrderNo());
        objMap.put("orderPrice", order.getOrderPrice());
        objMap.put("cashMoney", waterOrder.getCashMoney());
        objMap.put("status", waterOrder.getPayStatus());
        objMap.put("createDate", waterOrder.getCreateDate());
        alList.add(objMap);
        
        Integer sum = sumMap.get(serialNo);
        if (null == sum) {
          sumMap.put(serialNo, 1);
        } else {
          sumMap.put(serialNo, sum + 1);
        }
        
      });
      
    });
    List<Map<String, Object>> marginList = new ArrayList<Map<String, Object>>();
    int start = 0;
    int end = 0;
    for (Map.Entry<String, Integer> entry : sumMap.entrySet()) {
      // 流水单号合并单元格
      Map<String, Object> obMap = new HashMap<String, Object>();
      /*
       * int firstRow, int lastRow, int firstCol, int lastCol)
       */
      end = start + entry.getValue();
      if (entry.getValue() > 1) {
        obMap.put("firstRow", start + 1);
        obMap.put("lastRow", end);
        obMap.put("firstCol", 0);
        obMap.put("lastCol", 0);
        // obMap.put("value", entry.getKey());
        marginList.add(obMap);
        // 总金额合并
        Map<String, Object> obMap1 = new HashMap<String, Object>();
        obMap1.put("firstRow", start + 1);
        obMap1.put("lastRow", end);
        obMap1.put("firstCol", 3);
        obMap1.put("lastCol", 3);
        // obMap1.put("value", getRate(Integer.parseInt(entry.getKey()),
        // task));
        marginList.add(obMap1);
        
        Map<String, Object> obMap2 = new HashMap<String, Object>();
        obMap2.put("firstRow", start + 1);
        obMap2.put("lastRow", end);
        obMap2.put("firstCol", 4);
        obMap2.put("lastCol", 4);
        // obMap1.put("value", getRate(Integer.parseInt(entry.getKey()),
        // task));
        marginList.add(obMap2);
        
        Map<String, Object> obMap3 = new HashMap<String, Object>();
        obMap3.put("firstRow", start + 1);
        obMap3.put("lastRow", end);
        obMap3.put("firstCol", 5);
        obMap3.put("lastCol", 5);
        // obMap1.put("value", getRate(Integer.parseInt(entry.getKey()),
        // task));
        marginList.add(obMap3);
      }
      start = end;
    }
    String[] gridTitles_ = { "流水单号", "订单编号", "需付金额", "总金额", "状态", "日期" };
    String[] coloumsKey_ = { "serialNo", "orderNo", "orderPrice", "cashMoney", "status", "createDate" };
    MapedExcelExport.doExcelExport("流水单号.xls", alList, gridTitles_, coloumsKey_, request, response, marginList);
  }

}
