package com.wangge.buzmgt.achieve.repository;

import java.math.BigDecimal;
import java.util.*;

import com.wangge.buzmgt.achieveaward.entity.Award;
import com.wangge.buzmgt.achieveaward.entity.AwardIncome;
import com.wangge.buzmgt.achieveaward.repository.AwardIncomeRepository;
import com.wangge.buzmgt.achieveaward.repository.AwardRepository;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.util.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.Achieve.AchieveStatusEnum;
import com.wangge.buzmgt.achieveset.repository.AchieveIncomeRepository;
import com.wangge.buzmgt.achieveset.repository.AchieveRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.entity.RewardPunishRule;
import com.wangge.buzmgt.plan.repository.MachineTypeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class AchieveRepositoryTest {
  @Autowired
  private AchieveRepository achieveRepository;
  @Autowired
  private AwardRepository awardRepository;
  @Autowired
  private AwardIncomeRepository awardIncomeRepository;
  @Autowired
  private AchieveIncomeRepository achieveIncomeRepository;
  @Autowired
  private MachineTypeRepository machineTypeRepository;

  @Test
  public void save(){
    try {
      
      Achieve achieve = new Achieve();
      //组装规则
      Set<RewardPunishRule> rprs=new HashSet<>();
      for(int i=0;i<3;i++){
        RewardPunishRule rewardPunishRules = new RewardPunishRule();
        rewardPunishRules.setFlag(FlagEnum.NORMAL);
        rewardPunishRules.setMax((i+1)*500);
        rewardPunishRules.setMoney((i+1)*2f);
        rewardPunishRules.setType(PlanTypeEnum.ACHIEVE);
        rprs.add(rewardPunishRules);
      }
//      List<GroupNumber> groupNumbers = new ArrayList<>();
//      for(int i=0;i<5;i++){
//        GroupNumber groupNumber = new GroupNumber();
//        List<GroupUser> groupUsers = new ArrayList<>();
//        for(int j=0;j<i;j++){
//          GroupUser groupUser = new GroupUser();
//          groupUser.setUserId(i+"-"+j);
//          groupUsers.add(groupUser);
//        }
//        groupNumber.setGroupUsers(groupUsers);
//        groupNumber.setFlag(FlagEnum.NORMAL);
//        groupNumber.setGroupName("A"+i);
//        groupNumber.setType(PlanTypeEnum.ACHIEVE);
//        groupNumbers.add(groupNumber);
//      }
      Date now = new Date();
      achieve.setAuditor("ceshi");;
//      achieve.setBrandId("23523");
//      achieve.setGoodId("sadasd212");
      achieve.setStartDate(now);
      achieve.setEndDate(now);
      achieve.setCreateDate(now);
      achieve.setIssuingDate(now);
      MachineType machineType = machineTypeRepository.findOne("lj");
      achieve.setMachineType(machineType);
      achieve.setStatus(AchieveStatusEnum.OVER);
      
      achieve.setRewardPunishRules(rprs);
//      achieve.setGroupNumbers(groupNumbers);
      
      achieveRepository.save(achieve);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void find(){
    try {
      Achieve achieve=achieveRepository.findOne(4L);
//      achieveRepository.delete(achieve);
	    System.out.println(achieve);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Test
  public void test1(){
    BigDecimal money=achieveIncomeRepository.sumMoneyByAchieveIdAndStatus(1L, 0);
    System.out.println(money);
  }
  @Test
  public void test2(){
    Long count = achieveIncomeRepository.countByAchieveIdAndUserId(7L, "A3703021250");
    System.out.println(count);
  }

  @Test
  public void test3(){
  	String [] goodIds = new String[]{"8d869d9e086d4afe8ca151995eb557fc","af5314c1d73b47b19233756053ac8503","a13221f975a74dcda2f39070b2fdd113"};
  	List<AwardIncome> awardIncomes = awardIncomeRepository.findOrderByUserIdAndGoodsAndPayDate("A371602210",goodIds, DateUtil.string2Date("2016-08-15"),DateUtil.string2Date("2016-10-15"));
  }
  @Test
  public void test4(){
  	Award award = awardRepository.findByAwardId(10L);
	  System.out.println(award);
  }
}
