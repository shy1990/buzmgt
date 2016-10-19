package com.wangge.buzmgt.ordersignfor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
* @ClassName: OrderItem
* @Description: TODO(这里用一句话描述这个类的作用)
* @author ChenGuop
* @date 2016年7月4日 下午2:47:16
* 
*/

@Entity
@Table(name="SYS_ORDER")
public class Order implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "MEMBER_ID")
  private String memberId;
  private String orderNum;
  private String shopName;
  
  private Date createTime;
  
  private String shipStatus;

  private Float amount;

  private String payMent;

  private String dealType;
  private String payStatus;

  private Float actualPayNum;

  private String walletPayNo;

  private String regionId;

  private String mobile;

  private String address;

  public String getMemberId() {
    return memberId;
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

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

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getShipStatus() {
    return shipStatus;
  }

  public void setShipStatus(String shipStatus) {
    this.shipStatus = shipStatus;
  }

  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

  public String getPayMent() {
    return payMent;
  }

  public void setPayMent(String payMent) {
    this.payMent = payMent;
  }

  public String getDealType() {
    return dealType;
  }

  public void setDealType(String dealType) {
    this.dealType = dealType;
  }

  public String getPayStatus() {
    return payStatus;
  }

  public void setPayStatus(String payStatus) {
    this.payStatus = payStatus;
  }

  public Float getActualPayNum() {
    return actualPayNum;
  }

  public void setActualPayNum(Float actualPayNum) {
    this.actualPayNum = actualPayNum;
  }

  public String getWalletPayNo() {
    return walletPayNo;
  }

  public void setWalletPayNo(String walletPayNo) {
    this.walletPayNo = walletPayNo;
  }

  public String getRegionId() {
    return regionId;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }
}
