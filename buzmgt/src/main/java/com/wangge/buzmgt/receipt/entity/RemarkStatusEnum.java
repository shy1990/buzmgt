package com.wangge.buzmgt.receipt.entity;

public enum RemarkStatusEnum {
  UnPay("0","未付款"),OverPay("1","已付款"),UnPayLate("2","超时未付款"),OverPayLate("3","超时已付款");
  
  private String name;
  private String value;
  RemarkStatusEnum(String value,String name){
    this.name=name;
    this.value=value;
  }
  public String getName(){
    return name;
  }
  public String getValue(){
    return value;
  }
}
