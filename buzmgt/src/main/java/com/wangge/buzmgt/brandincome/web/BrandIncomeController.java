package com.wangge.buzmgt.brandincome.web;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.areaattribute.service.AreaAttributeService;
import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.service.BrandIncomeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.JsonResponse;
import com.wangge.buzmgt.util.excel.ExcelExport;
import com.wangge.json.JSONFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  private static final String SEARCH_OPERTOR = "sc_";

  /**
   * @Title: addBrandIncome @Description: 品牌型号收益添加
   * planId @param model @param @return 设定文件 @return String 返回类型 @throws
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String addBrandIncome(@RequestParam String planId, @RequestParam String machineType, Model model) {

    List<BrandType> brandTypes = mainPlanService.findCodeByMachineType(machineType);
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
      brandIncome.setCreateDate(new Date());
      brandIncome = brandIncomeService.save(brandIncome);
      logService.log(null, brandIncome, Log.EventType.SAVE);
      json.setStatus(JsonResponse.Status.SUCCESS);
      json.setSuccessMsg("保存成功");
      json.setResult(brandIncome);
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
                                                      @PageableDefault(page = 0, size = 10, sort = {"createDate"}, direction = Sort.Direction.DESC) Pageable pageable) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    searchParams.put("EQ_planId", planId);
    Page<BrandIncome> brandIncomePage = brandIncomeService.findAll(searchParams, pageable);
    return brandIncomePage;
  }

  /**
   * @param brandIncome
   * @param model
   * @param @return  设定文件
   * @return String
   * @throws
   * @Title: show
   * @Description: 根据品牌型号方案ID查看对应的详情
   */
  @RequestMapping(value = "/show/{brandId}", method = RequestMethod.GET)
  public String show(@PathVariable(value = "brandId") BrandIncome brandIncome,Model model) {
    List<AreaAttribute> areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(brandIncome.getId(), PlanType.BRANDMODEL);
    model.addAttribute("areaAttributes",areaAttributes);
    model.addAttribute("brandIncome",brandIncome);
    return "brandincome/brand_look";
  }

  /**
   * @param brandIncome
   * @param @return  设定文件
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
   * @param @return  设定文件
   * @return void
   * @throws
   * @Title: exportBrandIncome
   * @Description: 导出进行中的品牌型号方案列表
   */
  @RequestMapping("/export")
  public void exportBrandIncome(HttpServletRequest request, HttpServletResponse response,
                                   @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Sort.Direction.DESC) Pageable pageRequest) {
    Map<String, Object> searchParams = WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    List<BrandIncome> list = brandIncomeService.findAll(searchParams, pageRequest.getSort());
    String[] gridTitles_1 = { "品牌", "型号", "提成金额", "方案开始日期", "方案结束日期", "状态", "设置日期" };
    String[] coloumsKey_1 = { "brand.name", "good.name", "commissions", "startDate", "endDate", "进行中", "createDate" };
    ExcelExport.doExcelExport("正在使用品牌型号方案表.xls", list, gridTitles_1, coloumsKey_1, request, response);
  }


  @RequestMapping(value = "/record", method = RequestMethod.GET)
  public String showAchieveRecord(@RequestParam String planId, @RequestParam String machineType, Model model) {
    model.addAttribute("planId", planId);
    model.addAttribute("machineType",machineType);
    return "brandincome/brand_record";
  }

  @RequestMapping(value = "/process", method = RequestMethod.GET)
  public String toProcess(@RequestParam String brandId, Model model) {
    model.addAttribute("brandId", brandId);
    return "brandincome/brand_process";
  }
}
