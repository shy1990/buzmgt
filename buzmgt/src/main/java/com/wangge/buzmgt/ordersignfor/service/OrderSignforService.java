package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public interface OrderSignforService {

  void updateOrderSignfor(OrderSignfor xlsOrder);

  List<OrderSignfor> findListByOrderNo(String orderNo);
  
  public OrderSignfor findByOrderNo(String orderNo);

  public List<OrderSignfor> findAll();
  
  public Long findCount();

  public Page<OrderSignfor> getOrderSingforList(Map<String, Object> searchParams, Pageable pageRequest);

  public List<OrderSignfor> findAll(Map<String, Object> searchParams);

  /**
   * 查询未报备订单
   * @param status
   * @param startTime
   * @param endTime
   * @return
   */
  List<OrderSignfor> getReceiptNotRemarkList(String status, String startTime, String endTime, String orderNo,String regionId);
  
  /**
   * 查询收现金订单
   * @param status
   * @param startTime
   * @param endTime
   * @return
   */
  List<OrderSignfor> getReceiptCashList(Map<String, Object> searchParams);

  void save(List<OrderSignfor> list);

  void deleteById(Long id);

  OrderSignfor save(OrderSignfor orderSignfor);

  OrderSignfor findByFastmailNo(String fastMailNo);
}
