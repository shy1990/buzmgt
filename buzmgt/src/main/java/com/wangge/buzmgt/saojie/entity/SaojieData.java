package com.wangge.buzmgt.saojie.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 扫街数据
 * 
 * @author wujiming
 *
 */
@Entity
@Table(name = "SYS_SAOJIEDATA")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler","fieldHandler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@NamedEntityGraph(name = "graph.SaojieData.region",
attributeNodes = @NamedAttributeNode(value = "region", subgraph = "graph.SaojieData.region" +
        ".coordinates"), subgraphs = {@NamedSubgraph(name = "graph.SaojieData.region.coordinates",
attributeNodes = @NamedAttributeNode("coordinates"))})
public class SaojieData implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "idgen", strategy = "increment")
	@GeneratedValue(generator = "idgen")
	@Column(name = "SAOJIEDATA_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SAOJIE_ID")
	private Saojie saojie;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID")
	private Region region;
	private String name;
	@Column(name = "DESCRIPTION")
	private String description = "";
	private String imageUrl;
	private String coordinate;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGISTDATA_ID")
	private RegistData registData;
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "SAOJIE_DATE")
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
  @Temporal(TemporalType.TIMESTAMP)
	private Date saojieDate;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID")
  private SalesMan salesman;
	@Transient
	private Long registId;
	@Transient
	private int colorStatus;
	@Transient
	private int incSize;
	@Transient
	private int dateInterval;
	
	public SaojieData() {
		super();
	}

	public SaojieData(String name, String coordinate) {
		this.name = name;
		this.coordinate = coordinate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Saojie getSaojie() {
		return saojie;
	}

	public void setSaojie(Saojie saojie) {
		this.saojie = saojie;
	}

	public String getDescription() {
		return description == null ? "" : description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public RegistData getRegistData() {
    return registData;
  }

  public void setRegistData(RegistData registData) {
    this.registData = registData;
  }

  public Long getRegistId() {
		return registId;
	}

	public void setRegistId(Long registId) {
		this.registId = registId;
	}

	public int getColorStatus() {
    return colorStatus;
  }
  public void setColorStatus(int colorStatus) {
    this.colorStatus = colorStatus;
  }

  public int getIncSize() {
    return incSize;
  }

  public void setIncSize(int incSize) {
    this.incSize = incSize;
  }

  public int getDateInterval() {
    return dateInterval;
  }

  public void setDateInterval(int dateInterval) {
    this.dateInterval = dateInterval;
  }

  public Date getSaojieDate() {
    return saojieDate;
  }

  public void setSaojieDate(Date saojieDate) {
    this.saojieDate = saojieDate;
  }

  public SalesMan getSalesman() {
    return salesman;
  }

  public void setSalesman(SalesMan salesman) {
    this.salesman = salesman;
  }
  
}
