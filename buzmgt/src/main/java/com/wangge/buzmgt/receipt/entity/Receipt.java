package com.wangge.buzmgt.receipt.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BIZ_RECEIPT")
public class Receipt {

	 @Id
	  @Column(name = "receipt_id")
	  @GenericGenerator(name = "idgen", strategy = "increment")
	  @GeneratedValue(generator = "idgen")
	private Integer id;
	private Float amountCollected;
	private Integer receiptType;
	private Date createTime;
	private String accountId;

	private String orderNo;


	public Receipt(){

	}
	
	public Receipt(Float amountCollected, Integer receiptType, Date createTime,
								 String accountId, String orderNo) {
		super();
		this.amountCollected = amountCollected;
		this.receiptType = receiptType;
		this.createTime = createTime;
		this.accountId = accountId;
		this.orderNo = orderNo;

	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getAmountCollected() {
		return amountCollected;
	}
	public void setAmountCollected(Float amountCollected) {
		this.amountCollected = amountCollected;
	}
	public Integer getReceiptType() {
		return receiptType;
	}
	public void setReceiptType(Integer receiptType) {
		this.receiptType = receiptType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getOrderno() {
		return orderNo;
	}
	public void setOrderno(String orderNo) {
		this.orderNo = orderNo;
	}

}
