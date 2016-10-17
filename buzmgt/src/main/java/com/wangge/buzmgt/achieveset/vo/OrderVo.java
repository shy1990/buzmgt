package com.wangge.buzmgt.achieveset.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 订单展示实体
 * OrderVo
 *
 * @author ChenGuop
 * @date 2016/10/14
 */
@Entity
@Table(name = "BIZ_ORDER_SIGNFOR")
public class OrderVo implements Serializable {

	@Id
	@Column(name = "SIGNID")
	private Long id;
	@Column(name = "ORDER_NO")
	private String orderNo;
	@Column(name="user_id")
	private String userId;
	private String shopName;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String toString() {
		return "OrderVo{" +
						"orderNo='" + orderNo + '\'' +
						", id=" + id +
						", userId='" + userId + '\'' +
						", shopName='" + shopName + '\'' +
						'}';
	}
}
