package com.wangge.buzmgt.receipt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.druid.sql.dialect.odps.parser.OdpsExprParser;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;

public interface OrderReceiptService {

//  public ReceiptRemark findByOrder(OrderSignfor orderNo);

  public List<ReceiptRemark> findAll();

  public Long findCount();

  public List<ReceiptRemark> findAll(Map<String, Object> searchParams);

  public Page<ReceiptRemark> getReceiptRemarkList(Map<String, Object> searchParams, Pageable pageRequest);

  public Page<OrderSignfor> getReceiptNotRemark(Map<String, Object> searchParams, Pageable pageRequest);
}

  
