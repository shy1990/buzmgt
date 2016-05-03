package com.wangge.buzmgt.ordersignfor.web;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.ExcelExport;

@Controller
@RequestMapping(value="/ordersignfor")
public class OrderSignforController {
  
  @Resource
  private OrderSignforService os;
  
  @Resource
  private RegionService regionService;
  
  @Resource
  private SalesManService salesManService;
  
  @Resource
  private AssessService assessService;
  
  
  private static final String SEARCH_OPERTOR = "sc_";
 
  @SuppressWarnings("deprecation")
  @RequestMapping(value="/list",method=RequestMethod.GET)
  @ResponseBody
  public  String OrderSignforList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    String type=request.getParameter("type");
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<OrderSignfor> orderSignforslist=null;
    if("ywOrderSignfor".equals(type)){
      List<OrderSignfor> list=os.findAll(searchParams);
      List<OrderSignfor> ywlist=new ArrayList<OrderSignfor>();
      //TODO  某一个时间点（如9:00）拓展后期后台设置;
      String timesGap = "9:00";
      String[] times=timesGap.split(":");
      int total=0;
      int number=pageRequest.getPageNumber();
      int size=pageRequest.getPageSize();
      for(OrderSignfor order : list){
        Date ctrateTime = order.getCreateTime();
        Date checkDate=DateUtil.moveDate(ctrateTime, 1);
        checkDate.setHours(Integer.parseInt(times[0]));
        checkDate.setMinutes(Integer.parseInt(times[1]));
        Date yewuSignforTime = order.getYewuSignforTime();
        
        if(yewuSignforTime != null &&yewuSignforTime.getTime()-checkDate.getTime()>0){
          //分页
          if(number*size <= total && total < (number+1)*size){
            ywlist.add(order);
          }
          total++;
        }
      }
      orderSignforslist = new PageImpl<OrderSignfor>(ywlist,pageRequest,total);
      
      
    }else{
      orderSignforslist=os.getOrderSingforList(searchParams, pageRequest);
    }
    String s ="";
    orderSignforslist.getContent().forEach(osfl->{
      if(osfl.getSalesMan()==null){
        osfl.setSalesMan(new SalesMan());
      }
      osfl.getSalesMan().setUser(null);
      osfl.getSalesMan().setRegion(null);
      String DateStr="";
      
      if(osfl.getCustomSignforTime() != null && osfl.getYewuSignforTime() != null){
        DateStr = DateUtil.getAging(osfl.getCustomSignforTime().getTime()-osfl.getYewuSignforTime().getTime());
      }
      osfl.setAging(DateStr);
    });
    try { s = JSON.toJSONString(orderSignforslist, SerializerFeature.DisableCircularReferenceDetect);
   }
    catch(Exception e){
      System.out.println(e.getMessage());
    }
    
    return s;
  }
  
  @RequestMapping(value="/show")
  public String showList(Model model){
    Long orderTotal=os.findCount();//订单总数
    model.addAttribute("totalCount",orderTotal);
    return "abnormal/abnormal_list";
  }
  
  /**
   * 导出交易记录
   * 
   * @param request
   * @param response
   */
  @SuppressWarnings("deprecation")
  @RequestMapping("/export")
  public void excelExport(HttpServletRequest request, HttpServletResponse response) {
    String[] gridTitles = { "业务名称","店铺名称","订单号", "签收地址","签收时间"};
    String[] coloumsKey = { "salesMan.truename","shopName", "orderNo", "yewuSignforGeopoint", "yewuSignforTime"};

    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<OrderSignfor> orderSignforslist=null;
    String type=request.getParameter("type");
    List<OrderSignfor> ywlist=null;
    List<OrderSignfor> list=os.findAll(searchParams);
    list.forEach(osfl->{
      osfl.getSalesMan().setUser(null);
      osfl.getSalesMan().setRegion(null);
      
      String DateStr="";
      if(osfl.getCustomSignforTime() != null && osfl.getYewuSignforTime() != null){
        DateStr = DateUtil.getAging(osfl.getCustomSignforTime().getTime()-osfl.getYewuSignforTime().getTime());
      }
      osfl.setAging(DateStr);
      
      });
    if("ywOrderSignfor".equals(type)){
      ywlist=new ArrayList<OrderSignfor>();
      //TODO  某一个时间点（如9:00）拓展后期后台设置;
      String timesGap = "9:00";
      String[] times=timesGap.split(":");
      for(OrderSignfor order : list){
        Date ctrateTime = order.getCreateTime();
        Date checkDate=DateUtil.moveDate(ctrateTime, 1);
        checkDate.setHours(Integer.parseInt(times[0]));
        checkDate.setMinutes(Integer.parseInt(times[1]));
        Date yewuSignforTime = order.getYewuSignforTime();
        
        if(yewuSignforTime != null &&yewuSignforTime.getTime()-checkDate.getTime()>0){
          ywlist.add(order);
        }
      }
      ExcelExport.doExcelExport("业务揽收异常.xls", ywlist, gridTitles, coloumsKey, request, response);
      
    }else{
      
      gridTitles=Arrays.copyOf(gridTitles, gridTitles.length+1);
      gridTitles[gridTitles.length-1]="送货时效";
      
      coloumsKey=Arrays.copyOf(coloumsKey, coloumsKey.length+1);
      coloumsKey[coloumsKey.length-1]="aging";
      coloumsKey[3]="customSignforGeopoint";
      
      
      ExcelExport.doExcelExport("客户签收异常.xls", list, gridTitles, coloumsKey, request, response);
    }

  }
  /**
   * 业务所有订单签收信息
   * @param model
   * @return
   */
  @RequestMapping(value="/showrecord/{salesManId}")
  public String showRecordList(Model model , @PathVariable("salesManId") SalesMan salesMan,HttpServletRequest request){
    String tabs=request.getParameter("tabs");
    Assess assesse = findAssessBySalesMan(salesMan);
    model.addAttribute("tabs",tabs);
    model.addAttribute("salesMan",salesMan);
    model.addAttribute("assess", assesse);
    model.addAttribute("userId",salesMan.getId());
    model.addAttribute("areaName", salesMan.getRegion().getName());
    //TODO  某一个时间点（如9:00）拓展后期后台设置;
    String timesGap = "9:00";
    model.addAttribute("timesGap",timesGap);
    return "abnormal/abnormal_record_list";
  }
  /**
   * 业务所有订单信息
   * @param model
   * @return
   */
  @RequestMapping(value="/getrecord/{salesManId}",method=RequestMethod.GET)
  @ResponseBody
  public String getRecordList(@PathVariable("salesManId") SalesMan salesMan, HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"createTime"},direction=Direction.DESC) Pageable pageRequest ){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_salesMan", salesMan);
    String s ="";
    try {
    Page<OrderSignfor> orderSignforslist=os.getOrderSingforList(searchParams, pageRequest);
    
    orderSignforslist.forEach(osfl->{
      osfl.getSalesMan().setUser(null);
      osfl.getSalesMan().setRegion(null);
      if(osfl.getCustomSignforTime()!=null&&osfl.getYewuSignforTime()!=null){
        osfl.setAging(DateUtil.getAging(osfl.getCustomSignforTime().getTime()-osfl.getYewuSignforTime().getTime()));
      }
    });
      s = JSON.toJSONString(orderSignforslist, SerializerFeature.DisableCircularReferenceDetect);
   } catch(Exception e){
      System.out.println(e.getMessage());
    }
    
    return s;
  }
  
  /**
   * 导出excel列表根据salesManId
   * @param salesMan
   * @param request
   * @param response
   */
  @RequestMapping("/export/{salesManId}")
  public void excelExportForSalesMan(@PathVariable("salesManId") SalesMan salesMan,HttpServletRequest request,
      HttpServletResponse response) {
    String[] gridTitles = { "店铺名称","订单号", "签收地点","签收时间","状态"};
    String[] coloumsKey = { "shopName", "orderNo", "yewuSignforGeopoint", "yewuSignforTime","orderStatus"};
    String type=request.getParameter("type");
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_salesMan", salesMan);
    List<OrderSignfor> list=os.findAll(searchParams);
    list.forEach(osfl->{
      osfl.getSalesMan().setUser(null);
      osfl.getSalesMan().setRegion(null);
      if(osfl.getCustomSignforTime() != null && osfl.getYewuSignforTime() != null){
        osfl.setAging(DateUtil.getAging(osfl.getCustomSignforTime().getTime()-osfl.getYewuSignforTime().getTime()));
      }
    });
    if("ywSignforDet".equals(type)){
      ExcelExport.doExcelExport(salesMan.getTruename()+"-揽收异常.xls", list, gridTitles, coloumsKey, request, response);
    }else{
      gridTitles=Arrays.copyOf(gridTitles, gridTitles.length+2);
      gridTitles[gridTitles.length-2]="送货时效";
      gridTitles[gridTitles.length-1]="支付方式";
      
      coloumsKey=Arrays.copyOf(coloumsKey, coloumsKey.length+2);
      coloumsKey[coloumsKey.length-2]="aging";
      coloumsKey[coloumsKey.length-1]="orderPayType";
      ExcelExport.doExcelExport(salesMan.getTruename()+"-客户签收异常.xls", list, gridTitles, coloumsKey, request, response);
    }
  }
  /**
   * 订单信息详情
   * @param orderSignfor
   * @param request
   * @param model
   * @return
   */
  @RequestMapping(value="/toAbnormalDet/{id}",method=RequestMethod.GET)
  public String toAbnormalDet(@PathVariable("id") OrderSignfor orderSignfor, 
      HttpServletRequest request, Model model ){
    String page="";
    String type=request.getParameter("type");
    String abnormal=request.getParameter("abnormal");
    orderSignfor.setYwSignforTag(abnormal);
    Assess assesse = findAssessBySalesMan(orderSignfor.getSalesMan());
    model.addAttribute("orderSignfor", orderSignfor);
    model.addAttribute("salesMan", orderSignfor.getSalesMan());
    model.addAttribute("assess", assesse);
    
    
    if("ywSignfor".equals(type)){
      page="abnormal/abnormal_det_yw";
    }else{
      page="abnormal/abnormal_det_member";
    }
    return page;
  }
  
  /**
   * 通过salesMan查询Assess考核阶段
   * @param salesMan
   * @return
   */
  public Assess findAssessBySalesMan(SalesMan salesMan) {
    List<Assess> assesseList = assessService.findBysalesman(salesMan);
    Assess assess = new Assess();
    Map<String, Assess> assessMaxStage=new HashMap<String, Assess>();
    assessMaxStage.put("max", assess);
    try {
      if(assesseList.size()>0){
        assesseList.forEach(assess_ -> {
          int max=Integer.parseInt(assessMaxStage.get("max").getAssessStage()==null ? "0" : assessMaxStage.get("max").getAssessStage());
          if(Integer.parseInt(assess_.getAssessStage()) > max){
            assessMaxStage.put("max", assess_);
            System.out.println(assess_.getAssessStage());
          }
        });
      }
    } catch (Exception e) {
      // TODO: handle exception 
      e.printStackTrace();
    }
    return assessMaxStage.get("max");
  }
  
}

