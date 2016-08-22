package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.entity.SysIncomeMain;

public interface MainIncomeRepository extends JpaRepository<SysIncomeMain, Long>, JpaSpecificationExecutor<SysIncomeMain> {
  
}
