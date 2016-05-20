package com.wangge.buzmgt.salesman.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * 业务员基础数据实体类
 * @author 
 *
 */
@Entity
@Table(name="SYS_SALESMAN_DATA")
public class SalesmanData implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;//id
	private String userId;//业务id
	private String name;//姓名
	private String tel;//电话
	private Date addTime;//添加时间
	private List<BankCard> card = new ArrayList<BankCard>();//银行卡信息
	@Id
	@GenericGenerator(name="idgen",strategy="increment")
	@GeneratedValue(generator="idgen")//自定义生成主键策略
	@Column(name="ID")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name="TEL")
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="ADD_TIME")
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="SDID")//在card表中有这个字段（这个表中的id）
	public List<BankCard> getCard() {
		return card;
	}
	public void setCard(List<BankCard> card) {
		this.card = card;
	}
	@Override
	public String toString() {
		return "SalesmanData [id=" + id + ", userId=" + userId + ", name=" + name + ", tel=" + tel + ", addTime="
				+ addTime + ", card=" + card + "]";
	}
	
}
