package com.wangge.buzmgt.sys.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.sys.entity.ChildAccount;
import com.wangge.buzmgt.sys.repository.ChildAccountRepository;

@Service
public class ChildAccountServiceImpl implements ChildAccountService {
  @Autowired
  private ChildAccountRepository childAccountRepository;
  @Override
  @Transactional
  public void save(ChildAccount childAccount) {
    childAccountRepository.save(childAccount);
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
  }
	
  
  
  
}
