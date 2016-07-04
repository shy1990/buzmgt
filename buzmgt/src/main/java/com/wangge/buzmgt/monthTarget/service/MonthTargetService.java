package com.wangge.buzmgt.monthTarget.service;

import java.util.Map;

import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MonthTargetService {
	
  Region getRegion();
  
  Map<String,Object> getOrderNum(String userId);
  
  Map<String,Object> getSeller(String userId);
  
  String save(MonthTarget mt,SalesMan sm);

  String save(MonthTarget monthTarget);

  Page<MonthTarget> findAll(String targetCycle, String userName, Pageable pageRequest);

  String publish(MonthTarget monthTarget);

  String publishAll();
}
