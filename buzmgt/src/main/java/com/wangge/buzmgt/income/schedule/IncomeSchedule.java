package com.wangge.buzmgt.income.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.schedule.service.JobService;
import com.wangge.buzmgt.income.ywsalary.service.BaseSalaryService;
import com.wangge.buzmgt.log.util.LogUtil;

/**
 * 收益计算执行的定时器 <br/>
 * date: 2016年9月3日 下午6:31:03 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Component
@EnableScheduling
public class IncomeSchedule {
  @Autowired
  JobService jobService;
  @Autowired
  MainIncomeService mainIncomeService;
  @Autowired
  BaseSalaryService basesalaryService;
  
  /*
   * 28号23点1分执行<br/> 初始化下个月的薪资
   */
  @Scheduled(cron = " 0 1 23 28 * ? ")
  public void initMonthIncome() {
    jobService.initMonthIncome();
  }
  /*
   * 每天23点14分执行删除任务
   */
  @Scheduled(cron = " 20 59 23 * * ? ")
  public void doUserDel() {
    jobService.doUserDel();
  }
  
  /**
   * 每月1日2点1分执行. <br/>
   * 重新计算本月有多个记录的业务员的基本工资
   * 
   * @since JDK 1.8
   */
  @Scheduled(cron = " 0 1 2 1 * ? ")
  public void calculateMonthIncome() {
    try {
      basesalaryService.calcuThisMonthSalarys();
    } catch (Exception e) {
      LogUtil.error("月初计算本月工资出错", e);
    }
  }
  
  /**
   * 每天0点30分执行;<br/>
   * 计算昨天的油补
   * 
   * @author yangqc
   * @since JDK 1.8
   */
  @Scheduled(cron = " 0 30 0 * * ? ")
  public void initDailyOilCost() {
    mainIncomeService.calculateOil();
  }
  /**
   * 计算上个月售后收件冲减
   * */
  @Scheduled(cron="0 1 23 9 * ? ")
  public void initShouHedge(){
    
  }
  /**计算总和
   * 
   * */
 public void calIncomePerMonth(){
   
 } 
}
