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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.wangge.buzmgt.region.entity.Region;

/**
 * 扫街数据
 * 
 * @author wujiming
 *
 */
@Entity
@Table(name = "SYS_SAOJIEDATA")
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
	private String discription;
	private String imageUrl;
	private String coordinate;
	@Temporal(TemporalType.DATE)
  private Date saojieDate;
/*	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "REGIST_ID")
	private Regist regist;*/
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

	public String getDiscription() {
    return discription != null ? discription : "";
  }

  public void setDiscription(String discription) {
    this.discription = discription;
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

  public Date getSaojieDate() {
    return saojieDate;
  }

  public void setSaojieDate(Date saojieDate) {
    this.saojieDate = saojieDate;
  }
	

	/*public Regist getRegist() {
		return regist;
	}

	public void setRegist(Regist regist) {
		this.regist = regist;
	}*/
	
}
