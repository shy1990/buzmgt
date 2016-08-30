package com.wangge.buzmgt.saojie.entity;

public enum Type {
  MOBILE("手机");
  private String type;
  Type(String type){
    this.type = type;
  }
  
  public String getType(){
    return this.type;
  }
}
