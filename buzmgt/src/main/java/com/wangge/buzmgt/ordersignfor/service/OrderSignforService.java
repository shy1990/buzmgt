package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public interface OrderSignforService {

  public List<OrderSignfor> findAll();
  
  /**
   * 获取客户签收异常订单列表
   * @param osf
   * @param pageNum
   * @return
   */
  public Page<OrderSignfor> getMemberSignforList(OrderSignfor osf,int pageNum,String startTime,String endTime);
  
  public Long findCount();

  //无条件分页
  Page<OrderSignfor> getOrderSingforList(Pageable pageRequest);
  
  /**
   * 获取业务揽收异常订单
   * @param osf
   * @param pageNum
   * @param startTime
   * @param endTime
   * @param timesGap //时间间隔
   * @return
   */
  public Page<OrderSignfor> getYwSignforList(OrderSignfor osf, int pageNum, String startTime, String endTime,String timesGap);
  
  public Page<OrderSignfor> findByCustomSignforException(String status,Pageable pageRequest);
  
  public Page<OrderSignfor> findByCustomSignforExceptionAndCreatTimeBetween(
      String status,String startTime,String endTime,Pageable pageRequest);

  public Page<OrderSignfor> getOrderSingforList(Map<String, Object> searchParams, Pageable pageRequest);

  public List<OrderSignfor> findAll(Map<String, Object> searchParams);
}
