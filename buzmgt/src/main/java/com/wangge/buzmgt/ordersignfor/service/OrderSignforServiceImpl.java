package com.wangge.buzmgt.ordersignfor.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class OrderSignforServiceImpl implements OrderSignforService {

 

  @PersistenceContext  
  private EntityManager em; 
  @Autowired
  private OrderSignforRepository orderSignforRepository;
  
  
  @Override
  public void updateOrderSignfor(OrderSignfor xlsOrder) {
    orderSignforRepository.save(xlsOrder);
    
  }

  @Override
  public OrderSignfor findByOrderNo(String orderNo) {
    return  orderSignforRepository.findByOrderNo(orderNo);
  }
  
  @Override
  public List<OrderSignfor> findAll(){
    return orderSignforRepository.findAll(); 
  }


  @Override
  public Long findCount() {
    return orderSignforRepository.count();
  }
  @Override
  public Page<OrderSignfor> getOrderSingforList(Pageable pageRequest){
    return orderSignforRepository.findAll(pageRequest);
  }


  @Override
  public Page<OrderSignfor> getMemberSignforList(OrderSignfor osf,int pageNum,String startTime,String endTime) {
    // TODO Auto-generated method stub
    String sql="select a.*, u.TRUENAME from(select * from SYS_ORDER_SIGNFOR t where t.CUSTOM_SIGNFOR_EXCEPTION='1') a "+
              "left join SYS_SALESMAN u on a.USER_ID=u.USER_ID";
    if((!"".equals(startTime))&&startTime!=null){
      sql+=" and a.CREAT_TIME>=to_date('"+startTime+"','yyyy-MM-dd')";
      if((!"".equals(endTime))&&startTime!=null){
        sql+=" and a.CREAT_TIME <= to_date('"+endTime+"','yyyy-MM-dd')";
      }
    }
    System.out.println(sql);
    Query q = em.createNativeQuery(sql,OrderSignfor.class);  
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* 7);
    q.setMaxResults(7);
    System.out.println(q.getResultList());
    List<OrderSignfor> list = new ArrayList<OrderSignfor>();
    for(Object obj : q.getResultList()){
      OrderSignfor orderSignfor =(OrderSignfor)obj;
      list.add(orderSignfor);
    }
    Page<OrderSignfor> page = new PageImpl<OrderSignfor>(list,new PageRequest(pageNum,7),count);   
    return page;  
    
  }

  @Override
  public Page<OrderSignfor> getYwSignforList(OrderSignfor osf, int pageNum, String startTime, String endTime,String timesGap) {
    // TODO Auto-generated method stub
    String sql="select * from SYS_ORDER_SIGNFOR t where ROUND(TO_NUMBER(t.YEWU_SIGNFOR_TIME - t.CREAT_TIME )*24)>="+timesGap;
    if((!"".equals(startTime))&&startTime!=null){
      sql+=" and t.CREAT_TIME>=to_date('"+startTime+"','yyyy-MM-dd')";
      if((!"".equals(endTime))&&startTime!=null){
        sql+=" and t.CREAT_TIME <= to_date('"+endTime+"','yyyy-MM-dd')";
      }
    }
    Query q = em.createNativeQuery(sql,OrderSignfor.class);  
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* 7);
    q.setMaxResults(7);
    System.out.println(q.getResultList());
    List<OrderSignfor> list = new ArrayList<OrderSignfor>();
    for(Object obj : q.getResultList()){
      OrderSignfor orderSignfor =(OrderSignfor)obj;
      
      list.add(orderSignfor);
    }
    Page<OrderSignfor> page = new PageImpl<OrderSignfor>(list,new PageRequest(pageNum,7),count);   
    return page;  
  }


  @Override
  public Page<OrderSignfor> findByCustomSignforException(String status, Pageable pageRequest) {
    // TODO Auto-generated method stub
    return orderSignforRepository.findByCustomSignforException("1", pageRequest);
  }


  @Override
  public Page<OrderSignfor> findByCustomSignforExceptionAndCreatTimeBetween(String status, String startTime,
      String endTime, Pageable pageRequest) {
    // TODO Auto-generated method stub
    return orderSignforRepository.findByCustomSignforExceptionAndCreatTimeBetween(status, startTime, endTime, pageRequest);
  }

  @Override
  public List<OrderSignfor> findAll(Map<String, Object> searchParams) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OrderSignfor> spec = orderSignforSearchFilter(filters.values(), OrderSignfor.class);
    return orderSignforRepository.findAll(spec);
  }

  @Override
  public Page<OrderSignfor> getOrderSingforList(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
    Specification<OrderSignfor> spec = orderSignforSearchFilter(filters.values(), OrderSignfor.class);

    return orderSignforRepository.findAll(spec,pageRequest);
  }
  
  
  private static <T> Specification<OrderSignfor> orderSignforSearchFilter(final Collection<SearchFilter> filters,
      final Class<OrderSignfor> entityClazz) {

    return new Specification<OrderSignfor>() {

      private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

      private final static String TIME_MIN = " 00:00:00 000";

      private final static String TIME_MAX = " 23:59:59 999";

      private final static String TYPE_RED_ENVELOP_TYPE = "com.wangge.buzmgt.ordersignfor.entity.OrderSignfor";

      private final static String TYPE_DATE = "java.util.Date";

      @SuppressWarnings({ "rawtypes", "unchecked" })
      @Override
      public Predicate toPredicate(Root<OrderSignfor> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
              } else if (javaTypeName.equals(TYPE_RED_ENVELOP_TYPE)) {
                String type = filter.value.toString();

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
              if (javaTypeName.equals(TYPE_RED_ENVELOP_TYPE)) {
                String type = filter.value.toString();

                predicates.add(cb.notEqual(expression, filter.value));
              } else {
                predicates.add(cb.notEqual(expression, filter.value));
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
  

 }
