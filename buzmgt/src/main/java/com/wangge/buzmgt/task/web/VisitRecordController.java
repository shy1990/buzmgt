package com.wangge.buzmgt.task.web;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.vo.VisitVo;
import com.wangge.buzmgt.task.entity.Visit;
import com.wangge.buzmgt.task.service.VisitRecordService;
import com.wangge.buzmgt.task.service.VisitTaskService;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;

@Controller
@RequestMapping("/visit")
public class VisitRecordController {
  @Resource
  private ManagerService managerService;
  @Resource
  private RegionService regionService;
  @Resource
  private VisitRecordService vrs;
  @Resource
  private AssessService assessService;
  @Resource
  private SalesManService salesManService;
  
  
  @RequestMapping("/visitRecordPage")
  public String visitRecordPage(Model model){
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    model.addAttribute("regionName", manager.getRegion().getName());
    model.addAttribute("regionId", manager.getRegion().getId());
    return "visit/visit_record_list";
  }
  
  @ResponseBody
  @RequestMapping("/visitRecordList")
  public Page<VisitVo> visitRecordList(HttpServletRequest request){
    String regionid = request.getParameter("regionid");
    int pageNum = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "0");
    int limit = Integer.parseInt(request.getParameter("size"));
    String begin = request.getParameter("beginTime");
    String end = request.getParameter("endTime");
    Region region=new Region();
    if(null!=regionid){
      region =regionService.getRegionById(regionid);
    }
    Page<VisitVo> list = vrs.getVisitData(pageNum,limit,region.getName(),begin,end);
    return list;
  }
  
  @ResponseBody
  @RequestMapping("/totalVisit")
  public int visitTotal(String regionid,String beginTime,String endTime){
    Region region=new Region();
    if(null!=regionid){
      region =regionService.getRegionById(regionid);
    }
    int total = vrs.getTotalVisit(region.getName(),beginTime,endTime);
    return total;
  }
  
  @RequestMapping("/visitRecordYWPage")
  public String visitRecordYWPage(String userId,Model model){
    SalesMan sm = salesManService.getSalesmanByUserId(userId);
    model.addAttribute("sm", sm);
    return "visit/visit_record_list_yw";
  }
  
  @ResponseBody
  @RequestMapping("/visitRecordYWList")
  public Page<Visit> visitRecordYWList(HttpServletRequest request){
    String userId = request.getParameter("userId");
    int pageNum = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "0");
    int limit = Integer.parseInt(request.getParameter("size"));
    String begin = request.getParameter("beginTime");
    String end = request.getParameter("endTime");
    Page<Visit> list = vrs.getVisitYWData(pageNum,limit,userId,begin,end);
    return list;
  }
  
  @RequestMapping("/visitDetPage")
  public String visitDetPage(String visitId,Model model){
    Visit visit = vrs.findById(Long.parseLong(visitId));
    model.addAttribute("visit", visit);
    return "visit/visit_det";
  }
}
