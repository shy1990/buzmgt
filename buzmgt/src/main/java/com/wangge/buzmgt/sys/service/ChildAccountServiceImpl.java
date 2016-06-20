package com.wangge.buzmgt.sys.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.sys.entity.ChildAccount;
import com.wangge.buzmgt.sys.repository.ChildAccountRepository;

@Service
public class ChildAccountServiceImpl implements ChildAccountService {
  @Autowired
  private ChildAccountRepository childAccountRepository;
  @Autowired
  private LogService logService;
  
  @Override
  @Transactional
  public void save(ChildAccount childAccount) {
    childAccount = childAccountRepository.save(childAccount);
    logService.log(null, childAccount, EventType.UPDATE);
  }
  @Override
  public int findChildCount(String id) {
    // TODO Auto-generated method stub
    return childAccountRepository.findByParentIdOrderByIdDesc(id).size();
  }
  @Override
  public List<ChildAccount> findChildCountByParentId(String id) {
    return childAccountRepository.findByParentIdOrderByIdDesc(id) ;
  }
  @Override
  public ChildAccount findbyUserId(Long id) {
    return childAccountRepository.findOne(id);
  }
  
  @Override
  public void delete(ChildAccount childAccount) {
    childAccountRepository.delete(childAccount);
    logService.log(childAccount, null, EventType.DELETE);
  }
	
  
  
  
}
