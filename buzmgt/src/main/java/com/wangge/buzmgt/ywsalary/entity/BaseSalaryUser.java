package com.wangge.buzmgt.ywsalary.entity;

import java.io.Serializable;

public class BaseSalaryUser implements Serializable{

  private static final long serialVersionUID = 1L;
  
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
