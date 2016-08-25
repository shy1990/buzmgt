package com.wangge.buzmgt.cash.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;

public interface WaterOrderCashRepository extends JpaRepository<WaterOrderCash, String>,
JpaSpecificationExecutor<WaterOrderCash>{
  
  @Override
  @EntityGraph("graph.WaterOrderCash.orderDetails")
  public Page<WaterOrderCash> findAll(Pageable pageRequest);
  
  @Override
  @EntityGraph("graph.WaterOrderCash.orderDetails")
  public Page<WaterOrderCash> findAll(Specification<WaterOrderCash> spec,Pageable pageRequest);
  
  @Override
  public List<WaterOrderCash> findAll(Specification<WaterOrderCash> spec);

  public WaterOrderCash findBySerialNo(String orderNo);
  
}
