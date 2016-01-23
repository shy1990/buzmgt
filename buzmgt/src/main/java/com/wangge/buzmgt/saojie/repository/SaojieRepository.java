package com.wangge.buzmgt.saojie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.salesman.entity.SalesMan;
import com.wangge.buzmgt.saojie.entity.Saojie;

public interface SaojieRepository extends JpaRepository<Saojie, Long>{
  
  Page<Saojie> findAll(Specification<Saojie> spec, Pageable pageable);
  
  List<Saojie> findBysalesmanOrderByOrderAsc(SalesMan salesman);
  
  Saojie findByregion(Region region);
  
  @Query("select s from Saojie s where s.order=?1 and s.salesman.id=?2")
  Saojie changeOrder(int ordernum, String userId);
  
  @Query("select count(*) from Saojie")
  int getRegionCount();
}
