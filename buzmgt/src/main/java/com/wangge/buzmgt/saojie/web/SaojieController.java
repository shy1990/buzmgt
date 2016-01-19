package com.wangge.buzmgt.saojie.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.service.SaojieService;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;

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
	
	@RequestMapping("/saojieList")
	public String toTeamMembers(String saojieList, Model model){
		model.addAttribute("saojieList", saojieList);
		return "saojie/saojie_list";
	}
	
	@RequestMapping("/toAdd")
  public String toAddTeamMembers(String add , Model model){
    model.addAttribute("add", add);
    return "saojie/saojie_add";
  }
	
	@RequestMapping(value = "/gainSaojieMan",method = RequestMethod.POST)
	@ResponseBody
	public List<SalesMan> gainSaojieMan(){
	  List<SalesMan> salesman = salesManService.gainSaojieMan();
	  return salesman;
	}
	
	@RequestMapping(value = "/gainSaojieTown",method = RequestMethod.POST)
	@ResponseBody
	public List<Region> gainSaojieTown(String id){
	  System.out.println(id);
	  SalesMan sm = salesManService.findById(id);
	  List<Region> list = null;
	  if(sm != null && !"".equals(sm)){
	    list = regionService.findByRegion(sm.getRegion().getId());
	  }
    return list;
	}
	
	
	
	@RequestMapping(value = "/getRegionName",method = RequestMethod.POST)
  @ResponseBody
  public String  getRegionName(String id){
    SalesMan sm = salesManService.findById(id);
    String  regionName=sm.getRegion().getName();
    return regionName;
  }
	
	@RequestMapping(value = "/saveSaojie",method = RequestMethod.POST)
	@ResponseBody
	public String saveSaojie(Saojie saojie){
	  System.out.println(saojie);
	  System.out.println(saojie.getSalesman().getRegion());
	  saojieService.saveSaojie(saojie);
	  return "ok";
	}
	
}
