package com.wangge.buzmgt.assess.entity;

import java.io.Serializable;
import java.text.NumberFormat;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 注册
 * 
 * @author jiabin
 *
 */
@Entity
@Table(name = "SYS_ASSESS")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Assess implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum AssessStatus {
		PENDING("进行中"), AGREE("通过"), FAIL("考核失败");
		private String name;

		private AssessStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "ASSESS_ID")
	private Long id;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private SalesMan salesman;
	@Column(name = "ASSESS_AREA")
	private String assessArea;
	@Column(name = "ASSESS_STAGE")
	private String assessStage;
	@Column(name = "ASSESS_ACTIVENUM")
	private String assessActivenum;
	@Column(name = "ASSESS_ORDERNUM")
	private String assessOrdernum;
	@Column(name = "ASSESS_CYCLE")
	private String assessCycle;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date assessTime;
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "ASSESS_STATUS")
	private AssessStatus status;
	@Column(name="ASSESS_DEFINE_AREA")
	private String defineArea;
	@Column(name = "ASSESS_ENDTIME")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.TIMESTAMP)
	private Date assessEndTime;
	@Transient
	private String regionName;
	@Transient
  private String percent;
  
  //辅助字段
  @Transient
  private int timing;
  
   public void addPercent(double num) {
     if(num > 0 ){
       NumberFormat nf = NumberFormat.getPercentInstance();
       this.percent = nf.format(num / 2);
     }else{
       this.percent = "0%";
     }
    
   }

	public Assess() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SalesMan getSalesman() {
		return salesman;
	}

	public void setSalesman(SalesMan salesman) {
		this.salesman = salesman;
	}

	public String getAssessArea() {
		return assessArea;
	}

	public void setAssessArea(String assessArea) {
		this.assessArea = assessArea;
	}

	public String getAssessStage() {
		return assessStage;
	}

	public void setAssessStage(String assessStage) {
		this.assessStage = assessStage;
	}

	public String getAssessActivenum() {
		return assessActivenum;
	}

	public void setAssessActivenum(String assessActivenum) {
		this.assessActivenum = assessActivenum;
	}

	public String getAssessOrdernum() {
		return assessOrdernum;
	}

	public void setAssessOrdernum(String assessOrdernum) {
		this.assessOrdernum = assessOrdernum;
	}

	public String getAssessCycle() {
		return assessCycle;
	}

	public void setAssessCycle(String assessCycle) {
		this.assessCycle = assessCycle;
	}

	public Date getAssessTime() {
		return assessTime;
	}

	public void setAssessTime(Date assessTime) {
		this.assessTime = assessTime;
	}

	public AssessStatus getStatus() {
		return status;
	}

	public void setStatus(AssessStatus status) {
		this.status = status;
	}

  public String getDefineArea() {
    return defineArea;
  }

  public void setDefineArea(String defineArea) {
    this.defineArea = defineArea;
  }
  
  public Date getAssessEndTime() {
    return assessEndTime;
  }

  public void setAssessEndTime(Date assessEndTime) {
    this.assessEndTime = assessEndTime;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public String getPercent() {
    return percent;
  }

  public void setPercent(String percent) {
    this.percent = percent;
  }

  public int getTiming() {
    return timing;
  }

  public void setTiming(int timing) {
    this.timing = timing;
  }

}
