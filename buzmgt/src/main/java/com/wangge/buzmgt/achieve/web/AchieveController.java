package com.wangge.buzmgt.achieve.web;

import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.achieve.entity.Achieve;
import com.wangge.buzmgt.achieve.server.AchieveServer;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.server.MachineTypeServer;

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
  @Autowired
  private MachineTypeServer machineTypeServer;

  private static final String SEARCH_OPERTOR = "sc_";

  /**
   * 
  * @Title: showAchieveList
  * @Description: 展示达量列表
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String showAchieveList(String planId, Model model) {
    model.addAttribute("planId", planId);
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
  @RequestMapping(value = "/{planId}", method = RequestMethod.GET)
  @ResponseBody
  public Page<Achieve> findByMachineTypeAndPlanId(HttpServletRequest request,
      @PathVariable(value = "planId") String planId,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_planId", planId);
    return achieveServer.findAll(searchParams, pageable);
  }

  @RequestMapping(value="add",method=RequestMethod.GET)
  public String showAddAchieve(@RequestParam String planId, Model model){
    
    List<MachineType> machineTypes = machineTypeServer.findAll();
    model.addAttribute("planId",planId);
    model.addAttribute("machineTypes",machineTypes);
    return "achieve/achieve_add2";
  }
  /**
   * 
  * @Title: submitUserList_4 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param achieve
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject addAchieve(@RequestBody Achieve achieve) {
    JSONObject json =new JSONObject();
    
    try {
      achieve.setCreateDate(new Date());
      achieveServer.save(achieve);
      json.put("result", "success"); 
      json.put("message", "操作成功"); 
      
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.put("result", "failure"); 
      json.put("message", "网络异常，稍后重试！"); 
      return json;
    }
    return json;
  }
}
