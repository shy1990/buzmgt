package com.wangge.buzmgt.receipt.entity;

import java.math.BigDecimal;

/**
 * Created by jiabin on 16-10-14.
 */
public class BillVo {

  private String userId;//业务员id

  private String salemanName;//业务名字

  private String regionName;//区域名字

  private BigDecimal todayAllShouldPay;//当日所有货物总款，不包括线上支付和pos

  private BigDecimal  todayShouldPay;//当日未支付的总额

  private BigDecimal historyShouldPay;//历史拖欠金额

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


  public BigDecimal getTodayAllShouldPay() {
    return todayAllShouldPay;
  }

  public void setTodayAllShouldPay(BigDecimal todayAllShouldPay) {
    this.todayAllShouldPay = todayAllShouldPay;
  }

  public BigDecimal getTodayShouldPay() {
    return todayShouldPay;
  }

  public void setTodayShouldPay(BigDecimal todayShouldPay) {
    this.todayShouldPay = todayShouldPay;
  }

  public BigDecimal getHistoryShouldPay() {
    return historyShouldPay;
  }

  public void setHistoryShouldPay(BigDecimal historyShouldPay) {
    this.historyShouldPay = historyShouldPay;
  }

  public String getTodayDate() {
    return todayDate;
  }

  public void setTodayDate(String todayDate) {
    this.todayDate = todayDate;
  }
}
