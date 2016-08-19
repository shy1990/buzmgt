package com.wangge.buzmgt.plan.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="SYS_MACHINE_TYPE")
public class MachineType implements Serializable{

  private static final long serialVersionUID = 1L;

  @Id
  private Long id;
  private String code;
  private String name;
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  @Override
  public String toString() {
    return "MachineType [id=" + id + ", code=" + code + ", name=" + name + "]";
  }
  
}
