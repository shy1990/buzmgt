package com.wangge.buzmgt.cash.service;

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

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.cash.entity.Cash;
import com.wangge.buzmgt.cash.entity.Cash.CashStatusEnum;
import com.wangge.buzmgt.cash.repository.CashRepository;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class CashServiceImpl implements CashService {

  @Resource
  private CashRepository cashRepository;
  @Resource
  private RegionService regionService;
  @Resource
  private OrderSignforService orderSignforService;
  
  
  @Override
  public List<Cash> findAll(Map<String, Object> searchParams) {
    regionService.disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<Cash> spec = CashSearchFilter(filters.values(), Cash.class);
    Order order=new Order(Direction.DESC,"createDate");
    Sort sort=new Sort(order);
    List<Cash> cashList = cashRepository.findAll(spec,sort);
    disposeCashList(cashList);
    
    return cashList;
    
  }

  @Override
  public Page<Cash> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    regionService.disposeSearchParams("userId",searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<Cash> spec = CashSearchFilter(filters.values(), Cash.class);
    Page<Cash> cashPage = cashRepository.findAll(spec,pageRequest);
    disposeCashList(cashPage.getContent());
    return cashPage;
  }

  @SuppressWarnings("deprecation")
  public void disposeCashList(List<Cash> cashList){
  //TODO  某一个时间点（如12:00）拓展后期后台设置 超时时间点;
    String timesGap = "12:00";
    String[] times=timesGap.split(":");
    cashList.forEach(cash->{
      //去除无用数据
      cash.getOrder().getSalesMan().setUser(null);
      cash.getOrder().getSalesMan().setRegion(null);
      //判断是否超时
      Date createTime = cash.getCreateDate();
      Date checkDate=DateUtil.moveDate(createTime, 1);
      checkDate.setHours(Integer.parseInt(times[0]));
      checkDate.setMinutes(Integer.parseInt(times[1]));
      Date payTime = cash.getPayDate();
      if((payTime==null?new Date():payTime).getTime()>checkDate.getTime()){
        cash.setIsTimeOut("超时");
      }
    });
  }
  @Override
  public Cash findById(Long id) {
    return cashRepository.findOne(id);
  }

  @Override
  public List<Cash> findAllByParams(Map<String, Object> searchParams) {
    String orderNo = (String) searchParams.get("EQ_orderNo");
    String startTime=(String) searchParams.get("GTE_createTime");
    String endTime=(String) searchParams.get("LTE_createTime");
    searchParams.remove("GTE_createTime");
    searchParams.remove("LTE_createTime");
    if(!"".equals(startTime) || !"".equals(endTime)){
      searchParams.put("GTE_createDate", startTime);
      searchParams.put("LTE_createDate", endTime);
    }
    List<Cash> listAll=new ArrayList<>();
    if(StringUtils.isNotEmpty(orderNo)){
      OrderSignfor order = orderSignforService.findByOrderNo(orderNo);
      if(order==null){
        return listAll;
      }
      searchParams.remove("EQ_orderNo");
      searchParams.put("EQ_cashId", order.getId());
    }
    //未支付
    String unStatus=(String) searchParams.get("EQ_status");
    if("UnPay".equals(unStatus)){
      searchParams.remove("EQ_status");
      searchParams.put("LT_status","OverPay");
    }
    
    //超时UnPayLate(为付款超时)
    String status = (String) searchParams.get("GTE_status");
    searchParams.remove("GTE_status");
    listAll = findAll(searchParams);
    if(StringUtils.isNotEmpty(status)){
      
      List<Cash> cashList=new ArrayList<>();
      listAll.forEach(cash->{
        String msg=cash.getIsTimeOut();
        if("超时".equals(msg)){
          cashList.add(cash);
        }
      });
      return cashList;
    }
    return listAll;
  }

  private static Specification<Cash> CashSearchFilter(final Collection<SearchFilter> filters,
      final Class<Cash> entityClazz) {

    return new Specification<Cash>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_ORDERSIGNFOR_TYPE = "com.wangge.buzmgt.cash.entity.Cash$CashStatusEnum";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<Cash> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
              } else if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {
                String status = filter.value.toString();
                if (CashStatusEnum.UnPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.UnPay;
                }
                if (CashStatusEnum.OverPay.toString().equals(status)) {
                  filter.value = CashStatusEnum.OverPay;
                }
                predicates.add(cb.lessThan(expression, (Comparable)filter.value));
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
