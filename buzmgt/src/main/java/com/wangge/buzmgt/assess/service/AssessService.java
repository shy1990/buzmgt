package com.wangge.buzmgt.assess.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.sys.vo.OrderVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface AssessService {
	
  void saveAssess(Assess assess);
  
  Page<Assess> getAssessList(Assess assess,int pageNum,String regionName);
  
  
  public Assess findAssess(long id);
  
  List<Assess> findBysalesman(SalesMan salesman);
  
  int gainMaxStage(String salesmanId);
  
  Page<OrderVo> getOrderStatistics(String salesmanId,String regionid,int pageNum,String begin,String end);
}
