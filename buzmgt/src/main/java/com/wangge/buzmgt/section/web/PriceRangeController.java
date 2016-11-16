package com.wangge.buzmgt.section.web;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import com.wangge.buzmgt.section.service.PriceRangeService;
import com.wangge.buzmgt.section.service.ProductionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.json.JSONFormat;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-11-10.
 */
@Controller
@RequestMapping("priceRange")
public class PriceRangeController {

  @Autowired
  private ProductionService productionService;

  @Autowired
  private PriceRangeService priceRangeService;

  @Autowired
  private MainPlanService mainPlanService;

  /**
   * 跳转到设置记录页面
   *
   * @return
   */
  @RequestMapping("/record")
  public String priceRange(Long planId,String check, Model model) {
    model.addAttribute("planId", planId);
    model.addAttribute("check", check);
    model.addAttribute("machineTypes", mainPlanService.getAllMachineType());
    model.addAttribute("today",getToday());
    return "pricerange/record";
  }

  /**
   * 查询正在进行
   *
   * @param planId
   * @param type
   * @return
   */
  @RequestMapping(value = "now", method = RequestMethod.POST)
  @ResponseBody
  public List<Production> searchNow(Long planId, String type) {
    return productionService.findOnGoing(planId, type, getToday());
  }

  /**
   * 查询过期的
   * @param pageable
   * @param planId
   * @param type
   * @return
   */
  @RequestMapping(value = "/over", method = RequestMethod.POST)
  @ResponseBody
  public Page<Production> findOver(
          @PageableDefault(page = 0,
                  size = 20,
                  sort = {"productionId"},
                  direction = Sort.Direction.DESC) Pageable pageable,
          Long planId,
          String type) {
    Page<Production> pageResponse = productionService.findAll(planId, type, pageable);
    return pageResponse;
  }

  /**
   * 查询新建审核
   * TODO 可以查询old标记不是null,并且是在审核状态中的
   * @param planId
   * @param type
   * @return
   */
  @RequestMapping(value = "/modifyReview",method = RequestMethod.POST)
  public List<PriceRange> findNew(Long planId,String type){

    return productionService.findReview(planId, type);
  }

  /**
   * 跳转到具体区间页面
   *
   * @return
   */
  @RequestMapping(value = "/details", method = RequestMethod.GET)
  public String details(Long productionId,String check,String planId, Model model) {
    Production production = productionService.findById(productionId);
    model.addAttribute("productionId", productionId);
    model.addAttribute("check",check);
    model.addAttribute("planId",planId);
    //用于判断用户是不是审核人的
//    String userId = getUser().getId();
//    boolean flag = false;
//    if(userId.equals(production.getUserId()) || "1".equals(userId)){
//      flag = true;
//    }
//    model.addAttribute("flag", flag);
    model.addAttribute("managerId",getUser().getId());//当前用户
    model.addAttribute("userId",production.getUserId());//审核人id
    model.addAttribute("priceRangeStatus",production.getProductStatus());
    return "pricerange/price_range";
  }

  /**
   * 根据productionId查询所有的小区间
   *
   * @param
   * @return
   */
  @RequestMapping(value = "/details/{productionId}", method = RequestMethod.GET)
  @ResponseBody
  public List<PriceRange> searchDetails(@PathVariable("productionId") Long productionId) {

    return priceRangeService.findByProductionId(productionId);
  }

  /**
   * 查询正在使用的区间方案
   * @param type
   * @param planId
   * @return
   */
  @RequestMapping(value = "/findUse",method = RequestMethod.POST)
  @ResponseBody
  public List<PriceRange> searchNowUse(String type,Long planId){

    return productionService.findNowUse(type,planId);
  }

  /**
   * 渠道审核:审核production
   * 审核功能:(状态:审核通过/驳回)
   *
   * @return
   */
  @RequestMapping(value = "review", method = RequestMethod.POST)
  @ResponseBody
  public Long review(Long id, String status) {
    Production production= productionService.review(id, status);
    return production.getPlanId();
  }

  /**
   * 审核修改的小区间
   * @param priceRange
   * @param status
   * @return
   */
  @RequestMapping(value = "reviewPrice/{id}", method = RequestMethod.POST)
  @ResponseBody
  public JSONObject reviewPriceRange(@PathVariable("id") PriceRange priceRange, String status) {
    JSONObject jsonObject = new JSONObject();
    try {
      priceRangeService.reviewPriceRange(priceRange, status);
      jsonObject.put("result","success");
      jsonObject.put("msg","操作成功");
      return jsonObject;//成功
    }catch (Exception e){
      jsonObject.put("result","error");
      jsonObject.put("msg","系统异常,操作失败");
      return jsonObject;
    }
  }
  /*
     * 获取用户的方法
     */
  public User getUser() {
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    return user;
  }

  public static String getToday(){

    String today = DateUtil.date2String(new Date(),"yyyy-MM-dd");
    return today;
  }
}
