package com.wangge.buzmgt.income.main.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.common.CheckedEnum;
import com.wangge.buzmgt.income.main.entity.MainIncome;

public interface MainIncomeRepository extends JpaRepository<MainIncome, Long>, JpaSpecificationExecutor<MainIncome> {
  MainIncome findBySalesman_IdAndMonth(String userId, String month);
  
  /**
   * 每月初始化薪资表
   * 
   * @author yangqc
   * @since JDK 1.8
   */
  @Procedure("init_Income_EveMonth")
  void initMonthSalary();
  
  @Procedure("oil_daily_calculate_prod")
  void calculateOilCost();
  
  /**
   * 计算业务佣金和
   * 
   */
  @Procedure("income_month_Busi_all")
  void calIncomePerMonth();
  
  @Modifying(clearAutomatically = true)
  @Transactional(rollbackFor = Exception.class)
  @Query("update MainIncome i set i.state=?1 where i.id=?2")
  void updateById(CheckedEnum check, Long id);
  
  /*
   * 刷新纪录,用于订单计算
   */
  @Modifying(clearAutomatically = true)
  @Transactional(rollbackFor = Exception.class)
  @Query("update MainIncome m set m.busiIncome=(m.busiIncome+?1),m.reachIncome=(m.reachIncome+?2)"
      + ",m.overlyingIncome=(m.overlyingIncome+?3),m.allresult=(m.allresult+?4)" + " where m.id=?5 ")
  void updateAllResult(double busiIncome, double reachIncome, double overlyingIncome, double allresult, Long id);
  
  @Modifying(clearAutomatically = true)
  @Transactional(rollbackFor = Exception.class)
  @Query("update MainIncome m set m.basicSalary=?1,m.punish=(m.punish+?2)"
      + ",m.allresult=(m.allresult+?3)  where m.id=?4 ")
  int updatebasicSalaryOrPunish(double basicSalary, double punish, double allresult, Long id);
  
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from  SYS_INCOME_ACHIEVE_SET i where i.plan_id=?1 and i.user_id=?2 and i.STATUS=1 and i.create_date>?3", nativeQuery = true)
  public void delAchieveIncome(Long planId, String userId, Date startDate);
  
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from SYS_INCOME_TICHENG_BRAND b where b.mainplan_id=? and b.user_id=? and b.orderflag=1 and b.count_date>?", nativeQuery = true)
  public void delBrandIncome(Long planId, String userId, Date startDate);
  
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from SYS_SECTION_RECORD i where i.plan_id=?1 and i.SALESMAN_ID=?2 and i.ORDERFLAG=1 and i.compute_time>?3", nativeQuery = true)
  public void delPriceIncome(Long planId, String userId, Date startDate);
  
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from  SYS_INCOME_ACHIEVE_SET i where i.plan_id=?1 and i.STATUS=1 and i.create_date>?2", nativeQuery = true)
  public void delAchieveIncomeByPlanId(Long planId, Date startDate);
  
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from SYS_INCOME_TICHENG_BRAND b where b.mainplan_id=? and b.orderflag=1 and b.count_date>?", nativeQuery = true)
  public void delBrandIncomeByPlanId(Long planId, Date startDate);
  
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query(value = "delete from SYS_SECTION_RECORD i where i.plan_id=?1  and i.ORDERFLAG=1 and i.compute_time>?2", nativeQuery = true)
  public void delPriceIncomeByPlanId(Long planId, Date startDate);
  /**获得业务期间的总销量,售后冲减量
   * 
   * */
  @Query(value = "select nvl(sum(nums), 0) nums, \n"
      + "       nvl(sum(h.sum), 0) shnums  from (select sum(t.num) nums, sum(t.percentage * t.num) incomes\n"
      + "          from SYS_SECTION_RECORD t     where t.orderflag = 1\n"
      + "           and t.salesman_id = ?1      and to_char('yyyy-mm', t.pay_time) = ?2\n" + "        union all\n"
      + "        select sum(t.SUM) nums, sum(INCOME) incomes\n"
      + "          from SYS_INCOME_TICHENG_BRAND t         where t.orderflag = 1\n" + "           and t.USER_ID = ?1\n"
      + "           and to_char('yyyy-mm', t.COUNT_DATE) = ?2)\n" + "  left join sys_income_shouhou_cost c on 1 = 1\n"
      + "  left join sys_income_shouhou_hedge h on c.hedge_id = h.id\n" + " where c.ruletype in (0, 1)\n"
      + "   and c.user_id = ?1\n" + "   and to_char('yyyy-mm', c.PAYTIME) = ?2", nativeQuery = true)
  public Object getBusinessIncome(String userId, String month);
  
}
