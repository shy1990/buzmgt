package com.wangge.buzmgt.assess.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.teammember.entity.SalesMan;
@Repository
public interface AssessRepository extends JpaRepository<Assess, Long>{
  
  public List<Assess> findBysalesman(SalesMan salesman);
  
}
