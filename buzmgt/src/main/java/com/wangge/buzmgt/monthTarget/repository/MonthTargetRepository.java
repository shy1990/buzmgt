package com.wangge.buzmgt.monthtarget.repository;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wangge.buzmgt.monthtarget.entity.MonthTarget;

import java.util.List;

@Repository
public interface MonthTargetRepository extends JpaRepository<MonthTarget, Long>{
  
  MonthTarget findBySalesman(SalesMan salesman);

  Page<MonthTarget> findAll(Specification<MonthTarget> specification, Pageable pageRequest);

  List<MonthTarget> findByManagerRegionAndPublishStatus(String regionId,int status);

//    public Page<MonthTarget> findAll(Specification specification, Pageable pageable);//分页查询

  MonthTarget findByRegionAndTargetCycle(Region region,String nextMonth);
  //根据大区经理的区域id和时间查询数据,用来导出数据
  public List<MonthTarget> findByManagerRegionAndTargetCycle(String managerRegion,String targetCycle);
}
