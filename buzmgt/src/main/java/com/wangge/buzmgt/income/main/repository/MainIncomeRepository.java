package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.transaction.annotation.Transactional;

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
}
