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
  private Date createDate;
  private Float cashMoney=new Float(0);//收现金额
  @Transient
  private String cardName;
  @Transient
  private Float incomeMoney=new Float(0);//打款金额
  @Transient
  private Float shouldPayMoney=new Float(0);//应付金额
  @Transient
  private Float debtMoney=new Float(0);//欠款金额（欠款+扣罚）
  @Transient
  private Float stayMoney;//待收金额
  @Transient
  private String isCheck;//是否审核
  
  @Transient
  private List<MonthPunish> monthPunishs ;
  @Transient
  private List<BankTrade> bankTrades;
  

  @Transient
  private List<WaterOrderCash> cashs;


  
  
  public String getIsCheck() {
    return isCheck;
  }


  public void setIsCheck(String isCheck) {
    this.isCheck = isCheck;
  }


  public String getCardName() {
    return cardName;
  }


  public void setCardName(String cardName) {
    this.cardName = cardName;
  }


  public Float getIncomeMoney() {
    return incomeMoney;
  }


  public void setIncomeMoney(Float incomeMoney) {
    this.incomeMoney = incomeMoney;
  }


  public Float getShouldPayMoney() {
    shouldPayMoney=cashMoney+debtMoney;
    return shouldPayMoney;
  }


  public void setShouldPayMoney(Float shouldPayMoney) {
    this.shouldPayMoney = shouldPayMoney;
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
    stayMoney = cashMoney+debtMoney-incomeMoney;
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

  
  public Date getCreateDate() {
    return createDate;
  }


  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }


  public List<MonthPunish> getMonthPunishs() {
    return monthPunishs;
  }


  public void setMonthPunishs(List<MonthPunish> monthPunishs) {
    this.monthPunishs = monthPunishs;
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
