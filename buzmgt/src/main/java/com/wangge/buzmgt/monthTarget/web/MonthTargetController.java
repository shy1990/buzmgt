package com.wangge.buzmgt.monthTarget.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.monthTarget.service.MonthTargetService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.JsonResponse;
import com.wangge.json.JSONFormat;

import javax.servlet.http.HttpServletRequest;

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
  public String toUpdate(String flag,Long id,Model model){
    Region region = mtService.getRegion();
    if(!"update".equals(flag)){
      Set<SalesMan> salesSet = new HashSet<SalesMan>();
      salesSet.addAll(smService.findForTargetByReginId(region.getId()));
      model.addAttribute("salesList", salesSet);
    }
    model.addAttribute("region", region);
    model.addAttribute("flag",flag);
    model.addAttribute("id",id);
    return "monthTarget/update";
  }
  
  @RequestMapping("/regionName")
  @JSONFormat(filterField = {"SalesMan.user","region.children"})
  public SalesMan getRegionName(String userId){
    SalesMan sm = smService.findById(userId);
    return sm;
  }
  
  @RequestMapping(value = "/orderNum",method = RequestMethod.GET)
  @ResponseBody
  public Map<String,Object> orderNum(String userId){
    Map<String,Object> map = mtService.getOrderNum(userId);
    return map;
  }
  
  @RequestMapping(value = "/seller",method = RequestMethod.GET)
  @ResponseBody
  public Map<String,Object> seller(String userId){
    Map<String,Object> map = mtService.getSeller(userId);
    return map;
  }
  
  @RequestMapping(value = "/save/{userId}",method = {RequestMethod.POST})
  @ResponseBody
  public String save(@RequestBody MonthTarget mt,@PathVariable("userId") SalesMan salesman){
    String msg = mtService.save(mt,salesman);
    return msg;
  }

  @RequestMapping(value = "/update/{id}",method = {RequestMethod.POST})
  @ResponseBody
  public String update(@RequestBody MonthTarget mt,@PathVariable("id") MonthTarget monthTarget){
    monthTarget.setActiveNum(mt.getActiveNum());
    monthTarget.setOrderNum(mt.getOrderNum());
    monthTarget.setMerchantNum(mt.getMerchantNum());
    monthTarget.setMatureNum(mt.getMatureNum());
    String msg = mtService.save(monthTarget);
    return msg;
  }

  @RequestMapping(value = "/publish/{id}",method = {RequestMethod.GET})
  @ResponseBody
  public String publish(@PathVariable("id") MonthTarget monthTarget){
    String msg = mtService.publish(monthTarget);
    return msg;
  }

  @RequestMapping(value = "/publishAll",method = RequestMethod.POST)
  @ResponseBody
  public String publishAll(){
    String msg = mtService.publishAll();
    return msg;
  }

  @RequestMapping(value = "/findMonthTarget",method = RequestMethod.GET)
  @JSONFormat(filterField = {"SalesMan.user","region.children"})
  public Page<MonthTarget> findMonthTarget(String targetCycle,String userName,
                                  @PageableDefault(page = 0,size=20,sort={"orderNum"},direction= Sort.Direction.DESC) Pageable pageRequest){
    Page<MonthTarget> page = mtService.findAll(targetCycle,userName,pageRequest);
    return page;
  }
}
