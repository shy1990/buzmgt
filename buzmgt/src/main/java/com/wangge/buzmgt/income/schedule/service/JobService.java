package com.wangge.buzmgt.income.schedule.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.IncomeSubRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.schedule.entity.Jobtask;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;
import com.wangge.buzmgt.log.util.LogUtil;
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
  
  @Transactional(rollbackFor = Exception.class)
  public void initMonthIncome() {
    mainIncomeRep.initMonthSalary();
  }
  
  @Transactional(rollbackFor = Exception.class)
  public void initDailyOilCost() {
    mainIncomeRep.calculateOilCost();
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
          case 1:
            deleteIncomeMainPlanUser(jobtask);
            break;
          case 2:
            mainPlanService.alterUserFlag(jobtask.getKeyid());
            break;
          default:
            break;
        }
      } catch (Exception e) {
        LogUtil.error("定时任务失败", e);
      }
    }
  }
  
  /**
   * 删除主方案的一个用户,要重新计算收益 <br/>
   * 查找出执行日期到今天的所有订单收益记录,重新计算; 删除之后如何添加时间<br/>
   * 
   * TODO 叠加计算待定<br/>
   */
  @Transactional(rollbackFor = Exception.class)
  private void deleteIncomeMainPlanUser(Jobtask jobtask) {
    Date execTime = jobtask.getExectime();
    String execMonth = DateUtil.getPreMonth(execTime, 0);
    String thisMonth = DateUtil.getPreMonth(new Date(), 0);
    IncomeMainplanUsers user = mainPlanUserRep.findOne(jobtask.getKeyid());
    String userId = user.getSalesmanId();
    Long planId = user.getPlanId();
    if (execMonth.equals(thisMonth)) {
      // 计算本月的
      calDelOrderByUserAndDate(execTime, thisMonth, userId, planId);
    } else {
      execTime = DateUtil.getPreMonthDate(new Date(), 0);
      // 计算本月的
      calDelOrderByUserAndDate(execTime, thisMonth, userId, planId);
      if (execTime.getTime() < DateUtil.getPreMonthDate(new Date(), -1).getTime()) {
        execTime = DateUtil.getPreMonthDate(new Date(), -1);
      }
      String preMonth = DateUtil.getPreMonth(new Date(), -1);
      // 计算上个月的
      calDelOrderByUserAndDate(execTime, preMonth, userId, planId);
    }
  }
  
  /**
   * 计算一个用户在某月被删除的订单. <br/>
   * 
   * @throws NumberFormatException
   * @since JDK 1.8
   */
  private void calDelOrderByUserAndDate(Date execTime, String thisMonth, String userId, Long planId)
      throws NumberFormatException {
    MainIncome main = mainIncomeRep.findBySalesman_IdAndMonth(userId, thisMonth);
    if (main.getState() != FlagEnum.DEL) {
      List<Object> subPlanList = incomeSubRep.getMainPlainOrdersByUserAndDate(planId, execTime, userId, thisMonth);
      int busisum = 0;
      int reachIncome = 0;
      for (Object o : subPlanList) {
        Object[] order = (Object[]) o;
        switch (Integer.valueOf(order[0].toString())) {
          case 0:
            busisum += Integer.valueOf(order[1].toString());
            break;
          case 1:
            busisum += Integer.valueOf(order[1].toString());
            break;
          case 2:
            reachIncome += Integer.valueOf(order[1].toString());
            break;
          default:
            break;
        }
      }
      main.setBusiIncome(main.getBusiIncome() - busisum);
      main.setReachIncome(main.getReachIncome() - reachIncome);
      main.reSetResult();
      mainIncomeRep.save(main);
    }
  }
  
  /**
   * 1.找到所有的当天的已付款的订单并按业务员和方案类型分组<br/>
   * 2.减去每个业务员的当天收益,并重新结算工资.<br/>
   * 3.将当天的订单收益状态更新为1(不可用);<br/>
   * 
   * @since JDK 1.8
   */
  private void deleteIncomeMainPlan(Jobtask jobtask) {
    Long planId = jobtask.getPlanId();
    
    // incomeSubRep.modifyFlag(usList);
  }
}
