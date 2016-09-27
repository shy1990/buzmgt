package com.wangge.buzmgt.plan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.repository.MachineTypeRepository;
@Service
public class MachineTypeServiceImpl implements MachineTypeService {

  @Autowired
  private MachineTypeRepository machineTypeRepository;
  
  @Override
  public List<MachineType> findAll() {
    return machineTypeRepository.findAll();
  }
}
