package com.wangge.buzmgt.assess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.assess.entity.Assess;
@Repository
public interface AssessRepository extends JpaRepository<Assess, Long>{
  
}
