package com.wangge.buzmgt.ordersignfor.service;

import com.wangge.buzmgt.ordersignfor.entity.Order;
import com.wangge.buzmgt.ordersignfor.entity.OrderItem;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

import java.util.List;


public interface OrderItemService {
  
  List<OrderItem> findByOrderNum(String orderNum);
  
  void disposeOrderSignfor(OrderSignfor order);

  Order findOrderByOrderNum(String orderNum);
}
