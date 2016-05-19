package com.wangge.buzmgt.cash.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 
 * @author thor
 *
 */
@Entity
@Table(name="SYS_BANK_TRADE")
public class BankTrade implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  

  @Id
  @GenericGenerator(name = "idgen", strategy = "increment")
  @GeneratedValue(generator = "idgen")
  private Integer id ; //订单id
//  @Transient
  private String cradName ; //姓名
  private String cardNo;//卡号
 
  @Column(name="INCOME_MONEY")
  private Float money; //打款款金额
  
  
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  private Date createDate ;//创建日期
  
  @JsonFormat(pattern="MM.dd HH:mm",timezone = "GMT+8")  
  private Date payDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCradName() {
    return cradName;
  }

  public void setCradName(String cradName) {
    this.cradName = cradName;
  }

  public String getCardNo() {
    return cardNo;
  }

  public void setCardNo(String cardNo) {
    this.cardNo = cardNo;
  }

  public Float getMoney() {
    return money;
  }

  public void setMoney(Float money) {
    this.money = money;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }
  
  
  

}
