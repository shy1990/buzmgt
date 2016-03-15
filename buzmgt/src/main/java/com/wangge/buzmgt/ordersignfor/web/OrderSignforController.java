package com.wangge.buzmgt.ordersignfor.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.hibernate.annotations.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.util.SortUtil;

@Controller
@RequestMapping(value="/ordersignfor")
public class OrderSignforController {
  
  @Resource
  private OrderSignforService os; 
  
  @Resource
  private RegionService regionService;
  
  private static final String SEARCH_OPERTOR = "sc_";
  
  @RequestMapping(value="/list",method=RequestMethod.GET)
  @ResponseBody
  public  ResponseEntity<Page<OrderSignfor>> OrderSignforList(HttpServletRequest request,
      @PageableDefault(page = 0,size=10,sort={"creatTime"},direction=Direction.DESC) Pageable pageRequest,
      @RequestBody JSONObject json){
    String type=request.getParameter("type");
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    OrderSignfor osf=new OrderSignfor();
    Page<OrderSignfor> orderSignforslist=os.getOrderSingforList(searchParams, pageRequest);
    
    if("ywOrderSignfor".equals(type)){
      //TODO  某一个时间段（如9:00）拓展后期后台设置;
      String timesGap = "24";
//      orderSignforslist =os.getYwSignforList(osf, page, startTime, endTime, timesGap);
    }else{
      String status="1";
//      if((!"".equals(startTime)||startTime!=null)&&(!"".equals(endTime)||endTime!=null)){
//        orderSignforslist =os.findByCustomSignforExceptionAndCreatTimeBetween(status, startTime, endTime, pageRequest);
//      }else{
//        orderSignforslist =os.findByCustomSignforException(status,pageRequest);
//      }
    }
    
    return new ResponseEntity<Page<OrderSignfor>>(orderSignforslist,HttpStatus.OK) ;
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
