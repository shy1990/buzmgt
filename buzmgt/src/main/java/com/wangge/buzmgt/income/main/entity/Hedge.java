package com.wangge.buzmgt.income.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 售后冲减类; 售后计算未定
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "sys_income_shouhou_hedge")
public class Hedge {
  /*
   * sys_shouhou_hedge(id number(19),orderno VARCHAR2(50),sku
   * varchar2(500),skuname varchar2(500),shdate date,sum number(3),uniquenumber
   * varchar2(200));
   */
  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Long id;
  // 订单号,sku号,唯一码
  private String orderno, sku, uniquenumber;
  // 商品名称
  private String goodsName;
  // 到货日期
  private Date shdate;
  // 品牌数量
  private Integer sum;
  // 是否计算
  private Integer flag = 0;
  
  public String getOrderno() {
    return orderno;
  }
  
  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }
  
  public String getSku() {
    return sku;
  }
  
  public void setSku(String sku) {
    this.sku = sku;
  }
  
  public String getUniquenumber() {
    return uniquenumber;
  }
  
  public void setUniquenumber(String uniquenumber) {
    this.uniquenumber = uniquenumber;
  }
  
  public String getGoodsName() {
    return goodsName;
  }
  
  public void setGoodsName(String goodsName) {
    this.goodsName = goodsName;
  }
  
  public Date getShdate() {
    return shdate;
  }
  
  public void setShdate(Date shdate) {
    this.shdate = shdate;
  }
  
  public Integer getSum() {
    return sum;
  }
  
  public void setSum(Integer sum) {
    this.sum = sum;
  }
  
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public Hedge(String orderno, String sku, String goodsName, Integer sum, Date shdate, String uniquenumber) {
    super();
    this.orderno = orderno;
    this.sku = sku;
    this.uniquenumber = uniquenumber;
    this.goodsName = goodsName;
    this.shdate = shdate;
    this.sum = sum;
  }
  
  public Hedge() {
    super();
  }
  
  public Integer getFlag() {
    return flag;
  }
  
  public void setFlag(Integer flag) {
    this.flag = flag;
  }
  
}
