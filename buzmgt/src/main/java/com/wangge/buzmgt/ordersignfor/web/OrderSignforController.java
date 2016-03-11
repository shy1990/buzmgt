package com.wangge.buzmgt.ordersignfor.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;

@Controller
@RequestMapping(value="/OrderSignfor")
public class OrderSignforController {
  
  @Resource
  private OrderSignforService os; 
  
  @RequestMapping(value="/list",method=RequestMethod.GET)
  @ResponseBody
  public List<OrderSignfor> OrderSignforList(){
    List<OrderSignfor> orderSignforslist =os.findAll();
    return orderSignforslist;
  }
  
}
