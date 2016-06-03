package com.wangge.buzmgt.salesman.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 业务员银行卡信息
 * @author 神盾局
 *
 */
@Entity
@Table(name="SYS_BANK_CARD")
public class BankCard implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long bankId;//id
	private String cardNumber;//卡号
	private String  bankName;//开户行 
	@Id
	@GenericGenerator(name="idgen",strategy="increment")
	@GeneratedValue(generator="idgen")//自定义生成主键策略
	@Column(name="ID")
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	
	@Column(name="CARD_NUMBER")
	public String getCardNumber() {
		return cardNumber;
	}
	
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	@Column(name="BANK_NAME")
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@Override
	public String toString() {
		return "BankCard [bankId=" + bankId + ", cardNumber=" + cardNumber + ", bankName=" + bankName + "]";
	}
	
}
