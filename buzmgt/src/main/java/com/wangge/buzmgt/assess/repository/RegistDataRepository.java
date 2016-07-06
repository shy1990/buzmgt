package com.wangge.buzmgt.assess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.region.entity.Region;

@Repository
public interface RegistDataRepository extends JpaRepository<RegistData, Long>{
  public RegistData findRegistDataById(Long registId);
  
  
  public List<RegistData> findByRegion(Region region);
}
