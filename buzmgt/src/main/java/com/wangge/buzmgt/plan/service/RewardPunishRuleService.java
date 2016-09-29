package com.wangge.buzmgt.plan.service;

import java.util.List;

import com.wangge.buzmgt.plan.entity.RewardPunishRule;

public interface RewardPunishRuleService {

  void delete(Long id);

  void delete(List<RewardPunishRule> rewardPunishRules); 
}
