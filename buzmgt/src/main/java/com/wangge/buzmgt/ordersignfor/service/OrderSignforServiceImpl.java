package com.wangge.buzmgt.ordersignfor.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
import com.wangge.buzmgt.saojie.entity.Saojie;

@Service
public class OrderSignforServiceImpl implements OrderSignforService {

  @PersistenceContext  
  private EntityManager em; 
  @Autowired
  private OrderSignforRepository orderSignforRepository;
  
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
  public Page<OrderSignfor> getOrderSingforList(Map<String, Object> searchParams, Pageable pageRequest) {
    // TODO Auto-generated method stub

//    Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//    Specification<OrderSignfor> spec = tradingRecordSearchFilter(filters.values(), Trading.class);

    return orderSignforRepository.findAll(pageRequest);
  }

 }
