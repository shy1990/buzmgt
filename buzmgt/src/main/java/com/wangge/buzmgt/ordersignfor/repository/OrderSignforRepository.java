package com.wangge.buzmgt.ordersignfor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long>,
JpaSpecificationExecutor<OrderSignfor>,OrderSignforRepositoryCustom{
  public Page<OrderSignfor> findAll(Pageable pageRequest);
  
  public Page<OrderSignfor> findByCustomSignforException(String status,Pageable pageRequest);
  
  public Page<OrderSignfor> findByCustomSignforExceptionAndCreateTimeBetween(
      String status,String startTime,String endTime,Pageable pageRequest);

  public OrderSignfor findByOrderNo(String orderNo);
  
}
