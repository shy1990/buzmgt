package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.entity.MainIncomePlan;

public interface MainPlanRepository
    extends JpaRepository<MainIncomePlan, Long>, JpaSpecificationExecutor<MainIncomePlan> {
  
}
