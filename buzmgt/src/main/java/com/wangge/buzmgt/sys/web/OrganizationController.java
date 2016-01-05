package com.wangge.buzmgt.sys.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.shiro.MyRealm;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.OrganizationService;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.vo.OrganizationVo;
/**
 * 
* @ClassName: OrganiztionController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaoZhen
* @date 2015年12月29日 下午1:05:20
*
 */
@Controller
@RequestMapping(value = "/organization")
public class OrganizationController {
	@Resource
	private OrganizationService organService;
	@Resource
	private UserService userService;

	@RequestMapping(value="/getOrganizationList")
	@ResponseBody
	public ResponseEntity<List<OrganizationVo>> getOrganList(HttpServletRequest request,String id ,PrincipalCollection principals){
		Long organizationId = 0L;
		if(id != null && !"".equals(id)){
		   organizationId = Long.parseLong(id);
		}else{
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			user = userService.getById(user.getId());
			organizationId = user.getOrganization().getId();
		}
		List<OrganizationVo> o = organService.getOrganListById(organizationId);
		return new ResponseEntity<List<OrganizationVo>>(o, HttpStatus.OK);
	}
	
	
	


}
