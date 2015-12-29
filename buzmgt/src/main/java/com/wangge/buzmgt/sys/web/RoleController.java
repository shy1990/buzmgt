package com.wangge.buzmgt.sys.web;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import net.sf.json.JSONArray;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.sys.base.BaseController;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.util.PageData;
import com.wangge.buzmgt.sys.util.PageNavUtil;
import com.wangge.buzmgt.sys.util.SortUtil;
import com.wangge.buzmgt.sys.vo.TreeData;

@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {
	
	@Autowired
	private UserService us;
	@Autowired
	private ResourceService rs;
	/**
	 * 
	 * @Description: 角色列表
	 * @param @param page
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author changjun
	 * @date 2015年12月19日
	 */
	@RequestMapping(value = "/roleList")
	public String roleList(Integer page, Model model){
		page = page== null ? 1 : page<1 ? 1 : page;
		int pageSize = 10;
		PageRequest pageRequest = SortUtil.buildPageRequest(page, pageSize,"role");
		Page<Role> list = us.getAllRoles(pageRequest);
		
		PageData pd = new PageData();
		pd = this.getPageData();
		model.addAttribute("roles", list.getContent());
		model.addAttribute("page", pd);
		model.addAttribute("totalCount", list.getContent().size());
		model.addAttribute("totalPage", list.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("pageNav", PageNavUtil.getPageNavHtml(page.intValue(), 10, list.getContent().size(), 15));
//		model.addAttribute("pageNav", PageNavUtil.getPageNavHtml(1, 1, list.getContent().size(), 15));
		return "purview-setting/purivew_setting";
	}
	@RequestMapping(value = "/selByRole", method = RequestMethod.GET)
	public String selByRoleId(Long id,String name,Model model){
		List<User> list =  us.getUserByRoles(id);
		model.addAttribute("users", list);
		model.addAttribute("name", name);
		return "roles/userList";
	}
	/**
	 * 新增角色
	 */
	@RequestMapping(value="/addRole" ,method = RequestMethod.POST)
	public String addRole(HttpServletRequest req){
		String name = req.getParameter("name");
		String des = req.getParameter("description");
		System.out.println(name+"==="+des);
		Role role = new Role(name);
		role.setDescription(des);
		if(us.saveRole(role)){
			return "suc";
		}
		return "error";
	}
	
	@RequestMapping(value="/delRole" ,method = RequestMethod.POST)
	@ResponseBody
	public String delRole(Long id){
		if(us.delRole(id)){
			return "suc";
		}
		return "";
	}
	/**
	 * 
	 * @Description: 授权
	 * @param @param id
	 * @param @param name
	 * @param @param model
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author changjun
	 * @date 2015年12月23日
	 */
	@RequestMapping(value = "/auth")
	public String auth(@RequestParam Long rId,Model model){
		Role roleEntity = us.getRoleById(rId);
		List<Menu> menus = rs.getMenusByRoleId(rId);
		List<Long> menuIds = new ArrayList<Long>();
		for (Menu menu : menus) {
			menuIds.add(menu.getId());
		}
		model.addAttribute("roleEntity", roleEntity);
		model.addAttribute("menuIds", StringUtils.join(menuIds,","));
		return "roles/roleResourceSet";
	}
	
	@RequestMapping(value = "/getMenuTree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeData> getMenuTree(Model model) {
		List<TreeData> treeData = rs.getTreeData();
		return treeData;
	}
	/**
	 * 
	 * @Description: 保存角色对应的菜单
	 * @param @param model
	 * @param @param roleId
	 * @param @param menuIds
	 * @param @return   
	 * @return Map<String,Object>  
	 * @throws
	 * @author changjun
	 * @date 2015年12月28日
	 */
	@RequestMapping(value = "/saveRoleResource", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveRoleResource(Model model,Long roleId,String menuIds) {
		boolean resultStatus = true;
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		//需要注意的是jsTree如果一个节点下所有子节点都被选中，则只会返回这个父节点的ID，下面的子节点ID不会返回
		String[] mIds = menuIds.split(",");
//		System.out.println(roleId+"&&&"+menuIds);
		resultStatus = rs.saveRoleResource(roleId,mIds);
//		if(resultStatus){
//			//角色权限改变，重新更新资源和角色关系
//			webSecurityMetadataSource.reloadResource();
//		}
		
		resMap.put("resultStatus", resultStatus);
		return resMap;
		
	}
}
