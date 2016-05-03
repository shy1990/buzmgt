package com.wangge.buzmgt.receipt.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;

public interface OrderReceiptRepository extends JpaRepository<ReceiptRemark, Long>,
JpaSpecificationExecutor<ReceiptRemark>{
  public Page<ReceiptRemark> findAll(Pageable pageRequest);

  public ReceiptRemark findByOrderno(String orderNo);
  
}
