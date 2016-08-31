package com.wangge.buzmgt.monthtarget.service;

import com.wangge.buzmgt.monthtarget.entity.MonthTarget;
import com.wangge.buzmgt.region.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface MonthTargetService {

  Region getRegion();

  Map<String,Object> getOrderNum(String regionId);

  Map<String,Object> getSeller(String regionId);

  Map<String,Object> getMerchant(String regionId);

  String save(MonthTarget mt,Region region);

  String save(MonthTarget monthTarget);

  Page<MonthTarget> findAll(String targetCycle, String userName, Pageable pageRequest);

  String publish(MonthTarget monthTarget);

  String publishAll();

  MonthTarget findOne(Long id);

  public Page<MonthTarget> findByTargetCycleAndManagerId(String truename,String time,Pageable pageable);


  public Page<MonthTarget> findCount(String time,Page page);

  public Page<MonthTarget> findActive(String time, Page page);

  public List<MonthTarget> exportExcel(String targetCycle);




  public Page<MonthTarget> findAllBySql(Integer page,Integer size,String truename,String time);

}
