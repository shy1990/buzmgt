package com.wangge.buzmgt.income.ywsalary.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="SYS_BASE_SALARY_SALESMAN")
public class BaseSalaryUser implements Serializable{

  private static final long serialVersionUID = 1L;
  
  @Id
  private String userId;
  private String truename;
  
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getTruename() {
    return truename;
  }
  public void setTruename(String truename) {
    this.truename = truename;
  }
  
  
}
