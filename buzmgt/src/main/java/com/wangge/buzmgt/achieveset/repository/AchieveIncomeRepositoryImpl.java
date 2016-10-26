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
	public BigDecimal sumMoneyByAchieveIdAndStatus(Long achieveId, Integer status) {
		String sql = "SELECT SUM(aai.MONEY) as MONEY FROM SYS_INCOME_ACHIEVE_SET aai where aai.ACHIEVE_ID = " + achieveId +
						"AND aai.status =" + status +" and aai.flag = 'NORMAL' ";
		Query query = em.createNativeQuery(sql);
		return (BigDecimal) query.getSingleResult();
	}

	@Override
	public BigDecimal sumMoneyByAchieveIdAndUserIdAndStatus(Long achieveId, String userId, Integer status) {
		String sql = "SELECT SUM(aai.MONEY) MONEY FROM SYS_INCOME_ACHIEVE_SET aai where aai.ACHIEVE_ID = " + achieveId + " and "
						+ "aai.USER_ID = '" + userId + "' AND  aai.STATUS= " + status +" and aai.flag = 'NORMAL'";
		Query query = em.createNativeQuery(sql);
		return (BigDecimal) query.getSingleResult();
	}


}
