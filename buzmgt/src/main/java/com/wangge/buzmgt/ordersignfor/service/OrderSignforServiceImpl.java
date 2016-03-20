package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
@Service
public class OrderSignforServiceImpl implements OrderSignforService {
  @Resource
  private OrderSignforRepository osr;

  @Override
  public void updateOrderSignfor(OrderSignfor xlsOrder) {
    osr.save(xlsOrder);
    
  }

  @Override
  @Transactional
  public OrderSignfor findByOrderNo(String orderNo) {
    OrderSignfor o = osr.findByOrderNo(orderNo);
    if(o == null){
      System.out.println(">>>>>>>>fuck");
    }
    return  osr.findByOrderNo(orderNo);
  }

}
