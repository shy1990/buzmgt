package com.wangge.buzmgt.achieveaward.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wangge.buzmgt.achieveaward.entity.Award;
import com.wangge.buzmgt.achieveaward.entity.Award.AwardStatusEnum;
import com.wangge.buzmgt.achieveaward.entity.AwardGood;
import com.wangge.buzmgt.achieveaward.service.AwardService;
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
 * 达量奖励控制层
* @ClassName: AwardController
* @Description: 达量奖励控制层
* @author ChenGuop
* @date 2016年9月14日 上午11:18:12
*
 */
@Controller
@RequestMapping("/award")
public class AwardController {
  @Autowired
  private LogService logService;
  @Autowired
  private AwardService awardServer;
  @Autowired
  private MainPlanService mainPlanService;
  @Autowired
  private MachineTypeService machineTypeServer;

  private static final String SEARCH_OPERTOR = "sc_";

  /**
   * 
   * @Title: showAwardList 
   * @Description: 展示达量奖励列表 
   * @param @return 设定文件 
   * @return String 返回类型
   * @throws
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String showAwardList(String planId, Model model) {
    model.addAttribute("planId", planId);
    return "award/award_list";
  }

  /**
   * 
   * @Title: findByMachineTypeAndPlanId 
   * @Description: 根据方案Id查询对应的达量分页,参数格式：sc_EQ_xxxx=yyyy; 
   * @param @param request 
   * @param @param planId 
   * @param @param pageable 
   * @param @return 设定文件 
   * @return Page<Award> 返回类型 
   * @throws
   */
  @RequestMapping(value = "/{planId}", method = RequestMethod.GET)
  @ResponseBody
  public Page<Award> findByMachineTypeAndPlanId(HttpServletRequest request,
      @PathVariable(value = "planId") String planId,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_planId", planId);
    return awardServer.findAll(searchParams, pageable);
  }

  /**
   * 
   * @Title: showAddAward 
   * @Description: 达量奖励添加
   * @param @param planId 
   * @param @param model 
   * @param @return 设定文件
   * @return String 返回类型 
   * @throws
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String showAddAward(@RequestParam String planId, Model model) {

    List<MachineType> machineTypes = machineTypeServer.findAll();
    model.addAttribute("planId", planId);
    model.addAttribute("machineTypes", machineTypes);
    return "award/award_add";
  }

  /**
   * 
   * @Title: addAward 
   * @Description: 添加数据 
   * @param @param award 
   * @param @return 设定文件 
   * @return JSONObject 返回类型
   * @throws
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject addAward(@RequestBody Award award) {
    JSONObject json = new JSONObject();

    try {
      //
      award.setCreateDate(new Date());
      awardServer.save(award);
      logService.log(null, award.toString(), EventType.SAVE);
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
   * @param @param pageRequest 设定文件
   * @return void 返回类型 
   * @throws
   */
  @RequestMapping("/export")
  public void exportWaterOrderCash(HttpServletRequest request, HttpServletResponse response,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<Award> list = awardServer.findAll(searchParams, pageRequest.getSort());
    String[] gridTitles_1 = { "型号", "一阶段达量", "二阶段达量", "三阶段达量", "审核人", "审核状态", "方案开始日期", "方案结束日期", "佣金发放日期",
        "设置日期" };
    String[] coloumsKey_1 = { "awardGoods.good.name", "numberFirst", "numberSecond", "numberThird", "auditor",
        "status.name", "startDate", "endDate", "issuingDate", "createDate" };
    ExcelExport.doExcelExport("正在使用达量奖励导出.xls", list, gridTitles_1, coloumsKey_1, request, response);
  }

  /**
   * 
   * @Title: showAwardRecord 
   * @Description: 设置记录页面跳转 
   * @param @param planId 
   * @param @param model 
   * @param @return 设定文件 
   * @return String 返回类型 
   * @throws
   */
  @RequestMapping(value = "/record", method = RequestMethod.GET)
  public String showAwardRecord(@RequestParam String planId, Model model) {
    model.addAttribute("planId", planId);
    return "award/award_record";
  }

  /**
   * 
   * @Title: showAwardRecord 
   * @Description: 跳转设置记录审核页面 
   * @param @param planId 
   * @param @param model 
   * @param @return 设定文件 
   * @return String 返回类型 
   * @throws
   */
  @RequestMapping(value = "/recordForAudit", method = RequestMethod.GET)
  public String showAwardRecordForaudit(@RequestParam String planId, Model model) {
    String userId = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
    model.addAttribute("planId", planId);
    model.addAttribute("userId", userId);
    return "award/award_record_audit";
  }

  /**
   * 
   * @Title: getAward 
   * @Description: 查询单个Award： 
   * @param @param award 
   * @param @return 设定文件
   * @return JSONObject 返回类型 
   * @throws
   */
  @RequestMapping(value = "getAward/{awardId}", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getAward(@PathVariable("awardId") Award award) {
    JSONObject json = new JSONObject();
    try {
      if (ObjectUtils.equals(award, null)) {
        json.put("result", "failure");
        json.put("message", "删除信息有误！");
        return json;
      }

      json.put("result", award);
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
   * @param @param award 
   * @param @return 设定文件 
   * @return JSONObject 返回类型 
   * @throws
   */
  @RequestMapping(value = "/{awardId}", method = RequestMethod.DELETE)
  @ResponseBody
  public JSONObject delete(@PathVariable("awardId") Award award) {
    JSONObject json = new JSONObject();
    try {
      if (ObjectUtils.equals(award, null)) {
        json.put("result", "failure");
        json.put("message", "删除信息有误！");
        return json;
      }
      award.setFlag(FlagEnum.DEL);
      awardServer.save(award);
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
   * @param @param award 
   * @param @return 设定文件
   * @return JSONObject 返回类型 
   * @throws
   */
  @RequestMapping(value = "/{awardId}", method = RequestMethod.PATCH)
  @ResponseBody
  public JSONObject auditAward(@PathVariable("awardId") Award award, @RequestParam("status") String status) {
    JSONObject json = new JSONObject();
    try {
      if (ObjectUtils.equals(award, null)) {
        json.put("result", "failure");
        json.put("message", "信息有误！");
        return json;
      }
      AwardStatusEnum statusEnum = AwardStatusEnum.valueOf(status);
      award.setStatus(statusEnum);
      awardServer.save(award);
      logService.log(null, "修改审核状态=" + status, EventType.UPDATE);
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
   * @param @param award 
   * @param @param model 
   * @param @return 设定文件 
   * @return String 返回类型 
   * @throws
   */
  @RequestMapping(value = "/upd")
  public String showUpdate(@RequestParam(name = "awardId") Award award, Model model) {
    List<MachineType> machineTypes = machineTypeServer.findAll();
    model.addAttribute("machineTypes", machineTypes);
    model.addAttribute("award", award);
    model.addAttribute("planId", award.getPlanId());
    return "award/award_upd";
  }

  /**
   * 
   * @Title: showtake 
   * @Description: 查看页面跳转 
   * @param @param award 
   * @param @param model 
   * @param @return 设定文件 
   * @return String 返回类型
   * @throws
   */
  @RequestMapping(value = "/list/{awardId}")
  public String showtake(@PathVariable(value = "awardId") Award award, Model model) {
    model.addAttribute("award", award);
    return "award/award_take";
  }

  /**
   * 
   * @Title: updAward 
   * @Description: 修改Award 
   * @param @param award 
   * @param @return 设定文件 
   * @return String 返回类型 
   * @throws
   */
  @RequestMapping(value = "", method = RequestMethod.PUT)
  @ResponseBody
  public JSONObject updAward(@RequestBody Award award) {
    JSONObject json = new JSONObject();
    Award oldAward = awardServer.findOne(award.getAwardId());
    try {
      award.setCreateDate(new Date());
      awardServer.save(award);
      logService.log(oldAward.toString(), award.toString(), EventType.UPDATE);
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
   * @Title: getPlanUsers 
   * @Description: 查询方案的所有用户 
   * @param @param planId 
   * @param @param pageRequest
   * @param @return 设定文件 
   * @return Page<PlanUserVo> 返回类型 
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
