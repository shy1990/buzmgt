package com.wangge.buzmgt.assess.web;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.entity.Assess.AssessStatus;
import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.saojie.service.SaojieService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;

/**
  * ClassName: AssessController <br/> 
  * Function: TODO ADD FUNCTION. <br/> 
  * Reason: TODO ADD REASON(可选). <br/> 
  * date: 2016年2月17日 下午5:11:07 <br/> 
  * @author peter 
  * @version  1.1
  * @since JDK 1.8
 */
@Controller
@RequestMapping("/assess")
public class AssessController {
  @Resource
  private SalesManService salesManService;
  @Resource
  private RegionService regionService;
  @Resource
  private SaojieService saojieService;
  @Resource
  private AssessService assessService;
  @Resource
  private ManagerService managerService;
  /**
   * 
    * toAssessSet:(跳转到考核设置页面). <br/> 
    * @author peter 
    * @param id
    * @param model
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping("/toAssessSet") 
  public String toAssessSet(@RequestParam("id") String id , Model model){
    SalesMan salesman = salesManService.findByUserId(id.trim());
    model.addAttribute("salesman", salesman);
    model.addAttribute("userId",salesman.getId());
    model.addAttribute("areaname",salesman.getRegion().getName());
    model.addAttribute("regionData",regionService.findByRegion(salesman.getRegion().getId()));
    return "kaohe/kaohe_set";
  }
  
  @RequestMapping(value = "/gainAuditTown",method = RequestMethod.POST)
  @ResponseBody
  public List<Region> gainAuditTown(@RequestParam("id") String id){
    System.out.println(id);
    List<Region> list = saojieService.findRegionById(id);
    /*Iterator<Region> regIter = list.iterator();
    while(regIter.hasNext()){
      Region region = regIter.next();
      Saojie saojie = saojieService.findByregion(region);
      if(saojie != null && region.getId().equals(saojie.getRegion().getId())){
        regIter.remove();
      }
    }*/
    return list;
  }
  
  /**
   * 
    * saveAssess:(保存考核设置). <br/> 
    * 
    * @author Administrator 
    * @param assess
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/saveAssess/{userId}",method = RequestMethod.POST)
  public String saveAssess(Assess assess,@PathVariable(value = "userId")SalesMan salesman){
    assess.setSalesman(salesman);
    assess.setStatus(AssessStatus.PENDING);
    assess.setAssessStage("1");
    assessService.saveAssess(assess);
    System.out.println(assess.getSalesman().getId());
    return "redirect:/assess/assessList";
  }
  
  @RequestMapping("/assessList")
  public String assessList(String assessList, Model model,Assess assess){
    int pageNum = 0;
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    if(null!=manager.getRegion().getCoordinates()){
      model.addAttribute("pcoordinates", manager.getRegion().getCoordinates());
    }
    Page<Assess> list = assessService.getAssessList(assess,pageNum,manager.getRegion().getName());
    System.out.println("=-----"+list.getTotalElements());
    model.addAttribute("list", list);
    model.addAttribute("assessList", assessList);
     model.addAttribute("regionName", manager.getRegion().getName());
     model.addAttribute("regionId", manager.getRegion().getId());
    
    return "kaohe/kaohe_list";
  } 
  
  /**
   * 
    * getAssessList:(考核列表(条件)). <br/> 
    * 
    * @author peter 
    * @param model
    * @param assess
    * @param assessStatus
    * @param page
    * @param requet
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping(value = "/getAssessList")
  public  String  getAssessList(Model model,Assess assess,String regionid,String regionName, String assessStatus,String page, HttpServletRequest requet){
        int pageNum = Integer.parseInt(page != null ? page : "0");
        if(AssessStatus.PENDING.getName().equals(assessStatus) ){
          assess.setStatus(AssessStatus.PENDING);
        }else if(AssessStatus.FAIL.getName().equals(assessStatus)){
          assess.setStatus(AssessStatus.FAIL);
        }
        Region region=new Region();
        if(null!=regionid){
          region =regionService.getRegionById(regionid);
          if(null!=region.getCoordinates()){
            model.addAttribute("pcoordinates", region.getCoordinates());
          }
          model.addAttribute("regionName", region.getName());
          model.addAttribute("regionId", region.getId());
        }
       if(null!=regionName){
         region =regionService.findByNameLike(regionName);
         if(null!=region.getCoordinates()){
           model.addAttribute("pcoordinates", region.getCoordinates());
         }
         model.addAttribute("regionName", region.getName());
         model.addAttribute("regionId", region.getId());
       }
       if(null != assess.getSalesman()){
         model.addAttribute("truename",assess.getSalesman().getTruename());
         model.addAttribute("jobNum",assess.getSalesman().getJobNum());
       }
        
    Page<Assess> list = assessService.getAssessList(assess,pageNum,region.getName());
    model.addAttribute("list", list);
    model.addAttribute("assessStatus",assessStatus);
    return   "kaohe/kaohe_list";
  }
  
  @RequestMapping("/toAccessDet")
  public String toAccessDet(String salesmanId,String asssessid,Model model){
    SalesMan salesman = salesManService.findByUserId(salesmanId.trim());
    Assess assess=assessService.findAssess(Long.parseLong(asssessid.trim()));
    model.addAttribute("assess", assess);
    model.addAttribute("salesman", salesman);
    List<Region> list = saojieService.findRegionById(salesmanId.trim());
    model.addAttribute("regionList", list);
    Date startDate=assess.getAssessTime();
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(startDate.getTime());
    c.add(Calendar.DATE, Integer.parseInt(assess.getAssessCycle()));//周期后的日期
    Date endDate= new Date(c.getTimeInMillis());
    model.addAttribute("startDate", DateUtil.date2String(startDate));
    model.addAttribute("endDate", DateUtil.date2String(endDate));
    return "kaohe/kaohe_det";
  } 
  
  
}
