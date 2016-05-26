package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;

public interface WaterOrderCashService {

  
  public List<WaterOrderCash> findAll();

  /**
   * 查询流水单号列表
   * @param searchParams
   * @return
   */
  public List<WaterOrderCash> findAll(Map<String, Object> searchParams);

  /**
   * 查询分页流水单号记录
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public Page<WaterOrderCash> findAll(Map<String, Object> searchParams, Pageable pageRequest);
  
  
  public WaterOrderDetail findByOrderNo(String orderNo);
  
  public WaterOrderCash findBySerialNo(String serialNo);

  public void save(List<WaterOrderCash> waterOrders);
  
}

  
