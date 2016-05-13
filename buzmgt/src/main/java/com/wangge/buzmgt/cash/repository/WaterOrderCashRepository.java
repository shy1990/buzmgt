package com.wangge.buzmgt.cash.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public interface WaterOrderCashRepository extends JpaRepository<WaterOrderCash, String>,
JpaSpecificationExecutor<WaterOrderCash>{
  public Page<WaterOrderCash> findAll(Pageable pageRequest);

  public WaterOrderCash findBySerialNo(String orderNo);
  
}
