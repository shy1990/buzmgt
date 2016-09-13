package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;

import com.wangge.buzmgt.income.main.entity.MainIncome;

public interface MainIncomeRepository extends JpaRepository<MainIncome, Long>, JpaSpecificationExecutor<MainIncome> {
  MainIncome findBySalesman_IdAndMonth(String userId,String month);
  /** 
    * 每月初始化薪资表
    * @author yangqc  
    * @since JDK 1.8 
    */  
  @Procedure("init_Income_EveMonth")
  void initMonthSalary();
  @Procedure("oil_daily_calculate_prod")
  void calculateOilCost();
}
