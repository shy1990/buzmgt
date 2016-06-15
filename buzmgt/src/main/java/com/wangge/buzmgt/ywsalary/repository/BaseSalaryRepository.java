package com.wangge.buzmgt.ywsalary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.ywsalary.entity.BaseSalary;

public interface BaseSalaryRepository extends JpaRepository<BaseSalary, Integer>, 
JpaSpecificationExecutor<BaseSalary> ,BaseSalaryRepositoryCustom {
  
}
