package com.wangge.buzmgt.assess.web;

import java.util.List;

import javax.annotation.Resource;

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
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.SalesManService;

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
    return "redirect:/saojie/saojieList";
  }
}
