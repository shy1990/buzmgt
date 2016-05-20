package com.wangge.buzmgt.cash.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.Cash;

public interface BankTradeRepository extends JpaRepository<BankTrade, Integer>,
JpaSpecificationExecutor<BankTrade>{
  
  
}
