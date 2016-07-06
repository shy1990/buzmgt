package com.wangge.buzmgt.monthTarget.web;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.json.JSONFormat;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wangge.buzmgt.monthTarget.service.MonthTargetService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.JsonResponse;

@RequestMapping("/monthTarget")
@Controller
public class MonthTargetController {
  private static final Logger logger = Logger.getLogger(MonthTargetController.class);
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




  /**
   * 根据时间与区域经理id查询 全部的业务员信息
   * @return
   */
  @RequestMapping(value = "/monthTargets",method = RequestMethod.GET)
//  @ResponseBody
  @JSONFormat(filterField = {"SalesMan.user","region.children"})
  public  Page<MonthTarget> findByTargetCycleAndManagerId(@RequestParam String time,
                                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                          @RequestParam (value = "name", defaultValue = "")String truename
  ){
    Sort sort = new Sort(Sort.Direction.DESC,"id");
    Pageable pageable = new PageRequest(page, size,sort);
    Page<MonthTarget> requestPage = mtService.findByTargetCycleAndManagerId(truename,time,pageable);

    return requestPage;
  }



}
