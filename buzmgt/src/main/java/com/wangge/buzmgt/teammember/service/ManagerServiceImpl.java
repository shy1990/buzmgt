package com.wangge.buzmgt.teammember.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.repository.ManagerRepository;

@Service
public class ManagerServiceImpl implements ManagerService {

	@Resource
	private ManagerRepository managerRepository;
	@Override
	public void addManager(Manager m) {
		managerRepository.save(m);
	}
	@Override
	public List<Manager> findByReginId(String regionId) {
		
		return managerRepository.findByRegionId(regionId);
	}
  @Override
  @Transactional
  public Manager getById(String id) {
    return managerRepository.findById(id);
  }

}
