package com.wangge.buzmgt.monthTarget.service;

import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.region.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MonthTargetService {
	
  Region getRegion();

  public Page<MonthTarget> findByTargetCycleAndManagerId(String truename,String time, String managerId,Pageable pageable);


  public Page<MonthTarget> findCount(String time,Page page);

  public Page<MonthTarget> findActive(String time, Page page);


}
