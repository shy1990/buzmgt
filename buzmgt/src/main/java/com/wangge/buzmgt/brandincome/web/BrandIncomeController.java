package com.wangge.buzmgt.brandincome.web;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.areaattribute.service.AreaAttributeService;
import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.entity.BrandIncome.BrandIncomeStatus;
import com.wangge.buzmgt.brandincome.entity.BrandIncomeVo;
import com.wangge.buzmgt.brandincome.service.BrandIncomeService;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.entity.MainIncomePlan;
import com.wangge.buzmgt.income.main.service.HedgeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.service.OrderItemService;
import com.wangge.buzmgt.ordersignfor.service.OrderSignforService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.superposition.service.GoodsOrderService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesmanLevel;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.JsonResponse;
import com.wangge.buzmgt.util.excel.ExcelExport;
import com.wangge.json.JSONFormat;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author peter
 * @ClassName: BrandIncomeController
 * @Description: 品牌型号控制层
 * @date 2016年8月29日 下午11:05:16
 */
@Controller
@RequestMapping("/brandIncome")
public class BrandIncomeController {
  @Autowired
  private LogService logService;
  @Autowired
  private MainPlanService mainPlanService;
  @Autowired
  private BrandIncomeService brandIncomeService;
  @Autowired
  private AreaAttributeService areaAttributeService;
  @Autowired
  private SalesManService salesManService;
  @Autowired
  private HedgeService hedgeService;
  @Autowired
  private RegionService regionService;
  @Autowired
  private GoodsOrderService goodsOrderService;
  @Autowired
  private OrderItemService orderItemService;
  @Autowired
  private OrderSignforService orderSignforService;

  private static final String SEARCH_OPERTOR = "sc_";

  private final static String TIME_MIN = " 00:00:00";

  private final static String TIME_MAX = " 23:59:59";

  /**
   * @Title: addBrandIncome @Description: 品牌型号收益添加
   * planId @param model @param @return 设定文件 @return String 返回类型 @throws
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addBrandIncome(@RequestParam String planId, @RequestParam String machineType, Model model) {

    List<BrandType> brandTypes = mainPlanService.findCodeByMachineType(machineType);
    MainIncomePlan mainIncomePlan = mainPlanService.findById(Long.valueOf(planId));
    model.addAttribute("createTime",mainIncomePlan.getCreatetime());
    model.addAttribute("fqTime",mainIncomePlan.getFqtime());
    model.addAttribute("planId", planId);
    model.addAttribute("brandTypes", brandTypes);
    model.addAttribute("machineType", machineType);
    return "brandincome/brand_add";
  }

  /**
   * @Title: saveBrandIncome @Description: 添加操作
   * 设定文件 @return String 返回类型 @throws
   */
  @RequestMapping(value = "", method = RequestMethod.POST)
  @ResponseBody
  public JsonResponse saveBrandIncome(@RequestBody BrandIncome brandIncome) {
    JsonResponse json = new JsonResponse();
    try {
      String startDate = DateUtil.date2String(brandIncome.getStartDate());
      String endDate = DateUtil.date2String(brandIncome.getEndDate());
      brandIncome.setStartDate(DateUtil.string2Date(startDate + TIME_MIN));
      brandIncome.setEndDate(DateUtil.string2Date(endDate + TIME_MAX));
      brandIncome.setCreateDate(new Date());
      brandIncome = brandIncomeService.save(brandIncome);
      logService.log(null, brandIncome, Log.EventType.SAVE);
      json.setStatus(JsonResponse.Status.SUCCESS);
      json.setSuccessMsg("保存成功!");
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.setStatus(JsonResponse.Status.ERROR);
      json.setErrorMsg("系统错误,请稍候重试!");
      return json;
    }
    return json;
  }

  /**
   * @param request
   * @param planId
   * @param pageable
   * @param @return  设定文件
   * @return Page<BrandIncome>    返回类型
   * @throws
   * @Title: findByMachineTypeAndPlanId
   * @Description: 根据方案Id查询对应的品牌型号分页
   */
  @RequestMapping(value = "/{planId}", method = RequestMethod.GET)
  @JSONFormat(filterField = {"BrandIncome.user"})
  public Page<BrandIncome> findByMachineTypeAndPlanId(HttpServletRequest request,
                                                      @PathVariable(value = "planId") String planId,
                                                      @PageableDefault(page = 0, size = 20, sort = {"createDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_planId", planId);
    Page<BrandIncome> brandIncomePage = brandIncomeService.findAll(searchParams, pageable);
    return brandIncomePage;
  }

  /**
   * @param brandIncome
   * @param model
   * @param @return     设定文件
   * @return String
   * @throws
   * @Title: show
   * @Description: 根据品牌型号方案ID查看对应的详情
   */
  @RequestMapping(value = "/show/{brandId}", method = RequestMethod.GET)
  public String show(@PathVariable(value = "brandId") BrandIncome brandIncome, Model model) {
    List<AreaAttribute> areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(brandIncome.getId(), PlanType.BRANDMODEL);
    model.addAttribute("areaAttributes", areaAttributes);
    model.addAttribute("brandIncome", brandIncome);
    return "brandincome/brand_look";
  }

  /**
   * @param brandIncome
   * @param @return     设定文件
   * @return JsonResponse
   * @throws
   * @Title: stop
   * @Description: 根据当前品牌型号方案ID终止方案
   */
  @RequestMapping(value = "/stop/{brandId}", method = RequestMethod.PUT)
  @ResponseBody
  public JsonResponse stop(@PathVariable(value = "brandId") BrandIncome brandIncome) {
    JsonResponse json = new JsonResponse();
    try {
      brandIncome.setEndDate(new Date());
      BrandIncome brandIn = brandIncomeService.save(brandIncome);
      //同时设置区域属性不可用
      List<AreaAttribute> areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(brandIn.getId(),PlanType.BRANDMODEL);
      if (CollectionUtils.isNotEmpty(areaAttributes)){
        areaAttributes.forEach(areaAttribute -> {
          areaAttribute.setDisabled(1);
          areaAttributeService.save(areaAttribute);
        });
      }
      logService.log(brandIncome, brandIn, Log.EventType.UPDATE);
      json.setStatus(JsonResponse.Status.SUCCESS);
      json.setSuccessMsg("操作成功!");
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.setStatus(JsonResponse.Status.ERROR);
      json.setErrorMsg("系统错误,请稍候重试!");
      return json;
    }
    return json;
  }

  /**
   * @param request
   * @param response
   * @param pageRequest
   * @param @return     设定文件
   * @return void
   * @throws
   * @Title: exportBrandIncome
   * @Description: 导出进行中的品牌型号方案列表
   */
  @RequestMapping("/export")
  public void exportBrandIncome(HttpServletRequest request, HttpServletResponse response,
                                @PageableDefault(page = 0, size = 20, sort = {"createDate"}, direction = Sort.Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<BrandIncome> list = brandIncomeService.findAll(searchParams, pageRequest.getSort());
    String[] gridTitles_1 = {"品牌", "型号", "提成金额", "方案开始日期", "方案结束日期", "状态", "设置日期"};
    String[] coloumsKey_1 = {"brand.name", "good.name", "commissions", "startDate", "endDate", "进行中", "createDate"};
    ExcelExport.doExcelExport("正在使用品牌型号方案表.xls", list, gridTitles_1, coloumsKey_1, request, response);
  }

  /**
   * @param planId
   * @param machineType
   * @param model
   * @param @return     设定文件
   * @return String
   * @throws
   * @Title: showAchieveRecord
   * @Description: 跳转到设置记录
   */
  @RequestMapping(value = "/record", method = RequestMethod.GET)
  public String showAchieveRecord(@RequestParam String planId, @RequestParam String machineType, Model model) {
    model.addAttribute("planId", planId);
    model.addAttribute("machineType", machineType);
    return "brandincome/brand_record";
  }

  /**
   * @param brandIncome
   * @param @return     设定文件
   * @return String
   * @throws
   * @Title: toProcess
   * @Description: 跳转到进程页
   */
  @RequestMapping(value = "/process/{brandId}", method = RequestMethod.GET)
  public String toProcess(@PathVariable(value = "brandId") BrandIncome brandIncome, Model model) {
    List<SalesmanLevel> salesmanLevels = salesManService.findAll();
    int cycleSales = brandIncomeService.findCycleSales(brandIncome);//周期销量
    int hedgeNums = hedgeService.countByGoodId(brandIncome.getGoodId());//售后冲减
    model.addAttribute("hedgeNums", hedgeNums);
    model.addAttribute("cycleSales", cycleSales);
    model.addAttribute("salesmanLevels", salesmanLevels);
    model.addAttribute("brandIncome", brandIncome);
    return "brandincome/brand_process";
  }

  /**
   * @param request
   * @param brandIncome
   * @param pageable
   * @param @return     设定文件
   * @return Page<BrandIncome>    返回类型
   * @throws
   * @Title: findProcessList
   * @Description: 根据品牌型号id查询该品牌进程
   */
  @RequestMapping(value = "/processList/{brandId}", method = RequestMethod.GET)
  @ResponseBody
  public Page<BrandIncomeVo> findProcessList(HttpServletRequest request,
                                             @PathVariable(value = "brandId") BrandIncome brandIncome,
                                             @PageableDefault(page = 0, size = 20, sort = {"nums"}, direction = Sort.Direction.DESC) Pageable pageable) {
    Page<BrandIncomeVo> brandIncomeVos = brandIncomeService.findAll(request, brandIncome, pageable);
    return brandIncomeVos;
  }

  /**
   * @param request
   * @param response
   * @param @return  设定文件
   * @return void
   * @throws
   * @Title: exportProcessList
   * @Description: 导出品牌型号进程列表
   */
  @RequestMapping("/process/export")
  public void exportProcessList(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "brandId") BrandIncome brandIncome) {
    List<BrandIncomeVo> list = brandIncomeService.findAll(request, brandIncome);
    String[] gridTitles_1 = {"姓名", "负责区域", "业务等级", "品牌", "型号", "查看进程(台)", "任务开始日期", "任务结束日期"};
    String[] coloumsKey_1 = {"truename", "namepath", "levelName", "brandName", "goodsName", "nums", "startDate", "endDate"};
    ExcelExport.doExcelExport("品牌型号进程导出表.xls", list, gridTitles_1, coloumsKey_1, request, response);
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
  public String toDetail(@RequestParam(value = "regionId") Region region, @RequestParam(value = "goodId") String goodId, Model model) {
    List<Region> regions = regionService.findByRegion(region.getId());
    model.addAttribute("regions", regions);
    model.addAttribute("region", region);
    model.addAttribute("goodId", goodId);
    return "brandincome/brand_det";
  }

  /**
   * @param request
   * @param region
   * @param pageable
   * @param @return  设定文件
   * @return Page<T>    返回类型
   * @throws
   * @Title: findDetailList
   * @Description: 根据条件查询品牌型号明细列表
   */
  @RequestMapping(value = "/detailList/{regionId}", method = RequestMethod.GET)
  @ResponseBody
  public Page<?> findDetailList(HttpServletRequest request,
                                @PathVariable(value = "regionId") Region region,
                                @PageableDefault(page = 0, size = 20) Pageable pageable) {
    Page<?> page = null;
    String orderType = request.getParameter("orderType");
    if ("sales".equals(orderType)) {
      page = goodsOrderService.findAll(request, region, null, pageable);
    } else {
      page = hedgeService.findAll(request, region, null, pageable);
    }
    return page;
  }

  /**
   * @param request
   * @param response
   * @param region
   * @param @return  设定文件
   * @return void
   * @throws
   * @Title: exportDetailList
   * @Description: 导出品牌型号明细列表
   */
  @RequestMapping("/detail/export")
  public void exportDetailList(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(value = "regionId") Region region) {
    String orderType = request.getParameter("orderType");
    List<?> list = null;
    if ("sales".equals(orderType)) {
      list = goodsOrderService.findAll(request, region, null);
      String[] gridTitles_1 = {"商家名称", "区域", "订单号", "产品", "数量", "付款日期"};
      String[] coloumsKey_1 = {"shopName", "namepath", "orderNum", "goodsName", "nums", "payTime"};
      ExcelExport.doExcelExport("品牌型号销量订单导出表.xls", list, gridTitles_1, coloumsKey_1, request, response);
    } else {
      list = hedgeService.findAll(request, region, null);
      String[] gridTitles_1 = {"商家名称", "区域", "订单号", "产品", "数量", "退货日期"};
      String[] coloumsKey_1 = {"shopName", "namepath", "orderno", "goodsName", "sum", "shdate"};
      ExcelExport.doExcelExport("品牌型号退货订单导出表.xls", list, gridTitles_1, coloumsKey_1, request, response);
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
    return "brandincome/brand_order_det";
  }

  /**
   * @param brandIncome
   * @param @return     设定文件
   * @return JsonResponse
   * @throws
   * @Title: deleteBrand
   * @Description: 删除品牌型号规则(逻辑删除)
   */
  @RequestMapping(value = "/{brandId}", method = RequestMethod.DELETE)
  @ResponseBody
  public JsonResponse deleteBrand(@PathVariable("brandId") BrandIncome brandIncome) {
    JsonResponse json = new JsonResponse();
    try {
      brandIncome.setFlag(FlagEnum.DEL);
      BrandIncome brandNew = brandIncomeService.save(brandIncome);
      //同时设置区域属性不可用
      List<AreaAttribute> areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(brandNew.getId(),PlanType.BRANDMODEL);
      if (CollectionUtils.isNotEmpty(areaAttributes)){
        areaAttributes.forEach(areaAttribute -> {
          areaAttribute.setDisabled(1);
          areaAttributeService.save(areaAttribute);
        });
      }
      logService.log(brandIncome, brandNew, Log.EventType.DELETE);
      json.setStatus(JsonResponse.Status.SUCCESS);
      json.setSuccessMsg("删除成功!");
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.setStatus(JsonResponse.Status.ERROR);
      json.setErrorMsg("系统错误,请稍候重试!");
      return json;
    }
    return json;
  }

  /**
   * @param brandIncome
   * @param model
   * @param @return     设定文件
   * @return String
   * @throws
   * @Title: update
   * @Description: 跳转到修改品牌型号页面
   */
  @RequestMapping(value = "/toUpdate/{brandId}", method = RequestMethod.GET)
  public String update(@PathVariable(value = "brandId") BrandIncome brandIncome,@RequestParam String planId, @RequestParam String machineType, Model model) {
    List<AreaAttribute> areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(brandIncome.getId(), PlanType.BRANDMODEL);
    model.addAttribute("areaAttributes", areaAttributes);
    model.addAttribute("brandIncome", brandIncome);
    model.addAttribute("planId", planId);
    model.addAttribute("machineType", machineType);
    return "brandincome/brand_alter";
  }

  /**
   * @Title: updateBrandIncome @Description: 修改操作
   * 设定文件 @return String 返回类型 @throws
   */
  @RequestMapping(value = "/update/{brandIncomeId}", method = RequestMethod.POST)
  @ResponseBody
  public JsonResponse updateBrandIncome(@PathVariable(value = "brandIncomeId") BrandIncome newBrandIncome,@RequestBody BrandIncome brandIncome) {
    JsonResponse json = new JsonResponse();
    try {
      BrandIncome old = newBrandIncome;
      newBrandIncome.setStatus(brandIncome.getStatus());
      newBrandIncome.setCommissions(brandIncome.getCommissions());
      newBrandIncome.setAuditor(brandIncome.getAuditor());
      String startDate = DateUtil.date2String(brandIncome.getStartDate());
      String endDate = DateUtil.date2String(brandIncome.getEndDate());
      newBrandIncome.setStartDate(DateUtil.string2Date(startDate + TIME_MIN));
      newBrandIncome.setEndDate(DateUtil.string2Date(endDate + TIME_MAX));
      newBrandIncome.setCreateDate(new Date());
      newBrandIncome = brandIncomeService.save(newBrandIncome);
      logService.log(old, newBrandIncome, Log.EventType.UPDATE);
      json.setStatus(JsonResponse.Status.SUCCESS);
      json.setSuccessMsg("修改成功!");
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.setStatus(JsonResponse.Status.ERROR);
      json.setErrorMsg("系统错误,请稍候重试!");
      return json;
    }
    return json;
  }

  /**
   *
   * @Title: toAudit
   * @Description: 跳转设置记录审核页面
   * @param @param planId
   * @param @param model
   * @param @return    设定文件
   * @return String    返回类型
   * @throws
   */
  @RequestMapping(value = "/toAudit", method = RequestMethod.GET)
  public String toAudit(@RequestParam String planId, Model model) {
    String userId = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
    model.addAttribute("planId", planId);
    model.addAttribute("userId", userId);
    return "brandincome/brand_record_audit";
  }

  /**
   * @param brandIncome
   * @param @return     设定文件
   * @return JsonResponse
   * @throws
   * @Title: auditBrandIncome
   * @Description: 审核通过该品牌型号规则
   */
  @RequestMapping(value = "/audit/{brandId}", method = RequestMethod.PUT)
  @ResponseBody
  public JsonResponse auditBrandIncome(@PathVariable(value = "brandId") BrandIncome brandIncome) {
    JsonResponse json = new JsonResponse();
    try {
      BrandIncome income = brandIncome;
      brandIncome.setStatus(BrandIncomeStatus.OVER);
      brandIncome = brandIncomeService.save(brandIncome);
      logService.log(income, brandIncome, Log.EventType.UPDATE);
      json.setStatus(JsonResponse.Status.SUCCESS);
      json.setSuccessMsg("审核成功!");
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.setStatus(JsonResponse.Status.ERROR);
      json.setErrorMsg("系统错误,请稍候重试!");
      return json;
    }
    return json;
  }

  /**
   * @param brandIncome
   * @param @return     设定文件
   * @return JsonResponse
   * @throws
   * @Title: rejectBrandIncome
   * @Description: 驳回该品牌型号规则
   */
  @RequestMapping(value = "/reject/{brandId}", method = RequestMethod.PUT)
  @ResponseBody
  public JsonResponse rejectBrandIncome(@PathVariable(value = "brandId") BrandIncome brandIncome) {
    JsonResponse json = new JsonResponse();
    try {
      BrandIncome income = brandIncome;
      brandIncome.setStatus(BrandIncomeStatus.BACK);
      brandIncome = brandIncomeService.save(brandIncome);
      logService.log(income, brandIncome, Log.EventType.UPDATE);
      json.setStatus(JsonResponse.Status.SUCCESS);
      json.setSuccessMsg("已驳回!");
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
      json.setStatus(JsonResponse.Status.ERROR);
      json.setErrorMsg("系统错误,请稍候重试!");
      return json;
    }
    return json;
  }
}
