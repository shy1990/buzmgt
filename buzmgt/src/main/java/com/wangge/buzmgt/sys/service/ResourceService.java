package com.wangge.buzmgt.sys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.vo.TreeData;

public interface ResourceService {
	/**
	 * 菜单
	 * @author wujiming
	 *
	 */
	@JsonInclude(Include.NON_EMPTY)
	public class Menu {
		private Long id;
		private String name;
		public String url;
		private Long parentId;
		private String icon;
		
		public String getIcon() {
      return icon;
    }
    public void setIcon(String icon) {
      this.icon = icon;
    }
    public Set<Menu> children=new HashSet<Menu>();
		
		public Menu(Long id,String name, String url) {
			super();
			this.id = id;
			this.name = name;
			this.url = url;
		}
		
		public Menu(Long id,String name, String url, String icon) {
      super();
      this.id = id;
      this.name = name;
      this.url = url;
      this.icon = icon;
    }
    public void addChild(Menu child){
			this.children.add(child);
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
		
		public Long getParentId() {
			return parentId;
		}
		public void setParentId(Long parentId) {
			this.parentId = parentId;
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
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		
	}
	
	/**
	 * 获取用户菜单
	 * @param username
	 * @return
	 */
	List<Menu> getMenusByUsername(String username);
	/**
	 * 获取角色对应的菜单
	 * @param username
	 * @return
	 */
	List<Menu> getMenusByRoleId(Long id) ;
	/**
	 * 
	 * @Description: 菜单列表
	 * @param @return   
	 * @return List<Menu>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	Set<Menu> getAllMenus();
	/**
	 * 
	 * @Description: 菜单列表分页
	 * @param @return   
	 * @return Set<Menu>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	Page<Resource> getMenusByPage(Pageable pageRequest);
	/**
	 * 
	 * @Description: 增加资源
	 * @param @param res
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	public Resource saveRes(Resource res);
	/**
	 * 
	 * @Description: id查找
	 * @param @param id
	 * @param @return   
	 * @return Resource  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	Resource getResourceById(Long id);
	/**
	 * 
	 * @Description: 通过菜单名称查找
	 * @param @param name
	 * @param @return   
	 * @return Resource  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	Resource getResourceByName(String name);
	/**
	 * 
	 * @Description: 树
	 * @param @return   
	 * @return List<TreeData>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月24日
	 */
	List<TreeData> getTreeData();
	/**
	 * 
	 * @Description: 删除菜单
	 * @param @param id
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年12月28日
	 */
	public void delResource(Long id);
	/**
	 * 
	 * @Description: 保存角色对应的权限菜单
	 * @param @param roleId
	 * @param @param menuIds
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年12月28日
	 */
	public boolean saveRoleResource(Long roleId,String[] menuIds);
}
