package com.wangge.buzmgt.region.repository;

import com.wangge.buzmgt.region.entity.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
   * Created by jiabin on 16-9-10.
   */
  public interface RegionTypeRepository extends JpaRepository<RegionType, Integer> {


    RegionType findByName(String name);

  }
