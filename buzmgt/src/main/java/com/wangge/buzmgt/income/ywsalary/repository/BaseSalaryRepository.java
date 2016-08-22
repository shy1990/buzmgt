package com.wangge.buzmgt.income.ywsalary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;

public interface BaseSalaryRepository extends JpaRepository<BaseSalary, Integer>, 
JpaSpecificationExecutor<BaseSalary>{
  
}
