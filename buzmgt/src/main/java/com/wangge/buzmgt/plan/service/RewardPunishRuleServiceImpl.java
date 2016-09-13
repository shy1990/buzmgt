package com.wangge.buzmgt.plan.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.plan.entity.RewardPunishRule;
import com.wangge.buzmgt.plan.repository.RewardPunishRuleRepository;

@Service
public class RewardPunishRuleServiceImpl implements RewardPunishRuleService {

  @Autowired
  private RewardPunishRuleRepository punishRuleRepository;
  @Override
  @Transactional
  public void delete(Long id) {
    punishRuleRepository.delete(id);
  }

  @Override
  @Transactional
  public void delete(List<RewardPunishRule> rewardPunishRules) {
    punishRuleRepository.delete(rewardPunishRules);
  }

}
