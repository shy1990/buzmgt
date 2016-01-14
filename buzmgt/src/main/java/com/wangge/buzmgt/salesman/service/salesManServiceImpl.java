package com.wangge.buzmgt.salesman.service;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.salesman.repository.salesManRepository;
import com.wangge.buzmgt.sys.entity.User;

@Service
public class salesManServiceImpl implements salesManService {

	@Resource
	private salesManRepository salesManRepository;
	@Override
	public void addSalesman(salesMan salesman) {
		
		salesManRepository.save(salesman);
	}
	

	@Override
	@Transactional
	public List<User> findByReginId(String regionId) {
		
		return salesManRepository.findByRegionId(regionId);
	}


  @Override
  public List<salesMan> gainSaojieMan() {
    return salesManRepository.gainSaojieMan();
  }


  @Override
  public salesMan findById(String id) {
    return salesManRepository.findById(id);
  }
  
}
