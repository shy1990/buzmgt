package com.wangge.buzmgt.oilcost.web;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.oilcost.entity.OilCost;
import com.wangge.buzmgt.oilcost.service.OilCostService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.ExcelExport;

@RequestMapping("/oilCost")
@Controller
public class OilCostController {
  private static final Logger logger = Logger.getLogger(OilCostController.class);

  @Autowired 
  private OilCostService oilCostService;
  @Autowired
  private ManagerService managerService;
  @Autowired
  private SalesManService salesManService; 
  
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
    List<OilCost> oilCostlistAll = oilCostService.findGroupByUserId(searchParams);
    List<OilCost> oilCostlist = new ArrayList<>();
    int total=0;
    int number=pageRequest.getPageNumber();
    int size=pageRequest.getPageSize();
    //分页
    for(OilCost oilCost: oilCostlistAll){
      if(number*size <= total && total < (number+1)*size){
        //添加条数
        oilCostlist.add(oilCost);
      }
      total++;
    }
    Page<OilCost> oilCostPage = new PageImpl<OilCost>(oilCostlist, pageRequest, total);
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
  @RequestMapping(value="/abnormalCoord",method=RequestMethod.GET)
  @ResponseBody
  public String getAbnormalCoordList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"dateTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("LIKE_oilRecord", "exception");
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
   * 油补统计查看页面跳转
   */
  @RequestMapping(value="/record/{userId}",method=RequestMethod.GET)
  public String showRecordList(@PathVariable String userId,Model model,HttpServletRequest request){
    model.addAttribute("userId", userId);
    SalesMan salesMan= salesManService.findById(userId);
    model.addAttribute("startTime",request.getParameter("startTime"));
    model.addAttribute("endTime",request.getParameter("endTime"));
    model.addAttribute("oilTotalCost",request.getParameter("oilTotalCost"));
    model.addAttribute("totalDistance",request.getParameter("totalDistance"));
    model.addAttribute("salesMan", salesMan);
    return "oilsubsidy/oil_subsidy_record";
  }
  /**
   * 
   * @param userId
   * @param model
   * @param request
   * @return
   */
  @RequestMapping(value="/detail/{Id}",method=RequestMethod.GET)
  public String showDetailList(@PathVariable("Id") Long id,Model model,HttpServletRequest request){
//    oilCostService.disposeOilCostRecord(oilCost);//处理数据
    OilCost oc=oilCostService.findOne(id);
    model.addAttribute("oilCost", oc);
    return "oilsubsidy/oil_subsidy_detail";
  }
  
  /**
   * 油补统计详情数据查询
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
  /**
   * 导出油补统计列表
   * 
   * @param request
   * @param response
   */
  @RequestMapping("/export/{type}")
  public void excelExport(@PathVariable String type,HttpServletRequest request, HttpServletResponse response) {
    String[] gridTitles = { "业务名称","负责区域","累计公里数", "累计油补金额","日期"};
    String[] coloumsKey = { "salesManPart.truename","salesManPart.regionName", "totalDistance", "oilTotalCost", "dateTime"};
    String oilId= request.getParameter("oilCostId");
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<OilCost> oilCostlist=null;
    switch (type) {
    case "statistics":
      oilCostlist = oilCostService.findGroupByUserId(searchParams);
      
      ExcelExport.doExcelExport("油补统计.xls", oilCostlist, gridTitles, coloumsKey, request, response);
      break;
    case "abnormalCoord":
      searchParams.put("LIKE_oilRecord", "异常");
      
    case "record":
      String[] gridTitles_ = { "业务名称","油补握手顺序","公里数","金额","日期"};
      String[] coloumsKey_ = { "salesManPart.truename","recordSort", "distance", "oilCost", "dateTime"};
      
      oilCostlist = oilCostService.findAll(searchParams);
      
      ExcelExport.doExcelExport("油补记录.xls", oilCostlist, gridTitles_, coloumsKey_, request, response);
      break;
    case "detail":
      String[] gridTitles_1 = { "握手镇","动作", "握手时间"};
      String[] coloumsKey_1 = { "regionName", "distance", "time"};
      Long oilCostId=Long.valueOf(oilId);
      OilCost oc = oilCostService.findOne(oilCostId);
      
      ExcelExport.doExcelExport("油补详情握手点记录.xls", oc.getOilRecordList(), gridTitles_1, coloumsKey_1, request, response);
      break;

    default:
      break;
    }

    
      
//    ExcelExport.doExcelExport("客户签收异常.xls", list, gridTitles, coloumsKey, request, response);

  }
  /**
   * 导出油补记录
   * 整理握手顺序
   * @param oilCostlist
   */
  public static void exportOilCostUtil(List<OilCost> oilCostlist){
    
  }
}
