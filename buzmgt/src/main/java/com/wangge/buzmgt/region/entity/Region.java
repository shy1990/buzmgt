package com.wangge.buzmgt.region.entity;


import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
@Entity
@Table(name = "SYS_REGION")
@SecondaryTable(name = "SYS_COORDINATES")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Region implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum RegionType {
		COUNTRY("国"), PARGANA("大区"), PROVINCE("省"), AREA("区"), CITY("市"), COUNTY("县"), TOWN("镇");
		private String name;

		RegionType(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	@Id
	@Column(name = "REGION_ID")
	private String id;
	private String name;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(table = "SYS_COORDINATES", columnDefinition = "CLOB", name = "content")
	private String coordinates;

	@Enumerated(EnumType.ORDINAL)
	private RegionType type;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PARENT_ID")
	private Region parent;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	private Collection<Region> children;
	
	public String getId() {
		return id;
	}

	public Region() {
		super();
	}

	/**
	 * 区域编号
	 * 
	 * @param id
	 * @param name
	 */
	public Region(String id, String name, RegionType type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}

	public RegionType getType() {
		return type;
	}

	public void setType(RegionType type) {
		this.type = type;
	}

	/**
	 * 区域编号
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Region getParent() {
		return parent;
	}

	public void setParent(Region parent) {
		this.parent = parent;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public Collection<Region> getChildren() {
		return Collections.unmodifiableCollection(children);
	}

	public void setChildren(Collection<Region> children) {
		this.children = children;
	}

	
}
