package com.wangge.buzmgt.salesman.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.sys.entity.User;

public interface salesManService {

	void addSalesman(salesMan salesman);

	List<User> findByReginId(String regionId);

  Page<salesMan> getSalesmanList(salesMan salesman,int pageNum);
  
}
