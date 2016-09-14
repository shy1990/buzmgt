package com.wangge.buzmgt.income.main.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
  private String orderNo, goodId, goodName, machineTypebrandName, brandid, skuNum, cat;
  private Double price, amount;
  private Integer nums;
  
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
  
  public String getMachineTypebrandName() {
    return machineTypebrandName;
  }
  
  public void setMachineTypebrandName(String machineTypebrandName) {
    this.machineTypebrandName = machineTypebrandName;
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
  
  public Double getPrice() {
    return price;
  }
  
  public void setPrice(Double price) {
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
  
}
