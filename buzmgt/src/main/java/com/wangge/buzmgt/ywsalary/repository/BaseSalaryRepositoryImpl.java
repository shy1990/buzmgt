package com.wangge.buzmgt.ywsalary.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.wangge.buzmgt.ywsalary.entity.BaseSalaryUser;

public class BaseSalaryRepositoryImpl implements BaseSalaryRepositoryCustom{

  @PersistenceContext
  private EntityManager em;
  
  @Override
  public List<BaseSalaryUser> getStaySetSalesMan() {
    String sql="select a.user_id ,a.truename from SYS_SALESMAN a  where a.user_id not in(SELECT b.user_id FROM SYS_BASE_SALARY b)";
    Query query = em.createNativeQuery(sql,BaseSalaryUser.class);
    return query.getResultList();
  }

}
