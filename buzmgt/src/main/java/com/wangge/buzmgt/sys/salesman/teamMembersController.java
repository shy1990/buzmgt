package com.wangge.buzmgt.sys.salesman;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;

//@RestController
//@RequestMapping("/salesman")
/**
 * 
* @ClassName: teamMembersController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaozhen
* @date 2015年12月22日 上午9:45:34
*
 */
@Controller
public class teamMembersController {
	
	@RequestMapping("/new")
	public String toTeamMembers(String news , Model model){
		model.addAttribute("new", news);
		return "salesman/salesman_list";
	}
	
	@RequestMapping("/add")
	public String toAddTeamMembers(String add , Model model){
		model.addAttribute("new", add);
		return "salesman/salesman_list";
	}
	
}
