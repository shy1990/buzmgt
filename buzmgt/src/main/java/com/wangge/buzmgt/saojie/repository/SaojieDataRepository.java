package com.wangge.buzmgt.saojie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.SaojieData;

public interface SaojieDataRepository extends JpaRepository<SaojieData, Long>{

  List<SaojieData> findBySaojie(Saojie s);
  
  List<SaojieData> findByRegion(Region r);

}
