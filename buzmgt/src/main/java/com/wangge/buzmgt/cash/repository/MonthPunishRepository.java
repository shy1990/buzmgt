package com.wangge.buzmgt.cash.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.cash.entity.MonthPunish;


public interface MonthPunishRepository extends JpaRepository<MonthPunish, Integer>,
JpaSpecificationExecutor<MonthPunish>{
  
}
