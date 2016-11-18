package com.wangge.buzmgt.achieveaward.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wangge.buzmgt.brandincome.entity.BrandIncomeVo;
import com.wangge.buzmgt.income.main.service.HedgeService;
import com.wangge.buzmgt.income.schedule.service.JobService;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderItemService;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.section.pojo.ChannelManager;
import com.wangge.buzmgt.section.service.ChannelManagerService;
import com.wangge.buzmgt.superposition.service.GoodsOrderService;
import com.wangge.buzmgt.teammember.entity.SalesmanLevel;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  private ChannelManagerService channelManagerService;
  @Autowired
  private MainPlanService mainPlanService;
  @Autowired
  private MachineTypeService machineTypeServer;
  @Autowired
  private SalesManService salesManService;
  @Autowired
  private JobService jobService;
  @Autowired
  private HedgeService hedgeService;
  @Autowired
  private RegionService regionService;
  @Autowired
  private GoodsOrderService goodsOrderService;
  @Autowired
  private OrderSignforService orderSignforService;
  @Autowired
  private OrderItemService orderItemService;

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
  public String showAwardList(String planId,String check, Model model) {
    model.addAttribute("planId", planId);
	  model.addAttribute("check", check);
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
	  List<ChannelManager> channelManagers = channelManagerService.findChannelManager("渠道总监");
    model.addAttribute("planId", planId);
    model.addAttribute("channelManagers", channelManagers);
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
	    String userId = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
	    if(!userId.equals(award.getAuditor())){
		    json.put("result", "failure");
		    json.put("message", "没有权限请联系管理员！");
		    return json;
	    }
      if (ObjectUtils.equals(award, null)) {
        json.put("result", "failure");
        json.put("message", "信息有误！");
        return json;
      }
      AwardStatusEnum statusEnum = AwardStatusEnum.valueOf(status);
      award.setStatus(statusEnum);
      awardServer.save(award);
	    Date curdDate = DateUtil.getPreMonthAndDay(award.getIssuingDate(),1);
	    //保存定时任务
	    jobService.saveJobTask(40,Long.valueOf(award.getPlanId()),award.getAwardId(),curdDate);
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

  /**
   * @param award
   * @param model
   * @param @return     设定文件
   * @return String
   * @throws
   * @Title: toProcess
   * @Description: 跳转到进程页
   */
  @RequestMapping(value = "/process/{awardId}", method = RequestMethod.GET)
  public String toProcess(@PathVariable(value = "awardId") Award award, Model model) {
    List<SalesmanLevel> salesmanLevels = salesManService.findAll();
    Set<AwardGood> awardGoods = award.getAwardGoods();
    List<String> goodIds = new ArrayList<>();
    if (CollectionUtils.isNotEmpty(awardGoods)){
      awardGoods.forEach(awardGood -> {
        goodIds.add(awardGood.getGoodId());
      });
    }
    int cycleSales = awardServer.findCycleSales(goodIds);//周期销量
    int hedgeNums = hedgeService.countByGoodId(goodIds);//售后冲减
    model.addAttribute("hedgeNums", hedgeNums);
    model.addAttribute("cycleSales", cycleSales);
    model.addAttribute("salesmanLevels", salesmanLevels);
    model.addAttribute("award", award);
    model.addAttribute("awardGoods",awardGoods);
    return "award/award_process";
  }

  /**
   * @param request
   * @param award
   * @param pageable
   * @param @return     设定文件
   * @return Page<BrandIncome>    返回类型
   * @throws
   * @Title: findProcessList
   * @Description: 根据达量奖励id查询该品牌达量奖励进程
   */
  @RequestMapping(value = "/processList/{awardId}", method = RequestMethod.GET)
  @ResponseBody
  public Page<BrandIncomeVo> findProcessList(HttpServletRequest request,
                                             @PathVariable(value = "awardId") Award award,
                                             @PageableDefault(page = 0, size = 20, sort = {"nums"}, direction = Sort.Direction.DESC) Pageable pageable) {
    Page<BrandIncomeVo> brandIncomeVos = awardServer.findAll(request, award, pageable);
    return brandIncomeVos;
  }

  /**
   * @param request
   * @param response
   * @param @return  设定文件
   * @return void
   * @throws
   * @Title: exportProcessList
   * @Description: 导出达量奖励进程列表
   */
  @RequestMapping("/process/export")
  public void exportProcessList(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "awardId") Award award) {
    List<BrandIncomeVo> list = awardServer.findAll(request, award);
    String[] gridTitles_1 = {"姓名", "负责区域", "业务等级", "任务量一", "任务量二", "任务量三", "分组", "当前完成量(台)", "任务开始日期", "任务结束日期"};
    String[] coloumsKey_1 = {"truename", "namepath", "levelName", "numberFirst", "numberSecond", "numberThird", "groupName", "nums", "startDate", "endDate"};
    ExcelExport.doExcelExport("达量奖励进程导出表.xls", list, gridTitles_1, coloumsKey_1, request, response);
  }

  /**
   * @param region
   * @param goodId
   * @param @return 设定文件
   * @return String
   * @throws
   * @Title: toDetail
   * @Description: 跳转到进程明细
   */
  @RequestMapping(value = "/detail", method = RequestMethod.GET)
  public String toDetail(@RequestParam(value = "regionId") Region region, @RequestParam(value = "goodId") String goodId,@RequestParam(value = "awardId") Award award, Model model) {
    List<Region> regions = regionService.findByRegion(region.getId());
    model.addAttribute("regions", regions);
    model.addAttribute("region", region);
    model.addAttribute("goodId", goodId);
    model.addAttribute("award",award);
    return "award/award_process_det";
  }

  /**
   * @param request
   * @param region
   * @param pageable
   * @param award
   * @param @return  设定文件
   * @return Page<T>    返回类型
   * @throws
   * @Title: findDetailList
   * @Description: 根据条件查询达量奖励明细列表
   */
  @RequestMapping(value = "/detailList/{regionId}", method = RequestMethod.GET)
  @ResponseBody
  public Page<?> findDetailList(HttpServletRequest request,
                                @PathVariable(value = "regionId") Region region,
                                @RequestParam(value = "awardId") Award award,
                                @PageableDefault(page = 0, size = 20) Pageable pageable) {
    Page<?> page = null;
    String orderType = request.getParameter("orderType");
    if ("sales".equals(orderType)) {
      page = goodsOrderService.findAll(request, region,award, pageable);
    } else {
      page = hedgeService.findAll(request, region,award, pageable);
    }
    return page;
  }

  /**
   * @param request
   * @param response
   * @param region
   * @param award
   * @param @return  设定文件
   * @return void
   * @throws
   * @Title: exportDetailList
   * @Description: 导出达量奖励明细列表
   */
  @RequestMapping("/detail/export")
  public void exportDetailList(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "regionId") Region region,@RequestParam(value = "awardId") Award award) {
    String orderType = request.getParameter("orderType");
    List<?> list = null;
    if ("sales".equals(orderType)) {
      list = goodsOrderService.findAll(request, region,award);
      String[] gridTitles_1 = {"商家名称", "区域", "订单号", "产品", "数量", "付款日期"};
      String[] coloumsKey_1 = {"shopName", "namepath", "orderNum", "goodsName", "nums", "payTime"};
      ExcelExport.doExcelExport("达量奖励订单导出表.xls", list, gridTitles_1, coloumsKey_1, request, response);
    } else {
      list = hedgeService.findAll(request, region,award);
      String[] gridTitles_1 = {"商家名称", "区域", "订单号", "产品", "数量", "退货日期"};
      String[] coloumsKey_1 = {"shopName", "namepath", "orderno", "goodsName", "sum", "shdate"};
      ExcelExport.doExcelExport("达量奖励退货订单导出表.xls", list, gridTitles_1, coloumsKey_1, request, response);
    }
  }

  /**
   * 品牌型号订单详情
   *
   * @param orderId
   * @param model
   * @return String
   */
  @RequestMapping(value = "/detail/{orderId}")
  public String getCashById(@PathVariable("orderId") String orderId, Model model) {
    //绑定数据订单详情
    OrderSignfor orderSignfor = orderSignforService.findByOrderNo(orderId);
    orderItemService.disposeOrderSignfor(orderSignfor);
    model.addAttribute("order", orderSignfor);
    return "award/award_order_det";
  }
}
