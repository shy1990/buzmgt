package com.wangge.buzmgt.sys.vo;

public class OrderHistData {
	private String menberid;
	private String month;
	private String days;
	private String username;
	private String town;
	public String getMenberid() {
		return menberid;
	}
	public void setMenberid(String menberid) {
		this.menberid = menberid;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDays() {
		return days;
	}
	public void setDays(String days) {
		this.days = days;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public OrderHistData() {
		super();
	}
	
}
