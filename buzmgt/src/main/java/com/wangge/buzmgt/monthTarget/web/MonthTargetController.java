package com.wangge.buzmgt.monthTarget.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.json.JSONFormat;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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






  /**
   * 根据时间与区域经理id查询 全部的业务员信息
   * @return
     */
  @RequestMapping(value = "/monthTargets/{time}",method = RequestMethod.GET)
//  @ResponseBody
  @JSONFormat(filterField = {"SalesMan.user","region.children"})
  public  Page<MonthTarget> findByTargetCycleAndManagerId(@PathVariable String time,
                                                          @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                          @RequestParam(value = "size", defaultValue = "20") Integer size,
                                                          @RequestParam (value = "name", defaultValue = "")String name
                                                          ){
    User user = (User)SecurityUtils.getSubject().getPrincipal();
//    String managerId = user.getId();B37000006290
    String managerId = "B37000006290";
    Pageable pageable = new PageRequest(page, size);
    Page<MonthTarget> requestPage = mtService.findByTargetCycleAndManagerId(time,managerId,pageable);

    return requestPage;
  }


}
