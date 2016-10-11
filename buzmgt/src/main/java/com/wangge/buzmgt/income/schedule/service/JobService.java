package com.wangge.buzmgt.income.schedule.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.common.IncomeThreadPool;
import com.wangge.buzmgt.income.main.entity.CheckedEnum;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.IncomeSubRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.HedgeService;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.OrderGoods;
import com.wangge.buzmgt.income.main.vo.repository.OrderGoodsRepository;
import com.wangge.buzmgt.income.schedule.entity.Jobtask;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;

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
  private IncomeMainplanUsersRepository mainPlanUserRep;
  @Autowired
  private JobRepository jobRep;
  @Autowired
  private IncomeSubRepository incomeSubRep;
  @Autowired
  private MainPlanService mainPlanService;
  @Autowired
  OrderGoodsRepository orderGoodsRep;
  @Autowired
  SalesManRepository salesmanRep;
  @Autowired
  MainIncomeService mainIncomeService;
  @Autowired
  HedgeService hedgeService;
  
  @Transactional(rollbackFor = Exception.class)
  public void initMonthIncome() {
    mainIncomeRep.initMonthSalary();
  }
  
  public void doTask() {
    List<Jobtask> jobList = jobRep.defaltfindAll(new Date());
    for (Jobtask jobtask : jobList) {
      int type = jobtask.getType();
      try {
        switch (type) {
          // 删除一个收益主方案
          case 0:
            deleteIncomeMainPlan(jobtask);
            break;
          case 10:
            deleteIncomeMainPlanUser(jobtask);
            break;
          case 12:
            calIncomeMainPlanUser(jobtask);
          case 60:
            calhedgeAchieve(jobtask);
          default:
            break;
        }
        jobtask.setFlag(1);
      } catch (Exception e) {
        LogUtil.error("定时任务失败", e);
      }
    }
    jobRep.save(jobList);
  }
  /**
   * 计算达量设置,叠加奖励,达量奖励的收益
   * */
  private void calhedgeAchieve(Jobtask jobtask) {
    hedgeService.calculateAchieveHedge(jobtask.getExectime());
  }

  
  
  /**
   * 计算某个业务员新加入方案时的收益<br/>
   * 1.查出某天的订单,计算每天的订单
   */
  private void calIncomeMainPlanUser(Jobtask jobtask) {
    
    Date startTime = jobtask.getExectime();
    Date endTime = jobtask.getInserttime();
    Long planId = jobtask.getPlanId();
    String userId = jobtask.getSalesmanId();
    String regionId = salesmanRep.findOne(userId).getRegion().getId();
    
    Date endDay = DateUtil.string2Date(DateUtil.date2String(endTime, "yyyy-mm-dd"));
    String execMonth = DateUtil.getPreMonth(startTime, 0);
    String thisMonth = DateUtil.getPreMonth(new Date(), 0);
    if (!execMonth.equals(thisMonth)) {
      // 查到上月的工资记录
      MainIncome income = mainIncomeRep.findBySalesman_IdAndMonth(userId, execMonth);
      if (null != income && income.getState() == CheckedEnum.CHECKED) {
        startTime = DateUtil.getPreMonthDate(endTime, 0);
      }
    }
    int between = DateUtil.daysBetween(startTime, endDay);
    
    //计算查找每天的结算订单
    for (int i = 0; i <= between; i++) {
      Date startDate = DateUtil.moveDate(startTime, i);
      Date endDate = DateUtil.moveDate(startDate, 1);
      IncomeThreadPool.exServ.execute(new Runnable() {
        @Override
        public void run() {
          List<OrderGoods> goodList = orderGoodsRep.findByorderNoByDateAndSalesman(userId, startDate, endDate);
          mainIncomeService.caculatePayedOrder(userId, planId, startDate, goodList, regionId);
        }
      });
    }
    //计算最后一天的订单
    IncomeThreadPool.exServ.execute(new Runnable() {
      @Override
      public void run() {
        List<OrderGoods> goodList = orderGoodsRep.findByorderNoByDateAndSalesman(userId, endTime, endDay);
        mainIncomeService.caculatePayedOrder(userId, planId, endDay, goodList, regionId);
      }
    });
    
  }
  
  /**
   * 删除主方案的一个用户,要重新计算收益 <br/>
   * 删除收益<br/>
   * 
   * 
   */
  @Transactional(rollbackFor = Exception.class)
  private void deleteIncomeMainPlanUser(Jobtask jobtask) {
    Date execTime = jobtask.getExectime();
    String execMonth = DateUtil.getPreMonth(execTime, 0);
    String thisMonth = DateUtil.getPreMonth(new Date(), 0);
    Date endDate = jobtask.getInserttime();
    IncomeMainplanUsers user = mainPlanUserRep.findOne(jobtask.getKeyid());
    String userId = user.getSalesmanId();
    Long planId = user.getPlanId();
    if (!execMonth.equals(thisMonth)) {
      // 查到上月的工资记录
      MainIncome income = mainIncomeRep.findBySalesman_IdAndMonth(userId, execMonth);
      if (null != income && income.getState() == CheckedEnum.CHECKED) {
        execTime = DateUtil.getPreMonthDate(endDate, 0);
      }
    }
    delOrderByUserAndDate(execTime, userId, planId);
  }
  
  /**
   * 计算一个用户在某月被删除的订单. <br/>
   * 
   * @throws NumberFormatException
   * @since JDK 1.8
   */
  private void delOrderByUserAndDate(Date execTime, String userId, Long planId) {
    try {
      mainIncomeService.deleteSubIncome(planId, userId, execTime);
    } catch (Exception e) {
      LogUtil.info("删除订单收益出错,信息如下:planId-->" + planId + ";userId-->" + userId + ";execTime-->" + execTime);
    }
  }
  
  /**
   * 0:删除主方案:不在计算收益;<br/>
   * 1.删除删除日期当天的收益<br/>
   * TODO 达量叠加如何处理<br/>
   * 
   * @since JDK 1.8
   */
  private void deleteIncomeMainPlan(Jobtask jobtask) {
    Long planId = jobtask.getPlanId();
    Date delDate = jobtask.getExectime();
    //Date today = new Date();
    // TODO 根据日期(某天),主方案删除品牌型号,叠加,达量的收益
    try {
      mainIncomeService.deleteSubIncomeByPlanId(planId, delDate);
    } catch (Exception e) {
     LogUtil.error("删除主方案"+planId+"下的订单收益失败");
    }
  }
  
  /**
   * doUserDel:执行第二天需删除的任务. <br/>
   */
  @Transactional(rollbackFor = Exception.class)
  public void doUserDel() {
    Date tomorrow = DateUtil.moveDate(new Date(), 1);
    List<Jobtask> joblist = jobRep.findUserDel(tomorrow);
    for (Jobtask job : joblist) {
      mainPlanService.alterUserFlag(job.getKeyid());
      job.setFlag(1);
    }
    LogUtil.info("定时任务:用户删除完成!!");
  }
}
