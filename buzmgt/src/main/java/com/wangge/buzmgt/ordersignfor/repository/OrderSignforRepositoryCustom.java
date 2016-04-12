package com.wangge.buzmgt.ordersignfor.repository;

import java.util.List;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;

public interface OrderSignforRepositoryCustom {
  public List<OrderSignfor> getReceiptNotRemarkList(String status,String startTime,String endTime);
}
