package com.wangge.buzmgt.income.ywsalary.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.income.ywsalary.entity.BaseSalaryUser;

public interface BaseSalaryUserRepository extends JpaRepository<BaseSalaryUser, String>{
  
}
