package com.wangge.buzmgt.oilcost.web;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.oilcost.entity.OilCost;
import com.wangge.buzmgt.oilcost.service.OilCostService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.service.ManagerService;

@RequestMapping("/oilCost")
@Controller
public class OilCostController {
  private static final Logger logger = Logger.getLogger(OilCostController.class);

  @Autowired 
  private OilCostService oilCostService;
  @Autowired
  private ManagerService managerService;
  @Autowired
  private RegionService regionService;
  
  
  private static final String SEARCH_OPERTOR="sc_";
  
  /**
   * 显示油补统计列表
   * @return
   */
  @RequestMapping("/list")
  public String showStatistics(Model model,String regionid){
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    model.addAttribute("regionName", manager.getRegion().getName());
    model.addAttribute("regionId", manager.getRegion().getId());
    model.addAttribute("regionType", manager.getRegion().getType());
    return "oilsubsidy/oil_subsidy_list";
  }
  /**
   * 选择区域后回调方法
   * @param model
   * @param region
   * @return
   */
  @RequestMapping("getOilCostList/{regionId}")
  public String getStatisticsByRegion(Model model,@PathVariable(value="regionId")Region region){
    model.addAttribute("regionName",region.getName());
    model.addAttribute("regionId",region.getId());
    model.addAttribute("regionType",region.getType());
    return "oilsubsidy/oil_subsidy_list";
  }
  /**
   * 油补统计
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value="/statistics",method=RequestMethod.GET)
  @ResponseBody
  public String getOilCostGroupByUserId(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"dateTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<OilCost> oilCostPage = oilCostService.findGroupByUserId(searchParams,pageRequest);
    String msg="";
    try {
      msg=JSON.toJSONString(oilCostPage,SerializerFeature.DisableCheckSpecialChar);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return msg;
  }
  /**
   * 获取异常坐标
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value="/AbnormalCoord",method=RequestMethod.GET)
  @ResponseBody
  public String getAbnormalCoordList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"dateTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("LIKE_oilRecord", "异常");
    Page<OilCost> oilCostPage = oilCostService.findAll(searchParams,pageRequest);
    String msg="";
    try {
      msg=JSON.toJSONString(oilCostPage,SerializerFeature.DisableCheckSpecialChar);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return msg;
  }
  /**
   * 油补统计详情
   * @param request
   * @param pageRequest
   * @return
   */
  @RequestMapping(value="/statistics/{userId}",method=RequestMethod.GET)
  @ResponseBody
  public String findByUserIdList(@PathVariable String userId,HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"dateTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("ORE_userId", "parentId_"+userId);
    Page<OilCost> oilCostPage = oilCostService.findAll(searchParams,pageRequest);
    String msg="";
    try {
      msg=JSON.toJSONString(oilCostPage,SerializerFeature.DisableCheckSpecialChar);
    } catch (Exception e) {
      logger.error(e.getMessage());
    }
    return msg;
  }
  
}
