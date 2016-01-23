package com.wangge.buzmgt.saojie.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.manager.entity.Manager;
import com.wangge.buzmgt.manager.service.ManagerService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.entity.SalesMan;
import com.wangge.buzmgt.salesman.entity.SalesMan.SalesmanStatus;
import com.wangge.buzmgt.salesman.service.SalesManService;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.Saojie.SaojieStatus;
import com.wangge.buzmgt.saojie.service.SaojieService;
import com.wangge.buzmgt.sys.entity.User;

/**
 * 
  * ClassName: SaojieController <br/> 
  * Function: TODO ADD FUNCTION. <br/> 
  * Reason: TODO ADD REASON(可选). <br/> 
  * date: 2016年1月5日 上午11:56:53 <br/> 
  * 
  * @author peter
  * @version
  * @since JDK 1.8
 */
@Controller
@RequestMapping(value = "/saojie")
public class SaojieController {
  @Resource
  private SalesManService salesManService;
  @Resource
  private RegionService regionService;
  @Resource
  private SaojieService saojieService;
  @Resource
  private ManagerService managerService;
	@RequestMapping("/saojieList")
	public String saojieList(String saojieList, Model model,Saojie saojie){
	  int pageNum = 0;
    Page<Saojie> list = saojieService.getSaojieList(saojie,pageNum);
    int count = saojieService.getRegionCount();
    model.addAttribute("count",count);
    model.addAttribute("list", list);
		model.addAttribute("saojieList", saojieList);
		 Subject subject = SecurityUtils.getSubject();
     User user=(User) subject.getPrincipal();
     Manager manager = managerService.getById(user.getId());
     if(null!=manager.getRegion().getCoordinates()){
       model.addAttribute("pcoordinates", manager.getRegion().getCoordinates());
     }
     model.addAttribute("regionName", manager.getRegion().getName());
     model.addAttribute("regionId", manager.getRegion().getId());
		
		return "saojie/saojie_list";
	} 
	
	/**
	 * 
	  * getSaojieList:(扫街列表(条件)). <br/> 
	  * 
	  * @author peter 
	  * @param model
	  * @param saojie
	  * @param saojieStatus
	  * @param page
	  * @param requet
	  * @return 
	  * @since JDK 1.8
	 */
	@RequestMapping(value = "/getSaojieList")
  public  String  getSaojieList(Model model,Saojie saojie,String regionid,String regionName, String saojieStatus,String page, HttpServletRequest requet){
        int pageNum = Integer.parseInt(page != null ? page : "0");
        if(SaojieStatus.PENDING.getName().equals(saojieStatus) ){
          saojie.setStatus(SaojieStatus.PENDING);
        }else if(SaojieStatus.AGREE.getName().equals(saojieStatus)){
          saojie.setStatus(SaojieStatus.AGREE);
        }
        Region region=new Region();
        if(null!=regionid){
          region =regionService.getRegionById(regionid);
          saojie.setRegion(region);
          if(null!=region.getCoordinates()){
            model.addAttribute("pcoordinates", region.getCoordinates());
          }
          model.addAttribute("regionName", region.getName());
          model.addAttribute("regionId", region.getId());
        }
       if(null!=regionName){
         region =regionService.findByNameLike(regionName);
         saojie.setRegion(region);
         if(null!=region.getCoordinates()){
           model.addAttribute("pcoordinates", region.getCoordinates());
         }
         model.addAttribute("regionName", region.getName());
         model.addAttribute("regionId", region.getId());
       }
        
    Page<Saojie> list = saojieService.getSaojieList(saojie,pageNum);
    model.addAttribute("list", list);
    model.addAttribute("saojieStatus",saojieStatus);
    int count = saojieService.getRegionCount();
    model.addAttribute("count",count);
    return "saojie/saojie_list";
  }
	
	@RequestMapping("/toAdd")
  public String toAddTeamMembers(String add , Model model){
    model.addAttribute("add", add);
    return "saojie/saojie_add";
  }
	
	/**
	 * 
	  * gainSaojieMan:(获得待扫街人). <br/> 
	  * 
	  * @author peter 
	  * @return 
	  * @since JDK 1.8
	 */
	@RequestMapping(value = "/gainSaojieMan",method = RequestMethod.POST)
	@ResponseBody
	public List<SalesMan> gainSaojieMan(){
	  List<SalesMan> salesman = salesManService.gainSaojieMan();
	  return salesman;
	}
	
	/**
	 * 
	  * gainSaojieTown:(获得扫街地区). <br/> 
	  * 
	  * @author peter 
	  * @param id
	  * @return 
	  * @since JDK 1.8
	 */
	@RequestMapping(value = "/gainSaojieTown",method = RequestMethod.POST)
	@ResponseBody
	public List<Region> gainSaojieTown(String id){
	  System.out.println(id);
	  SalesMan sm = salesManService.findByUserId(id);
	  List<Region> list = null;
	  if(sm != null && !"".equals(sm)){
	    list = regionService.findByRegion(sm.getRegion().getId());
	  }
    return list;
	}
	
	/** 
	  * saveSaojie:(添加扫街保存). <br/> 
	  * 
	  * @author peter 
	  * @param saojie
	  * @return 
	  * @since JDK 1.8 
	  */  
	@RequestMapping(value = "/saveSaojie",method = RequestMethod.POST)
	public String saveSaojie(Saojie saojie,String value,@RequestParam String num){
	  String regionId=saojie.getRegion().getId();
	  String[] strArray = regionId.split(",");
	  String[] strValue = value.split(",");
	  String[] strOrder = num.split(",");
	  System.out.println(strArray.length);
	  for(int i=0; i<strArray.length;i++){
	    Saojie sj = new Saojie();
	    sj.setStatus(SaojieStatus.PENDING);
	    sj.setBeginTime(saojie.getBeginTime());
	    sj.setExpiredTime(saojie.getExpiredTime());
	    sj.setOrder(Integer.valueOf(strOrder[i]));
	    sj.setMinValue(Integer.valueOf(strValue[i]));
	    Region region = regionService.getRegionById(strArray[i]);
	    sj.setRegion(region);
	    sj.setSalesman(saojie.getSalesman());
	    saojieService.saveSaojie(sj);
	  }
	  
	  return "redirect:/saojie/saojie_list";
	}
	
	@RequestMapping("/toSaojieInstall")
	public String toSaojieInstall(@RequestParam("id") String id,Model model){
	  SalesMan salesman = salesManService.findByUserId(id.trim());
	  List<Saojie> list=null;
	  if(salesman != null && !"".equals(salesman)){
	     list = saojieService.findBysalesman(salesman);
	  }
	  model.addAttribute("list",list);
	  model.addAttribute("salesman",salesman);
	  model.addAttribute("areaname",salesman.getRegion().getName());
	  model.addAttribute("regionData",regionService.findByRegion(salesman.getRegion().getId()));
	  return "saojie/saojie_set";
	}

	@RequestMapping("/auditPass")
	@ResponseBody
  public String auditPass(String saojieId,String description,Model model){
    Saojie saojie = saojieService.findById(saojieId);
    saojie.setStatus(SaojieStatus.AGREE);
    saojie.setDescription(description);
    saojieService.saveSaojie(saojie);
    return "ok";
  }
	
	/**
	 * 
	  * changeOrder:(这里用一句话描述这个方法的作用). <br/> 
	  * TODO 上移使用到<br/> 
	  * @author Administrator 
	  * @param id
	  * @param ordernum
	  * @return 
	  * @since JDK 1.8
	 */
	@RequestMapping("/changeOrder")
	@ResponseBody
	public String changeOrder(String id,int ordernum,String userId,int flag){
	    //下边数据移动到上边，处理上面数据
	  Saojie saojie = null;
	  if(flag == -1){
	    saojie = saojieService.changeOrder(ordernum+1,userId);
	    saojie.setOrder(ordernum);
	  }else{
	    saojie = saojieService.changeOrder(ordernum,userId);
	    saojie.setOrder(ordernum+1);
	  }
	  saojieService.saveSaojie(saojie);
	  Saojie sj = saojieService.findById(id);//当前行id
	  if(flag == -1){
	    sj.setOrder(ordernum+1);
	  }else{
	    sj.setOrder(ordernum);
	  }
	  saojieService.saveSaojie(sj);
	  return "ok";
	}
	@RequestMapping(value = "/getRegionName",method = RequestMethod.POST)
  @ResponseBody
  public String getRegionName(String id){
    SalesMan sm = salesManService.findByUserId(id);
    String  regionName=sm.getRegion().getName();
    return regionName;
  }
}
