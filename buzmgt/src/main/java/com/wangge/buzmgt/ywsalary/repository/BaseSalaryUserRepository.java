package com.wangge.buzmgt.ywsalary.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.ywsalary.entity.BaseSalaryUser;

public interface BaseSalaryUserRepository extends JpaRepository<BaseSalaryUser, String>{
  
}
