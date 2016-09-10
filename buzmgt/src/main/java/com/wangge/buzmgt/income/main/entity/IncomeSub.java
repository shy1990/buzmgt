package com.wangge.buzmgt.income.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
  * 业务员订单收益子表,存储每个订单的收益 
  * date: 2016年9月7日 上午9:57:00 <br/> 
  * 
  * @author yangqc 
  * @version  
  * @since JDK 1.8 
  */  
@Entity
@Table(name="sys_income_ticheng_sub")
public class IncomeSub {
  @SequenceGenerator( name = "idgen", sequenceName = "sys_income_job_seq")
  @GeneratedValue(generator = "idgen",strategy=GenerationType.SEQUENCE)
  @Id
  private long id;
  //主方案id,子方案id
  private long mainplanId,subplanId;
  //收益
  private double income;
  //订单id,业务员id
  private String orderno,usrId;
  //子方案类型,订单状态,记录状态(默认有效)
  private Integer planType,orderflag,flag=0;
  //收益计算时间
  @Temporal(TemporalType.DATE)
  private Date countDate;
  public long getMainplanId() {
    return mainplanId;
  }
  public void setMainplanId(long mainplanId) {
    this.mainplanId = mainplanId;
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
  public String getUsrId() {
    return usrId;
  }
  public void setUsrId(String usrId) {
    this.usrId = usrId;
  }
  public Integer getPlanType() {
    return planType;
  }
  public void setPlanType(Integer planType) {
    this.planType = planType;
  }
  public Integer getOrderflag() {
    return orderflag;
  }
  public void setOrderflag(Integer orderflag) {
    this.orderflag = orderflag;
  }
  public Integer getFlag() {
    return flag;
  }
  public void setFlag(Integer flag) {
    this.flag = flag;
  }
  public Date getCountDate() {
    return countDate;
  }
  public void setCountDate(Date countDate) {
    this.countDate = countDate;
  } 
  
}