package com.wangge.buzmgt.ordersignfor.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.sys.entity.Role;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long>{
  public Page<OrderSignfor> findAll(Pageable pageRequest);
  
  public Page<OrderSignfor> findByCustomSignforException(String status,Pageable pageRequest);
  
  public Page<OrderSignfor> findByCustomSignforExceptionAndCreatTimeBetween(
      String status,String startTime,String endTime,Pageable pageRequest);
  
}
