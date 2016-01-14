package com.wangge.buzmgt.salesman.service;

import java.util.List;

import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.sys.entity.User;

public interface salesManService {

	void addSalesman(salesMan salesman);

	List<User> findByReginId(String regionId);
	
	List<salesMan> gainSaojieMan();
	
	salesMan findById(String id);

}
