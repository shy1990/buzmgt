package com.wangge.buzmgt.receipt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.tunnel.client.TunnelClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;
import com.wangge.buzmgt.receipt.entity.RemarkStatusEnum;
import com.wangge.buzmgt.receipt.repository.OrderReceiptRepository;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class OrderReceiptServiceImpl implements OrderReceiptService {

 

  @PersistenceContext  
  private EntityManager em; 
  @Autowired
  private OrderReceiptRepository orderReceiptRepository ;
  
  @Autowired
  private OrderSignforService orderSignforService ;
  
//  @Override
//  public ReceiptRemark findByOrder(OrderSignfor orderNo) {
//    return  orderReceiptRepository.findByOrder(orderNo);
//  }
  
  @Override
  public List<ReceiptRemark> findAll(){
    return orderReceiptRepository.findAll(); 
  }


  @Override
  public Long findCount() {
    return orderReceiptRepository.count();
  }

  @Override
  public List<ReceiptRemark> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<ReceiptRemark> spec = orderReceiptSearchFilter(filters.values(), ReceiptRemark.class);
    List<ReceiptRemark> remarkedList = orderReceiptRepository.findAll(spec);
    remarkedList.forEach(remark->{
      remark.setOrder(orderSignforService.findByOrderNo(remark.getOrderno()));
    });
    return remarkedList;
  }

  @Override
  public Page<ReceiptRemark> getReceiptRemarkList(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<ReceiptRemark> spec = orderReceiptSearchFilter(filters.values(), ReceiptRemark.class);

    Page<ReceiptRemark> receiptRemarkPage=orderReceiptRepository.findAll(spec,pageRequest);
    receiptRemarkPage.getContent().forEach(receiptRemark->{
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
      
      private final static String TYPE_ORDERSIGNFOR = "com.wangge.buzmgt.receipt.entity.ReceiptRemark";
      
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
              } else if(javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)){
                String status=filter.value.toString();
                if(RemarkStatusEnum.UnPay.toString().equals(status)){
                  filter.value=RemarkStatusEnum.UnPay;
                }
                if(RemarkStatusEnum.OverPay.toString().equals(status)){
                  filter.value=RemarkStatusEnum.OverPay;
                }
                if(RemarkStatusEnum.UnPayLate.toString().equals(status)){
                  filter.value=RemarkStatusEnum.UnPayLate;
                }
                if(RemarkStatusEnum.OverPayLate.toString().equals(status)){
                  filter.value=RemarkStatusEnum.OverPayLate;
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
              } else if(javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)){
                String status=filter.value.toString();
                if(RemarkStatusEnum.UnPay.toString().equals(status)){
                  filter.value=RemarkStatusEnum.UnPay;
                }
                if(RemarkStatusEnum.OverPay.toString().equals(status)){
                  filter.value=RemarkStatusEnum.OverPay;
                }
                if(RemarkStatusEnum.UnPayLate.toString().equals(status)){
                  filter.value=RemarkStatusEnum.UnPayLate;
                }
                if(RemarkStatusEnum.OverPayLate.toString().equals(status)){
                  filter.value=RemarkStatusEnum.OverPayLate;
                }
                predicates.add(cb.greaterThanOrEqualTo(expression,(Comparable) filter.value));
                  
              }else{
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
              if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {
                String type = filter.value.toString();

                predicates.add(cb.notEqual(expression, filter.value));
              } else {
                predicates.add(cb.notEqual(expression, filter.value));
              }

              break;
            case ISNULL:
              if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {

                predicates.add(cb.isNull(expression));
              }

              break;
            case NOTNULL:
              if (javaTypeName.equals(TYPE_ORDERSIGNFOR_TYPE)) {
                
                predicates.add(cb.isNull(expression));
              }
              
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
  public List<OrderSignfor> getReceiptNotRemark(Map<String, Object> searchParams) {
    // TODO Auto-generated method stub
    String status="";
    String startTime="";
    String endTime="";
    startTime = (String) searchParams.get("GTE_createTime");
    endTime = (String) searchParams.get("LTE_createTime");
    status=(String) searchParams.get("EQ_status");
    List<OrderSignfor> list = orderSignforService.getReceiptNotRemarkList(status, startTime, endTime);
    List<OrderSignfor> notRemarkList = new ArrayList<OrderSignfor>();
    String timesGap ="24:00";
    String[] timesGapAry=timesGap.split(":");
    //获取当前时间
    Date now=new Date();
    list.forEach(notRemark->{
      //截止时间
      Date abortTime = notRemark.getYewuSignforTime();
      abortTime.setHours(Integer.parseInt(timesGapAry[0]));
      abortTime.setMinutes(Integer.parseInt(timesGapAry[1]));
      if(notRemark.getCustomSignforTime()!=null){
        //付款超时
        if(notRemark.getCustomSignforTime().getTime()>abortTime.getTime()){
          notRemarkList.add(notRemark);
        }
        
      }else{
        //超时未付款
        if(now.getTime() > abortTime.getTime()){
          notRemarkList.add(notRemark);
        }
      }
      
    });
    return notRemarkList;
  }
  

 }
