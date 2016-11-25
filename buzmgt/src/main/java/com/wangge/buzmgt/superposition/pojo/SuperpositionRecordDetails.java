package com.wangge.buzmgt.superposition.pojo;

/**
 * 收益详情
 * Created by joe on 16-11-24.
 */
public class SuperpositionRecordDetails {

  private String shopName;//商家名称

  private String namePath;//商家区域

  private String orderNo;//订单号

  private String productionName;//手机名字

  private Integer amount;//数量

  private String payTime;//付款日期

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  public String getNamePath() {
    return namePath;
  }

  public void setNamePath(String namePath) {
    this.namePath = namePath;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getProductionName() {
    return productionName;
  }

  public void setProductionName(String productionName) {
    this.productionName = productionName;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getPayTime() {
    return payTime;
  }

  public void setPayTime(String payTime) {
    this.payTime = payTime;
  }

  @Override
  public String toString() {
    return "SuperpositionRecordDetails{" +
            "shopName='" + shopName + '\'' +
            ", namePath='" + namePath + '\'' +
            ", orderNo='" + orderNo + '\'' +
            ", productionName='" + productionName + '\'' +
            ", amount=" + amount +
            ", payTime='" + payTime + '\'' +
            '}';
  }
}
