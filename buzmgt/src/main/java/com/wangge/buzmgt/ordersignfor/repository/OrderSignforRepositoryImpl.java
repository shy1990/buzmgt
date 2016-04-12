package com.wangge.buzmgt.ordersignfor.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public class OrderSignforRepositoryImpl implements OrderSignforRepositoryCustom {

  @PersistenceContext
  private EntityManager em;
  
  @Override
  public List<OrderSignfor> getReceiptNotRemarkList(String status, String startTime, String endTime) {
    // TODO Auto-generated method stub
    
    String sql="select a.* from BIZ_ORDER_SIGNFOR a left JOIN BIZ_UNPAYMENT_REMARK b on a.ORDER_NO=b.ORDERNO where b.ORDERNO is null and a.YEWU_SIGNFOR_TIME is not null and a.ORDER_STATUS in (0,2,3)";
    if(startTime !=null && startTime != ""){
      sql+="and a.CREAT_TIME >= TO_DATE('%s', 'YYYY-MM-DD')";
      sql = String.format(sql, startTime);
    }
    if(endTime != null && endTime != ""){
      sql+="and a.CREAT_TIME <= TO_DATE('%s', 'YYYY-MM-DD')";
      sql = String.format(sql, endTime);
    }
    Query query = em.createNativeQuery(sql,OrderSignfor.class);
    return query.getResultList();
  }

}
