package com.wangge.buzmgt.sys.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.sys.entity.Organization;
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
	@Autowired
	private OrganizationService organService;
	@Resource
	private UserService userService;
	
	
	/**
	 * 
	  * getOrganList: 添加团队成员时用到
	  * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	  * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	  * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	  * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	  * 
	  * @author Administrator 
	  * @param request
	  * @param id
	  * @param principals
	  * @return 
	  * @since JDK 1.8
	 */
	@RequestMapping(value="/getOrganizationList")
	@ResponseBody
	public ResponseEntity<List<OrganizationVo>> getOrganList(HttpServletRequest request,String id ,PrincipalCollection principals){
		int organizationId = 0;
		if(id != null && !"".equals(id)){
		   organizationId = Integer.parseInt(id);
		}else{
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			user = userService.getById(user.getId());
			organizationId = user.getOrganization().getId();
		}
		List<OrganizationVo> o = organService.getOrganListById(organizationId);
		return new ResponseEntity<List<OrganizationVo>>(o, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/initOrganization")
	public String initRegion(){
    return "organization/organization_view";
  } 
	
	  /**
	   * 
	    * findOneOrganization:组织结构一级查询
	    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	    * 
	    * @author Administrator 
	    * @return 
	    * @since JDK 1.8
	   */
	 @RequestMapping(value = "/findOneOrganization", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<List<OrganizationVo>> findOneOrganization() {
	     List<OrganizationVo> listTreeVo =new ArrayList<OrganizationVo>();
	     
	      Subject subject = SecurityUtils.getSubject();
	      User user=(User) subject.getPrincipal();
	    
	      Organization organ=user.getOrganization();
	      listTreeVo.add(getOrganizationVo(organ));
	      
	      if(null!=organ.getChildren()){
	        List<Organization>  setO=organService.getOrganById(organ.getId()).getChildren();
	        for(Organization o:setO ){
	          listTreeVo.add(getOrganizationVo(o));
	        }
	      }
	      
	    return new ResponseEntity<List<OrganizationVo>>(listTreeVo,HttpStatus.OK);
	  }
	   
	 
	 /**
	  * 
	   * findOneTeamOrganization:团队成员显示组织结构
	   * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	   * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	   * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	   * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	   * 
	   * @author Administrator 
	   * @return 
	   * @since JDK 1.8
	  */
	 @RequestMapping(value = "/findOneTeamOrganization", method = RequestMethod.POST)
   @ResponseBody
   public ResponseEntity<List<OrganizationVo>> findOneTeamOrganization() {
      List<OrganizationVo> listTreeVo =new ArrayList<OrganizationVo>();
      
       Subject subject = SecurityUtils.getSubject();
       User user=(User) subject.getPrincipal();
     
       Organization organ=user.getOrganization();
       List<User> listUser=userService.getUserByOrgan(organ.getId());
       
       listTreeVo.add(getTeamOrganizationVo(listUser.get(0),null));
       
       if(null!=organ.getChildren()){
         for(Organization o: organService.getOrganById(organ.getId()).getChildren()){
           List<User> list=userService.getUserByOrgan(o.getId());
            for(User u:list){
              listTreeVo.add(getTeamOrganizationVo(u,listUser.get(0)));
            }
          
         }
       }
       
     return new ResponseEntity<List<OrganizationVo>>(listTreeVo,HttpStatus.OK);
   }
	
	/**
	 * 
	  * findOrganizationByid:点击加号，加载下一级别
	  * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	  * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	  * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	  * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	  * 
	  * @author Administrator 
	  * @param id
	  * @return 
	  * @since JDK 1.8
	 */
	@RequestMapping(value = "/findOrganizationByid", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<List<OrganizationVo>> findOrganizationByid(String id) {
     List<OrganizationVo> listTreeVo =new ArrayList<OrganizationVo>();
     for(Organization o: organService.getOrganById(Integer.parseInt(id)).getChildren()){
       listTreeVo.add(getOrganizationVo(o));
     }
    return new ResponseEntity<List<OrganizationVo>>(listTreeVo,HttpStatus.OK);
  }
	
	@RequestMapping(value = "/findTeamOrganizationByid", method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity<List<OrganizationVo>> findTeamOrganizationByid(String id) {
     List<OrganizationVo> listTreeVo =new ArrayList<OrganizationVo>();
     
     User user =userService.getById(id);
     for(Organization o: organService.getOrganById(user.getOrganization().getId()).getChildren()){
       for(User  u: userService.getUserByOrgan(o.getId())){
         listTreeVo.add(getTeamOrganizationVo(u,user));
       }
     }
    
     
    return new ResponseEntity<List<OrganizationVo>>(listTreeVo,HttpStatus.OK);
  }
	  /**
	   * 
	    * addOrganization:(这里用一句话描述这个方法的作用). <br/> 
	    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	    * 
	    * @author Administrator 
	    * @param pid
	    * @param name
	    * @return 
	    * @since JDK 1.8
	   */
	  @RequestMapping(value = "/addOrganization", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<OrganizationVo> addOrganization(String pid,String name) {
	    Organization organ=organService.getOrganById(Integer.parseInt(pid));
	    
	    Organization o=new Organization();
	    o.setName(name);
	    o.setParent(organ);
	    o.setLev(organ.getLev()+1);
	    organService.addOrganization(o);
	    
	    return new ResponseEntity<OrganizationVo>(getOrganizationVo(o),HttpStatus.OK);
	  }
	
	
	  
	  /**
	   * 
	    * editOrganization:修改组织结构
	    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	    * 
	    * @author Administrator 
	    * @param id
	    * @param pid
	    * @param name
	    * @return 
	    * @since JDK 1.8
	   */
	  @RequestMapping(value = "/editOrganization", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<OrganizationVo>   editOrganization(String id,String pid,String name) {
  	    Organization organ=organService.getOrganById(Integer.parseInt(id));
  	    organ.setName(name);
        organService.addOrganization(organ);
	    return new ResponseEntity<OrganizationVo>(getOrganizationVo(organ),HttpStatus.OK);
	  }
	  
	  
	  @RequestMapping(value = "/deleteOrganizationbyId", method = RequestMethod.POST)
	  @ResponseBody
	  public boolean deleteOrganizationbyId(String id, String pid) {
	    Organization o = organService.getOrganById(Integer.parseInt(id));
	    if(o.getChildren().size()>0){
	      return false;
	    }
	    organService.deleteOrganization(o);
	    return true;
	  }
	  
	  
	  
	
	  /**
	   * 
	    * getOrganizationVo:返回实体OrganizationVo
	    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
	    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
	    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
	    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
	    * 
	    * @author Administrator 
	    * @param organ
	    * @return 
	    * @since JDK 1.8
	   */
  	private OrganizationVo getOrganizationVo(Organization organ){
    	  OrganizationVo vo=new OrganizationVo();
        vo.setId(organ.getId()+"");
        vo.setName(organ.getName());
        String iconUrl=null;
        switch (organ.getLev()) {
        case 1:
          iconUrl="/static/img/organization/jl.png";
          break;
        case 2:
          iconUrl="/static/img/organization/qyzj.png";
          break;
        case 3:
          iconUrl="/static/img/organization/dqjl.png";
          break;
        case 4:
          iconUrl="/static/img/organization/dqjl.png";
          break;
        case 5:
          iconUrl="/static/img/organization/dqjl.png";
          break;
        case 6:
          iconUrl="/static/img/organization/dqjl.png";
          break;
        default:
          iconUrl="/static/img/organization/dqjl.png";
          break;
        }
        vo.setIcon(iconUrl);
        vo.setIconClose(iconUrl);
        vo.setIconOpen(iconUrl);
        if(organ.getChildren().size()>0){
          vo.setIsParent("true");
        }else{
          vo.setIsParent("false");
        }
        vo.setOpen("true");
        if(null!=organ.getParent()){
          vo.setpId(organ.getParent().getId()+"");;
        }
    	  return vo;
  	}
  	
  	/**
  	 * 
  	  * getTeamOrganizationVo:(这里用一句话描述这个方法的作用). <br/> 
  	  * TODO(这里描述这个方法适用条件 – 可选).<br/> 
  	  * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
  	  * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
  	  * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
  	  * 
  	  * @author Administrator 
  	  * @param u
  	  * @param parent
  	  * @return 
  	  * @since JDK 1.8
  	 */
    private OrganizationVo getTeamOrganizationVo(User u,User parent){
      OrganizationVo vo=new OrganizationVo();
      vo.setId(u.getId()+"");
      if (null != u.getManager()) {
        vo.setName(u.getManager().getTruename() + "("
            + u.getOrganization().getName() + ")");
      } else if (null != u.getSalseMan()) {
        vo.setName(u.getSalseMan().getTruename() + "("
            + u.getOrganization().getName() + ")");
      }
      
      String iconUrl=null;
    switch (u.getOrganization().getLev()) {
      case 1:
        iconUrl="/static/img/organization/jl.png";
        break;
      case 2:
        iconUrl="/static/img/organization/qyzj.png";
        break;
      case 3:
        iconUrl="/static/img/organization/dqjl.png";
        break;
      case 4:
        iconUrl="/static/img/organization/dqjl.png";
        break;
      case 5:
        iconUrl="/static/img/organization/dqjl.png";
        break;
      case 6:
        iconUrl="/static/img/organization/dqjl.png";
        break;
      default:
        iconUrl="/static/img/organization/dqjl.png";
        break;
      }
      vo.setIcon(iconUrl);
      vo.setIconClose(iconUrl);
      vo.setIconOpen(iconUrl);
    if (u.getOrganization().getChildren().size() > 0) {
      if (userService.getUserByOrgan(u.getOrganization().getId() + 1).size() > 0) {
        vo.setIsParent("true");
      } else {
        vo.setIsParent("false");
      }
       
    } else {
      vo.setIsParent("false");
    }
      vo.setOpen("true");
      if(null!=parent){
        vo.setpId(parent.getId()+"");;
      }
      return vo;
  }
	
}
