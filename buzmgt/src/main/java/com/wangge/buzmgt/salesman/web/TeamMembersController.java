package com.wangge.buzmgt.salesman.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.buzmgt.manager.entity.Manager;
import com.wangge.buzmgt.manager.service.ManagerService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.entity.SalesMan;
import com.wangge.buzmgt.salesman.entity.SalesMan.SalesmanStatus;
import com.wangge.buzmgt.salesman.service.SalesManService;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.entity.User.UserStatus;
import com.wangge.buzmgt.sys.service.OrganizationService;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;
import com.wangge.buzmgt.sys.service.RoleService;
import com.wangge.buzmgt.sys.service.UserService;

/**
 * 
* @ClassName: teamMembersController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaozhen
* @date 2015年12月22日 上午9:45:34
*
 */
@Controller
@RequestMapping(value = "/salesman")
public class TeamMembersController {
	private static final Pageable Pageable = null;
  @Resource
	private OrganizationService organizationService;
	@Resource
	private RoleService roleService;
	@Resource
	private RegionService regionService;
	@Resource
	private SalesManService salesManService;
	@Resource
	private ManagerService managerService;
	@Resource
	private UserService userService;
	
	/**
	 * 
	* @Title: toTeamMembers 
	* @Description: TODO(跳转到业务员列表) 
	* @param @param salesManList
	* @param @param Status
	* @param @param model
	* @param @param salesman
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	
	@RequestMapping("/salesManList")
	public String toTeamMembers(String salesManList, String Status, Model model,SalesMan salesman){
	  int pageNum = 0;
	  Page<SalesMan> list = salesManService.getSalesmanList(salesman,pageNum);
    model.addAttribute("list", list);
    model.addAttribute("Status", "扫街中");
		model.addAttribute("salesManList", salesManList);
	  Subject subject = SecurityUtils.getSubject();
		 User user=(User) subject.getPrincipal();
     Manager manager = managerService.getById(user.getId());
     if(null!=manager.getRegion().getCoordinates()){
       model.addAttribute("pcoordinates", manager.getRegion().getCoordinates());
     }
     model.addAttribute("regionName", manager.getRegion().getName());
		return "salesman/salesman_list";
	}
	/**
	 * 
	* @Title: toAddTeamMembers 
	* @Description: TODO(跳转到添加团队成员页面) 
	* @param @param add
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping("/toAdd")
	public String toAddTeamMembers(String add , Model model){
		model.addAttribute("add", add);
		return "salesman/team_member_add";
	}
	/**
	 * 
	* @Title: addTeamMembers 
	* @Description: TODO(添加团队成员) 
	* @param @param salesman
	* @param @param username
	* @param @param regionId
	* @param @param organizationId
	* @param @param roleId
	* @param @param regionPid
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/addTeamMember",method = RequestMethod.POST)
	public String addTeamMembers(SalesMan salesman,String username,String regionId,String organizationId,String roleId,String regionPid ,Model model){
	  if(!userService.existUsername(username)){
	  Organization o = organizationService.getOrganById(Long.parseLong(organizationId));
		User u = new User();
		u.setOrganization(o);
    u.addRole(roleService.getRoleById(roleId));
    u.setPassword("123456");
    u.setUsername(username);
    u.setStatus(UserStatus.NORMAL);
		if("服务站经理".equals(o.getName())){
			
			if(!"".equals(regionPid)){
			  salesman.setRegion(regionService.getRegionById(regionPid.trim()));
			  salesman.setTowns(regionId);
			  u.setId(createUerId(regionPid.trim(),o));
			}
			u.setId(createUerId(regionId.trim(),o));
      u = userService.addUser(u);
			salesman.setRegion(regionService.getRegionById(regionId.trim()));
			salesman.setSalesmanStatus(SalesmanStatus.SAOJIE);
			salesman.setRegdate(new Date());
			salesman.setUser(u);
			salesManService.addSalesman(salesman);
		//	return "redirect:/salesman/salesManList";
			
		}else{
			u.setId(createUerId(regionId.trim(),o));
			u = userService.addUser(u);
			Manager m = new Manager();
			m.setJobNum(salesman.getJobNum());
			m.setTruename(salesman.getTruename());
			m.setMobile(salesman.getMobile());
			m.setRegdate(new Date());
			m.setRegion(regionService.getRegionById(regionId.trim()));
			m.setUser(u);
			managerService.addManager(m);
		//	return Redirect("/User/Edit");"salesman/salesman_list";
			
		}
		  return "redirect:/salesman/salesManList";
	  }else{
	    model.addAttribute("userName", username);
	    model.addAttribute("salesman", salesman);
	    return "salesman/team_member_add";
	  }
	}
	
	/**
	 * 
	* @Title: getSalesManList 
	* @Description: TODO(获取业务员列表) 
	* @param @param model
	* @param @param salesman
	* @param @param Status
	* @param @param page
	* @param @param requet
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/getSalesManList",method=RequestMethod.GET)
	public  String  getSalesManList(Model model,SalesMan salesman, String Status,String page, HttpServletRequest requet){
	      String name = Status != null ? Status : "扫街中";
	      int pageNum = Integer.parseInt(page != null ? page : "0");
    	  if(SalesmanStatus.SAOJIE.getName().equals(name) ){
    	    salesman.setSalesmanStatus(SalesmanStatus.SAOJIE);
    	  }else if(SalesmanStatus.KAIFA.getName().equals(name)){
    	    salesman.setSalesmanStatus(SalesmanStatus.KAIFA);
    	  }else if(SalesmanStatus.WEIHU.getName().equals(name)){
    	    salesman.setSalesmanStatus(SalesmanStatus.WEIHU);
    	  }else if(SalesmanStatus.ZHUANZHENG.getName().equals(name)){
    	    salesman.setSalesmanStatus(SalesmanStatus.ZHUANZHENG);
    	  }else if(SalesmanStatus.SHENHE.getName().equals(name)){
    	    salesman.setSalesmanStatus(SalesmanStatus.SHENHE);
    	  }
	  Page<SalesMan> list = salesManService.getSalesmanList(salesman,pageNum);
	  model.addAttribute("list", list);
	  model.addAttribute("Status", Status);
	  return "salesman/salesman_list";
	}
	
	@RequestMapping("/salesmanInfo")
  public String salesmanInfo(String userId){
	  salesManService.findByUserId(userId);
    return null;
  }	
	/**
	 * 
	* @Title: createUerId 
	* @Description: TODO(创建userId) 
	* @param @param id
	* @param @param o
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	private String  createUerId(String id,Organization o){
		String[] num = {"A","B","C","D","E","F"} ;
		SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
		String time = formatter.format(new Date());
		String userId = "";
		List<User> uList = salesManService.findByReginId(id);
		if("服务站经理".equals(o.getName())){
			
			if(uList.size() > 0){
					for(int i=0;i<uList.size();i++){
						userId += num[uList.size()]+id+time+"0";
						break;
				}
			}else{
				for(int j=0;j<num.length;j++){
					userId += num[0]+id+time+"0";
					break;
				}
			}
		}else{
			if(uList.size() > 0){
					for(int j=0;j<uList.size();j++){
						userId += num[uList.size()]+id+time+"0";
						break;
					}
			}else{
				for(int j=0;j<num.length;j++){
					userId += num[0]+id+time+"0";
					break;
				}
			}
		}
		return userId;
	}
}