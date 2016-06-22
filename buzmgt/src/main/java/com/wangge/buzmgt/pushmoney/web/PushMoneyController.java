package com.wangge.buzmgt.pushmoney.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.pushmoney.entity.Category;
import com.wangge.buzmgt.pushmoney.entity.PriceScope;
import com.wangge.buzmgt.pushmoney.entity.PushMoney;
import com.wangge.buzmgt.pushmoney.entity.PushMoneyRegion;
import com.wangge.buzmgt.pushmoney.service.PushMoneyService;
import com.wangge.json.JSONFormat;

@Controller
@RequestMapping("/pushMoney")
public class PushMoneyController {

  private Logger logger=Logger.getLogger(PushMoneyController.class);
  
  @Resource
  private PushMoneyService pushMoneyService;
  
  private static final String SEARCH_OPERTOR = "sc_";
  
  @RequestMapping("/show")
  public String toPushMoneySet(Model model){
    List<PriceScope> list=pushMoneyService.findPriceScopeAll();
    List<Category> categories=pushMoneyService.findCategoryAll();
    model.addAttribute("priceScopes", list);
    model.addAttribute("categories", categories);
    return "ywsalary/push_money_set";
  }
  @RequestMapping(value="",method=RequestMethod.GET)
  @JSONFormat(filterField = { "Region.children", "Region.parent",
  "SalesMan.user" }, nonnull = true, dateFormat = "yyyy-MM-dd HH:mm")
  public Page<PushMoney> getPushMoney(HttpServletRequest request,
     @PageableDefault(page=0, size=10,sort={"createDate"},direction=Direction.DESC) Pageable pageable){
    Map<String, Object> searchParams=WebUtils.getParametersStartingWith(request, SEARCH_OPERTOR);
    Page<PushMoney> page=pushMoneyService.findAll(searchParams, pageable);
    return page;
  }
  
  @RequestMapping(value="",method=RequestMethod.POST)
  @ResponseBody
  public JSONObject add(PushMoney pushMoney){
    JSONObject json=new JSONObject();
    try {
      pushMoneyService.save(pushMoney);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      logger.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  @RequestMapping(value="/{id}",method=RequestMethod.POST)
  @ResponseBody
  public JSONObject update(@PathVariable("id") PushMoney pushMoney,@RequestParam Integer money){
    JSONObject json=new JSONObject();
    try {
      pushMoneyService.save(pushMoney);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      logger.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  
  @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
  @ResponseBody
  public JSONObject delete(@PathVariable("id") Integer id ){
    JSONObject json=new JSONObject();
    try {
      pushMoneyService.delete(id);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      logger.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  
  
  @RequestMapping(value="/addRegion",method=RequestMethod.POST)
  @ResponseBody
  public JSONObject addPushMoneyRegion(PushMoneyRegion pushMoneyRegion){
    JSONObject json=new JSONObject();
    try {
      pushMoneyService.save(pushMoneyRegion);
      json.put("status", "success");
      json.put("successMsg", "操作成功！");
    } catch (Exception e) {
      logger.info(e.getMessage());
      json.put("status", "failure");
      json.put("errorMsg", "操作失败！");
      return json;
    }
    return json;
  }
  
  
}
