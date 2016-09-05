package com.wangge.buzmgt.achieve.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wangge.buzmgt.achieve.entity.Achieve;
import com.wangge.buzmgt.achieve.service.AchieveService;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.server.MachineTypeServer;
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
  private MachineTypeServer machineTypeServer;

  private static final String SEARCH_OPERTOR = "sc_";

  /**
   * 
   * @Title: showAchieveList @Description: 展示达量列表 @param @return 设定文件 @return
   *         String 返回类型 @throws
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String showAchieveList(String planId, Model model) {
    model.addAttribute("planId", planId);
    model.addAttribute("machineTypes", mainPlanService.getAllMachineType());
    return "achieve/achieve_list";
  }

  /**
   * 
   * @Title: findByMachineTypeAndPlanId @Description: 根据方案Id查询对应的达量分页,
   *         参数格式：sc_EQ_xxxx=yyyy; @param @param request @param @param
   *         planId @param @param pageable @param @return 设定文件 @return Page
   *         <Achieve> 返回类型 @throws
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
   * @Title: showAddAchieve @Description: 达量添加 @param @param
   *         planId @param @param model @param @return 设定文件 @return String
   *         返回类型 @throws
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
   * @Title: addAchieve @Description: 添加操作 @param @param achieve @param @return
   *         设定文件 @return String 返回类型 @throws
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
   * @Title: exportWaterOrderCash @Description: 导出达量列表 @param @param
   *         request @param @param response @param @param pageRequest
   *         设定文件 @return void 返回类型 @throws
   */
  @RequestMapping("/export")
  public void exportWaterOrderCash(HttpServletRequest request, HttpServletResponse response,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<Achieve> list = achieveServer.findAll(searchParams, pageRequest.getSort());
    String[] gridTitles_1 = { "品牌", "型号", "一阶段达量", "二阶段达量", "三阶段达量", "方案开始日期", "方案结束日期", "佣金发放日期", "设置日期" };
    String[] coloumsKey_1 = { "brand.name", "good.name", "numberFirst", "numberSecond", "numberThird", "startDate",
        "endDate", "issuingDate", "createDate" };
    ExcelExport.doExcelExport("正在使用达量导出.xls", list, gridTitles_1, coloumsKey_1, request, response);
  }

  /**
   * 
   * @Title: showAchieveRecord @Description: 设置记录页面 @param @param
   *         planId @param @param model @param @return 设定文件 @return String
   *         返回类型 @throws
   */
  @RequestMapping(value = "/record", method = RequestMethod.GET)
  public String showAchieveRecord(@RequestParam String planId, Model model) {
    model.addAttribute("planId", planId);
    return "achieve/achieve_record";
  }

  /**
   * 
   * @Title: delete @Description: 删除：更改flag状态DEL @param @param
   *         achieve @param @return 设定文件 @return JSONObject 返回类型 @throws
   */
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public JSONObject delete(@PathVariable("id") Achieve achieve) {
    JSONObject json = new JSONObject();
    try {
      achieve.setFlag(FlagEnum.DEL);
      achieveServer.save(achieve);
      logService.log(null, "逻辑删除flag=DEl", EventType.SAVE);
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

  @RequestMapping(value = "/upd")
  public String showUpdate(@RequestParam(name = "achieveId") Achieve achieve, Model model) {
    model.addAttribute("achieve", achieve);
    return "achieve/achieve_upd";
  }

  /**
   * 
   * @Title: updAchieve @Description: 修改Achieve @param @param
   *         achieve @param @return 设定文件 @return String 返回类型 @throws
   */
  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public JSONObject updAchieve(@RequestBody Achieve achieve) {
    JSONObject json = new JSONObject();

    try {
      achieve.setCreateDate(new Date());
      achieveServer.save(achieve);
      logService.log(null, achieve.toString(), EventType.UPDATE);
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
   * model @param @return 设定文件 @return String 返回类型 @throws
   */
  @RequestMapping(value = "/detial")
  public String showDetial(@RequestParam(name = "achieveId") Achieve achieve, Model model) {
    model.addAttribute("achieve", achieve);
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
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = new HashMap<>();
    Page<PlanUserVo> page = new PageImpl<>(null);
    try {
      searchParams.put("EQ_planId", Integer.parseInt(planId));
      page = mainPlanService.getUserpage(pageRequest, searchParams);
    } catch (Exception e) {
      LogUtil.error("查询planId=" + planId + "方案人员失败", e);
    }
    return page;
  }
}
