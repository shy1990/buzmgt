package com.wangge.buzmgt.ordersignfor.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.buzmgt.teammember.entity.SalesMan;

//@JsonInclude(Include.NON_EMPTY)
@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler"})
@Table(name = "SYS_ORDER_SIGNFOR")
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
  @Transient
  private String aging;//时效
  //业务签收异常标记
  @Transient
  private String ywSignforTag;
  
//  private String userId;
//  private String truename;
//  @Transient
//  private String ywName;//业务名称;
 
 
  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private SalesMan salesMan;
  
  private String userPhone;
  private String shopName;
  private Float orderPrice;
  private Integer phoneCount;
  private Date creatTime;
  private Date yewuSignforTime; 
  private Date customSignforTime;
  private Integer orderStatus;
  private Integer orderPayType;
  private String yewuSignforGeopoint;
  private String customSignforGeopoint;
  private Integer customSignforException;
  private int partsCount;
  private Date fastmailTime;
  private String customUnSignRemark;
  
  
  public String getYwSignforTag() {
    return ywSignforTag;
  }

  public void setYwSignforTag(String ywSignforTag) {
    this.ywSignforTag = ywSignforTag;
  }

  
  public String getAging() {
    return aging;
  }

  public void setAging(String aging) {
    this.aging = aging;
  }

  public Date getCreatTime() {
    return creatTime;
  }

  public void setCreatTime(Date creatTime) {
    this.creatTime = creatTime;
  }

  public SalesMan getSalesMan() {
    return salesMan;
  }

  public void setSalesMan(SalesMan salesMan) {
    this.salesMan = salesMan;
  }

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
//  public String getUserId() {
//    return userId;
//  }
//  public void setUserId(String userId) {
//    this.userId = userId;
//  }
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
 
  public Float getOrderPrice() {
    return orderPrice;
  }

  public void setOrderPrice(Float orderPrice) {
    this.orderPrice = orderPrice;
  }

  public Integer getPhoneCount() {
    return phoneCount;
  }

  public void setPhoneCount(Integer phoneCount) {
    this.phoneCount = phoneCount;
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

  public Date getFastmailTime() {
    return fastmailTime;
  }

  public void setFastmailTime(Date fastmailTime) {
    this.fastmailTime = fastmailTime;
  }

  public String getCustomUnSignRemark() {
    return customUnSignRemark;
  }

  public void setCustomUnSignRemark(String customUnSignRemark) {
    this.customUnSignRemark = customUnSignRemark;
  }

  public int getPartsCount() {
    return partsCount;
  }

  public void setPartsCount(int partsCount) {
    this.partsCount = partsCount;
  }

  
}
