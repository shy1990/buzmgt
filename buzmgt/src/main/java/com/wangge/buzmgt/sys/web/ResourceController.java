package com.wangge.buzmgt.sys.web;

import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.wangge.buzmgt.sys.base.BaseController;
import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.util.Chinese2English;
import com.wangge.buzmgt.sys.util.PageData;
import com.wangge.buzmgt.sys.util.PageNavUtil;
import com.wangge.buzmgt.sys.util.SortUtil;

//import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

@Controller
@RequestMapping(value = "/res")
public class ResourceController extends BaseController {
	
	private static final Logger LOG = Logger.getLogger(ResourceController.class);
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
		PageRequest pageRequest = SortUtil.buildPageRequest(page, pageSize,"res");
		Page<Resource> mlist = res.getMenusByPage(pageRequest) ;
		Set<Menu> menuList =     res.getAllMenus(null);
		PageData pd = new PageData();
		pd = this.getPageData();
		//下拉列表
		model.addAttribute("menuList", menuList);
		//分页用
		model.addAttribute("menus", mlist.getContent());
		model.addAttribute("page", pd);
		model.addAttribute("totalCount", menuList.size());
		model.addAttribute("totalPage", (int) Math.ceil(menuList.size()/Double.parseDouble(String.valueOf(pageSize))));
		model.addAttribute("currentPage", page);
		model.addAttribute("pageNav", PageNavUtil.getPageNavHtml(page.intValue(), pageSize,menuList.size(), 15));
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
		Resource r = new Resource(name, Resource.ResourceType.MENU, url, 1,new Date());
		r.setIcon(Chinese2English.converterToSpell(name));
		r.setParent(res.getResourceById(Long.parseLong(pid)));
		try {
		  res.saveRes(r);
			return "suc";
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value="/getAllMenus",method = RequestMethod.POST)
	@ResponseBody
	public String getAllMenus(){
		Set<Menu> mlist = res.getAllMenus(null); 
		String jsonText =  JSON.toJSONString(mlist, true); 
//		JSONArray list1 = JSONArray.fromObject(mlist);
		return jsonText;
	}
	
	@RequestMapping(value="/removeMenu" ,method = RequestMethod.POST)
	@ResponseBody
	public String removeMenu(Long id){
		try {
			res.delResource(id);
			return "suc";
		} catch (Exception e) {
			LOG.error(e);
			e.printStackTrace();
		}
		return "err";
	}
}
