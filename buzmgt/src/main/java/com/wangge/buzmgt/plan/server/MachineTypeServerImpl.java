package com.wangge.buzmgt.plan.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.repository.MachineTypeRepository;

public class MachineTypeServerImpl implements MachineTypeServer {

  @Autowired
  private MachineTypeRepository machineTypeRepository;
  
  @Override
  public List<MachineType> findAll() {
    return machineTypeRepository.findAll();
  }

  @Override
  public MachineType findByCode(String code) {
    return machineTypeRepository.findByCode(code);
  }

}
