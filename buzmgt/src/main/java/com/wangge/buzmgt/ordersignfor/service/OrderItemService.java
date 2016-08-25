package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;

import com.wangge.buzmgt.ordersignfor.entity.OrderItem;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;


public interface OrderItemService {
  
  List<OrderItem> findByOrderNum(String orderNum);
  
  void disposeOrderSignfor(OrderSignfor order);
}
