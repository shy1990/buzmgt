package com.wangge.buzmgt.income.main.repository.impl;/**
 * Created by ChenGuop on 2016/10/11.
 */

import com.wangge.buzmgt.income.main.repository.CustomRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;

/**
 * 实现自定义查询售后冲减
 * HedgeCostRepositoryImpl
 *
 * @author ChenGuop
 * @date 2016/10/11
 */
@Repository
public class HedgeCostRepositoryImpl implements CustomRepository {
	@PersistenceContext
	private EntityManager em;

	@Override
	public Long countByRuleIdAndRuleType(Long ruleId, Integer ruleType) {
		String sql = "select sum(sh.SUM) from sys_income_shouhou_cost sc left join sys_income_shouhou_hedge sh " +
						"on sc.HEDGE_ID=sh.ID where sc.RULETYPE = " + ruleType + " and sc.RULE_ID= " + ruleId;
		Query query = em.createNativeQuery(sql);
		Object count = query.getSingleResult();
		count = (count == null ? 0L : count);
		System.out.println(count);
		return Long.valueOf(count.toString());
	}

	@Override
	public Long countByRuleIdAndRuleTypeAndUserId(Long ruleId, Integer ruleType, String userId) {
		String sql = "select sum(sh.SUM) from sys_income_shouhou_cost sc left join sys_income_shouhou_hedge sh " +
						"on sc.HEDGE_ID=sh.ID where sc.RULETYPE = " + ruleType + " and sc.RULE_ID= " + ruleId +
						"and sc.USER_Id=" + userId;
		Query query = em.createNativeQuery(sql);
		Object count = query.getSingleResult();
		count = (count == null ? 0L : count);
		return Long.valueOf(count.toString());
	}
}
