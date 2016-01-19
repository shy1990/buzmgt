package com.wangge.buzmgt.saojie.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.salesman.entity.salesMan.SalesmanStatus;
import com.wangge.buzmgt.salesman.service.salesManService;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.Saojie.SaojieStatus;
import com.wangge.buzmgt.saojie.service.SaojieService;

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
  private salesManService salesManService;
  @Resource
  private RegionService regionService;
  @Resource
  private SaojieService saojieService;
	
	@RequestMapping("/saojieList")
	public String saojieList(String saojieList, Model model,Saojie saojie){
	  int pageNum = 0;
    Page<Saojie> list = saojieService.getSaojieList(saojie,pageNum);
    model.addAttribute("list", list);
		model.addAttribute("saojieList", saojieList);
		return "saojie/saojie_list";
	}
	
	@RequestMapping(value = "/getSaojieList")
  public  String  getSaojieList(Model model,Saojie saojie, String saojieStatus,String page, HttpServletRequest requet){
        int pageNum = Integer.parseInt(page != null ? page : "0");
        if(SaojieStatus.PENDING.getName().equals(saojieStatus) ){
          saojie.setStatus(SaojieStatus.PENDING);
        }else if(SaojieStatus.AGREE.getName().equals(saojieStatus)){
          saojie.setStatus(SaojieStatus.AGREE);
        }
    Page<Saojie> list = saojieService.getSaojieList(saojie,pageNum);
    model.addAttribute("list", list);
    model.addAttribute("saojieStatus",saojieStatus);
    return "saojie/saojie_list";
  }
	
	@RequestMapping("/toAdd")
  public String toAddTeamMembers(String add , Model model){
    model.addAttribute("add", add);
    return "saojie/saojie_add";
  }
	
	@RequestMapping(value = "/gainSaojieMan",method = RequestMethod.POST)
	@ResponseBody
	public List<salesMan> gainSaojieMan(){
	  List<salesMan> salesman = salesManService.gainSaojieMan();
	  return salesman;
	}
	
	@RequestMapping(value = "/gainSaojieTown",method = RequestMethod.POST)
	@ResponseBody
	public List<Region> gainSaojieTown(String id){
	  System.out.println(id);
	  salesMan sm = salesManService.findById(id);
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
	@ResponseBody
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
	  return "ok";
	}
	
}
