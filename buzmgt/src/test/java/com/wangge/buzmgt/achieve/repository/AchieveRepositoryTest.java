package com.wangge.buzmgt.achieve.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.wangge.buzmgt.BuzmgtApplication;
import com.wangge.buzmgt.achieve.entity.Achieve;
import com.wangge.buzmgt.achieve.entity.RewardPunishRule;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.repository.MachineTypeRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BuzmgtApplication.class)
@WebAppConfiguration
public class AchieveRepositoryTest {
  @Autowired
  private AchieveRepository achieveRepository;
  @Autowired
  private MachineTypeRepository machineTypeRepository;

  @Test
  public void save(){
    try {
      
      Achieve achieve = new Achieve();
      //组装规则
      List<RewardPunishRule> rprs=new ArrayList<>();
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
      achieve.setBrandId("23523");
      achieve.setGoodId("sadasd212");
      achieve.setStartDate(now);
      achieve.setEndDate(now);
      achieve.setCreateDate(now);
      achieve.setIssuingDate(now);
      MachineType machineType = machineTypeRepository.findOne("lj");
      achieve.setMachineType(machineType);
      achieve.setStatus(PlanTypeEnum.ACHIEVE);
      
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
      
      Achieve achieve=achieveRepository.findOne(1l);
      System.out.println(achieve);
      List<RewardPunishRule> punishRules = achieve.getRewardPunishRules();
      System.out.println(punishRules);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void delete(){
    
  }
}
