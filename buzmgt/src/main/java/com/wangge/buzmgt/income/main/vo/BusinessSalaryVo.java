package com.wangge.buzmgt.income.main.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.OrderPayType;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor.OrderStatus;

/**
 * *2016-11-23 杨其才 主收益中业务员佣金的订单收益列表的查询视图
 */
@Entity
@Table(name = "view_income_basinesssal_order")
public class BusinessSalaryVo {
  @Id
  private String orderno;
  // 店铺名称,业务员id,月份,业务员姓名,店铺区域id(镇),业务员负责区域
  private String shopName, userId, cmonth, truename, regionId, rowind, namepath;
  @Enumerated(EnumType.ORDINAL)
  private OrderStatus orderStatus;
  // 收益
  private Double income = 0D;
  // 手机总数,收益类型 0预计,1实际
  private Integer phoneCount = 0, incometype = 0;
  @Temporal(TemporalType.DATE)
  private Date createTime = new Date();
  // --支付状态;支付状态：0 未付款，1 已付款， 2待退款， 3 已退款,4 卖家拒绝退款
  @Enumerated(EnumType.ORDINAL)
  private PayType payStatus;
  @Enumerated(EnumType.ORDINAL)
  private OrderPayType orderPayType;
  
  public String getOrderno() {
    return orderno;
  }
  
  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }
  
  public String getShopName() {
    return shopName;
  }
  
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  
  public String getUserId() {
    return userId;
  }
  
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
  public String getIncome() {
    if (income == 0) {
      return "无提成";
    } else {
      return income + "元";
    }
  }
  
  public void setIncome(Double income) {
    this.income = income;
  }
  
  public String getRowind() {
    if (rowind.length() < 2) {
      return "0" + rowind;
    } else {
      return rowind;
    }
  }
  
  public String getCmonth() {
    return cmonth;
  }
  
  public void setCmonth(String cmonth) {
    this.cmonth = cmonth;
  }
  
  public String getPhoneCount() {
    return phoneCount + "台";
  }
  
  public void setPhoneCount(Integer phoneCount) {
    this.phoneCount = phoneCount;
  }
  
  public void setRowind(String rowind) {
    this.rowind = rowind;
  }
  
  public Date getCreateTime() {
    return createTime;
  }
  
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public String getTruename() {
    return truename;
  }
  
  public void setTruename(String truename) {
    this.truename = truename;
  }
  
  public String getRegionId() {
    return regionId;
  }
  
  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }
  
  public String getOrderStatus() {
    return orderStatus == null ? "" : orderStatus.getName();
  }
  
  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }
  
  public Integer getIncometype() {
    
    return incometype;
  }
  
  public void setIncometype(Integer incometype) {
    this.incometype = incometype;
  }
  
  public String getPayStatus() {
    return payStatus == null ? "" : payStatus.getName();
  }
  
  public void setPayStatus(PayType payStatus) {
    this.payStatus = payStatus;
  }
  
  public String getOrderPayType() {
    return orderPayType == null ? "" : "(" + orderPayType.getName() + ")";
  }
  
  public void setOrderPayType(OrderPayType orderPayType) {
    this.orderPayType = orderPayType;
  }
  
  public String getNamepath() {
    return namepath;
  }
  
  public void setNamepath(String namepath) {
    this.namepath = namepath;
  }
  
  @Override
  public String toString() {
    return "BusinessSalaryVo [orderno=" + orderno + ", shopName=" + shopName + ", userId=" + userId + ", income="
        + income + ", cmonth=" + cmonth + ", truename=" + truename + ", regionId=" + regionId + ", orderStatus="
        + orderStatus + ", phoneCount=" + phoneCount + ", incometype=" + incometype + ", creatTime=" + createTime
        + ", payStatus=" + payStatus + ", orderPayType=" + orderPayType + "]";
  }
  
  public BusinessSalaryVo() {
    super();
  }
  
  public static enum PayType {
    // 0 未付款，1 已付款， 2待退款， 3 已退款,4 卖家拒绝退款
    UNPAY("未付款"), PAYED("已付款"), BACKING("待退款"), BACKED("已退款"), UNBACK("卖家拒绝退款");
    private String name;
    
    PayType(String name) {
      this.name = name;
    }
    
    public String getName() {
      return name;
    }
  }
}
