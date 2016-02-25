package com.wangge.buzmgt.assess.service;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.assess.entity.Assess;

public interface AssessService {
	
  void saveAssess(Assess assess);
  
  Page<Assess> getAssessList(Assess assess,int pageNum,String regionName);
  
  
  public Assess findAssess(long id);
}
