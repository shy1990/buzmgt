package com.wangge.buzmgt.brandincome.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/** 
  * 业务员品牌型号订单收益子表,存储每个品牌型号规则的收益
  * date: 2016年9月7日 上午9:57:00 <br/> 
  * 
  * @version
  * @since JDK 1.8 
  */  
@Entity
@Table(name="SYS_INCOME_TICHENG_BRAND")
public class BrandIncomeSub {
  @Id
  @GenericGenerator(name = "idgen",strategy = "increment")
  @GeneratedValue(generator="idgen")
  @Column(name = "ID")
  private long id;
  //子方案id,主方案id
  private long subplanId,mainplanId;
  //收益
  private double income;
  //订单id,业务员id
  private String orderno,userId;
  //订单状态(1已付款,0已出库),记录状态(默认有效)
  private Integer orderflag,used=0;
  //收益计算时间
  @Temporal(TemporalType.DATE)
  private Date countDate;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getSubplanId() {
    return subplanId;
  }

  public void setSubplanId(long subplanId) {
    this.subplanId = subplanId;
  }

  public double getIncome() {
    return income;
  }

  public void setIncome(double income) {
    this.income = income;
  }

  public String getOrderno() {
    return orderno;
  }

  public void setOrderno(String orderno) {
    this.orderno = orderno;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public long getMainplanId() {
    return mainplanId;
  }

  public void setMainplanId(long mainplanId) {
    this.mainplanId = mainplanId;
  }

  public Integer getOrderflag() {
    return orderflag;
  }

  public void setOrderflag(Integer orderflag) {
    this.orderflag = orderflag;
  }

  public Integer getUsed() {
    return used;
  }

  public void setUsed(Integer used) {
    this.used = used;
  }

  public Date getCountDate() {
    return countDate;
  }

  public void setCountDate(Date countDate) {
    this.countDate = countDate;
  }
}
