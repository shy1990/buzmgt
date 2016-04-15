package com.wangge.buzmgt.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.task.entity.Visit;

public interface VisitRecordRepository extends JpaRepository<Visit, Long>{
  
  
}
