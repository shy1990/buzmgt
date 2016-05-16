package com.wangge.buzmgt.cash.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.cash.entity.WaterOrderDetail;

public interface WaterOrderDetailRepository extends JpaRepository<WaterOrderDetail, String>,
JpaSpecificationExecutor<WaterOrderDetail>{

  public List<WaterOrderDetail> findBySerialNo(String SerialNo);
  
  public WaterOrderDetail findByCashId(String cashId);
  
}
