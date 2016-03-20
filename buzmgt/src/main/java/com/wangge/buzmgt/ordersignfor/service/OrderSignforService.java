package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public interface OrderSignforService {

  void updateOrderSignfor(OrderSignfor xlsOrder);

  OrderSignfor findByOrderNo(String orderNo);

}
