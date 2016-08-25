package com.wangge.buzmgt.income.ywsalary.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface SalaryProj {
  public Long getId();
  
  public Date getNewdate();
  
  @Value("#{target.user.region.namepath}")
  public String getRegionName();
  
  public Double getSalary();
  
  @Value("#{target.salary}")
  public Double getDaySalary();
  
  @Value("#{target.user.truename}")
  public String getUsername();
}
