package com.wangge.buzmgt.sys.vo;

import java.math.BigDecimal;
//
public class CustomerVo {
  private int registId;
	private String shopName;//店铺名
	private BigDecimal orderTimes;//提货次数
	private BigDecimal orderNum;//累计提货量
  private String address;//地址
  private String  period;//距上次提货
  private int associate;//关联商家
  private String lastVisit;//上次拜访
  private String userId;
  private String coordinate;
  private int avgOrderNum;//平均提货量
  
  public String getShopName() {
    return shopName;
  }
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  public BigDecimal getOrderTimes() {
    return orderTimes;
  }
  public void setOrderTimes(BigDecimal orderTimes) {
    this.orderTimes = orderTimes;
  }
  public BigDecimal getOrderNum() {
    return orderNum;
  }
  public void setOrderNum(BigDecimal orderNum) {
    this.orderNum = orderNum;
  }
  public String getAddress() {
    return address;
  }
  public void setAddress(String address) {
    this.address = address;
  }
  public String getPeriod() {
    return period;
  }
  public void setPeriod(String period) {
    this.period = period;
  }
  public int getAssociate() {
    return associate;
  }
  public void setAssociate(int associate) {
    this.associate = associate;
  }
  public String getLastVisit() {
    return lastVisit;
  }
  public void setLastVisit(String lastVisit) {
    this.lastVisit = lastVisit;
  }
  public int getRegistId() {
    return registId;
  }
  public void setRegistId(int registId) {
    this.registId = registId;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getCoordinate() {
    return coordinate;
  }
  public void setCoordinate(String coordinate) {
    this.coordinate = coordinate;
  }
  public int getAvgOrderNum() {
    return avgOrderNum;
  }
  public void setAvgOrderNum(int avgOrderNum) {
    this.avgOrderNum = avgOrderNum;
  }
	
}
