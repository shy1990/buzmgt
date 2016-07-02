package com.wangge.buzmgt.monthTarget.service;

import java.util.Map;

import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface MonthTargetService {
	
  Region getRegion();
  
  Map<String,Object> getOrderNum(String userId);
  
  Map<String,Object> getSeller(String userId);
  
  String save(MonthTarget mt,SalesMan sm);
}
