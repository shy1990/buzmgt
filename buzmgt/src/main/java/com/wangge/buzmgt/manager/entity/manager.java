package com.wangge.buzmgt.manager.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import com.wangge.buzmgt.sys.entity.User;
@Entity
@Table(name = "SYS_MANAGER")
public class manager implements Serializable {
	
	/**
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	*/
	
	private static final long serialVersionUID = -4389885527660615969L;
	@Id
	@GeneratedValue(generator = "foreign")
	@GenericGenerator(name = "foreign", strategy = "foreign", parameters = {
	@Parameter(name = "property", value = "user") })
	@Column(name = "user_id")
	private String id;
	
    private String truename;
	
	private String jobNum;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
