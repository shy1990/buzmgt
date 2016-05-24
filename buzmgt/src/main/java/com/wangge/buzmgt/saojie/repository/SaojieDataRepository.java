package com.wangge.buzmgt.saojie.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.SaojieData;

public interface SaojieDataRepository extends JpaRepository<SaojieData, Long>{

  List<SaojieData> findBySaojie(Saojie s);

  @EntityGraph("graph.SaojieData.region")
  List<SaojieData> findAll(Specification<SaojieData> specification);
  
  Page<SaojieData> findAll(Specification<SaojieData> spec, Pageable pageable);
}
