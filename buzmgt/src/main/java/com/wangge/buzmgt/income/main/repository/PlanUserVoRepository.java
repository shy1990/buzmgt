package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.entity.PlanUserVo;

public interface PlanUserVoRepository
    extends JpaRepository<PlanUserVo, String>, JpaSpecificationExecutor<PlanUserVo> {
  
}
