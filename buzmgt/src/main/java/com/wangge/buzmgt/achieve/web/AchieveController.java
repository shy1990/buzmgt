package com.wangge.buzmgt.achieve.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.wangge.buzmgt.achieve.entity.Achieve;
import com.wangge.buzmgt.achieve.server.AchieveServer;
import com.wangge.buzmgt.income.main.service.MainPlanService;

/**
 * 
* @ClassName: AchieveController
* @Description: 达量控制层
* @author ChenGuop
* @date 2016年8月25日 下午3:05:16
*
 */
@Controller
@RequestMapping("/achieve")
public class AchieveController {

  @Autowired
  private AchieveServer achieveServer;
  
  @Autowired
  private MainPlanService mainPlanService;
  
  private static final String SEARCH_OPERTOR = "sc_";
  
  /**
   * 
  * @Title: showAchieveList 
  * @Description: 展示达量列表
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  @RequestMapping(value="/show",method=RequestMethod.GET)
  public String showAchieveList(String planId,Model model){
    model.addAttribute("planId", planId);
    model.addAttribute("machineTypes", mainPlanService.getAllMachineType());
    model.addAttribute("machineTypes", mainPlanService.getAllMachineType());
    return "achieve/achieve_list";
  }
  
  /**
   * 
  * @Title: findByMachineTypeAndPlanId 
  * @Description: 根据方案Id查询对应的达量分页, 参数格式：sc_EQ_xxxx=yyyy;
  * @param @param request
  * @param @param planId
  * @param @param pageable
  * @param @return    设定文件 
  * @return Page<Achieve>    返回类型 
  * @throws
   */
  @RequestMapping(value="/{planId}",method=RequestMethod.GET)
  @ResponseBody
  public Page<Achieve> findByMachineTypeAndPlanId(HttpServletRequest request,
      @PathVariable(value="planId")String planId,
      @PageableDefault(page=0, size=10, sort={"createDate"},direction=Direction.DESC)Pageable pageable){
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_planId", planId);
    return achieveServer.findAll(searchParams, pageable);
  }
}
