package com.wangge.buzmgt.income.main.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 订单商品明细<br/>
 * 表 date: 2016年9月13日 下午3:08:19 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "view_order_shouji_brandsku")
public class OrderGoods {
  @Id
  private String orderItemId;
  // 订单号;商品ID,商品名称,机型,品牌名称,品牌ID,sku编码,cat
  private String orderNo, goodId, goodName, machineType, brandName, brandid, skuNum, cat;
  private Double amount;
  private Integer nums;
  private float price;
  @Temporal(TemporalType.DATE)
  private Date payDate;
  
  public String getOrderItemId() {
    return orderItemId;
  }
  
  public void setOrderItemId(String orderItemId) {
    this.orderItemId = orderItemId;
  }
  
  public String getOrderNo() {
    return orderNo;
  }
  
  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }
  
  public String getGoodId() {
    return goodId;
  }
  
  public void setGoodId(String goodId) {
    this.goodId = goodId;
  }
  
  public String getGoodName() {
    return goodName;
  }
  
  public void setGoodName(String goodName) {
    this.goodName = goodName;
  }
  
  public String getBrandid() {
    return brandid;
  }
  
  public void setBrandid(String brandid) {
    this.brandid = brandid;
  }
  
  public String getSkuNum() {
    return skuNum;
  }
  
  public void setSkuNum(String skuNum) {
    this.skuNum = skuNum;
  }
  
  public String getCat() {
    return cat;
  }
  
  public void setCat(String cat) {
    this.cat = cat;
  }
  
  public Float getPrice() {
    return price;
  }
  
  public void setPrice(Float price) {
    this.price = price;
  }
  
  public Double getAmount() {
    return amount;
  }
  
  public void setAmount(Double amount) {
    this.amount = amount;
  }
  
  public Integer getNums() {
    return nums;
  }
  
  public void setNums(Integer nums) {
    this.nums = nums;
  }
  
  public String getMachineType() {
    return machineType;
  }
  
  public void setMachineType(String machineType) {
    this.machineType = machineType;
  }
  
  public String getBrandName() {
    return brandName;
  }
  
  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }
  
  public Date getPayDate() {
    return payDate;
  }
  
  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }
  
  public void setPrice(float price) {
    this.price = price;
  }
  
}
