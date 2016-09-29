package com.wangge.buzmgt.income.main.vo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 售后冲减表视图
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Entity
@Table(name = "view_income_shouhou_hedge")
public class HedgeVo {
  /*
   * sys_shouhou_hedge(id number(19),orderno VARCHAR2(50),sku
   * varchar2(500),skuname varchar2(500),shdate date,sum number(3),uniquenumber
   * varchar2(200));
   */
  @Id
  private Long id;
  // 订单号,sku号,唯一码  s.user_id, s.truename,r.namepath
  private String orderno, sku, uniquenumber,userId,namepath,truename;
  // 型号名称
  private String goodsName;
  // 到货日期
  private Date shdate;
  // 品牌数量,是否已计算
  private Integer sum,flag=0;
  //序号
  private Long rowind;
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
  

  public HedgeVo() {
    super();
  }

  public Integer getFlag() {
    return flag;
  }

  public void setFlag(Integer flag) {
    this.flag = flag;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getNamepath() {
    return namepath;
  }

  public void setNamepath(String namepath) {
    this.namepath = namepath;
  }

  public String getTruename() {
    return truename;
  }

  public void setTruename(String truename) {
    this.truename = truename;
  }

  public Long getRowind() {
    return rowind;
  }

  public void setRowind(Long rowind) {
    this.rowind = rowind;
  }
  
}
