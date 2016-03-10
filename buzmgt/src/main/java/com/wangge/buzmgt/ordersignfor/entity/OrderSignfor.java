package com.wangge.buzmgt.ordersignfor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@Entity
@SecondaryTable(name = "SYS_COORDINATES")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Table(name = "BIZ_ORDER_SIGNFOR")
public class OrderSignfor implements Serializable {

  /**
  * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
  */
  
  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "SIGNID")
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  
  private String fastmailNo;
  private String orderNo;
  private String userId;
  private String userPhone;
  private String shopName;
  private String orderPrice;
  private String phoneCount;
  private String crreateTime;
  private Date yewuSignforTime; 
  private Date customSignforTime;
  private Integer orderStatus;
  private Integer orderPayType;
  private String yewuSignforGeopoint;
  private String customSignforGeopoint;
  private Integer customSignforException;
  
  public OrderSignfor (){
    super();
  }
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getFastmailNo() {
    return fastmailNo;
  }
  public void setFastmailNo(String fastmailNo) {
    this.fastmailNo = fastmailNo;
  }
  public String getOrderNo() {
    return orderNo;
  }
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getUserPhone() {
    return userPhone;
  }
  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone;
  }
  public String getShopName() {
    return shopName;
  }
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  public String getOrderPrice() {
    return orderPrice;
  }
  public void setOrderPrice(String orderPrice) {
    this.orderPrice = orderPrice;
  }
  public String getPhoneCount() {
    return phoneCount;
  }
  public void setPhoneCount(String phoneCount) {
    this.phoneCount = phoneCount;
  }
  public String getCrreateTime() {
    return crreateTime;
  }
  public void setCrreateTime(String crreateTime) {
    this.crreateTime = crreateTime;
  }
  public Date getYewuSignforTime() {
    return yewuSignforTime;
  }
  public void setYewuSignforTime(Date yewuSignforTime) {
    this.yewuSignforTime = yewuSignforTime;
  }
  public Date getCustomSignforTime() {
    return customSignforTime;
  }
  public void setCustomSignforTime(Date customSignforTime) {
    this.customSignforTime = customSignforTime;
  }
  public Integer getOrderStatus() {
    return orderStatus;
  }
  public void setOrderStatus(Integer orderStatus) {
    this.orderStatus = orderStatus;
  }
  public Integer getOrderPayType() {
    return orderPayType;
  }
  public void setOrderPayType(Integer orderPayType) {
    this.orderPayType = orderPayType;
  }
  public String getYewuSignforGeopoint() {
    return yewuSignforGeopoint;
  }
  public void setYewuSignforGeopoint(String yewuSignforGeopoint) {
    this.yewuSignforGeopoint = yewuSignforGeopoint;
  }
  public String getCustomSignforGeopoint() {
    return customSignforGeopoint;
  }
  public void setCustomSignforGeopoint(String customSignforGeopoint) {
    this.customSignforGeopoint = customSignforGeopoint;
  }
  public Integer getCustomSignforException() {
    return customSignforException;
  }
  public void setCustomSignforException(Integer customSignforException) {
    this.customSignforException = customSignforException;
  }
  
  

}
