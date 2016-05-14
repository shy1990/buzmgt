package com.wangge.buzmgt.monthTask.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.teammember.entity.SalesMan;

@Entity
@Table(name = "sys_Monthshop_Basdata")
public class MonthshopBasData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 102L;
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	private long id;
	private String regionId;
//	private String shopid;
	private int lastmonthcount;
	private int monthAvg;
	private String month;
	private int visitCount;
//	private String salemanId;
//	private String salemanName;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGISTDATA_ID")
	private RegistData registData;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "salesman_ID")
	private SalesMan salesman;
	private int used;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public RegistData getRegistData() {
		return registData;
	}

	public void setRegistData(RegistData registData) {
		this.registData = registData;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

//	public String getShopid() {
//		return shopid;
//	}
//
//	public void setShopid(String shopid) {
//		this.shopid = shopid;
//	}

	public int getLastmonthcount() {
		return lastmonthcount;
	}

	public void setLastmonthcount(int lastmonthcount) {
		this.lastmonthcount = lastmonthcount;
	}

	public int getMonthAvg() {
		return monthAvg;
	}

	public void setMonthAvg(int monthAvg) {
		this.monthAvg = monthAvg;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}
	
//	public String getSalemanId() {
//		return salemanId;
//	}
//
//	public void setSalemanId(String salemanId) {
//		this.salemanId = salemanId;
//	}
//
//	public String getSalemanName() {
//		return salemanName;
//	}
//
//	public void setSalemanName(String salemanName) {
//		this.salemanName = salemanName;
//	}

	public MonthshopBasData() {
		super();
	}

	public int getUsed() {
		return used;
	}

	public void setUsed(int used) {
		this.used = used;
	}

	public SalesMan getSalesman() {
		return salesman;
	}

	public void setSalesman(SalesMan salesman) {
		this.salesman = salesman;
	}

	public MonthshopBasData( String regionId, int lastmonthcount, int monthAvg, String month, int visitCount,
			RegistData registData, SalesMan salesman) {
		super();
		this.regionId = regionId;
		this.lastmonthcount = lastmonthcount;
		this.monthAvg = monthAvg;
		this.month = month;
		this.visitCount = visitCount;
		this.registData = registData;
		this.salesman = salesman;
	}

	

	

}
