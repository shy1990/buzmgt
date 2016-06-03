package com.wangge.buzmgt.cash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.cash.entity.Cash;

public interface CashRepository extends JpaRepository<Cash, Long>,
JpaSpecificationExecutor<Cash>{
  
}
