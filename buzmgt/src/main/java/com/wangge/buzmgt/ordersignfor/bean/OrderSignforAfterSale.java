package com.wangge.buzmgt.ordersignfor.bean;

import java.util.Date;

/**
 * Created by joe on 16-11-18.
 * 用于展示售后的数据
 * select os.ORDER_NO,os.FASTMAIL_NO,usr.NICKNAME,region.NAMEPATH,os.OVER_TIME from biz_order_signfor os
 */
public class OrderSignforAfterSale {
  private String orderNo;
  private String fastMallNo;
  private String nickName;
  private String namePath;
  private String overTime;
  private String createTime;
  private String status;

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getFastMallNo() {
    return fastMallNo;
  }

  public void setFastMallNo(String fastMallNo) {
    this.fastMallNo = fastMallNo;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getNamePath() {
    return namePath;
  }

  public void setNamePath(String namePath) {
    this.namePath = namePath;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getOverTime() {
    return overTime;
  }

  public void setOverTime(String overTime) {
    this.overTime = overTime;
  }

  public String getCreateTime() {
    return createTime;
  }

  public void setCreateTime(String createTime) {
    this.createTime = createTime;
  }

  @Override
  public String toString() {
    return "OrderSignforAfterSale{" +
            "orderNo='" + orderNo + '\'' +
            ", fastMallNo='" + fastMallNo + '\'' +
            ", nickName='" + nickName + '\'' +
            ", namePath='" + namePath + '\'' +
            ", overTime='" + overTime + '\'' +
            ", createTime='" + createTime + '\'' +
            ", status='" + status + '\'' +
            '}';
  }
}
