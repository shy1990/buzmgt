package com.wangge.buzmgt.monthTarget.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wangge.buzmgt.monthTarget.service.MonthTargetService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.JsonResponse;

@RequestMapping("/monthTarget")
@Controller
public class MonthTargetController {
  @Autowired
  private SalesManService smService;
  @Autowired
  private MonthTargetService mtService;
	
  @RequestMapping("/monthTarget")
  public String toMonthTarget(){
    
    return "monthTarget/mouth_target";
  }
  
  
  @RequestMapping("/monthSetting")
  public String toMonthSetting(){
    
    return "monthTarget/mouth_setting";
  }
  
  @RequestMapping(value = "/toUpdate")
  public String toUpdate(Model model){
    Set<SalesMan> salesSet = new HashSet<SalesMan>();
    Region region = mtService.getRegion();
    salesSet.addAll(smService.findForTargetByReginId(region.getId()));
    model.addAttribute("salesList", salesSet);
    model.addAttribute("region", region);
    return "monthTarget/update";
  }
  
  @RequestMapping(value = "/save",method = RequestMethod.PUT)
  public JsonResponse save(){
    JsonResponse jr = new JsonResponse();
    
    return jr;
  }
}
