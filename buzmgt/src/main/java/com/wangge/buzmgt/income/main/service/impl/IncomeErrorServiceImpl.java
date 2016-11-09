package com.wangge.buzmgt.income.main.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.income.main.entity.IncomeSubError;
import com.wangge.buzmgt.income.main.repository.IncomeSubErroRepository;
import com.wangge.buzmgt.income.main.service.IncomeErrorService;

@Service
public class IncomeErrorServiceImpl implements IncomeErrorService {
  @Autowired
  private IncomeSubErroRepository errRep;
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(int type, String message) {
    IncomeSubError error = new IncomeSubError(message, type);
    errRep.save(error);
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void save(String orderno, String userId, String errorInfo, String goodId, Integer type, Long keyId) {
    IncomeSubError error = new IncomeSubError(0,orderno, userId, errorInfo, goodId, type, keyId);
    errRep.save(error);
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveScheduleError(int type, long jobId, String message) {
    IncomeSubError error = new IncomeSubError(message, type, jobId);
    errRep.save(error);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveHedgeError(String orderno, String userId, String errorInfo, String goodId, Integer type, Long keyId) {
    IncomeSubError error = new IncomeSubError(1,orderno, userId, errorInfo, goodId, type, keyId);
    errRep.save(error);
  }
}