package com.wangge.buzmgt.income.schedule.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.achieveaward.service.AwardIncomeService;
import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.common.IncomeCommon;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.HedgeService;
import com.wangge.buzmgt.income.main.service.IncomeErrorService;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.OrderGoods;
import com.wangge.buzmgt.income.main.vo.repository.OrderGoodsRepository;
import com.wangge.buzmgt.income.schedule.entity.Jobtask;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.superposition.service.SuperpositonService;
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
  private MainPlanService mainPlanService;
  @Autowired
  OrderGoodsRepository orderGoodsRep;
  @Autowired
  SalesManRepository salesmanRep;
  @Autowired
  MainIncomeService mainIncomeService;
  @Autowired
  HedgeService hedgeService;
  @Autowired
  IncomeErrorService errorService;
  @Autowired
  SuperpositonService superService;
  @Autowired
  AchieveIncomeService achieveService;
  @Autowired
  AwardIncomeService awardService;
  
  @Transactional(rollbackFor = Exception.class)
  public void initMonthIncome() {
    mainIncomeRep.initMonthSalary();
  }
  
  public void doTask() {
    List<Jobtask> jobList = jobRep.defaltfindAll(new Date());
    for (Jobtask jobtask : jobList) {
      IncomeCommon.EXECUTORSERVICEPOOL.execute(new Runnable() {
        @Override
        public void run() {
          int type = jobtask.getType();
          try {
            switch (type) {
              /**
               * 0:删除主方案:不在计算收益;<br/>
               * 1.删除删除日期当天的收益<br/>
               */
              case 0:
                mainPlanService.deleteIncomeMainPlan(jobtask);
                break;
              case 10:
                deleteIncomeMainPlanUser(jobtask);
                break;
              case 12:
                calIncomeMainPlanUser(jobtask);
                break;
              case 20:
                // 叠加计算
                superService.superIncomeCompute(jobtask.getPlanId(), jobtask.getKeyid());
                superService.computeOneSingle(jobtask.getPlanId(), jobtask.getKeyid());
                break;
              case 30:
                // 计算达量收益
                achieveService.calculateAchieveIncomeTotal(jobtask.getPlanId(), jobtask.getKeyid());
                break;
              case 40:
                // 计算达量奖励
                awardService.calculateAwardIncomeTotal(jobtask.getPlanId(), jobtask.getKeyid());
                break;
              case 60:
                calhedgeAchieve(jobtask);
                break;
              default:
                break;
            }
            jobRep.updateFlag(jobtask.getId());
          } catch (Exception e) {
            errorService.saveScheduleError(71, jobtask.getId(), "执行定时任务出错");
            LogUtil.error("定时任务失败", e);
          }
        }
      });
    }
    
  }
  
  /**
   * 计算达量设置,叠加奖励,达量奖励的收益
   */
  private void calhedgeAchieve(Jobtask jobtask) {
    hedgeService.calculateAchieveHedge(jobtask.getExectime());
  }
  
  /**
   * 计算某个业务员新加入方案时的收益<br/>
   * 1.查出某天的订单,计算每天的订单
   */
  private void calIncomeMainPlanUser(Jobtask jobtask) {
    try {
      Date startTime = jobtask.getExectime();
      Date endTime = jobtask.getInserttime();
      Long planId = jobtask.getPlanId();
      String userId = jobtask.getSalesmanId();
      String regionId = salesmanRep.getRegionIdByUserId(userId);
      
      Date endDay = DateUtil.string2Date(DateUtil.date2String(endTime, "yyyy-mm-dd"));
      startTime = mainIncomeService.getEffectiveStartTime(startTime, userId);
      int between = DateUtil.daysBetween(startTime, endDay);
      
      // 计算查找每天的结算订单
      for (int i = 0; i <= between; i++) {
        Date startDate = DateUtil.moveDate(startTime, i);
        Date endDate = DateUtil.moveDate(startDate, 1);
        IncomeCommon.EXECUTORSERVICEPOOL.execute(new Runnable() {
          @Override
          public void run() {
            List<OrderGoods> goodList = orderGoodsRep.findByorderNoByDateAndSalesman(userId, startDate, endDate);
            mainIncomeService.caculatePayedOrder(userId, planId, startDate, goodList, regionId, 0);
          }
        });
      }
      // 计算最后一天的订单
      IncomeCommon.EXECUTORSERVICEPOOL.execute(new Runnable() {
        @Override
        public void run() {
          List<OrderGoods> goodList = orderGoodsRep.findByorderNoByDateAndSalesman(userId, endTime, endDay);
          mainIncomeService.caculatePayedOrder(userId, planId, endDay, goodList, regionId, 0);
        }
      });
    } catch (Exception e) {
      LogUtil.error("计算某人之前某段时间收益出错!!", e);
      errorService.saveScheduleError(jobtask.getType(), jobtask.getId(), "计算某人之前某段时间收益出错!!");
    }
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
    IncomeMainplanUsers user = mainPlanUserRep.findOne(jobtask.getKeyid());
    String userId = user.getSalesmanId();
    Long planId = user.getPlanId();
    try {
      mainIncomeService.deleteSubIncome(planId, userId, execTime);
    } catch (Exception e) {
      LogUtil.info("删除订单收益出错,信息如下:planId-->" + planId + ";userId-->" + userId + ";execTime-->" + execTime);
    }
  }
  
  /**
   * doUserDel:执行第二天需删除的任务. <br/>
   */
  @Transactional(rollbackFor = Exception.class)
  public void doUserDel() {
    Date tomorrow = DateUtil.moveDate(new Date(), 1);
    try {
      List<Jobtask> joblist = jobRep.findUserDel(tomorrow);
      for (Jobtask job : joblist) {
        mainPlanService.alterUserFlag(job.getKeyid());
        job.setFlag(1);
      }
      jobRep.save(joblist);
    } catch (Exception e) {
      errorService.saveScheduleError(71, 0, "删除以前的主方案用户任务出错,日期为" + DateUtil.date2String(tomorrow));
    }
  }
  
  /**
   * saveJobTask:保存定时任务. <br/>
   * 
   * @author yangqc
   * @param type
   *          类型2开头:叠加;20 叠加计算; 3开头:达量设置;30 达量计算;4达量奖励:40达量奖励计算
   * @param planId
   *          主方案id
   * @param keyid
   *          规则id
   * @param exectime
   *          执行时间:发放收益的下个月某天计算;叠加2号,达量3号
   * @since JDK 1.8
   */
  public void saveJobTask(Integer type, Long planId, Long keyid, Date exectime) {
    Jobtask task = new Jobtask(type, planId, keyid, exectime);
    jobRep.save(task);
  }
}
