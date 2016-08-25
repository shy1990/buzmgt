package com.wangge.buzmgt.cash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.cash.entity.CheckCash;

public interface CheckCashRepository extends JpaRepository<CheckCash, Integer>,
JpaSpecificationExecutor<CheckCash>{
  
  
}
