package com.wangge.buzmgt.manager.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.manager.entity.manager;
import com.wangge.buzmgt.manager.repository.managerRepository;

@Service
public class managerServiceImpl implements managerService {

	@Resource
	private managerRepository managerRepository;
	@Override
	public void addManager(manager m) {
		managerRepository.save(m);
	}
	@Override
	public List<manager> findByReginId(String regionId) {
		
		return managerRepository.findByRegionId(regionId);
	}
  @Override
  @Transactional
  public manager getById(String id) {
    return managerRepository.findById(id);
  }

}