package com.wangge.buzmgt.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 组织机构
 * 
 * @author wujiming
 *
 */
@Entity
@Table(name = "sys_organization")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Organization implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "ORGANIZATION_ID")
	private Long id;

	private String name;
	private String description;

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Organization parent;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Organization> children = new HashSet<Organization>();

	public Organization() {
	}

	public Organization(String name) {
		this.name = name;
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

}
