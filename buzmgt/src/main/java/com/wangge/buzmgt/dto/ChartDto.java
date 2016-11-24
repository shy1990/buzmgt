package com.wangge.buzmgt.dto;

import java.math.BigDecimal;

public class ChartDto {

  //总人数
  private String persons;
  //出库百分比
  private String orderPercent ;
  //总金额百分比
  private String amountPercent;
  //流水单百分比
  private String serialPercent;
  //总人数百分比
  private String  personPercent;
  //总订单数
  private String orders;
  //总金额
  private BigDecimal amount;
  //总流水单
  private String serialNum ;

  public ChartDto(String orderPercent, String amountPercent, String orders,
      BigDecimal amount) {
    super();
    this.orderPercent = orderPercent;
    this.amountPercent = amountPercent;
    this.orders = orders;
    this.amount = amount;
  }

  public ChartDto(String persons, String orderPercent, String amountPercent,
      String personPercent, String orders, BigDecimal amount) {
    super();
    this.persons = persons;
    this.orderPercent = orderPercent;
    this.amountPercent = amountPercent;
    this.personPercent = personPercent;
    this.orders = orders;
    this.amount = amount;
  }

  public ChartDto(String persons, String orderPercent, String amountPercent,
      String serialPercent, String personPercent, String orders,
      BigDecimal amount, String serialNum) {
    super();
    this.persons = persons;
    this.orderPercent = orderPercent;
    this.amountPercent = amountPercent;
    this.serialPercent = serialPercent;
    this.personPercent = personPercent;
    this.orders = orders;
    this.amount = amount;
    this.serialNum = serialNum;
  }

  public String getPersons() {
    return persons;
  }

  public void setPersons(String persons) {
    this.persons = persons;
  }

  public String getOrderPercent() {
    return orderPercent;
  }

  public void setOrderPercent(String orderPercent) {
    this.orderPercent = orderPercent;
  }

  public String getAmountPercent() {
    return amountPercent;
  }

  public void setAmountPercent(String amountPercent) {
    this.amountPercent = amountPercent;
  }

  public String getSerialPercent() {
    return serialPercent;
  }

  public void setSerialPercent(String serialPercent) {
    this.serialPercent = serialPercent;
  }

  public String getPersonPercent() {
    return personPercent;
  }

  public void setPersonPercent(String personPercent) {
    this.personPercent = personPercent;
  }

  public String getOrders() {
    return orders;
  }

  public void setOrders(String orders) {
    this.orders = orders;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getSerialNum() {
    return serialNum;
  }

  public void setSerialNum(String serialNum) {
    this.serialNum = serialNum;
  }
  
  

  @Override
  public String toString() {
    return "ChartDto [persons=" + persons + ", orderPercent=" + orderPercent
        + ", amountPercent=" + amountPercent + ", serialPercent="
        + serialPercent + ", personPercent=" + personPercent + ", orders="
        + orders + ", amount=" + amount + ", serialNum=" + serialNum + "]";
  }
  

}
