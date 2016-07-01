package com.wangge.buzmgt.task.web;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.vo.CustomerVo;
import com.wangge.buzmgt.task.entity.Visit;
import com.wangge.buzmgt.task.entity.Visit.VisitStatus;
import com.wangge.buzmgt.task.service.VisitTaskService;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;

@Controller
@RequestMapping("/task")
public class VisitTaskController {
  @Resource
  private ManagerService managerService;
  @Resource
  private RegionService regionService;
  @Resource
  private VisitTaskService visitTaskService;
  @Resource
  private AssessService assessService;
  @Resource
  private SalesManService salesManService;
  
  /**
   * 
    * visitList:(跳转到任务列表). <br/> 
    * @author peter 
    * @param visitList
    * @param model
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping("/visitList")
  public String visitList(String visitList,String regionid, Model model){
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    Region region=new Region();
    if(null!=regionid && !"".equals(regionid)){
      region =regionService.getRegionById(regionid);
      model.addAttribute("regionName", region.getName());
      model.addAttribute("regionId", region.getId());
    }else{
      model.addAttribute("regionName", manager.getRegion().getName());
      model.addAttribute("regionId", manager.getRegion().getId());
    }
    model.addAttribute("visitList", visitList);
    return "task/task_list";
  }
  
  /**
   * 
    * visitDataList:(获取任务列表的拜访数据). <br/> 
    * @author peter 
    * @param request
    * @return 
    * @since JDK 1.8
   */
  @ResponseBody
  @RequestMapping(value = "/visitDataList",method = RequestMethod.GET)
  public Page<Visit> visitDataList(HttpServletRequest request){
    String regionid = request.getParameter("regionid");
    int pageNum = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "0");
    int limit = Integer.parseInt(request.getParameter("size"));
    Region region=new Region();
    if(null!=regionid){
      region =regionService.getRegionById(regionid);
    }
    Page<Visit> list = visitTaskService.getVisitData(pageNum,limit,region.getName());
    return list;
  }
  
  /**
   * 
    * addVisitMap:(跳转到添加拜访的地图). <br/> 
    * @author peter 
    * @param addVisitMap
    * @param model
    * @return String
    * @since JDK 1.8
   */
  @RequestMapping("/addVisitMap")
  public String addVisitMap(String addVisitMap,String regionid, Model model){
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    Region region=new Region();
    if(null!=regionid && !"".equals(regionid)){
      region =regionService.getRegionById(regionid);
      model.addAttribute("regionName", region.getName());
      model.addAttribute("regionId", region.getId());
    }else{
      model.addAttribute("regionName", manager.getRegion().getName());
      model.addAttribute("regionId", manager.getRegion().getId());
    }
    model.addAttribute("addVisitMap", addVisitMap);
    return "task/task_add-map";
  }
  
  /**
   * 
    * shopMap:(获取店铺统计数据地图显示). <br/> 
    * @author peter 
    * @param request
    * @return Page
    * @since JDK 1.8
   */
  @ResponseBody
  @RequestMapping(value = "/shopMap",method = RequestMethod.GET)
  public List<CustomerVo> shopMap(HttpServletRequest request){
    String regionid = request.getParameter("regionid");
    int status = Integer.parseInt(request.getParameter("status"));
    int condition = Integer.parseInt(request.getParameter("condition"));
    Region region=new Region();
    if(null!=regionid){
      region =regionService.getRegionById(regionid);
    }
    List<CustomerVo> list = visitTaskService.getshopMap(region.getName(),status,condition);
    return list;
  }
  
  /**
   * 
    * gainPoint:(获取地区坐标点). <br/> 
    * 
    * @author peter 
    * @param regionid
    * @param model
    * @return 
    * @since JDK 1.8
   */
  @ResponseBody
  @RequestMapping("/gainPoint")
  public JSONObject gainPoint(String regionid, Model model){
    JSONObject json = new JSONObject();
    Region region=new Region();
    if(null!=regionid){
      region =regionService.getRegionById(regionid);
      if(null!=region.getCoordinates()){
        json.put("pcoordinates", region.getCoordinates());
      }
    }
    return json;
  }
  
  /**
   * 
    * addVisitList:(跳转到添加拜访的列表). <br/> 
    * @author peter 
    * @param addVisitList
    * @param model
    * @return String
    * @since JDK 1.8
   */
  @RequestMapping("/addVisitList")
  public String addVisitList(String addVisitList,String regionid,Model model){
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    model.addAttribute("addVisitList", addVisitList);
    Region region=new Region();
    if(null!=regionid && !"".equals(regionid)){
      region =regionService.getRegionById(regionid);
      model.addAttribute("regionName", region.getName());
      model.addAttribute("regionId", region.getId());
    }else{
      model.addAttribute("regionName", manager.getRegion().getName());
      model.addAttribute("regionId", manager.getRegion().getId());
    }
    return "task/task_add";
  }
  
  /**
   * 
    * shopList:(获取添加拜访店铺统计数据). <br/> 
    * @author peter 
    * @param request
    * @return Page
    * @since JDK 1.8
   */
  @ResponseBody
  @RequestMapping(value = "/shopList",method = RequestMethod.GET)
  public Page<CustomerVo> shopList(HttpServletRequest request){
    String regionid = request.getParameter("regionid");
    int status = Integer.parseInt(request.getParameter("status"));
    int condition = Integer.parseInt(request.getParameter("condition"));
    int pageNum = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "0");
    int limit = Integer.parseInt(request.getParameter("size"));
    Region region=new Region();
    if(null!=regionid){
      region =regionService.getRegionById(regionid);
    }
    Page<CustomerVo> list = visitTaskService.getshopList(pageNum,limit,region.getName(),status,condition);
    System.out.println(pageNum);
    System.out.println("limit:"+limit);
    return list;
  }
  
  /**
   * 
    * saveVisit:(保存添加拜访的任务). <br/> 
    * @author Administrator 
    * @param registId
    * @param taskName
    * @param expiredTime
    * @param userId
    * @return JSONObject
    * @since JDK 1.8
   */
  @ResponseBody
  @RequestMapping(value = "/saveVisit",method = RequestMethod.POST)
  public JSONObject saveVisit(String registId,String taskName,String expiredTime,String userId){
    JSONObject result = new JSONObject();
    try {
      RegistData rd = assessService.findRegistData(Long.parseLong(registId));
      SalesMan sm = salesManService.findById(userId);
      Visit visit = new Visit();
      visit.setRegistData(rd);
      visit.setSalesman(sm);
      visit.setBeginTime(new Date());
      if(StringUtils.isBlank(expiredTime)){
        visit.setExpiredTime(DateUtil.moveDate(new Date(), 2));
      }else{
        visit.setExpiredTime(DateUtil.string2Date(expiredTime));
      }
      visit.setStatus(VisitStatus.PENDING);
      visit.setTaskName(taskName);
      visitTaskService.addVisit(visit);
      System.out.println(visit.getBeginTime());
      result.put("status", "ok");
    } catch (NumberFormatException e) {
      e.printStackTrace();
      result.put("status", "notok");
    }
    return result;
  }
  
  /**
   * 
    * lastVisit:(查看该商铺当前是否有未完成的拜访任务). <br/> 
    * @author peter 
    * @param registId
    * @return JSONObject
    * @since JDK 1.8
   */
  @ResponseBody
  @RequestMapping("/lastVisit")
  public JSONObject lastVisit(String registId){
    JSONObject result = new JSONObject();
    String status = visitTaskService.findMaxLastVisit(Long.parseLong(registId));
    if(status != null && "0".equals(status)){
      result.put("status", "notok");
    }else{
      result.put("status", "ok");
    }
    return result;
  }
  
}
