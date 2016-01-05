package com.wangge.buzmgt.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Resource.ResourceType;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.repository.ResourceRepository;
import com.wangge.buzmgt.sys.repository.RoleRepository;
import com.wangge.buzmgt.sys.vo.NodeData;
import com.wangge.buzmgt.sys.vo.TreeData;

@Service
public class ResourceServiceImpl implements ResourceService {
	@Autowired
	private RoleRepository roleRepository;
	
	private ResourceRepository resourceRepository;

	@Autowired
	public ResourceServiceImpl(ResourceRepository resourceRepository) {
		super();
		this.resourceRepository = resourceRepository;
	}

	@Override
	public List<Menu> getMenusByUsername(String username) {
		List<Resource> resources = resourceRepository.findByRolesUsersUsernameAndType(username, ResourceType.MENU);
		return resource2Menu(resources).stream().collect(Collectors.toList());
	}

	public List<Menu> getMenusByRoleId(Long id) {
		List<Resource> resources = resourceRepository.findByRolesId(id);
		return resource2Menu(resources).stream().collect(Collectors.toList());
	}

	
	private Set<Menu> resource2Menu(Collection<Resource> resources) {
		Set<Menu> menus=new HashSet<Menu>();
		resources.forEach(r->{
			Menu menu=new Menu(r.getId(),r.getName(), r.getUrl());
			if (!r.getChildren().isEmpty()) {
				menu.setChildren(resource2Menu(r.getChildren()));
			}  
			if(r.getParent()!=null){
				menu.setParentId(r.getParent().getId());
			}
				menus.add(menu);
		});
		return menus;
	}
	//单个转
	private Menu resource2MenuByOne(Resource r) {
		Menu menu=new Menu(r.getId(),r.getName(), r.getUrl());
		if (!r.getChildren().isEmpty()) {
			menu.setChildren(resource2Menu(r.getChildren()));
		}  
		if(r.getParent()!=null){
			menu.setParentId(r.getParent().getId());
		}
		
		return menu;
	}
	@Override
	public Set<Menu> getAllMenus() {
		List<Resource> list = resourceRepository.findAll();
		return resource2Menu(list);
	}

	@Override
	public boolean saveRes(Resource res) {
		try {
			resourceRepository.save(res);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Resource getResourceById(Long id) {
		return resourceRepository.findOne(id);
	}

	@Override
	public Resource getResourceByName(String name) {
		return resourceRepository.findByName(name);
	}
	
	//   菜单树
	
	
	public List<Menu> loadMenuInfos(Set<Menu> allMenus){
		
		List<Menu> menus = new ArrayList<Menu>();
				
		for (Menu menu : allMenus) {
			//先遍历出第1级菜单   			menu.getParentId()!=null && menu.getParentId()==0
			if(	menu.getParentId()!=null && menu.getParentId()==0){
//				System.out.println(menu.getId()+ menu.getName()+ menu.getUrl()+"%%%111");
				Menu currentMenu = new Menu(menu.getId(), menu.getName(), menu.getUrl());
//				currentMenu.setChildren(menu.getChildren());
				menus.add(currentMenu);
				this.loadChildMenus(currentMenu, allMenus);
			}
		}
		
		return menus;		
		
	}
	
	private void loadChildMenus(Menu currentMenu,Set<Menu> allMenus){
		
		for (Menu menu : allMenus) {
			//如果是当前菜单的子菜单
			if( menu.getParentId() == currentMenu.getId()){
				System.out.println(menu.getId()+ menu.getName()+ menu.getUrl()+"%%%110");
				Menu childMenu = new Menu(menu.getId(), menu.getName(),menu.getUrl());
				currentMenu.addChild(childMenu);
				//递归
				this.loadChildMenus(childMenu, allMenus);
			}
			
//			if(menu.getParentId() == currentMenu.getMenuId()){
//				MenuInfo childMenu = new MenuInfo(menu.getId(), menu.getMenuName(),menu.getMenuCode(), menu.getMenuUrl());
//				currentMenu.addChild(childMenu);
//				
//				//递归
//				this.loadChildMenus(childMenu, allMenus);
//			}
		}
				
	}
	
	
	public List<TreeData> getTreeData(){
		
		List<TreeData> treeDatas = new ArrayList<TreeData>();
		
		//取得所有菜单
		Set<Menu> allMenus = this.getAllMenus();
		List<Menu> menus = this.loadMenuInfos(allMenus);
		for (Menu menu : menus) {
//			System.out.println(menu.getName()+"***222");  
			//
			TreeData treeData = new TreeData();
			NodeData nodeData = new NodeData();
			nodeData.setTitle(menu.getName());
			nodeData.getAttr().put("id", menu.getId());
			
			treeData.getData().add(nodeData);			
			this.loadTreeChild(treeData, menu.getChildren());
			treeDatas.add(treeData);
		}
		
		
		return treeDatas;
		
		
	}
	
	private void loadTreeChild(TreeData treeData,Set<Menu> menus){
		
		
		for (Menu menu : menus) {
//			System.out.println(menu.getName()+"^^^333");
			TreeData child = new TreeData();
			NodeData childNode = new NodeData();
			childNode.setTitle(menu.getName());
			childNode.getAttr().put("id", menu.getId());
			child.getData().add(childNode);
			
			//递归
			this.loadTreeChild(child, menu.getChildren());
			treeData.getChildren().add(child);
		}
				
	}
	/**
	 * 
	 * @Description: 保存角色对应菜单的修改
	 * @param @param roleId
	 * @param @param menuIds
	 * @param @return   
	 * @return boolean  
	 * @throws
	 * @author changjun
	 * @date 2015年12月28日
	 */
	@Transactional
	public boolean saveRoleResource(Long roleId,String[] menuIds) {
		
		Role roleEntity = roleRepository.findOne(roleId);
		
		Set<Resource> menus = new HashSet<Resource>();
		//提交的菜单ID
		for (String menuId : menuIds) {
			Resource res = this.getResourceById(Long.parseLong(menuId));
			menus.add(res);
			//取菜单下所有子菜单
			List<Resource> childMenus =resourceRepository.findByParentId(res.getId());	
			for (Resource child : childMenus) {
				menus.add(child);
			}
		}
		roleEntity.setResource(menus);
		
		try {
			roleRepository.save(roleEntity);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	@Override
	@Transactional
	public boolean delResource(Long id) {
		try {
			resourceRepository.delete(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
