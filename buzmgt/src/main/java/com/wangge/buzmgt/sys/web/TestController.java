package com.wangge.buzmgt.sys.web;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;

@Controller
public class TestController {
	@Autowired
	private ResourceService resourceService;
	
	/**
	 * 拜访
	 * @param test
	 * @param model
	 * @return
	 */
	@RequestMapping("/visit_record_list")
	public String purviewSetting(String test,Model model){
		return "visit/visit_det";
	}

	@RequestMapping("/menu")
	@ResponseBody
	public List<Menu> menu(){
		List<Menu> menus = resourceService.getMenusByUsername(getCurrentUser().getUsername());
		return menus;
	}
	@RequestMapping("/test")
  public String test(String test,Model model){
    model.addAttribute("test", test);
    return "test";
  }
	@RequestMapping("/index")
	public String index(String test,Model model){
	  model.addAttribute("test", test);
	  return "index";
	}

	private User getCurrentUser() {
		return (User)SecurityUtils.getSubject().getPrincipal();
	}
}
