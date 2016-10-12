package com.wangge.buzmgt.income.main.repository;/**
 * Created by ChenGuop on 2016/10/11.
 */

/**
 * 自定义查询售后冲减
 * CustomRepository
 *
 * @author ChenGuop
 * @date 2016/10/11
 */
public interface CustomRepository {
	/**
	 * 根据规则类型和规则Id总查询数量
	 * @param ruleId
	 * @param ruleType
	 * @return
	 */
	Long countByRuleIdAndRuleType(Long ruleId,Integer ruleType);

	/**
	 * 根据用户ID和 规则类型 和 规则Id 总查询数量
	 * @param ruleId
	 * @param ruleType
	 * @param userId
	 * @return
	 */
	Long countByRuleIdAndRuleTypeAndUserId(Long ruleId,Integer ruleType,String userId);

}
