package com.wangge.buzmgt.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;

@Entity
@Table(name = "sys_user")
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" ,"handler"})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public enum UserStatus {
		NORMAL, LOCKED
	}

	@Id
//	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "user_id")
	private String id;

	private String username;
	@JsonIgnore
	private String password;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "organization_id")
	private Organization organization;
	
	@OneToOne(fetch=FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private Manager manager;
	
	@OneToOne(fetch=FetchType.EAGER)
  @JoinColumn(name = "user_id")
  private SalesMan salseMan;

	@Enumerated(EnumType.ORDINAL)
	private UserStatus status = UserStatus.NORMAL;

	@ManyToMany
	@JoinTable(name = "sys_users_roles", joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "role_id") )
	private Set<Role> roles = new HashSet<Role>();
    
	public User() {
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

  public Manager getManager() {
    return manager;
  }

  public void setManager(Manager manager) {
    this.manager = manager;
  }

  public SalesMan getSalseMan() {
    return salseMan;
  }

  public void setSalseMan(SalesMan salseMan) {
    this.salseMan = salseMan;
  }
	
}
