package com.wangge.buzmgt.sys.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 系统模块
 * 
 * @author wujiming
 *
 */
@Entity
@Table(name = "sys_resource")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Resource implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum ResourceType {
		MENU("菜单"), BUTTON("按钮");

		private final String info;

		private ResourceType(String info) {
			this.info = info;
		}

		public String getInfo() {
			return info;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name = "resource_ID")
	private Long id;

	private String name;
	@Enumerated(EnumType.ORDINAL)
	private ResourceType type;
	private String permission;//权限字符串
	private String url;
	private int priority;

	@ManyToMany
	@JoinTable(name = "sys_resources_roles",joinColumns=@JoinColumn(name="resource_id"),inverseJoinColumns=@JoinColumn(name="role_id"))
	private Set<Role> roles = new HashSet<Role>();

	@OneToMany(mappedBy = "parent", fetch=FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<Resource> children = new HashSet<Resource>();
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Resource parent;

	public Resource() {
	}

	public Resource(String name,ResourceType type, String url, int priority) {
		super();
		this.name = name;
		this.url = url;
		this.priority = priority;
		this.type=type;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Resource getParent() {
		return parent;
	}

	public void setParent(Resource parent) {
		this.parent = parent;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	public void removeRole(Role role) {
		this.roles.remove(role);
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Collection<Resource> getChildren() {
		return children;
	}

	public void setChildren(Collection<Resource> children) {
		this.children = children;
	}

	


}
