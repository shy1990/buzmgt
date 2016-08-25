package com.wangge.buzmgt.cash.service;

import java.util.List;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;

public interface WaterOrderDetialService {
  
  public WaterOrderDetail findByOrderNo(String orderNo);
  
  public void save(WaterOrderDetail waterOrderDetail);
  
  public void save(List<WaterOrderDetail> waterOrderDetails);
  
}

  
