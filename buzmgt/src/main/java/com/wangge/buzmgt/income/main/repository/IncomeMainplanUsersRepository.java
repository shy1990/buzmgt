package com.wangge.buzmgt.income.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;

public interface IncomeMainplanUsersRepository
    extends JpaRepository<IncomeMainplanUsers, Long>, JpaSpecificationExecutor<IncomeMainplanUsers> {
  
}
