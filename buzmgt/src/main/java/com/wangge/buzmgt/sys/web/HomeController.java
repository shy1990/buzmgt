package com.wangge.buzmgt.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	private ResourceService resourceService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest req) {
		List<Menu> menus = resourceService.getMenusByUsername(((User) SecurityUtils.getSubject()
				.getPrincipal()).getUsername());
//		model.addAttribute("menus", menus);
		req.getSession().setAttribute("menus", menus);
		return "left_menu";
//		return "index";
	}

	@Autowired
	public HomeController(ResourceService resourceService) {
		super();
		this.resourceService = resourceService;
	}

}
