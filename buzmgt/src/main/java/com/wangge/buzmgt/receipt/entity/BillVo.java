package com.wangge.buzmgt.receipt.entity;

/**
 * Created by jiabin on 16-10-14.
 */
public class BillVo {

  private String userId;//业务员id

  private String salemanName;//业务名字

  private String regionName;//区域名字

  private Double todayAllShouldPay;//当日所有货物总款，不包括线上支付和pos

  private Double  todayShouldPay;//当日未支付的总额

  private Double historyShouldPay;//历史拖欠金额

  private String todayDate;//日期


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getSalemanName() {
    return salemanName;
  }

  public void setSalemanName(String salemanName) {
    this.salemanName = salemanName;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public Double getTodayAllShouldPay() {
    return todayAllShouldPay;
  }

  public void setTodayAllShouldPay(Double todayAllShouldPay) {
    this.todayAllShouldPay = todayAllShouldPay;
  }

  public Double getTodayShouldPay() {
    return todayShouldPay;
  }

  public void setTodayShouldPay(Double todayShouldPay) {
    this.todayShouldPay = todayShouldPay;
  }

  public Double getHistoryShouldPay() {
    return historyShouldPay;
  }

  public void setHistoryShouldPay(Double historyShouldPay) {
    this.historyShouldPay = historyShouldPay;
  }

  public String getTodayDate() {
    return todayDate;
  }

  public void setTodayDate(String todayDate) {
    this.todayDate = todayDate;
  }
}
