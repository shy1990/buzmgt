package com.wangge.buzmgt.salesman.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;

/**
 * 
* @ClassName: Salesman
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaoZhen
* @date 2015年12月29日 上午11:33:20
*
 */
@Entity
@Table(name = "SYS_SALESMAN")
public class salesMan implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static enum SalesmanStatus {
		SAOJIE(1), KAIFA(2), WEIHU(3);
		
		private Integer num;
		
		 SalesmanStatus(Integer num) {
			this.num = num;
		}

		public Integer getNum() {
			return num;
		}
	}
	
	@Id
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = {
	@Parameter(name = "property", value = "user") })
	@Column(name = "user_id")
	private String id;
	
	private String simId;
	
    @Enumerated(EnumType.ORDINAL)
	private SalesmanStatus salesmanStatus = SalesmanStatus.SAOJIE;
	
	private String truename;
	
	private String jobNum;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;
	
	private String towns;

	public salesMan() {
		super();
	}
	
	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getJobNum() {
		return jobNum;
	}



	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}



	public SalesmanStatus getSalesmanStatus() {
		return salesmanStatus;
	}



	public void setSalesmanStatus(SalesmanStatus salesmanStatus) {
		this.salesmanStatus = salesmanStatus;
	}



	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getSimId() {
		return simId;
	}

	public void setSimId(String simId) {
		this.simId = simId;
	}



	public String getTowns() {
		return towns;
	}



	public void setTowns(String towns) {
		this.towns = towns;
	}
	

}
