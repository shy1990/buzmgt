package com.wangge.buzmgt.receipt.entity;

/**
 * Created by jiabin on 16-10-14.
 */
public class BillSalesmanVo {

  private String orderNum;//订单号

  private String shopName;//商店名字

  private int isPrimaryAccount;//是否主张号

  private Double  arrears;//拖欠金额

  private int orderPayStatus;//收款方式

  private int billStatus;//待收情况

  private String todayDate;//日期


  public String getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(String orderNum) {
    this.orderNum = orderNum;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public int getIsPrimaryAccount() {
    return isPrimaryAccount;
  }

  public void setIsPrimaryAccount(int isPrimaryAccount) {
    this.isPrimaryAccount = isPrimaryAccount;
  }

  public Double getArrears() {
    return arrears;
  }

  public void setArrears(Double arrears) {
    this.arrears = arrears;
  }

  public int getOrderPayStatus() {
    return orderPayStatus;
  }

  public void setOrderPayStatus(int orderPayStatus) {
    this.orderPayStatus = orderPayStatus;
  }

  public int getBillStatus() {
    return billStatus;
  }

  public void setBillStatus(int billStatus) {
    this.billStatus = billStatus;
  }

  public String getTodayDate() {
    return todayDate;
  }

  public void setTodayDate(String todayDate) {
    this.todayDate = todayDate;
  }
}
