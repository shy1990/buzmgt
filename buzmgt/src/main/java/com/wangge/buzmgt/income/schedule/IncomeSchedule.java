package com.wangge.buzmgt.income.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangge.buzmgt.income.schedule.service.JobService;

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
  
  /*
   * 每月1号0点1分开始执行<br/> 初始化每个业务员的工资
   */
  @Scheduled(cron = " 0 1 0 1 * ? ")
  public void initMonthIncome() {
    jobService.initMonthIncome();
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
    jobService.initDailyOilCost();
  }
}
