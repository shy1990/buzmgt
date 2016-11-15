package com.wangge.buzmgt.importExcel.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author peter
 * @ClassName: CacelOrder
 * @Description: 取消订单表
 * @date 2016年11月12日 下午2:12:33
 */
@Entity
@Table(name = "VIEW_CACEL_ORDER")
public class CacelOrder implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  private String orderNum; //  主键orderNum

  private String id; //  订单Id

  private String userName; //  店铺名

  private String trueName; //  真实姓名

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.DATE)
  private Date createTime; //订单创建日期

  private int cacelSum;//取消订单量

  private int orderSum;//下单量

  public String getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(String orderNum) {
    this.orderNum = orderNum;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTrueName() {
    return trueName;
  }

  public void setTrueName(String trueName) {
    this.trueName = trueName;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public int getCacelSum() {
    return cacelSum;
  }

  public void setCacelSum(int cacelSum) {
    this.cacelSum = cacelSum;
  }

  public int getOrderSum() {
    return orderSum;
  }

  public void setOrderSum(int orderSum) {
    this.orderSum = orderSum;
  }
}
