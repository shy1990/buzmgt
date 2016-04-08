package com.wangge.buzmgt.receipt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.teammember.entity.SalesMan;


/**
 * 未收款报备
 * @author Thor
 *
 */

@Entity
@Table(name="BIZ_UNPAYMENT_REMARK")
public class ReceiptRemark implements Serializable{
  
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Integer  id;
  private String aboveImgUrl;
  private String frontImgUrl;
  private String sideImgUrl;
  private String shopName;
  private String orderno;
  @Transient
  private OrderSignfor order;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  @Temporal(TemporalType.TIMESTAMP)
  private Date createTime = new Date();
  
  @Enumerated(EnumType.ORDINAL)
  private RemarkStatusEnum status;
  
  private String remark;
  
  @OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
  @JoinColumn(name = "salesman_id")
  private SalesMan salesMan;
  
  public Integer getId() {
    return id;
  }
  public void setId(Integer id) {
    this.id = id;
  }
  public String getAboveImgUrl() {
    return aboveImgUrl;
  }
  public void setAboveImgUrl(String aboveImgUrl) {
    this.aboveImgUrl = aboveImgUrl;
  }
  public String getFrontImgUrl() {
    return frontImgUrl;
  }
  public void setFrontImgUrl(String frontImgUrl) {
    this.frontImgUrl = frontImgUrl;
  }
  public String getSideImgUrl() {
    return sideImgUrl;
  }
  public void setSideImgUrl(String sideImgUrl) {
    this.sideImgUrl = sideImgUrl;
  }
  public String getShopName() {
    return shopName != null ? shopName : "";
  }
  public void setShopName(String shopName) {
    this.shopName = shopName;
  }
  public String getOrderno() {
    return orderno != null ? orderno : "";
  }
  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }
 
  public Date getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  public String getStatus() {
    return status != null ? status.getName() : "";
  }
  public void setStatus(RemarkStatusEnum status) {
    this.status = status;
  }
  public SalesMan getSalesMan() {
    return salesMan;
  }
  public void setSalesMan(SalesMan salesman) {
    this.salesMan = salesman;
  }
  public String getRemark() {
    return remark != null ? remark : "";
  }
  public void setRemark(String remark) {
    this.remark = remark;
  }
  public OrderSignfor getOrder() {
    return order;
  }
  public void setOrder(OrderSignfor order) {
    this.order = order;
  }
  
  
  
}
