package com.wangge.buzmgt.cash.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
  private Integer rnid;
  private String userId;
  private Date payDate;
  private String username;
  
  @Transient
  private List<BankTrade> bankTrades;
  

  @Transient
  private List<WaterOrderCash> cashs;


  
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
