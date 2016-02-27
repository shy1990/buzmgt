package com.wangge.buzmgt.sys.vo;

import java.math.BigDecimal;
//
public class OrderVo {
	private String shopName;//店铺名
	private BigDecimal orderTimes;//提货次数
	private BigDecimal orderNum;//累计提货量
	private BigDecimal orderTotalCost;	//累计交易额
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
  public BigDecimal getOrderTotalCost() {
    return orderTotalCost;
  }
  public void setOrderTotalCost(BigDecimal orderTotalCost) {
    this.orderTotalCost = orderTotalCost;
  }
	
}
