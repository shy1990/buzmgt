package com.wangge.buzmgt.saojie.service;

import java.util.List;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.SaojieData;


import org.springframework.data.domain.Page;

import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface SaojieDataService {
  
  SaojieDataVo getsaojieDataList(String userId, String regionId);
  
  Page<SaojieData> getsaojieDataList(String userId, String regionId,int pageNum,int limit);
    List<SaojieData> findByReion(Region r);
    
    
    SaojieData findById(Long id);
    
    
    void saveSaojieData(SaojieData saojieData);
  
  List<SaojieData> findByregionId(String regionId);
  
  List<SaojieData> findBySalesman(SalesMan salesMan);

  int getCountByUserId(String userId);


}
