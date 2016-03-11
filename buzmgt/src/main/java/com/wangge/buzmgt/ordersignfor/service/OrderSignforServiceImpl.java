package com.wangge.buzmgt.ordersignfor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;

@Service
public class OrderSignforServiceImpl implements OrderSignforService {

  @Autowired
  private OrderSignforRepository orderSignforRepository;
  
  
  public List<OrderSignfor> findAll(){
    return orderSignforRepository.findAll(); 
  }
}
