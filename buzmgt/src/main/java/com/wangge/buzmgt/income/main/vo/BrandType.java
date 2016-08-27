package com.wangge.buzmgt.income.main.vo;


public class BrandType {
  private String brandId,machineType,name;

  public String getBrandId() {
    return brandId;
  }

  public void setBrandId(String brandId) {
    this.brandId = brandId;
  }

  public String getMachineType() {
    return machineType;
  }

  public void setMachineType(String machineType) {
    this.machineType = machineType;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BrandType(String brandId, String machineType, String name) {
    super();
    this.brandId = brandId;
    this.machineType = machineType;
    this.name = name;
  }

  public BrandType() {
    super();
  }
  
}
