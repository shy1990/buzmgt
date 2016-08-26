package com.wangge.buzmgt.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.plan.entity.RewardPunishRule;

/**
 * 
* @ClassName: RewardPunishRuleRepository
* @Description: 奖罚规则设置持久化层
* @author ChenGuop
* @date 2016年8月24日 下午2:15:09
*
 */
public interface RewardPunishRuleRepository extends JpaRepository<RewardPunishRule, Long> {

}
