package com.wangge.buzmgt.areaattribute.web;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.areaattribute.service.AreaAttributeService;
import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.service.BrandIncomeService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.service.PriceRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 提成区域属性controller
 */
@Controller
@RequestMapping("/areaAttr")
public class AreaAttributeController {

  @Autowired
  private AreaAttributeService areaAttributeService;
  @Autowired
  private BrandIncomeService brandIncomeService;
  @Autowired
  private PriceRangeService priceRangeService;

  /**
   * 跳转到提成区域属性设置
   *
   * @return
   */
  @RequestMapping( value = "/setting", method = RequestMethod.GET)
  public String toAreaAttr(String ruleId, String type, Model model) {
    List<AreaAttribute> areaAttributes = null;
    if (PlanType.BRANDMODEL.name().equals(type)) {
      BrandIncome brandIncome = brandIncomeService.findById(Long.valueOf(ruleId));
      areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(Long.valueOf(ruleId),PlanType.BRANDMODEL);
      model.addAttribute("brandIncome", brandIncome);
    }else if (PlanType.PRICERANGE.name().equals(type)){
      PriceRange priceRange = priceRangeService.findById(Long.valueOf(ruleId));
      areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(Long.valueOf(ruleId),PlanType.PRICERANGE);
      model.addAttribute("priceRange",priceRange);
    }
    model.addAttribute("planType",type);
    model.addAttribute("areaAttributes",areaAttributes);
    return "areaattribute/area_attr_set";
  }

  /**
   * 添加区域属性提成
   *
   * @return
   */
  @RequestMapping( value = "/save", method = RequestMethod.POST)
  public @ResponseBody String save(@RequestParam("commission")double commission, @RequestParam("regionId")Region region, @RequestParam("ruleId")String ruleId,String type) {
    String result = areaAttributeService.save(commission,region,ruleId,type);
    return result;
  }

  /**
   * 修改
   * @param id
   * @param commission
   * @return
   */
  @RequestMapping(value="/{id}",method=RequestMethod.PUT)
  public @ResponseBody String update(@PathVariable("id") Long id, double commission){
    AreaAttribute areaAttribute = areaAttributeService.findById(id);
    areaAttribute.setCommissions(commission);
    areaAttributeService.save(areaAttribute);
    return "修改成功!";
  }

  /**
   * 逻辑删除
   * @param id
   * @return
   */
  @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
  public @ResponseBody String delete(@PathVariable("id") Long id){
    AreaAttribute areaAttribute = areaAttributeService.findById(id);
    areaAttributeService.delete(areaAttribute);
    return "删除成功!";
  }

  /**
   * 跳转到提成区域属性查看
   *
   * @return
   */
  @RequestMapping( value = "/show", method = RequestMethod.GET)
  public String show(String ruleId, String type, Model model) {
    List<AreaAttribute> areaAttributes = null;
    if (PlanType.BRANDMODEL.name().equals(type)) {
      BrandIncome brandIncome = brandIncomeService.findById(Long.valueOf(ruleId));
      areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(Long.valueOf(ruleId),PlanType.BRANDMODEL);
      model.addAttribute("brandIncome", brandIncome);
    }else if (PlanType.PRICERANGE.name().equals(type)){
      PriceRange priceRange = priceRangeService.findById(Long.valueOf(ruleId));
      areaAttributes = areaAttributeService.findByRuleIdAndTypeAndDisabled(Long.valueOf(ruleId),PlanType.PRICERANGE);
      model.addAttribute("priceRange",priceRange);
    }
    model.addAttribute("areaAttributes",areaAttributes);
    return "areaattribute/area_attr_show";
  }
}