package com.wangge.buzmgt.cash.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.wangge.buzmgt.teammember.entity.SalesMan;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;



/**
 * 
 * @author ydhl
 *
 */
@Entity
@Table(name="SYS_WATER_ORDER_CASH")
@NamedEntityGraph(
    name = "graph.WaterOrderCash.orderDetails",
    attributeNodes={
        @NamedAttributeNode(value="orderDetails",subgraph = "graph.WaterOrderCash.orderDetails.cash")
    },
    subgraphs = {
        @NamedSubgraph(
            name = "graph.WaterOrderCash.orderDetails.cash",
            attributeNodes = {
                @NamedAttributeNode("cash")
            }
        )
    }
)
public class WaterOrderCash implements Serializable  {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

	public enum WaterPayStatusEnum{
    UnPay("待付款"), OverPay("已付款");
    private String name;
    WaterPayStatusEnum(String name){
      this.name=name;
    }
    public String getName(){
      return name;
    }
  }

  @Id
  @Column(name="SERIAL_NO",insertable=false,updatable=false)
  private String serialNo ; //流水单号
  @Column(name = "user_id")
  private String userId ; //用户id
	@OneToOne
	@JoinColumn(name = "user_id",updatable = false,insertable = false)
	private SalesMan salesMan;//用户名

  private Float cashMoney  ;//收现金额
  private Float paymentMoney ;//支付金额
  private Integer isPunish ;//是否扣罚
  
  @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.LAZY)
  @JoinColumn(name="SERIAL_NO")
  private List<WaterOrderDetail> orderDetails;//订单详情
  
  @Enumerated(EnumType.ORDINAL)
  private WaterPayStatusEnum payStatus=WaterPayStatusEnum.UnPay;//审核状态
  
  @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")  
  private Date createDate ;//创建日期
  @JsonFormat(pattern="yyyy-MM-dd HH:mm",timezone = "GMT+8")  
  private Date payDate  ;//支付时间
  
  
  public List<WaterOrderDetail> getOrderDetails() {
    return orderDetails;
  }
  public void setOrderDetails(List<WaterOrderDetail> orderDetails) {
    this.orderDetails = orderDetails;
  }
  public String getPayStatus() {
    return payStatus.getName();
  }
  public void setPayStatus(WaterPayStatusEnum payStatus) {
    this.payStatus = payStatus;
  }
  public String getSerialNo() {
    return serialNo;
  }
  public void setSerialNo(String serialNo) {
    this.serialNo = serialNo;
  }
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  public Float getCashMoney() {
    return cashMoney;
  }
  public void setCashMoney(Float cashMoney) {
    this.cashMoney = cashMoney;
  }
  public Float getPaymentMoney() {
    return paymentMoney;
  }
  public void setPaymentMoney(Float paymentMoney) {
    this.paymentMoney = paymentMoney;
  }
  public Integer getIsPunish() {
    return isPunish;
  }
  public void setIsPunish(Integer isPunish) {
    this.isPunish = isPunish;
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
	public SalesMan getSalesMan() {
		return salesMan;
	}
	public void setSalesMan(SalesMan salesMan) {
		this.salesMan = salesMan;
	}

}
