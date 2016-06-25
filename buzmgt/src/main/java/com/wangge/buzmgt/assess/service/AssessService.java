package com.wangge.buzmgt.assess.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.entity.AssessTime;
import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.sys.vo.OrderVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface AssessService {
	
  void saveAssess(Assess assess);
  
  Page<Assess> getAssessList(Assess assess,int pageNum,String regionName);
  
  
  Assess findAssess(long id);
  
  List<Assess> findBysalesman(SalesMan salesman);
  
  int gainMaxStage(String salesmanId);
  
  Page<OrderVo> getOrderStatistics(String salesmanId,String regionid,int pageNum,String begin,String end,int limit);
  
  RegistData findRegistData(Long registId);
  
  Assess findByStageAndSalesman(String stage,String userId);
  
  AssessTime findAssessTimeByRegion(Region region);
  
  AssessTime saveAssessTime(AssessTime at);
  
  /**
   * 查询考核次数列表
   */
  Page<AssessTime> findAll(Pageable pageable);
}
