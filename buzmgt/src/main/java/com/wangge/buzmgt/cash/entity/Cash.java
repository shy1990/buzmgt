package com.wangge.buzmgt.cash.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ywsalary.entity.FlagEnum;



/**
 * 
 * @author ydhl
 *
 */
@Entity
@Table(name="SYS_CASH_RECORD")
public class Cash implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  
  public enum CashStatusEnum{
    //结算
    UnPay("未支付"),OverCash("已结算"), OverPay("已支付");
    private String name;
    CashStatusEnum(String name){
      this.name=name;
    }
    public String getName(){
      return name;
    }
  }
  
  
  @Id
  @Column(name="id")
  private Long cashId ; //订单id
//  @Transient
  @OneToOne(cascade = CascadeType.REFRESH,fetch=FetchType.LAZY)
  @JoinColumn(name="id",insertable=false,updatable=false)
  private OrderSignfor order;//订单
  private String userId ; //用户id
  
  @Enumerated(EnumType.ORDINAL)
  private CashStatusEnum status;//支付状态
  
  private Date createDate ;//创建日期
  private Date payDate  ;//支付时间
  @Enumerated(EnumType.STRING)
  private FlagEnum flag = FlagEnum.NORMAL;
  
  
  @Transient
  private String isTimeOut;
  
  
  
  public String getIsTimeOut() {
    return isTimeOut;
  }
  public void setIsTimeOut(String isTimeOut) {
    this.isTimeOut = isTimeOut;
  }
  public Long getCashId() {
    return cashId;
  }
  public void setCashId(Long id) {
    this.cashId = id;
  }
  public OrderSignfor getOrder() {
    return order;
  }
  public void setOrder(OrderSignfor order) {
    this.order = order;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public String getStatus() {
    return status.getName();
  }
  public void setStatus(CashStatusEnum status) {
    this.status = status;
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
  public FlagEnum getFlag() {
    return flag;
  }
  public void setFlag(FlagEnum flag) {
    this.flag = flag;
  }
  @Override
  public String toString() {
    return "Cash [cashId=" + cashId + ", order=" + order + ", userId=" + userId + ", status=" + status + ", createDate="
        + createDate + ", payDate=" + payDate + ", isTimeOut=" + isTimeOut + "]";
  }

}
