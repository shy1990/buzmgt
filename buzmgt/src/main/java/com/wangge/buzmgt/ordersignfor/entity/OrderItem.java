package com.wangge.buzmgt.ordersignfor.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
* @ClassName: OrderItem
* @Description: TODO(这里用一句话描述这个类的作用)
* @author ChenGuop
* @date 2016年7月4日 下午2:47:16
* 
*/

@Entity
@Table(name="SYS_ORDER_ITEM")
public class OrderItem implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "ORDER_ITEM_ID")
  private String id;
  private String name;
  private String orderNum;
  private String type;//sku（手机）,accessories（配件）,gift（赠品）,point(积分)
  
  private Float price;
	@Transient
	private Float incomeMoney = 0.0f;//收益金额
  
  private Integer nums;

  private Float amount;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOrderNum() {
    return orderNum;
  }

  public void setOrderNum(String orderNum) {
    this.orderNum = orderNum;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Integer getNums() {
    return nums;
  }

  public void setNums(Integer nums) {
    this.nums = nums;
  }

  public Float getAmount() {
    return amount;
  }

  public void setAmount(Float amount) {
    this.amount = amount;
  }

	public Float getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(Float incomeMoney) {
		this.incomeMoney = incomeMoney;
	}
}
