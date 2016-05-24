package com.wangge.buzmgt.cash.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;





/**
 * 
 * @author thor
 *
 */
@Entity
@Table(name="SYS_CHECK_CASH")
public class CheckCash implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  

  @Id
  @Column(name="RNID")
  private Integer rnid;
  private String userId;
  private Date payDate;
  @Column(name="card_name")
  private String username;
  
  private Float incomeMoney=new Float(0);//打款金额
  
  @Transient
  private Float cashMoney=new Float(0);//收现金额
  @Transient
  private Float debtMoney=new Float(0);//欠款金额（欠款+扣罚）
  @Transient
  private Float stayMoney=cashMoney+debtMoney-incomeMoney;//待收金额
  
  @Transient
  private MonthPunish monthPunish ;
  @Transient
  private List<BankTrade> bankTrades;
  

  @Transient
  private List<WaterOrderCash> cashs;


  
  
  public Float getIncomeMoney() {
    return incomeMoney;
  }


  public void setIncomeMoney(Float incomeMoney) {
    this.incomeMoney = incomeMoney;
  }


  public Float getCashMoney() {
    return cashMoney;
  }


  public void setCashMoney(Float cashMoney) {
    this.cashMoney = cashMoney;
  }


  public Float getDebtMoney() {
    return debtMoney;
  }


  public void setDebtMoney(Float debtMoney) {
    this.debtMoney = debtMoney;
  }


  public Float getStayMoney() {
    return stayMoney;
  }


  public void setStayMoney(Float stayMoney) {
    this.stayMoney = stayMoney;
  }


  public Integer getRnid() {
    return rnid;
  }


  public void setRnid(Integer rnid) {
    this.rnid = rnid;
  }


  public String getUserId() {
    return userId;
  }


  public void setUserId(String userId) {
    this.userId = userId;
  }


  public Date getPayDate() {
    return payDate;
  }


  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }


  public String getUsername() {
    return username;
  }


  public void setUsername(String username) {
    this.username = username;
  }


  
  public MonthPunish getMonthPunish() {
    return monthPunish;
  }


  public void setMonthPunish(MonthPunish monthPunish) {
    this.monthPunish = monthPunish;
  }


  public List<BankTrade> getBankTrades() {
    return bankTrades;
  }


  public void setBankTrades(List<BankTrade> bankTrades) {
    this.bankTrades = bankTrades;
  }


  public List<WaterOrderCash> getCashs() {
    return cashs;
  }


  public void setCashs(List<WaterOrderCash> cashs) {
    this.cashs = cashs;
  }

  
  
  

}
