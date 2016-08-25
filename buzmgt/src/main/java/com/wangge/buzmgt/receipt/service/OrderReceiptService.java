package com.wangge.buzmgt.receipt.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.receipt.entity.ReceiptRemark;

public interface OrderReceiptService {

//  public ReceiptRemark findByOrder(OrderSignfor orderNo);

  public List<ReceiptRemark> findAll();

  public Long findCount();

  /**
   * 查询报备记录
   * @param searchParams
   * @return
   */
  public List<ReceiptRemark> findAll(Map<String, Object> searchParams);

  /**
   * 查询分页报备记录
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public Page<ReceiptRemark> getReceiptRemarkList(Map<String, Object> searchParams, Pageable pageRequest);

  /**
   * 查询未报备记录
   * @param searchParams
   * @return
   */
  public List<OrderSignfor> getReceiptNotRemark(Map<String, Object> searchParams);
  
  /**
   * 查询收现金记录
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public List<OrderSignfor> getCashList(Map<String, Object> searchParams);
}

  
