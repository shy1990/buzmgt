package com.wangge.buzmgt.income.main.vo;

public class MachineType {
  private String  name;
  private String code;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public MachineType(String name, String code) {
    super();
    this.name = name;
    this.code = code;
  }
  public MachineType() {
    super();
  }
  
}
