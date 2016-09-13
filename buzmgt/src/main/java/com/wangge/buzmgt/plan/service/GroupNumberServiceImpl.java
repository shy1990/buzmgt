package com.wangge.buzmgt.plan.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.plan.entity.GroupNumber;
import com.wangge.buzmgt.plan.repository.GroupNumberRepository;
@Service
public class GroupNumberServiceImpl implements GroupNumberService {

  @Autowired
  private GroupNumberRepository groupNumberRepository;
  
  @Override
  @Transactional
  public void delete(Long id){
    groupNumberRepository.delete(id);
  }
  @Override
  @Transactional
  public void delete(List<GroupNumber> groupNumbers){
    groupNumberRepository.delete(groupNumbers);
  }
  
}
