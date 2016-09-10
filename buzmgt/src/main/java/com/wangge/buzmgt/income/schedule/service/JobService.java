package com.wangge.buzmgt.income.schedule.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.income.main.repository.IncomeSubRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.schedule.entity.Jobtask;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;

/**
 * 收益定时任务执行逻辑 date: 2016年9月3日 下午6:28:44 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Service
public class JobService {
  @Autowired
  private MainIncomeRepository mainIncomeRep;
  @Autowired
  private MainIncomePlanRepository mainPlanRep;
  @Autowired
  JobRepository jobRep;
  @Autowired
  IncomeSubRepository incomeSubRep;
  @Transactional(rollbackFor = Exception.class)
  public void initMonthIncome() {
    mainIncomeRep.initMonthSalary();
  }
  public void doTask(){
    List<Jobtask> jobList=  jobRep.defaltfindAll(new Date());
    for(Jobtask jobtask:jobList){
      int type=jobtask.getType();
      switch(type){
        //删除一个收益主方案
         case 0:
           deleteIncomeMainPlan(jobtask);
      }
    }
  }
  /** 
    * 1.找到所有的当天的已付款的订单并按业务员和方案类型分组
    * 2.减去每个业务员的当天收益,并重新结算工资.
    * 3.将当天的订单收益状态更新为1(不可用);
    * @since JDK 1.8 
    */  
  private void deleteIncomeMainPlan(Jobtask jobtask) {
    Long planId=jobtask.getPlanId();
    
//    incomeSubRep.modifyFlag(usList);
  }
}
