package com.wangge.buzmgt.income.main.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.income.main.entity.MainIncomePlan;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.income.main.vo.MachineType;

import net.sf.json.JSONArray;

@Service
public class MainPlanServiceImpl implements MainPlanService {
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
  
  @Override
  public List<MachineType> getAllMachineType() {
    List<Object> list = mainPlanRep.findAllMachineType();
    List<MachineType> mList = new ArrayList<MachineType>();
    for (Object o : list) {
      Object[] ob = (Object[]) o;
      mList.add(getMachine(ob));
    }
    return mList;
  }
  
  private MachineType getMachine(Object[] ob) {
    String name = null == ob[0] ? "" : ob[0].toString();
    String code = null == ob[1] ? "" : ob[1].toString();
    return new MachineType(name, code);
  }
  
  @Override
  public JSONArray getAllBrandType() {
    List<Object> list = mainPlanRep.findAllBrandCode();
    List<BrandType> bList = new ArrayList<BrandType>();
    for (Object o : list) {
      Object[] ob = (Object[]) o;
      bList.add(getType(ob));
    }
    JSONArray json=JSONArray.fromObject(bList);
    return json;
  }
  
  private BrandType getType(Object[] ob) {
    String brandId = null == ob[0] ? "" : ob[0].toString();
    String machineType = null == ob[1] ? "" : ob[1].toString();
    String name = null == ob[2] ? "" : ob[2].toString();
    return new BrandType(brandId, machineType, name);
  }
}
