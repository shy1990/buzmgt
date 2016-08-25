package com.wangge.buzmgt.income.main.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.income.main.entity.MainIncomePlan;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
@Service
public class MainPlanImpl implements MainPlanService {
  @Autowired
  MainIncomePlanRepository mainPlanRep;
  
  @Override
  public List<Object> findByUser() {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public void modifyUser() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void deletePlan() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void assemblebeforeNew() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public Page<MainIncomePlan> findAll(String regionId, Pageable pageReq) {
    if (!StringUtils.isBlank(regionId)) {
      return mainPlanRep.findByRegion_Id(regionId, pageReq);
    }
    return mainPlanRep.findAll(pageReq);
  }
  
}
