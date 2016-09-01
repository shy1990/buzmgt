package com.wangge.buzmgt.income.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;

public interface IncomeMainplanUsersRepository
    extends JpaRepository<IncomeMainplanUsers, Long>, JpaSpecificationExecutor<IncomeMainplanUsers> {
  List<IncomeMainplanUsers> findByMainplan_IdAndState(Long pid, FlagEnum flag);
  
}
