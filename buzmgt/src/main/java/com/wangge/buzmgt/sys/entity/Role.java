package com.wangge.buzmgt.sys.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name="sys_role")
public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "role_id")
	private Long id;
	
	private String name;

    private String description;

	@ManyToMany(mappedBy="roles")
	private Set<User> users = new HashSet<User>();
	
	/**
	 * 角色和菜单资源的多对多关系映射
	 */
//	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST,
//			CascadeType.MERGE })
//	@JoinTable(name = "SYS_RESOURCES_ROLES", joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID") })
//	true@ManyToMany(mappedBy="roles")
	@ManyToMany
	@JoinTable(name = "sys_resources_roles",joinColumns = { @JoinColumn(name = "ROLE_ID") }, inverseJoinColumns = { @JoinColumn(name = "RESOURCE_ID")})
	private Set<Resource> resource;

	
    public Set<Resource> getResource() {
		return resource;
	}

	public void setResource(Set<Resource> resource) {
		this.resource = resource;
	}

	protected Role() {
    }

    public Role(String name) {
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

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}


    
}
