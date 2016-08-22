package com.wangge.buzmgt.common;

public enum FlagEnum {
  // 正常
  NORMAL("正常"), DEL("删除");
  private String name;

  private FlagEnum(String name) {
    this.name = name; 
  }

  public String getName() {
    return name;
  }
}
