package com.wangge.buzmgt.sys.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 组织机构
 * 
 * @author wujiming
 *
 */
@JsonInclude(Include.NON_EMPTY)
@Entity
@Table(name = "sys_organization")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ORGANIZATION_ID")
	private int id;

	private String name;
	private String description;
	private int lev;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Organization parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Organization> children = new  ArrayList<Organization>();

	public Organization() {
	}

	public Organization(String name) {
		this.name = name;
	}

	
	public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}
	

  public List<Organization> getChildren() {
    return children;
  }

  public void setChildren(List<Organization> children) {
    this.children = children;
  }

  public int getLev() {
    return lev;
  }

  public void setLev(int lev) {
    this.lev = lev;
  }

  @Override
  public String toString() {
    return "Organization [id=" + id + ", name=" + name + ", description="
        + description + ", lev=" + lev + "]";
  }
	
}
