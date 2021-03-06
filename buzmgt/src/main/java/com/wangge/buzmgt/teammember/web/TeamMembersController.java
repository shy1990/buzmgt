package com.wangge.buzmgt.teammember.web;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.saojie.service.SaojieDataService;
import com.wangge.buzmgt.saojie.service.SaojieService;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.entity.User.UserStatus;
import com.wangge.buzmgt.sys.service.OrganizationService;
import com.wangge.buzmgt.sys.service.RoleService;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesmanStatus;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.json.JSONFormat;

/**
 * 
* @ClassName: teamMembersController
* @Description: TODO(这里用一句话描述这个类的作用)
* @author SongBaozhen
* @date 2015年12月22日 上午9:45:34
*
 */
@Controller
@RequestMapping(value = "/teammember")
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
  @Resource
  private SaojieService saojieService;
  @Resource
  private AssessService assessService;
  @Resource
  private SaojieDataService sds;
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
  public String toTeamMembers(String salesManList, String salesmanStatus, Model model,SalesMan salesman,String regionId){
    int pageNum = 0;
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    Page<SalesMan> list = salesManService.getSalesmanList(salesman,salesmanStatus,pageNum,manager.getRegion().getName(),null);
    model.addAttribute("list", list);
    model.addAttribute("Status", "全部");
    model.addAttribute("salesManList", salesManList);
    
     if(null!=manager.getRegion().getCoordinates()){
       model.addAttribute("pcoordinates", manager.getRegion().getCoordinates());
     }
     model.addAttribute("regionName", manager.getRegion().getName());
     model.addAttribute("regionId", manager.getRegion().getId());
    return "teammember/salesman_list";
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
    return "teammember/team_member_add";
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
    Organization o = organizationService.getOrganById(Integer.parseInt(organizationId));
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
      }else{
          u.setId(createUerId(regionId.trim(),o));
          salesman.setRegion(regionService.getRegionById(regionId.trim()));
      }
    
      u = userService.addUser(u);
      salesman.setRegion(regionService.getRegionById(regionId.trim()));
      if(salesman.getIsOldSalesman()==1){
        salesman.setStatus(SalesmanStatus.weihu);
      }else{
        salesman.setStatus(com.wangge.buzmgt.teammember.entity.SalesmanStatus.saojie);
      }
      salesman.setRegdate(new Date());
      salesman.setUser(u);
      salesman.setIsPrimaryAccount(1);
      salesManService.addSalesman(salesman);
    //  return "redirect:/salesman/salesManList";
      
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
    //  return Redirect("/User/Edit");"salesman/salesman_list";
      
    }
      return "redirect:/teammember/salesManList";
    }else{
      model.addAttribute("userName", username);
      model.addAttribute("salesman", salesman);
      return "teammember/team_member_add";
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
  public  String  getSalesManList(Model model,SalesMan salesman, String salesmanStatus,String page,String regionId,String regionName, HttpServletRequest requet){
        String name = salesmanStatus != null ? salesmanStatus : "全部";
        int pageNum = Integer.parseInt(page != null ? page : "0");
        if(SalesmanStatus.saojie.getName().equals(name) ){
          salesman.setStatus(SalesmanStatus.saojie); 
        }else if(SalesmanStatus.kaifa.getName().equals(name)){
          salesman.setStatus(SalesmanStatus.kaifa); 
        }else if(SalesmanStatus.weihu.getName().equals(name)){
          salesman.setStatus(SalesmanStatus.weihu); 
        }else if(SalesmanStatus.zhuanzheng.getName().equals(name)){
          salesman.setStatus(SalesmanStatus.zhuanzheng);
        }else if(SalesmanStatus.shenhe.getName().equals(name)){
          salesman.setStatus(SalesmanStatus.shenhe);
        }
        Region region=new Region();
        if(null!=regionId){
          region =regionService.getRegionById(regionId);
          salesman.setRegion(region);
          if(null!=region.getCoordinates()){
            model.addAttribute("pcoordinates", region.getCoordinates());
          }
          model.addAttribute("regionName", region.getName());
          model.addAttribute("regionId", region.getId());
        }
       if(null!=regionName){
         region =regionService.findByNameLike(regionName);
         salesman.setRegion(region);
         if(null!=region.getCoordinates()){
           model.addAttribute("pcoordinates", region.getCoordinates());
         }
         model.addAttribute("regionName", region.getName());
         model.addAttribute("regionId", region.getId());
       }
       StringBuilder jpql = new StringBuilder();
       if((salesman.getTruename()!=null && !"".equals(salesman.getTruename())) || (salesman.getJobNum()!= null && !"".equals(salesman.getJobNum()))){
         jpql.append("(t.truename like ?1 or t.job_num like ?2)");
       }
    Page<SalesMan> list = salesManService.getSalesmanList(salesman,salesmanStatus,pageNum,region.getName(),jpql.toString());
    model.addAttribute("list", list);
    model.addAttribute("Status", salesmanStatus);
    return "teammember/salesman_list";
  }
  
  @RequestMapping("/salesmanInfo")
  public String salesmanInfo(String userId){
    salesManService.findByUserId(userId);
    return null;
  } 
  @RequestMapping("/{truename}")
  @ResponseBody
  public String getUserIdByTurename(@PathVariable("truename") String truename){
    return salesManService.findByTruename(truename);
  }
  
  /**
   * 
    * getSalesManInfo:(跳转到扫街详情页). <br/> 
    * 
    * @author Administrator 
    * @param saojie
    * @param flag
    * @param model
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/toSalesManInfo", method = RequestMethod.GET)
  public String getSalesManInfo(@RequestParam(value = "saojieId",required = false)Saojie saojie,String flag,String regionId, Model model){
	   //根据SalesMan获取saojie
	   System.out.println("****************"+regionId);
	   if(null!=regionId){
	     saojie = saojieService.findByregion(regionService.getRegionById(regionId.trim()));
	   }
	  
       SalesMan salesMan  =  salesManService.getSalesmanByUserId(saojie.getSalesman().getId());
       List<Region> rList = regionService.getListByIds(salesMan);
       model.addAttribute("salesMan", salesMan);
       model.addAttribute("rList", rList);
       model.addAttribute("areaName", salesMan.getRegion().getParent().getName()+salesMan.getRegion().getName());
       model.addAttribute("pcoordinates", salesMan.getRegion().getCoordinates());
       model.addAttribute("saojieId",saojie.getId());
       //判断业务员所处的模式
       List<Assess> listAssess=assessService.findBysalesman(salesMan);
       
       if(salesMan.getStatus().equals(SalesmanStatus.kaifa)&&listAssess.size()==0){
         model.addAttribute("salesStatus", "kaifa");
       }else{
        List<Saojie> listSaojie=saojieService.findSaojie(Saojie.SaojieStatus.PENDING, salesMan.getId());
          if(null==listSaojie || listSaojie.isEmpty()){
            model.addAttribute("salesStatus", "kaifa");
          }
       }
       
       if("saojie".equals(flag)){
         model.addAttribute("saojie",saojie);
         return "saojie/saojie_det";
       }else{
         return "teammember/saojie_det";
       }
  }
  
  /**
   * 
    * getSaojiedataMap:(异步获取扫街详情地图数据). <br/> 
    * 
    * @author peter 
    * @param saojie
    * @param regionId
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/getSaojiedataMap", method = RequestMethod.GET)
  @JSONFormat(filterField={"SaojieData.saojie","SaojieData.registData","SalesMan.region","SalesMan.user","Region.children","Region.parent"})
  public SaojieDataVo getSaojiedataMap(@RequestParam(value = "userId",required = false)SalesMan salesMan,String regionId){
    SaojieDataVo saojiedatalist  = sds.getsaojieDataList(salesMan.getId(), regionId);
    saojiedatalist.setAreaName(salesMan.getRegion().getName());//设置业务负责区域，用于地图加载
    List<SaojieData> list = saojiedatalist.getList();
    int size = 0;
    if(list!=null && !list.isEmpty()){
      size = list.size();
      saojiedatalist.setShopNum(size);
    }
    return saojiedatalist;
  }
  
  /**
   * 
    * getSojieDtaList:(加载扫街详情列表). <br/> 
    * 
    * @author Administrator 
    * @param userId
    * @param regionId
    * @param page
    * @param size
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/getSaojiedataList", method = RequestMethod.GET)
  @JSONFormat(filterField={"SaojieData.saojie","SaojieData.registData","SalesMan.region","SalesMan.user","Region.children","Region.parent"})
  public Page<SaojieData> getSojieDtaList(@RequestParam(value = "userId",required = false)SalesMan salesMan, String regionId,String page,String size){
    int pageNum = Integer.parseInt(page != null ? page : "0");
    int limit = Integer.parseInt(size);
    Page<SaojieData> dataPage  = sds.getsaojieDataList(salesMan.getId(), regionId,pageNum,limit);
    return dataPage;
  }
  
  @RequestMapping(value = "/percent", method = RequestMethod.GET)
  @ResponseBody
  public String getPercent(@RequestParam(value = "userId",required = false)SalesMan salesMan,String regionId){
    SaojieDataVo sdv  = new SaojieDataVo();
    if(!StringUtils.isBlank(regionId)){
      Saojie saojie = saojieService.findByregionId(regionId);
      List<SaojieData> list = sds.findByregionId(regionId);
      sdv.addPercent(list.size(), saojie.getMinValue());
    }else{
      Integer percent = 0;
      List<Saojie> saojie = saojieService.findBysalesman(salesMan);
      List<SaojieData> list = sds.findBySalesman(salesMan);
      for(Saojie sj: saojie){
        percent += sj.getMinValue();
      }
      sdv.addPercent(list.size(), percent);
    }
    return sdv.getPercent();
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
    List<Manager> umList=managerService.findByReginId(id);
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
//      if(umList.size() > 0){
//          for(int j=0;j<umList.size();j++){
//            userId += num[umList.size()]+id+time+"0";
//            break;
//          }
//      }else{
//        for(int j=0;j<num.length;j++){
//          userId += num[0]+id+time+"0";
//          break;
//        }
    	
//      }
    	String  str=getRandomString(4);
    	userId+="M"+id+str+"0";
    }
    return userId;
  }
  
  /**
   * 随机字符串
   * @param length
   * @return
   */
  public static String getRandomString(int length) {   
      StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");   
      StringBuffer sb = new StringBuffer();   
      Random random = new Random();   
      int range = buffer.length();   
      for (int i = 0; i < length; i ++) {   
          sb.append(buffer.charAt(random.nextInt(range)));   
      }   
      return sb.toString();   
  }  
}
