package com.wangge.buzmgt.assess.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.wangge.buzmgt.sys.vo.OrderVo;
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
  @Autowired
  private EntityManagerFactory emf;
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
    List<Assess> list = assessService.findBysalesman(salesman);
    model.addAttribute("list", list);
    model.addAttribute("salesman", salesman);
    model.addAttribute("userId",salesman.getId());
    model.addAttribute("areaname",salesman.getRegion().getName());
    model.addAttribute("regionData",regionService.findByRegion(salesman.getRegion().getId()));
    List<Object> listRegion =  regionList(salesman.getRegion().getId());
    model.addAttribute("listAdminDivision", listRegion);
    model.addAttribute("pcoordinates", salesman.getRegion().getCoordinates());
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
  public String saveAssess(Assess assess,@PathVariable(value = "userId")SalesMan salesman,int stage){
    assess.setSalesman(salesman);
    assess.setStatus(AssessStatus.PENDING);
    if(stage == 0){
      assess.setAssessStage("1");
    }
    if(stage == 1){
      assess.setAssessStage("2");
    }
    if(stage == 2){
      assess.setAssessStage("3");
    }
    Date startDate=assess.getAssessTime();
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(startDate.getTime());
    c.add(Calendar.DATE, Integer.parseInt(assess.getAssessCycle()));//周期后的日期
    Date endDate= new Date(c.getTimeInMillis());
    assess.setAssessEndTime(endDate);
    assess.setAssesszh(getRegionName(assess.getAssessArea()));
    assessService.saveAssess(assess);
    salesman.setAssessStage(assess.getAssessStage());
    salesManService.addSalesman(salesman);
    System.out.println(assess.getSalesman().getId());
    return "redirect:/assess/assessList";
  }
  
  /**
   * 
    * assessList:(这里用一句话描述这个方法的作用). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param assessList
    * @param model
    * @param assess
    * @return 
    * @since JDK 1.8
   */
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
    model.addAttribute("list", list);
    model.addAttribute("total",list.getTotalElements());
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
  
  /**
   * 
    * toAccessDet:(这里用一句话描述这个方法的作用). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param salesmanId
    * @param assess
    * @param page
    * @param regionid
    * @param begin
    * @param end
    * @param baifen
    * @param active
    * @param orderNum
    * @param model
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping("/toAccessDet")
  public String toAccessDet(String salesmanId,@RequestParam("asssessid") Assess assess,String page,String regionid,String begin,String end,String baifen,Integer active,Integer orderNum,Model model){
    int pageNum = Integer.parseInt(page != null ? page : "0");
    SalesMan salesman = salesManService.findByUserId(salesmanId.trim());
//    Assess assess=assessService.findAssess(Long.parseLong(asssessid.trim()));
    model.addAttribute("assess", assess);
    model.addAttribute("salesman", salesman);
    List<Region> list = saojieService.findRegionById(salesmanId.trim());
    model.addAttribute("regionList", list);
    Page<OrderVo> statistics = assessService.getOrderStatistics(salesmanId.trim(),regionid,pageNum,begin,end);
    model.addAttribute("statistics",statistics);
    model.addAttribute("regionId",regionid != null ? regionid : "");
    model.addAttribute("startDate", DateUtil.date2String(assess.getAssessTime()));
    model.addAttribute("endDate", DateUtil.date2String(assess.getAssessEndTime()));
    model.addAttribute("begin",begin);
    model.addAttribute("end",end);
    try {
      int timing = DateUtil.daysBetween(assess.getAssessTime(), assess.getAssessEndTime());
      model.addAttribute("timing",timing);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    model.addAttribute("percent",baifen);
    model.addAttribute("active",active);
    model.addAttribute("orderNum",orderNum);
    return "kaohe/kaohe_det";
  } 
  
  /**
   * 
    * toAssessSet:(跳转到考核设置页面). <br/> 
    * @author peter 
    * @param id
    * @param model
    * @return 
    * @since JDK 1.8
   */
  @RequestMapping("/toAssessStage") 
  public String toAssessStage(@RequestParam("id") String id , Model model,@RequestParam("assessId") Assess assess){
    SalesMan salesman = salesManService.findByUserId(id.trim());
    List<Assess> list = assessService.findBysalesman(salesman);
    int stage = assessService.gainMaxStage(id.trim());
    model.addAttribute("stage",stage);
    model.addAttribute("list", list);
    model.addAttribute("salesman", salesman);
    model.addAttribute("userId",salesman.getId());
    return "kaohe/kaohe_set_stage";
  }
  
    /**
     * 
      * regionList:(这里用一句话描述这个方法的作用). <br/> 
      * TODO(这里描述这个方法适用条件 – 可选).<br/> 
      * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
      * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
      * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
      * 
      * @author Administrator 
      * @param regionId
      * @return 
      * @since JDK 1.8
     */
      public List<Object> regionList(String regionId) {
          
          EntityManager em = emf.createEntityManager();
          List<Object> objecArraytList = new ArrayList<Object>();
          
          try {  
            // 定义SQL
            String sql = "select id,name from SJZAIXIAN.SJ_TB_REGIONS where pid=(select id from SJZAIXIAN.SJ_TB_REGIONS where id2='"+regionId+"')";
            // 创建原生SQL查询QUERY实例
            Query query = em.createNativeQuery(sql);
            // 每一个对象数组存的是相应的实体属性
            objecArraytList = query.getResultList();
      //      for (int i = 0; i < objecArraytList.size(); i++) {
      //        Map<String, Object> map = new HashMap<String, Object>();
      //        Object[] obj = (Object[]) objecArraytList.get(i);
      //        // 使用obj[0],obj[1],obj[2]...取出属性　
      //        map.put("id", obj[0]);
      //        map.put("name", obj[1]);
      //        ormap.add(map);
      //      }
            em.close();
          } catch (Exception e) {
            e.printStackTrace();
          }
          return objecArraytList;
        }
  
  
      public String getRegionName(String regionid){
        
        EntityManager em = emf.createEntityManager();
        List<Object> objecArraytList = new ArrayList<Object>();
        try { 
        // 定义SQL
        String sql = "select name from SJZAIXIAN.SJ_TB_REGIONS where id in ("+regionid+")";
        // 创建原生SQL查询QUERY实例
        Query query = em.createNativeQuery(sql);
        // 每一个对象数组存的是相应的实体属性
        objecArraytList = query.getResultList();
       System.out.println(objecArraytList.toString()); 
        em.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
        
        return objecArraytList.toString().substring(1, objecArraytList.toString().length()-1);
      }
      
      
}
