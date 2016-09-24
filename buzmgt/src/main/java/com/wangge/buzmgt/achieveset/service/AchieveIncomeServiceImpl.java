package com.wangge.buzmgt.achieveset.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.achieveset.repository.AchieveIncomeRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.GroupNumber;
import com.wangge.buzmgt.plan.entity.GroupUser;
import com.wangge.buzmgt.plan.entity.RewardPunishRule;

/**
 * 
* @ClassName: AchieveIncomeServiceImpl
* @Description: 达量收益业务处理实现
* @author ChenGuop
* @date 2016年9月23日 下午3:34:53
*
 */
public class AchieveIncomeServiceImpl implements AchieveIncomeService {
  
  @Autowired
  private AchieveIncomeRepository air;
  
  @Override
  public Long countByAchieveId(Long achieveId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Long countByAchieveIdAndUserId(Long achieveId, String userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<AchieveIncome> findAll(Map<String, Object> spec, Sort sort) {
    return null;
  }

  @Override
  public List<AchieveIncome> save(List<AchieveIncome> achieveIncomes) {
    return air.save(achieveIncomes);
  }

  @Override
  public AchieveIncome save(AchieveIncome ai) {
    return air.save(ai);
  }

  @Override
  public boolean createAchieveIncomeBy(List<Map<String, Object>> maps, String orderNo, String userId) {
    try {
      maps.forEach(map->{
       String goodId = (String) map.get("goodId");
       Achieve ac = (Achieve) map.get("rule");
       Integer num = (Integer) map.get("num");
       //计算收益 
       Float money = disposeAchieveIncome(ac, userId , num);
       AchieveIncome achieveIncome = new AchieveIncome();
       achieveIncome.setAchieveId(ac.getAchieveId());
       achieveIncome.setUserId(userId);
       achieveIncome.setOrderNo(orderNo);
       achieveIncome.setFlag(FlagEnum.NORMAL);
       achieveIncome.setNum(num);
       achieveIncome.setGoodId(goodId);
       achieveIncome.setMoney(money);
       achieveIncome.setCreateDate(new Date());
      });
      
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      return false;
    }
    return false;
  }

  /**
   * 
  * @Title: disposeAchieveIncome 
  * @Description: 根据规则计算收益
  * 1.查询此商品当前的销量，
  * 2.根据销量匹配出提成金额
  * -- 查询是否有特殊分组，若有分组则在规则中 增加对应阶段区间量值;
  * 3.根据数量计算提成金额
  * 
  * @param @param ac
  * @param @param userId
  * @param @param num
  * @param @return    设定文件 
  * @return Float    返回类型 
  * @throws
   */
  private Float disposeAchieveIncome(Achieve ac, String userId, Integer num) {
    //获取当前商品当前规则的销量；
    Integer nowNumber = Integer.valueOf(countByAchieveIdAndUserId(ac.getAchieveId(), userId).toString()) ;
    Integer firstAdd = 0;
    Integer secondAdd = 0;
    Integer thirdAdd = 0;
    //查询特殊分组人员增量
    for(GroupNumber group : ac.getGroupNumbers()){
      for(GroupUser user : group.getGroupUsers()){
        //存在这个组的userId
        if(user.getUserId()!=null && user.getUserId().equals(userId)){
          firstAdd = group.getNumberFirstAdd();
          secondAdd = group.getNumberSecondAdd();
          thirdAdd = group.getNumberThirdAdd();
        }
      }
    }
    List<RewardPunishRule> rules = ac.getRewardPunishRules();
    
    Integer minAdd =null;
    Integer maxAdd =null;
    for(int i=0;i<rules.size();i++){
      switch (i) {
      case 0:
        minAdd = 0;
        maxAdd = firstAdd;
        break;
      case 1:
        minAdd = firstAdd;
        maxAdd = secondAdd;
        break;
      case 2:
        minAdd = secondAdd;
        maxAdd = thirdAdd;
        break;

      default:
        minAdd = thirdAdd;
        maxAdd = 0;
        break;
      }
      RewardPunishRule rule =  rules.get(i);
      Integer min = rule.getMin() + minAdd;
      Integer max = rule.getMax() + maxAdd;
      if((min==null && max>=nowNumber)||
          (nowNumber>rule.getMin()&&nowNumber<=rule.getMax())||
          (max==null && min < nowNumber)){
        rule.getMoney();
        break;
      }
    }
    
    return null;
  }

}
