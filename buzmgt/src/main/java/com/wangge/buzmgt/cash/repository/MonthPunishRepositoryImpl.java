package com.wangge.buzmgt.cash.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.wangge.buzmgt.cash.entity.MonthPunish;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public class MonthPunishRepositoryImpl implements MonthPunishRepositoryCustom {
  
  @PersistenceContext
  private EntityManager em;

  @Override
  public List<MonthPunish> findAllISNotCash(Date minDate, Date maxDate) {
    String sql="select m.* ";
    Query query = em.createNativeQuery(sql,OrderSignfor.class);
    
    return query.getResultList();
  }

}
