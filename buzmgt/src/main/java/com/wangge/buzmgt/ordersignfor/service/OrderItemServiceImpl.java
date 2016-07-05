package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.ordersignfor.entity.OrderItem;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderItemRepository;

@Service
public class OrderItemServiceImpl implements OrderItemService {
  
  @Autowired
  private OrderItemRepository itemRepository;

  @Override
  public List<OrderItem> findByOrderNum(String orderNum) {
    
    return itemRepository.findByOrderNum(orderNum);
  }
  
  @Override
  public void disposeOrderSignfor(OrderSignfor order){
    if(order != null){
      String orderNum=order.getOrderNo();
      List<OrderItem> items=this.findByOrderNum(orderNum);
      order.setItems(items);
    }
  }

}
