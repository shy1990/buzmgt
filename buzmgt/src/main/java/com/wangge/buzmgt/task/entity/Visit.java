package com.wangge.buzmgt.task.entity;

import java.io.Serializable;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 
  * ClassName: Visit <br/> 
  * date: 2016年3月9日 下午3:52:32 <br/> 
  * @author peter 
  * @version  
  * @since JDK 1.8
 */
@Entity
@Table(name = "SYS_VISIT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Visit implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum VisitStatus {
		PENDING("进行中"), FINISHED("完成");

		private String name;

		private VisitStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "VISIT_ID")
	private Long id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGISTDATA_ID")
	private RegistData registData;

	
	private String address;
	private String summary;
	@Column(name = "IMAGE_URL1")
	private String imageurl1;
	@Column(name = "IMAGE_URL2")
	private String imageurl2;
	@Column(name = "IMAGE_URL3")
	private String imageurl3;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "VISIT_STATUS")
	private VisitStatus status;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date expiredTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @Temporal(TemporalType.TIMESTAMP)
  private Date finishTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private SalesMan salesman;
	@Column(name = "TASK_NAME")
	private String taskName;
	
	@Transient
	private String shopAddress; //店铺地址
	@Transient
	private String visitStatus; //拜访状态
	@Transient
	private String executor; //执行人
	@Transient
	private int period; //间隔天数
	
	public Visit() {
		super();
	}

	public Long getId() {
		return id;
	}

	public RegistData getRegistData() {
		return registData;
	}

	public void setRegistData(RegistData registData) {
		this.registData = registData;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getImageurl1() {
		return imageurl1;
	}

	public void setImageurl1(String imageurl1) {
		this.imageurl1 = imageurl1;
	}

	public String getImageurl2() {
		return imageurl2;
	}

	public void setImageurl2(String imageurl2) {
		this.imageurl2 = imageurl2;
	}

	public String getImageurl3() {
		return imageurl3;
	}

	public void setImageurl3(String imageurl3) {
		this.imageurl3 = imageurl3;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VisitStatus getStatus() {
		return status;
	}

	public void setStatus(VisitStatus status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(Date expiredTime) {
		this.expiredTime = expiredTime;
	}

	public SalesMan getSalesman() {
		return salesman;
	}

	public void setSalesman(SalesMan salesman) {
		this.salesman = salesman;
	}

  public String getTaskName() {
    return taskName;
  }

  public void setTaskName(String taskName) {
    this.taskName = taskName;
  }

  public String getShopAddress() {
    return shopAddress;
  }

  public void setShopAddress(String shopAddress) {
    this.shopAddress = shopAddress;
  }

  public String getVisitStatus() {
    return visitStatus;
  }

  public void setVisitStatus(String visitStatus) {
    this.visitStatus = visitStatus;
  }

  public String getExecutor() {
    return executor;
  }

  public void setExecutor(String executor) {
    this.executor = executor;
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public Date getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Date finishTime) {
    this.finishTime = finishTime;
  }
	
}
