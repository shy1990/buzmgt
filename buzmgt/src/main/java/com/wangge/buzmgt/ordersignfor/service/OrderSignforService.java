package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public interface OrderSignforService {

  void updateOrderSignfor(OrderSignfor xlsOrder);

  OrderSignfor findByOrderNo(String orderNo);

  public List<OrderSignfor> findAll();
  
  public Long findCount();

  public Page<OrderSignfor> getOrderSingforList(Map<String, Object> searchParams, Pageable pageRequest);

  public List<OrderSignfor> findAll(Map<String, Object> searchParams);
}
