package com.wangge.buzmgt.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Resource.ResourceType;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.repository.ResourceRepository;
import com.wangge.buzmgt.sys.repository.RoleRepository;
import com.wangge.buzmgt.sys.util.SortUtil;
import com.wangge.buzmgt.sys.vo.NodeData;
import com.wangge.buzmgt.sys.vo.TreeData;

@Service
public class ResourceServiceImpl implements ResourceService {
  @Autowired
  private RoleRepository roleRepository;
  
  private ResourceRepository resourceRepository;
  
  @Autowired
  private LogService logService;
  
  @Autowired
  public ResourceServiceImpl(ResourceRepository resourceRepository) {
    super();
    this.resourceRepository = resourceRepository;
  }
  
  @Override
  public List<Menu> getMenusByUsername(String username) {
    Sort s = new Sort(Direction.ASC, "createTime");
    List<Resource> resources = resourceRepository.findByRolesUsersUsernameAndType(username, ResourceType.MENU, s);
    
    return resource2MenuByParent(resources).stream().collect(Collectors.toList());
  }
  
  public List<Menu> getMenusByRoleId(Long id) {
    List<Resource> resources = resourceRepository.findByRolesId(id);
    return resource2Menu(resources).stream().collect(Collectors.toList());
  }
  
  private Set<Menu> resource2Menu(Collection<Resource> resources) {
    Set<Menu> menus = new HashSet<Menu>();
    resources.forEach(r -> {
      addMenuToSet(menus, r);
    });
    return menus;
  }
  
  /**
   * addMenuToSet:(这里用一句话描述这个方法的作用). <br/>
   * 以前的处理单个菜单项的方法
   * @author yangqc
   * @param menus
   * @param r
   * @since JDK 1.8
   */
  private void addMenuToSet(Set<Menu> menus, Resource r) {
    Menu menu = new Menu(r.getId(), r.getName(), r.getUrl());
    menu.setIcon(r.getIcon());
    if (!r.getChildren().isEmpty()) {
      menu.setChildren(resource2Menu(r.getChildren()));
    }
    if (r.getParent() != null) {
      menu.setParentId(r.getParent().getId());
    }
    menus.add(menu);
  }
  
  /**
   * resource2MenuByParent:新的构造菜单的方法. <br/>
   * 本方法思路:按上下级树来整理受控资源; 将一个资源树分支整理成一个menu;包括其本身和下级区域;
   * 只支持两级菜单,如果是三级菜单,推测:方案1:则可以使用Map<Resource,Map<Resource,Set
   * <Resource>>>类型的容器进行嵌套; 方案二:通过不断层级精确Resource的child的值,可以构造一个可以精确反映受控资源树;
   * 
   * @author yangqc
   * @param resources
   * @return
   * @since JDK 1.8
   */
  private Set<Menu> resource2MenuByParent(Collection<Resource> resources) {
    // 整理资源为资源树
    Set<Menu> menus = new TreeSet<Menu>();
    Map<Resource, Set<Resource>> treeMap = new HashMap<Resource, Set<Resource>>();
    resources.forEach(r -> {
      Resource fatherR = r.getParent();
      /*为二级节点时,为三级节点时
       * */
      if (fatherR != null && fatherR.getId() == 1) {
        if (null == treeMap.get(r)) {
          treeMap.put(r, new HashSet<Resource>());
        }
      } else if (fatherR != null && fatherR.getId() != 1) {
        Set<Resource> childSet = treeMap.get(fatherR);
        if (null == childSet) {
          childSet = new HashSet<Resource>();
        }
        childSet.add(r);
        treeMap.put(fatherR, childSet);
      }
    });
    
    // 处理资源树到Set<Menu>
    for (Map.Entry<Resource, Set<Resource>> tree : treeMap.entrySet()) {
      Resource fatherR = tree.getKey();
      Set<Resource> childSet = tree.getValue();
      Menu menu = new Menu(fatherR.getId(), fatherR.getName(), fatherR.getUrl(), fatherR.getIcon());
      Set<Menu> childMenus = new TreeSet<Menu>();
      childSet.forEach(r -> {
        Menu childMenu = new Menu(r.getId(), r.getName(), r.getUrl(), r.getIcon());
        childMenu.setParentId(fatherR.getId());
        childMenus.add(childMenu);
      });
      menu.setParentId(fatherR.getParent().getId());
      menu.setChildren(childMenus);
      
      menus.add(menu);
    }
    return menus;
  }
  
  @Override
  public Set<Menu> getAllMenus() {
    Sort s = new Sort(Direction.ASC, "createTime");
    List<Resource> list = resourceRepository.findAll(s);
    return resource2MenuByParent(list);
  }
  
  @Override
  public Resource saveRes(Resource res) {
    Resource resource = resourceRepository.save(res);
    logService.log(null, resource, EventType.SAVE);
    return resource;
  }
  
  @Override
  public Resource getResourceById(Long id) {
    return resourceRepository.findOne(id);
  }
  
  @Override
  public Resource getResourceByName(String name) {
    return resourceRepository.findByName(name);
  }
  
  // 菜单树
  
  public List<Menu> loadMenuInfos(Set<Menu> allMenus) {
    
    List<Menu> menus = new ArrayList<Menu>();
    
    for (Menu menu : allMenus) {
      // 先遍历出第1级菜单 menu.getParentId()!=null && menu.getParentId()==0
      if (menu.getParentId() != null && menu.getParentId() == 1) {
        // System.out.println(menu.getId()+ menu.getName()+
        // menu.getUrl()+"%%%111");
        Menu currentMenu = new Menu(menu.getId(), menu.getName(), menu.getUrl());
        // currentMenu.setChildren(menu.getChildren());
        menus.add(currentMenu);
        this.loadChildMenus(currentMenu, allMenus);
      }
    }
    
    return menus;
    
  }
  
  private void loadChildMenus(Menu currentMenu, Set<Menu> allMenus) {
    
    for (Menu menu : allMenus) {
      // 如果是当前菜单的子菜单
      if (menu.getParentId() == currentMenu.getId()) {
        // System.out.println(menu.getId()+ menu.getName()+
        // menu.getUrl()+"%%%110");
        Menu childMenu = new Menu(menu.getId(), menu.getName(), menu.getUrl());
        currentMenu.addChild(childMenu);
        // 递归
        this.loadChildMenus(childMenu, allMenus);
      }
      
      // if(menu.getParentId() == currentMenu.getMenuId()){
      // MenuInfo childMenu = new MenuInfo(menu.getId(),
      // menu.getMenuName(),menu.getMenuCode(), menu.getMenuUrl());
      // currentMenu.addChild(childMenu);
      //
      // //递归
      // this.loadChildMenus(childMenu, allMenus);
      // }
    }
    
  }
  
  public List<TreeData> getTreeData() {
    
    List<TreeData> treeDatas = new ArrayList<TreeData>();
    
    // 取得所有菜单
    Set<Menu> allMenus = this.getAllMenus();
    List<Menu> menus = this.loadMenuInfos(allMenus);
    for (Menu menu : menus) {
      // System.out.println(menu.getName()+"***222");
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
  
  private void loadTreeChild(TreeData treeData, Set<Menu> menus) {
    
    for (Menu menu : menus) {
      // System.out.println(menu.getName()+"^^^333");
      TreeData child = new TreeData();
      NodeData childNode = new NodeData();
      childNode.setTitle(menu.getName());
      childNode.getAttr().put("id", menu.getId());
      child.getData().add(childNode);
      
      // 递归
      this.loadTreeChild(child, menu.getChildren());
      treeData.getChildren().add(child);
    }
    
  }
  
  /**
   * 
   * @Description: 保存角色对应菜单的修改
   * @param @param
   *          roleId
   * @param @param
   *          menuIds
   * @param @return
   * @return boolean
   * @throws @author
   *           changjun
   * @date 2015年12月28日
   */
  @Transactional
  public boolean saveRoleResource(Long roleId, String[] menuIds) {
    
    Role roleEntity = roleRepository.findOne(roleId);
    
    Set<Resource> menus = new HashSet<Resource>();
    // 提交的菜单ID
    for (String menuId : menuIds) {
      Resource res = this.getResourceById(Long.parseLong(menuId));
      menus.add(res);
      // 取菜单下所有子菜单
      List<Resource> childMenus = resourceRepository.findByParentId(res.getId());
      for (Resource child : childMenus) {
        menus.add(child);
      }
    }
    roleEntity.setResource(menus);
    
    try {
      roleEntity = roleRepository.save(roleEntity);
      logService.log(null, roleEntity, EventType.SAVE);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
  
  @Override
  @Transactional
  public void delResource(Long id) {
    Resource r = this.getResourceById(id);
    resourceRepository.delete(id);
    logService.log(r, null, EventType.DELETE);
  }
  
  @Override
  public Page<Resource> getMenusByPage(Pageable pageRequest) {
    return resourceRepository.findAll(pageRequest);
    
  }
}
