package com.wangge.buzmgt.pushmoney.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYS_CATEGORY")
public class Category implements Serializable{

  /**
   * 种类
   */
  private static final long serialVersionUID = 1L;
  @Id
  private String id;
  private String name;
  private String remark;
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getRemark() {
    return remark;
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  
  
}