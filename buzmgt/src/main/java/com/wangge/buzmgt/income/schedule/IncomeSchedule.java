package com.wangge.buzmgt.income.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangge.buzmgt.income.main.service.HedgeService;
import com.wangge.buzmgt.income.main.service.IncomeErrorService;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.schedule.service.JobService;
import com.wangge.buzmgt.income.ywsalary.service.BaseSalaryService;
import com.wangge.buzmgt.log.util.LogUtil;

/**
 * 收益计算执行的定时器 <br/>
 * date: 2016年9月3日 下午6:31:03 <br/>
 * TODO 需讨论:业务离职之后如何算: 收益,售后,工资,离职有新业务员接手,新业务的如何算(冲减,售后 ).
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
  @Autowired
  HedgeService hedgeService;
  @Autowired
  IncomeErrorService errorService;
  
  /**
   * 1号0点0分1秒执行<br/>
   * 初始化本月的薪资;这个人这月已没有收益.
   */
  @Scheduled(cron = " 1 0 0 1 * ? ")
  public void initMonthIncome() {
    jobService.initMonthIncome();
  }
  /**
   * 每天11点计算当月的收益(主要是业务佣金)
   * */
  @Scheduled(cron = " 1 1 23 * * ? ")
  public void calBusinessSal(){
    mainIncomeService.calBusinessSal();
  } 
  /**
   * 每天23点14分执行删除任务
   */
  @Scheduled(cron = " 20 59 23 * * ? ")
  public void doUserDel() {
    jobService.doUserDel();
  }
  
  /**
   * 每月1日2点1分0秒执行. <br/>
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
   * 每月8号23点,<br/>
   * 计算上个月售后收件冲减
   */
  @Scheduled(cron = "0 1 23 8 * ? ")
  public void initShouHedge() {
    hedgeService.calculateHedge();
  }
  
  /**
   * 计算总和 9号23点开始计算
   */
  @Scheduled(cron = "0 1 23 9 * ? ")
  public void calIncomePerMonth() {
    mainIncomeService.calIncomePerMonth();
  }
  
  /**
   * 处理每天需要做的任务 1点10分
   */
  @Scheduled(cron = "0 10 1 * * ? ")
  public void doChange() {
    jobService.doTask();
  }
}
