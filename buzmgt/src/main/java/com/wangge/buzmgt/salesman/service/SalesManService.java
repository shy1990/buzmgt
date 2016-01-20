package com.wangge.buzmgt.salesman.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.salesman.entity.SalesMan;
import com.wangge.buzmgt.sys.entity.User;

public interface SalesManService {

	void addSalesman(SalesMan salesman);

	List<User> findByReginId(String regionId);

  Page<SalesMan> getSalesmanList(SalesMan salesman,int pageNum);

  SalesMan findByUserId(String userId);

  List<SalesMan> gainSaojieMan();
  
}
