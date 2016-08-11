package com.wangge.buzmgt.receipt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;
import com.wangge.buzmgt.receipt.entity.RemarkStatusEnum;
import com.wangge.buzmgt.receipt.repository.OrderReceiptRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class OrderReceiptServiceImpl implements OrderReceiptService {

  @PersistenceContext
  private EntityManager em;
  @Autowired
  private OrderReceiptRepository orderReceiptRepository;

  @Autowired
  private OrderSignforService orderSignforService;
  @Autowired
  private RegionService regionService;
  @Autowired
  private SalesManService salesManService;

  // @Override
  // public ReceiptRemark findByOrder(OrderSignfor orderNo) {
  // return orderReceiptRepository.findByOrder(orderNo);
  // }

  @Override
  public List<ReceiptRemark> findAll() {
    return orderReceiptRepository.findAll();
  }

  @Override
  public Long findCount() {
    return orderReceiptRepository.count();
  }

  @Override
  public List<ReceiptRemark> findAll(Map<String, Object> searchParams) {
    regionService.disposeSearchParams("salesmanId",searchParams);
    salesManService.disposeSearchParams("salesmanId", searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<ReceiptRemark> spec = orderReceiptSearchFilter(filters.values(), ReceiptRemark.class);
    List<ReceiptRemark> remarkedList = orderReceiptRepository.findAll(spec);
    remarkedList.forEach(remark -> {
      remark.setOrder(orderSignforService.findByOrderNo(remark.getOrderno()));
    });
    return remarkedList;
  }

  @Override
  public Page<ReceiptRemark> getReceiptRemarkList(Map<String, Object> searchParams, Pageable pageRequest) {
    regionService.disposeSearchParams("salesmanId", searchParams);
    salesManService.disposeSearchParams("salesmanId", searchParams);
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<ReceiptRemark> spec = orderReceiptSearchFilter(filters.values(), ReceiptRemark.class);

    Page<ReceiptRemark> receiptRemarkPage = orderReceiptRepository.findAll(spec, pageRequest);
    receiptRemarkPage.getContent().forEach(receiptRemark -> {
      receiptRemark.setOrder(orderSignforService.findByOrderNo(receiptRemark.getOrderno()));
    });

    return receiptRemarkPage;
  }

  private static <T> Specification<ReceiptRemark> orderReceiptSearchFilter(final Collection<SearchFilter> filters,
      final Class<ReceiptRemark> entityClazz) {

    return new Specification<ReceiptRemark>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_ORDERSIGNFOR_TYPE = "com.wangge.buzmgt.receipt.entity.RemarkStatusEnum";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<ReceiptRemark> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
            case IN:
              predicates.add(cb.in(expression).value(filter.value));
              
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

  /**
   * 查询未报备订单列表
   * 
   * @param searchParams
   * @return
   */
  @SuppressWarnings("deprecation")
  @Override
  public List<OrderSignfor> getReceiptNotRemark(Map<String, Object> searchParams) {
    String status = "";
    String startTime = "";
    String endTime = "";
    String orderNo ="";
    String regionId ="";
    regionService.disposeSearchParams("userId",searchParams);
    startTime = (String) searchParams.get("GTE_createTime");
    endTime = (String) searchParams.get("LTE_createTime");
    status = (String) searchParams.get("EQ_status");
    orderNo =(String) searchParams.get("EQ_orderNo");
    regionId=(String) searchParams.get("ORMLK_salesmanId");
    List<OrderSignfor> list = orderSignforService.getReceiptNotRemarkList(status, startTime, endTime,orderNo,regionId);
    List<OrderSignfor> notRemarkList = new ArrayList<OrderSignfor>();
    //TODO 
    String timesGap = "24:00";
    String[] timesGapAry = timesGap.split(":");
    // 获取当前时间
    Date now = new Date();
    list.forEach(notRemark -> {
      // 截止时间
      Date abortTime = notRemark.getYewuSignforTime();
      abortTime.setHours(Integer.parseInt(timesGapAry[0]));
      abortTime.setMinutes(Integer.parseInt(timesGapAry[1]));
      if(notRemark.getCustomSignforTime() != null && notRemark.getYewuSignforTime() != null){
        notRemark.setAging(DateUtil.getAging(notRemark.getCustomSignforTime().getTime()-notRemark.getYewuSignforTime().getTime()));
      }
      if (notRemark.getCustomSignforTime() != null) {
        // 付款超时
        if (notRemark.getCustomSignforTime().getTime() > abortTime.getTime()) {
          notRemarkList.add(notRemark);
        }

      } else {
        // 超时未付款
        if (now.getTime() > abortTime.getTime()) {
          notRemarkList.add(notRemark);
        }
      }

    });
    return notRemarkList;
  }

  @Override
  public List<OrderSignfor> getCashList(Map<String, Object> searchParams) {
    // TODO Auto-generated method stub
    String status,orderNo;
    status = (String) searchParams.get("EQ_status");
    searchParams.remove("EQ_status");
    orderNo =(String) searchParams.get("EQ_orderNo");
    List<OrderSignfor> cashList=orderSignforService.getReceiptCashList(searchParams);
    List<OrderSignfor> cashForParm=new ArrayList<>();
    if(StringUtils.isEmpty(orderNo) && StringUtils.isNotEmpty(status)){
//      UnPay("0","未付款"),OverPay("1","已付款"),UnPayLate("2","超时未付款"),OverPayLate
      switch (status) {
      case "UnPay":
        cashList.forEach(cash->{
          if(cash.getPayDate()==null){
            cashForParm.add(cash);
          }
        });
        break;
      case "OverPay":
        cashList.forEach(cash->{
          if(cash.getPayDate()!=null){
            cashForParm.add(cash);
          }
        });
        break;
      case "UnPayLate":
      //TODO  某一个时间点（如12:00）拓展后期后台设置;
        String timesGap = "9:00";
        String[] times=timesGap.split(":");
        cashList.forEach(cash->{
          
          if(cash.getPayDate()==null){
            
            cashForParm.add(cash);
          }
        });
        break;

      default:
        break;
      }
      
      
    }
    return cashList;
  }

}
