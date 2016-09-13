package com.wangge.buzmgt.disruptor;

/**
 * Created by jiabin on 16-9-2.
 */
public class MallOrder {
  private String id;
  private String shipStatus;//订单发货状态
  private String member_id;//商城业务员
  private String orderNum;//订单号


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getShipStatus() {
    return shipStatus;
  }

  public void setShipStatus(String shipStatus) {
    this.shipStatus = shipStatus;
  }

  public String getMember_id() {
    return member_id;
  }

  public void setMember_id(String member_id) {
    this.member_id = member_id;
  }

  public String getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(String orderNum) {
    this.orderNum = orderNum;
  }
}
