package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.entity.MainIncome;

public interface MainIncomeRepository extends JpaRepository<MainIncome, Long>, JpaSpecificationExecutor<MainIncome> {
  MainIncome findBySalesman_IdAndMonth(String userId,String month);
}
