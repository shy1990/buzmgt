package com.wangge.buzmgt.sys.web;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;

@Controller
public class HomeController {
	
	private static final Logger LOG = Logger.getLogger(HomeController.class);
	
	private ResourceService resourceService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req) {
	  if(req.getSession().getAttribute("menus")!=null){
	    return "home";
	  }
		String username =  ((User) SecurityUtils.getSubject().getPrincipal()).getUsername();
		req.getSession().setAttribute("username", username);
		LOG.info("loginer====="+username);
		if("root".equals(username)){
			Set<Menu> menus = resourceService.getAllMenus();
			req.getSession().setAttribute("menus", menus);
		}else{
			List<Menu> menus = resourceService.getMenusByUsername(((User) SecurityUtils.getSubject().getPrincipal()).getUsername());
			req.getSession().setAttribute("menus", menus);
		}
//		model.addAttribute("menus", menus);
//		return "left_menu";
		return "home";
	}

	@Autowired
	public HomeController(ResourceService resourceService) {
		super();
		this.resourceService = resourceService;
	}

}
