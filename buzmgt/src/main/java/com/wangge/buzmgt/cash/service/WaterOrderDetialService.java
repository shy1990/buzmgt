package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;

public interface WaterOrderDetialService {
  
  public WaterOrderDetail findByOrderId(Long orderId);
  
  public void save(WaterOrderDetail waterOrderDetail);
  
  public void save(List<WaterOrderDetail> waterOrderDetails);
  
}

  
