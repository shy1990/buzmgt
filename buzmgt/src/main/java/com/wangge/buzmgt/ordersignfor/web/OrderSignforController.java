package com.wangge.buzmgt.ordersignfor.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.util.DateUtil;

@Controller
@RequestMapping(value="/ordersignfor")
public class OrderSignforController {
  
  @Resource
  private OrderSignforService os; 
  
  @Resource
  private RegionService regionService;
  
  private static final String SEARCH_OPERTOR = "sc_";
  
//  @SuppressWarnings("deprecation")
//  @RequestMapping(value="/list",method=RequestMethod.GET)
//  @ResponseBody
//  public  ResponseEntity<Page<OrderSignfor>> OrderSignforList(HttpServletRequest request,
//      @PageableDefault(page = 0,size=10,sort={"creatTime"},direction=Direction.DESC) Pageable pageRequest ){
//    String type=request.getParameter("type");
//    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
//    OrderSignfor osf=new OrderSignfor();
//    Page<OrderSignfor> orderSignforslist=null;
//    if("ywOrderSignfor".equals(type)){
//      List<OrderSignfor> list=os.findAll(searchParams);
//      
//      List<OrderSignfor> ywlist=new ArrayList<OrderSignfor>();
//      //TODO  某一个时间点（如9:00）拓展后期后台设置;
//      String timesGap = "9:00";
//      String[] times=timesGap.split(":");
//      int total=0;
//      for(OrderSignfor order : list){
//        Date ctrateTime = order.getCreateTime();
//        Date checkDate=DateUtil.moveDate(ctrateTime, 1);
//        checkDate.setHours(Integer.parseInt(times[0]));
//        checkDate.setMinutes(Integer.parseInt(times[1]));
//        System.out.println(DateUtil.date2String(checkDate));
//        Date yewuSignforTime = order.getYewuSignforTime();
//        
//        if(yewuSignforTime.getTime()-checkDate.getTime()>0){
//          ywlist.add(order);
//          total++;
//        }
//      }
//      orderSignforslist = new PageImpl<OrderSignfor>(ywlist,pageRequest,total);
//      
//      
//    }else{
//      String status="1";
////      if((!"".equals(startTime)||startTime!=null)&&(!"".equals(endTime)||endTime!=null)){
////        orderSignforslist =os.findByCustomSignforExceptionAndCreatTimeBetween(status, startTime, endTime, pageRequest);
////      }else{
////        orderSignforslist =os.findByCustomSignforException(status,pageRequest);
////      }
//      orderSignforslist=os.getOrderSingforList(searchParams, pageRequest);
//    }
//    
//    return new ResponseEntity<Page<OrderSignfor>>(orderSignforslist,HttpStatus.OK) ;
//  }
//  
  @SuppressWarnings("deprecation")
  @RequestMapping(value="/list",method=RequestMethod.GET)
  @ResponseBody
  public  String OrderSignforList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"creatTime"},direction=Direction.DESC) Pageable pageRequest ){
    String type=request.getParameter("type");
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    OrderSignfor osf=new OrderSignfor();
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
      System.out.println(number+":"+size);
      for(OrderSignfor order : list){
        Date ctrateTime = order.getCreateTime();
        Date checkDate=DateUtil.moveDate(ctrateTime, 1);
        checkDate.setHours(Integer.parseInt(times[0]));
        checkDate.setMinutes(Integer.parseInt(times[1]));
        Date yewuSignforTime = order.getYewuSignforTime();
        
        if(yewuSignforTime.getTime()-checkDate.getTime()>0){
          //分页
          if(number*size <= total && total < (number+1)*size){
            ywlist.add(order);
          }
          total++;
        }
      }
      orderSignforslist = new PageImpl<OrderSignfor>(ywlist,pageRequest,total);
      
      
    }else{
      String status="1";
//      if((!"".equals(startTime)||startTime!=null)&&(!"".equals(endTime)||endTime!=null)){
//        orderSignforslist =os.findByCustomSignforExceptionAndCreatTimeBetween(status, startTime, endTime, pageRequest);
//      }else{
//        orderSignforslist =os.findByCustomSignforException(status,pageRequest);
//      }
      orderSignforslist=os.getOrderSingforList(searchParams, pageRequest);
    }
    String s ="";
    orderSignforslist.forEach(osfl->{
      osfl.getSalesMan().setUser(null);
      osfl.getSalesMan().setRegion(null);
      osfl.setAging(osfl.getCustomSignforTime().getTime()-osfl.getYewuSignforTime().getTime());
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
    //获取组织结构
   /* Organization organization=((User) SecurityUtils.getSubject().getPrincipal()).getOrganization(); 
    if("1".equals(organization.getLev())){
      
    }
    page = page== null ? 1 : page<1 ? 1 : page;
    int pageSize = 10;
    PageRequest pageRequest = SortUtil.buildPageRequest(page,pageSize ,"role");
    
    Page<OrderSignfor> list = os.getOrderSingforList(pageRequest);*/
    Long orderTotal=os.findCount();//订单总数
//    Long memberSignforCount=os.getMemberSignforCount();//客户签收异常条数
    model.addAttribute("totalCount",orderTotal);
//    model.addAttribute("memberCount",memberSignforCount);
    return "abnormal/abnormal_list";
  }
  
}
