package com.wangge.buzmgt.common;

public enum CheckedEnum {
  // 正常
  UNCHECKED("未审核"), CHECKED("已审核");
  private String name;
  
  private CheckedEnum(String name) {
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
}
