package com.wangge.buzmgt.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public interface ResourceService {
	/**
	 * 菜单
	 * @author wujiming
	 *
	 */
	@JsonInclude(Include.NON_EMPTY)
	public class Menu {
		private String name;
		public String url;
		
		public Set<Menu> children=new HashSet<Menu>();
		
		public Menu(String name, String url) {
			super();
			this.name = name;
			this.url = url;
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
		public Set<Menu> getChildren() {
			return children;
		}
		public void setChildren(Set<Menu> children) {
			this.children = children;
		}
		
	}
	
	/**
	 * 获取用户菜单
	 * @param username
	 * @return
	 */
	List<Menu> getMenusByUsername(String username);
}
