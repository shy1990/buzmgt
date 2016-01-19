package com.wangge.buzmgt.saojie.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;

@Entity
@Table(name = "SYS_SAOJIE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Saojie implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum SaojieStatus {
		PENDING("扫街中"), COMMIT("提交审核"), AGREE("已完成");

		private String name;

		private SaojieStatus(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "SAOJIE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Saojie parent;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REGION_ID")
	private Region region;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private SalesMan salesman;

	private String name;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "SAOJIE_STATUS")
	private SaojieStatus status;
	private Integer minValue;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date beginTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expiredTime;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(columnDefinition = "CLOB")
	private String description;

	@Column(name = "SAOJIE_ORDER")
	private Integer order;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	@OrderBy("saojie_order")
	private Collection<Saojie> children;

	public Saojie() {
		super();
	}

	public Long getId() {
    return id;
  }


  public void setId(Long id) {
    this.id = id;
  }


  public Saojie getParent() {
		return parent;
	}

	public void setParent(Saojie parent) {
		this.parent = parent;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public SalesMan getSalesman() {
		return salesman;
	}

	public void setSalesman(SalesMan salesman) {
		this.salesman = salesman;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public SaojieStatus getStatus() {
		return status;
	}

	public void setStatus(SaojieStatus status) {
		this.status = status;
	}

	public Integer getMinValue() {
		return minValue;
	}

	public void setMinValue(Integer minValue) {
		this.minValue = minValue;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Collection<Saojie> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	public void setChildren(Collection<Saojie> children) {
		this.children = children;
	}

}
