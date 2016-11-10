package com.wangge.buzmgt.region.repository;

import com.wangge.buzmgt.region.entity.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
   * Created by jiabin on 16-9-10.
   */
  public interface RegionTypeRepository extends JpaRepository<RegionType, Integer> {

    @Query("select r from RegionType r   order by r.parentId ")
    List<RegionType> findAllRegion();
    RegionType findByName(String name);

    @Query("select MAX(id) from RegionType   ORDER by id desc")
    int findMaxId();

  }
