package com.wangge.buzmgt.assess.web;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.entity.Assess.AssessStatus;
import com.wangge.buzmgt.assess.entity.AssessTime;
import com.wangge.buzmgt.assess.service.AssessService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.saojie.service.SaojieService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.vo.OrderVo;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesmanStatus;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.JsonResponse;
import com.wangge.json.JSONFormat;

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
  
  /**
   * 
    * gainAuditTown:(获取考核阶段设置的地区). <br/> 
    * 
    * @author Administrator 
    * @param id
    * @return 
    * @since JDK 1.8
   */
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
  public String saveAssess(Assess assess,@PathVariable(value = "userId")SalesMan salesman,int stage,HttpServletRequest request){
    assess.setSalesman(salesman);
    assess.setStatus(AssessStatus.PENDING);
      /*Assess a = assessService.findByStageAndSalesman("1",assess.getSalesman().getId());//查第一阶段的考核
      assess.setActiveNum(assess.getActiveNum()+a.getActiveNum());//累加第一阶段活跃客户
      assess.setOrderNum(assess.getOrderNum()+a.getOrderNum());//累加第一阶段提货量
*/    assess.setAssessStage(String.valueOf(stage));
    Date startDate=assess.getAssessTime();
    Calendar c = Calendar.getInstance();
    c.setTimeInMillis(startDate.getTime());
    c.add(Calendar.DATE, Integer.parseInt(assess.getAssessCycle()));//周期后的日期
    Date endDate= new Date(c.getTimeInMillis());
    assess.setAssessEndTime(endDate);
    assess.setAssesszh(getRegionName(assess.getAssessArea()));
    assessService.saveAssess(assess);
    if("1".equals(assess.getAssessStage())){
      salesman.setStatus(SalesmanStatus.kaifa);
    }
    salesman.setAssessStage(assess.getAssessStage());
    salesManService.addSalesman(salesman);
    return "redirect:/assess/assessList";
  }
  
  /**
   * 
    * assessList:(获取考核列表). <br/> 
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
    int size = 10;
    Subject subject = SecurityUtils.getSubject();
    User user=(User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    if(null!=manager.getRegion().getCoordinates()){
      model.addAttribute("pcoordinates", manager.getRegion().getCoordinates());
    }
    Page<Assess> list = assessService.getAssessList(assess,pageNum,size,manager.getRegion().getName());
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
  public  String  getAssessList(Model model,Assess assess,String regionid,String regionName, String assessStatus,String page, String size, HttpServletRequest requet){
        int pageNum = Integer.parseInt(page != null ? page : "0");
        int sizeNum = Integer.parseInt(size !=null ? size : "10");
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
        
    Page<Assess> list = assessService.getAssessList(assess,pageNum,sizeNum,region.getName());
    model.addAttribute("list", list);
    model.addAttribute("assessStatus",assessStatus);
    return   "kaohe/kaohe_list";
  }
  
  /**
   * 
    * toAccessDet:(跳转到考核详情页). <br/> 
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
  public String toAccessDet(String salesmanId,@RequestParam("asssessid") Assess assess,String regionid,String baifen,Integer active,Integer orderNum,Model model){
    SalesMan salesman = salesManService.findByUserId(salesmanId.trim());
//    Assess assess=assessService.findAssess(Long.parseLong(asssessid.trim()));
    model.addAttribute("assess", assess);
    model.addAttribute("salesman", salesman);
    List<Region> list = saojieService.findRegionById(salesmanId.trim());
    model.addAttribute("regionList", list);
    model.addAttribute("regionId",regionid != null ? regionid : "");
    model.addAttribute("startDate", DateUtil.date2String(assess.getAssessTime()));
    model.addAttribute("endDate", DateUtil.date2String(assess.getAssessEndTime()));
    try {
      int timing = DateUtil.daysBetween(assess.getAssessTime(), assess.getAssessEndTime());
      model.addAttribute("timing",timing);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    if("2".equals(assess.getAssessStage())){
      Assess firstStage = assessService.findByStageAndSalesman("1", salesmanId.trim());
      model.addAttribute("passType",firstStage.getPassType());
    }
    model.addAttribute("percent",Integer.parseInt("".equals(baifen) ? "0" :baifen));
    model.addAttribute("active",active);
    model.addAttribute("orderNum",orderNum);
    return "kaohe/kaohe_det";
  } 
  
  @ResponseBody
  @RequestMapping(value = "/getOrderStatistics",method = RequestMethod.GET)
  public Page<OrderVo> getOrderStatistics(String salesmanId,String regionId,String begin,String end,String page,String size){
    int pageNum = Integer.parseInt(page != null ? page : "0");
    int limit = Integer.parseInt(size);
    Page<OrderVo> statistics = assessService.getOrderStatistics(salesmanId.trim(),regionId,pageNum,begin,end,limit);
    return statistics;
  }
  
  /**
   * 
    * passed:(考核通过进入维护模式). <br/> 
    * 
    * @author peter 
    * @param salesmanId
    * @return 
    * @since JDK 1.8
   */
  @ResponseBody
  @RequestMapping(value = "/passed",method = RequestMethod.GET)
  public String passed(String salesmanId){
    SalesMan salesman = salesManService.findById(salesmanId);
    salesman.setStatus(SalesmanStatus.weihu);//修改业务状态为维护
    salesManService.addSalesman(salesman);
    return "ok";
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
  public String toAssessStage(@RequestParam("id") String id , Model model,@RequestParam("assessId") Assess assess,String percent){
    SalesMan salesman = salesManService.findByUserId(id.trim());
    List<Assess> list = assessService.findBysalesman(salesman);
    int stage = assessService.gainMaxStage(id.trim());
    int per = Integer.parseInt(percent);
    if(stage == 1 && per < 100){//第一阶段且不达标时标记为手工通过
      assess.setPassType(1);
    }
    if(stage == 2 && per < 100){//第二阶段且不达标时通过标记为手工通过
      assess.setPassType(1);
    }
    assess.setStatus(AssessStatus.AGREE);
    assessService.saveAssess(assess);
    String regionId = salesman.getRegion().getId();
    regionId = regionId.substring(0, 2);
    Region region = regionService.getRegionById(regionId+"0000");
    AssessTime at = assessService.findAssessTimeByRegion(region);//按省份查找次数
    model.addAttribute("assessStageSum",at.getAssessStageSum());
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
      
      /**
       * 跳转到考核次数设置页面
       *
       * @return
       */
      @RequestMapping(value = "/assessTimeList", method = RequestMethod.GET)
      public String toAssessTimeList(Model model) {
        List<Region> regionList = regionService.findByTypeOrderById(RegionType.PARGANA);
        model.addAttribute("regionList",regionList);
        return "kaohe/check_time";
      }
      
      @RequestMapping(value = "/saveAssessTime/{regionId}", method = RequestMethod.POST)
      public
      @ResponseBody
      String saveBankCard(@PathVariable(value = "regionId")Region region,Integer times) {
        AssessTime at = assessService.findAssessTimeByRegion(region);
        if(at == null){
          at = new AssessTime();
        }
        at.setAssessStageSum(times);
        at.setCreateTime(new Date());
        at.setRegion(region);
        at = assessService.saveAssessTime(at);
        return "ok";
      }

      /**
       * 分页查询考核次数设置信息
       * 用ResponseEntity<JsonResponse>返回的的方式
       * resetful方式
       *
       * @return
       */
      @RequestMapping(value = "/assessTimes", method = RequestMethod.GET)
      @JSONFormat(filterField={"Region.children","Region.parent"})
      public JsonResponse getAssessTimes(
              @RequestParam(value = "page", defaultValue = "0") Integer page,
              @RequestParam(value = "size", defaultValue = "20") Integer size) {
          Sort sort = new Sort(Direction.DESC, "createTime");
          Pageable pageable = new PageRequest(page, size, sort);
          Page<AssessTime> result = assessService.findAll(pageable);
          JsonResponse json = new JsonResponse(result);
          return json;
      }
      
}
