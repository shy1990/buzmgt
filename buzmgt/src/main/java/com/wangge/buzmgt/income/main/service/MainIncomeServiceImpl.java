package com.wangge.buzmgt.income.main.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.income.main.vo.MachineType;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class MainIncomeServiceImpl implements MainIncomeService {
  @Autowired
  MainIncomeRepository mainIncomeRep;
  @Autowired
  SalesManRepository salesmanRep;
  
  @Override
  public void caculateOutedOrder() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void caculatePayedOrder() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void caculateSalesman() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public MainIncome findIncomeMain(String salesmanId, String month) {
    MainIncome main = mainIncomeRep.findBySalesman_IdAndMonth(salesmanId, month);
    if (null == main) {
      main = mainIncomeRep.save(new MainIncome(salesmanRep.findById(salesmanId), month));
    }
    return main;
  }
  
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public MainIncome findIncomeMain(String salesmanId) {
    String month = DateUtil.getPreMonth(new Date(), 0);
    return findIncomeMain(salesmanId,month);
  }

  @Override
  public List<MachineType> getAllMachineType() {
    
    return null;
  }

  @Override
  public List<BrandType> getAllBrandType() {
    // TODO Auto-generated method stub
    return null;
  }
}
