package com.wangge.buzmgt.achieveset.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.Achieve.AchieveStatusEnum;
import com.wangge.buzmgt.achieveset.service.AchieveService;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.service.MachineTypeService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.util.excel.ExcelExport;

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
  private LogService logService;
  @Autowired
  private AchieveService achieveServer;
  @Autowired
  private MainPlanService mainPlanService;
  @Autowired
  private MachineTypeService machineTypeServer;

  private static final String SEARCH_OPERTOR = "sc_";

  /**
   * 
   * @Title: showAchieveList 
   * @Description: 展示达量列表 @param 
   * @return 设定文件
   * @return String 返回类型
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
   * @Description: 根据方案Id查询对应的达量分页,参数格式：sc_EQ_xxxx=yyyy; 
   * @param @param request 
   * @param @param planId 
   * @param @param pageable 
   * @param @return 设定文件
   * @return Page<Achieve> 返回类型 
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

  /**
   * 
   * @Title: showAddAchieve 
   * @Description: 达量添加
   * @param @param planId 
   * @param @param model 
   * @param @return 设定文件 
   * @return String 返回类型
   *  @throws
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String showAddAchieve(@RequestParam String planId, Model model) {

    List<MachineType> machineTypes = machineTypeServer.findAll();
    model.addAttribute("planId", planId);
    model.addAttribute("machineTypes", machineTypes);
    return "achieve/achieve_add";
  }
  /**
   * 
  * @Title: addAchieve 
  * @Description: 添加数据 
  * @param @param achieve
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject addAchieve(@RequestBody Achieve achieve) {
    JSONObject json = new JSONObject();

    try {
      achieve.setCreateDate(new Date());
      achieveServer.save(achieve);
      logService.log(null, achieve.toString(), EventType.SAVE);
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

  /**
   * 
  * @Title: exportWaterOrderCash 
  * @Description:导出达量列表
  * @param @param request
  * @param @param response
  * @param @param pageRequest    设定文件 
  * @return void    返回类型 
  * @throws
   */
  @RequestMapping("/export")
  public void exportWaterOrderCash(HttpServletRequest request, HttpServletResponse response,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<Achieve> list = achieveServer.findAll(searchParams, pageRequest.getSort());
    String[] gridTitles_1 = { "品牌", "型号", "一阶段达量", "二阶段达量", "三阶段达量", "审核人", "审核状态", "方案开始日期", "方案结束日期", "佣金发放日期", "设置日期" };
    String[] coloumsKey_1 = { "brand.name", "good.name", "numberFirst", "numberSecond", "numberThird", "auditor", "status.name", "startDate",
        "endDate", "issuingDate", "createDate" };
    ExcelExport.doExcelExport("正在使用达量导出.xls", list, gridTitles_1, coloumsKey_1, request, response);
  }

  /**
   * 
  * @Title: showAchieveRecord 
  * @Description: 设置记录页面跳转
  * @param @param planId
  * @param @param model
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  @RequestMapping(value = "/record", method = RequestMethod.GET)
  public String showAchieveRecord(@RequestParam String planId, Model model) {
    model.addAttribute("planId", planId);
    return "achieve/achieve_record";
  }
  /**
   * 
   * @Title: showAchieveRecord 
   * @Description: 跳转设置记录审核页面
   * @param @param planId
   * @param @param model
   * @param @return    设定文件 
   * @return String    返回类型 
   * @throws
   */
  @RequestMapping(value = "/recordForAudit", method = RequestMethod.GET)
  public String showAchieveRecordForaudit(@RequestParam String planId, Model model) {
    String userId = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
    model.addAttribute("planId", planId);
    model.addAttribute("userId", userId);
    return "achieve/achieve_record_audit";
  }

  /**
   * 
   * @Title: getAchieve 
   * @Description: 查询单个Achieve：
   * @param @param achieve
   * @param @return    设定文件 
   * @return JSONObject    返回类型 
   * @throws
   */
  @RequestMapping(value = "getAchieve/{achieveId}", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getAchieve(@PathVariable("achieveId") Achieve achieve) {
    JSONObject json = new JSONObject();
    try {
      if(ObjectUtils.equals(achieve, null)){
        json.put("result", "failure");
        json.put("message", "删除信息有误！");
        return json;
      }
      
      json.put("result", achieve);
      json.put("status", "success");
      json.put("message", "操作成功");
    } catch (Exception e) {
      LogUtil.error("达量提成删除失败:" + e.getMessage(), e);
      json.put("status", "failure");
      json.put("message", "网络异常，稍后重试！");
      return json;
    }
    return json;
  }
  /**
   * 
  * @Title: delete 
  * @Description: 删除：更改flag状态DEL
  * @param @param achieve
  * @param @return    设定文件 
  * @return JSONObject    返回类型 
  * @throws
   */
  @RequestMapping(value = "/{achieveId}", method = RequestMethod.DELETE)
  @ResponseBody
  public JSONObject delete(@PathVariable("achieveId") Achieve achieve) {
    JSONObject json = new JSONObject();
    try {
      if(ObjectUtils.equals(achieve, null)){
        json.put("result", "failure");
        json.put("message", "删除信息有误！");
        return json;
      }
      achieve.setFlag(FlagEnum.DEL);
      achieveServer.save(achieve);
      logService.log(null, "逻辑删除flag=DEl", EventType.UPDATE);
      json.put("result", "success");
      json.put("message", "操作成功");
    } catch (Exception e) {
      LogUtil.error("达量提成删除失败:" + e.getMessage(), e);
      json.put("result", "failure");
      json.put("message", "网络异常，稍后重试！");
      return json;
    }
    return json;
  }
  /**
   * 
   * @Title: delete 
   * @Description: 审核：更改status状态
   * @param @param achieve
   * @param @return    设定文件 
   * @return JSONObject    返回类型 
   * @throws
   */
  @RequestMapping(value = "/{achieveId}", method = RequestMethod.PATCH)
  @ResponseBody
  public JSONObject auditAchieve(@PathVariable("achieveId") Achieve achieve,@RequestParam("status") String status) {
    JSONObject json = new JSONObject();
    try {
      if(ObjectUtils.equals(achieve, null)){
        json.put("result", "failure");
        json.put("message", "信息有误！");
        return json;
      }
      AchieveStatusEnum statusEnum = AchieveStatusEnum.valueOf(status);
      achieve.setStatus(statusEnum);
      achieveServer.save(achieve);
      logService.log(null, "修改审核状态="+status, EventType.UPDATE);
      json.put("result", "success");
      json.put("message", "操作成功");
    } catch (Exception e) {
      LogUtil.error("达量提成修改审核状态:" + e.getMessage(), e);
      json.put("result", "failure");
      json.put("message", "网络异常，稍后重试！");
      return json;
    }
    return json;
  }

  /**
   * 
  * @Title: showUpdate 
  * @Description: 修改页面跳转
  * @param @param achieve
  * @param @param model
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  @RequestMapping(value = "/upd")
  public String showUpdate(@RequestParam(name = "achieveId") Achieve achieve, Model model) {
    List<MachineType> machineTypes = machineTypeServer.findAll();
    model.addAttribute("machineTypes", machineTypes);
    model.addAttribute("achieve", achieve);
    model.addAttribute("planId", achieve.getPlanId());
    return "achieve/achieve_upd";
  }

  /**
   * 
  * @Title: showtake 
  * @Description: 查看页面跳转
  * @param @param achieve
  * @param @param model
  * @param @return    设定文件 
  * @return String    返回类型 
  * @throws
   */
  @RequestMapping(value = "/list/{achieveId}")
  public String showtake(@PathVariable(value = "achieveId") Achieve achieve, Model model) {
    model.addAttribute("achieve", achieve);
    return "achieve/achieve_take";
  }
  /**
   *
  * @Title: showtake
  * @Description: 查看页面跳转
  * @param @param achieve
  * @param @param model
  * @param @return    设定文件
  * @return String    返回类型
  * @throws
   */
  @RequestMapping(value = "/course/{achieveId}",method = RequestMethod.GET)
  public String showCourse(@PathVariable(value = "achieveId") Achieve achieve, Model model) {
  	try {
		  model.addAttribute("achieveJson", new ObjectMapper().writeValueAsString(achieve));
	  }catch (Exception e){
		  e.getMessage();
	  }
	  model.addAttribute("achieve", achieve);
	  //查询周期销量
	  model.addAttribute("totalNumber", 100);
	  //查询退货量
	  model.addAttribute("retreatAmount", 10);
	  return "achieve/achieve_course";
  }

  /**
   * 
   * @Title: updAchieve 
   * @Description: 修改Achieve
   *  @param @param 
   *  achieve @param 
   *  @return 设定文件 
   *  @return String 返回类型 
   *  @throws
   */
  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public JSONObject updAchieve(@RequestBody Achieve achieve) {
    JSONObject json = new JSONObject();
    Achieve oldAchieve = achieveServer.findOne(achieve.getAchieveId());
    try {
      achieve.setCreateDate(new Date());
      achieveServer.save(achieve);
      logService.log(oldAchieve.toString(), achieve.toString(), EventType.UPDATE);
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

  /**
   * 
   * @Title: showDetial @Description: 查看 @param @param achieve @param @param
   *         model @param @return 设定文件 @return String 返回类型 @throws
   */
  @RequestMapping(value = "/local")
  public String showLocal(@RequestParam(name = "planId") String planId, Model model) {
    return "achieve/achieve_detial";
  }

  /**
   * 
  * @Title: getPlanUsers 
  * @Description: 查询方案的所有用户
  * @param @param planId
  * @param @param pageRequest
  * @param @return    设定文件 
  * @return Page<PlanUserVo>    返回类型 
  * @throws
   */
  @RequestMapping(value = "/planUsers")
  @ResponseBody
  public Page<PlanUserVo> getPlanUsers(@RequestParam String planId,
      @PageableDefault(page = 0, size = 10, sort = { "regdate" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = new HashMap<>();
    Page<PlanUserVo> page = new PageImpl<>(new ArrayList<>());
    try {
      searchParams.put("EQ_planId", Integer.parseInt(planId));
      page = mainPlanService.getUserpage(pageRequest, searchParams);
    } catch (Exception e) {
      LogUtil.error("查询planId=" + planId + "方案人员失败", e);
    }
    return page;
  }
}