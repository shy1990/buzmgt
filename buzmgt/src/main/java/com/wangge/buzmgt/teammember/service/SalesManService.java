package com.wangge.buzmgt.teammember.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesManVo;

public interface SalesManService {

	void addSalesman(SalesMan salesman);

	List<User> findByReginId(String regionId);

	Page<SalesMan> getSalesmanList(SalesMan salesman,int pageNum);

  SalesMan getSalesmanByUserId(String userId);
  
  SalesMan findById(String id);
  
  List<SalesMan> gainSaojieMan();
  SalesMan findByUserId(String userId);

  
}
