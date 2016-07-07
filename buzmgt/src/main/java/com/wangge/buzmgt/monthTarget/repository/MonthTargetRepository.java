package com.wangge.buzmgt.monthTarget.repository;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wangge.buzmgt.monthTarget.entity.MonthTarget;

import java.util.List;

import java.util.List;

@Repository
public interface MonthTargetRepository extends JpaRepository<MonthTarget, Long>{
  
  MonthTarget findBySalesman(SalesMan salesman);

  Page<MonthTarget> findAll(Specification<MonthTarget> specification, Pageable pageRequest);

  List<MonthTarget> findByManagerRegionAndPublishStatus(String regionId,int status);

//    public Page<MonthTarget> findAll(Specification specification, Pageable pageable);//分页查询

  MonthTarget findByRegion(Region region);
}
