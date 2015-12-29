package com.wangge.buzmgt.sys.web;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

//import net.sf.json.JSONArray;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.wangge.buzmgt.sys.base.BaseController;
import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.util.PageData;
import com.wangge.buzmgt.sys.util.PageNavUtil;

@Controller
@RequestMapping(value = "/res")
public class ResourceController extends BaseController {
	@Autowired
	private ResourceService res;
	@Autowired
	private UserService us;
	/**
	 * 
	 * @Description: 菜单列表
	 * @param @return   
	 * @return String  
	 * @throws
	 * @author changjun
	 * @date 2015年12月22日
	 */
	@RequestMapping(value = "/menuList")
	public String menuList(Integer page, Model model,HttpServletRequest request){
		page = page== null ? 1 : page<1 ? 1 : page;
		int pageSize = 10;
//		PageRequest pageRequest = SortUtil.buildPageRequest(page, pageSize,"res");
//		Page<Role> list = us.getAllRoles(pageRequest);
		Set<Menu> mlist = res.getAllMenus(); 
		PageData pd = new PageData();
		pd = this.getPageData();
		model.addAttribute("menus", mlist);
		model.addAttribute("page", pd);
		model.addAttribute("totalCount", mlist.size());
		model.addAttribute("totalPage", (int) Math.ceil(mlist.size()/Double.parseDouble(String.valueOf(pageSize))));
		model.addAttribute("currentPage", page);
		model.addAttribute("pageNav", PageNavUtil.getPageNavHtml(page.intValue(), 10, mlist.size(), 15));
		
		request.setAttribute("menuList", com.alibaba.fastjson.JSONArray.toJSONString(mlist, true));
		return "roles/menusList";
	}
	
	/**
	 * 新增菜单
	 */
	@RequestMapping(value="/addMenu" ,method = RequestMethod.POST)
	@ResponseBody
	public String addMenu(HttpServletRequest req){
		String name = req.getParameter("name");
		String url = req.getParameter("url");
		String pid = req.getParameter("parentid");

		System.out.println(name+":"+url+":"+pid);
		Resource r = new Resource(name, Resource.ResourceType.MENU, url, 1);
		Role roleEntity = us.getRoleById(32768L);
		Set<Role> roles = new HashSet<Role>();
		roles.add(roleEntity);
		r.setRoles(roles);
		r.setParent(res.getResourceById(Long.parseLong(pid)));
		if(res.saveRes(r)){
			return "suc";
		}
		return "";
	}
	
	@RequestMapping(value="/getAllMenus",method = RequestMethod.POST)
	@ResponseBody
	public String getAllMenus(){
		Set<Menu> mlist = res.getAllMenus(); 
		String jsonText =  com.alibaba.fastjson.JSONArray.toJSONString(mlist, true); 
//		JSONArray list1 = JSONArray.fromObject(mlist);
		return jsonText;
	}
	
	@RequestMapping(value="/removeMenu" ,method = RequestMethod.POST)
	@ResponseBody
	public String removeMenu(Long id){
		if(res.delResource(id)){
			return "suc";
		}
		return "";
	}
}
