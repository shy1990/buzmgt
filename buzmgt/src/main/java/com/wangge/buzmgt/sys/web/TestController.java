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
	 * 权限设置页面跳转
	 * @param test
	 * @param model
	 * @return
	 */
	@RequestMapping("/purviewSetting")
	public String purviewSetting(String test,Model model){
		return "purview-setting/purivew_setting";
	}

	/**
	 * 角色查看
	 * @param test
	 * @param model
	 * @return
	 */
	@RequestMapping("/character")
	public String character(String test,Model model){
		model.addAttribute("test", test);
		return "purview-setting/character";
	}
	/**
	 * 团队成员添加
	 * @param test
	 * @param model
	 * @return
	 */
	@RequestMapping("/team_saojie_det")
	public String teamMemberAdd(String test,Model model){
		return "saojie/saojie_det";
	}
	/**
	 * 团队成员添加
	 * @param test
	 * @param model
	 * @return
	 */
	@RequestMapping("/saojie_set")
	public String saojieSet(String test,Model model){
	  return "saojie/saojie_set";
	}
	/**
	 * 团队成员列表
	 * @param test
	 * @param model
	 * @return
	 */
	@RequestMapping("/team_member_det")
	public String teamMemberList(String test,Model model){
		return "salesman/saleman_det";
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
