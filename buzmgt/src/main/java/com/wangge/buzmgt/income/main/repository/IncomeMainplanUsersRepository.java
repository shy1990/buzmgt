package com.wangge.buzmgt.income.main.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;

public interface IncomeMainplanUsersRepository
    extends JpaRepository<IncomeMainplanUsers, Long>, JpaSpecificationExecutor<IncomeMainplanUsers> {
  List<IncomeMainplanUsers> findByMainplan_IdAndState(Long pid, FlagEnum flag);
  
  List<IncomeMainplanUsers> findByMainplan_Id(Long pid);
  
  @Query("update IncomeMainplanUsers u set u.state=?1 where u.id=?2")
  @Modifying(clearAutomatically = true)
  @Transactional
  void updateFlagById(FlagEnum flag, Long id);
  
  @Query("select u.planId from IncomeMainplanUsers u where u.salesmanId=?1 and u.state=?2")
  Optional<IncomeMainplanUsers> findFirst(String userId, FlagEnum states);
}
