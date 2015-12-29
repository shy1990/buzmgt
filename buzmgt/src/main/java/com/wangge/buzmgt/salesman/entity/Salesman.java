package com.wangge.buzmgt.salesman.entity;

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
import org.hibernate.annotations.Parameter;

import com.wangge.buzmgt.region.entity.Region;
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
public class Salesman implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = {
			@Parameter(name = "property", value = "user") })
	@Column(name = "user_id")
	private String id;
	
	private String simId;
	
	private SalesmanStatus status = SalesmanStatus.saojie;
	
	private String truename;
	private String jobName;
	
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public Salesman() {
		super();
	}
	
	
	
	public String getTruename() {
		return truename;
	}



	public void setTruename(String truename) {
		this.truename = truename;
	}



	public String getJobName() {
		return jobName;
	}



	public void setJobName(String jobName) {
		this.jobName = jobName;
	}



	public SalesmanStatus getStatus() {
		return status;
	}

	public void setStatus(SalesmanStatus status) {
		this.status = status;
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
	

}
