package com.wangge.buzmgt.teammember.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wangge.buzmgt.region.entity.Region;
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
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SalesMan implements Serializable {
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

	private String jobNum;

	@Column(name = "ASSESS_STAGE")
	private String assessStage;
	
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "region_id")
	private Region region;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	private String towns;

	private String mobile;
	@Temporal(TemporalType.DATE)
	private Date regdate;

	private int isOldSalesman;
	private int isPrimaryAccount;
	
	@Column(name="ASSESS_STAGE_SUM")
	private Integer assessStageSum;

	public SalesMan() {
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

	public String getTowns() {
		return towns;
	}

	public void setTowns(String towns) {
		this.towns = towns;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	public String getAssessStage() {
		return assessStage;
	}

	public void setAssessStage(String assessStage) {
		this.assessStage = assessStage;
	}

	public int getIsOldSalesman() {
		return isOldSalesman;
	}

	public void setIsOldSalesman(int isOldSalesman) {
		this.isOldSalesman = isOldSalesman;
	}

  public int getIsPrimaryAccount() {
    return isPrimaryAccount;
  }

  public void setIsPrimaryAccount(int isPrimaryAccount) {
    this.isPrimaryAccount = isPrimaryAccount;
  }

  public Integer getAssessStageSum() {
    return assessStageSum;
  }

  public void setAssessStageSum(Integer assessStageSum) {
    this.assessStageSum = assessStageSum;
  }

  @Override
  public String toString() {
    return "SalesMan [id=" + id + ", simId=" + simId + ", status=" + status
        + ", truename=" + truename + ", jobNum=" + jobNum + ", assessStage="
        + assessStage + ", towns=" + towns + ", mobile=" + mobile
        + ", regdate=" + regdate + ", isOldSalesman=" + isOldSalesman
        + ", isPrimaryAccount=" + isPrimaryAccount + ", assessStageSum="
        + assessStageSum + "]";
  }

}
