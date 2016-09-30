package com.wangge.buzmgt.achieveset.repository;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

@Repository
public class AchieveIncomeRepositoryImpl implements CustomRepository {

  @PersistenceContext
  private EntityManager em;

  @Override
  public BigDecimal sumMoneyByAchieveId(Long achieveId) {
    String sql = "SELECT SUM(aai.MONEY) as MONEY FROM SYS_ACHIEVE_AWARD_INCOME aai where aai.ACHIEVE_ID = "+ achieveId ;
    Query query = em.createNativeQuery(sql);
    return (BigDecimal) query.getSingleResult();
  }

  @Override
  public BigDecimal sumMoneyByAchieveIdAndUserId(Long achieveId, String userId) {
    String sql = "SELECT SUM(aai.MONEY) MONEY FROM SYS_ACHIEVE_AWARD_INCOME aai where aai.ACHIEVE_ID = "+ achieveId +" and "
        + "aai.USER_ID = '"+userId+"' ";
    Query query = em.createNativeQuery(sql);
    return (BigDecimal) query.getSingleResult();
  }
  
  
}
