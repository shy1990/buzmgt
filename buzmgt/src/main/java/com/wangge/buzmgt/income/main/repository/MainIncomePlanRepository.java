package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.entity.MainIncomePlan;

public interface MainIncomePlanRepository
    extends JpaRepository<MainIncomePlan, Long> {
 
  Page<MainIncomePlan> findByRegion_Id(String regionId, Pageable pageReq);
   
}
