package com.wangge.buzmgt.cash.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangge.buzmgt.common.FlagEnum;



/**
 * 
* @ClassName: BankTrade
* @Description: TODO(银行交易记录请求层)
* @author ChenGuop
* @date 2016年6月30日 下午3:06:01
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
  private String userId;
  private String cardName ; //姓名
  private String cardNo;//卡号
  private String bankName;//开户行
 
  @Column(name="INCOME_MONEY")
  private Float money; //打款款金额
  
  
  @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
  private Date createDate = new Date();//创建日期
  
  @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
  private Date payDate;
  
  @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")  
  private Date importDate;
  
  private Integer isArchive;//是否归档
  
  @Enumerated(EnumType.STRING)
  private FlagEnum flag = FlagEnum.NORMAL;
  
  public Integer getIsArchive() {
    return isArchive;
  }

  public void setIsArchive(Integer isArchive) {
    this.isArchive = isArchive;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getCardName() {
    return cardName;
  }

  public void setCardName(String cardName) {
    this.cardName = cardName;
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
    this.createDate = new Date();
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public Date getImportDate() {
    return importDate;
  }

  public void setImportDate(Date importDate) {
    this.importDate = importDate;
  }

  public FlagEnum getFlag() {
    return flag;
  }

  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }

  @Override
  public String toString() {
    return "BankTrade [id=" + id + ", userId=" + userId + ", cardName=" + cardName + ", cardNo=" + cardNo
        + ", bankName=" + bankName + ", money=" + money + ", createDate=" + createDate + ", payDate=" + payDate
        + ", importDate=" + importDate + ", isArchive=" + isArchive + "]";
  }

}
