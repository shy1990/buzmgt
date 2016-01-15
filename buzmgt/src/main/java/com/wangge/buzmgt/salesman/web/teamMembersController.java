package com.wangge.buzmgt.salesman.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wangge.buzmgt.manager.entity.manager;
import com.wangge.buzmgt.manager.service.managerService;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.salesman.entity.salesMan.SalesmanStatus;
import com.wangge.buzmgt.salesman.service.salesManService;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.entity.User.UserStatus;
import com.wangge.buzmgt.sys.service.OrganizationService;
import com.wangge.buzmgt.sys.service.ResourceService;
import com.wangge.buzmgt.sys.service.ResourceService.Menu;
import com.wangge.buzmgt.sys.service.RoleService;
import com.wangge.buzmgt.sys.util.Page;

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
public class teamMembersController {
	@Resource
	private OrganizationService organizationService;
	@Resource
	private RoleService roleService;
	@Resource
	private RegionService regionService;
	@Resource
	private salesManService salesManService;
	@Resource
	private managerService managerService;
	
	@RequestMapping("/salesManList")
	public String toTeamMembers(String salesManList , Model model,Saojie saojie){
	  int pageNum = 0;
    Page<salesMan> list = salesManService.getSalesmanList(saojie,pageNum);
    model.addAttribute("list", list);
		model.addAttribute("salesManList", salesManList);
		return "salesman/salesman_list";
	}
	
	@RequestMapping("/toAdd")
	public String toAddTeamMembers(String add , Model model){
		model.addAttribute("add", add);
		return "salesman/team_member_add";
	}
	@RequestMapping(value = "/addTeamMember")
	public String addTeamMembers(salesMan salesman,String username,String regionId,String organizationId,String roleId,String regionPid ,HttpServletRequest request){
		System.out.println("truename===="+salesman.getTruename());
		System.out.println("jobNum===="+salesman.getJobNum());
		System.out.println("region===="+regionId);
		System.out.println("username===="+username);
		System.out.println("organizationId===="+organizationId);
		System.out.println("roleId===="+roleId);
		System.out.println("parentId===="+regionPid);
		Organization o = organizationService.getOrganById(Long.parseLong(organizationId));
		User u = new User();
		if("服务站经理".equals(o.getName())){
			u.setId(createUerId(regionPid,o));
			u.setOrganization(o);
			u.addRole(roleService.getRoleById(roleId));
			u.setPassword("123456");
			u.setUsername(username);
			u.setStatus(UserStatus.NORMAL);
			salesman.setRegion(regionService.getRegionById(regionPid));
			salesman.setSalesmanStatus(SalesmanStatus.SAOJIE);
			salesman.setTowns(regionId);
			salesman.setUser(u);
			salesManService.addSalesman(salesman);
			return null;
		}else{
			u.setId(createUerId(regionId,o));
			u.setOrganization(o);
			u.addRole(roleService.getRoleById(roleId));
			u.setPassword("123456");
			u.setUsername(username);
			manager m = new manager();
			m.setJobNum(salesman.getJobNum());
			m.setTruename(salesman.getTruename());
			m.setRegion(regionService.getRegionById(regionId));
			m.setUser(u);
			managerService.addManager(m);
			return null;
		}
		
	}
	
	
	private String  createUerId(String regionId,Organization o){
		String[] num = {"A","B","C","D","E","F"} ;
		SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
		String time = formatter.format(new Date());
		String userId = "";
		if("服务站经理".equals(o.getName())){
			List<User> uList = salesManService.findByReginId(regionId);
			if(uList.size() > 0){
				for(int i=0;i<uList.size();i++){
					System.out.println("<<======>>"+uList.get(i));
					for(int j=0;j<uList.size();j++){
						userId += num[i]+regionId+time+"0";
						System.out.println("======>>"+userId);
						break;
					}
				}
			}else{
				for(int j=0;j<num.length;j++){
					userId += num[0]+regionId+time+"0";
					System.out.println("======>>"+userId);
					break;
				}
			}
		}else{
			List<manager> uList = managerService.findByReginId(regionId);
			if(uList.size() > 0){
				for(int i=0;i<uList.size();i++){
					System.out.println("<<======>>"+uList.get(i));
					for(int j=0;j<uList.size();j++){
						userId += num[i]+regionId+time+"0";
						System.out.println("======>>"+userId);
						break;
					}
				}
			}else{
				for(int j=0;j<num.length;j++){
					userId += num[0]+regionId+time+"0";
					System.out.println("======>>"+userId);
					break;
				}
			}
		}
		return userId;
	}
}
