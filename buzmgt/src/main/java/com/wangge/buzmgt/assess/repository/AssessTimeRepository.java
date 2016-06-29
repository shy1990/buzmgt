package com.wangge.buzmgt.assess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.assess.entity.AssessTime;
import com.wangge.buzmgt.region.entity.Region;

@Repository
public interface AssessTimeRepository extends JpaRepository<AssessTime, Long>{
  
  /*
   * 根据regionId查询考核次数
   */
  public AssessTime findAssessTimeByRegion(Region region);
}
