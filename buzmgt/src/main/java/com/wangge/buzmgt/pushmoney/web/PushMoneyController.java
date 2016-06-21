package com.wangge.buzmgt.pushmoney.web;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wangge.buzmgt.pushmoney.entity.PriceScope;
import com.wangge.buzmgt.pushmoney.service.PushMoneyService;

@Controller
@RequestMapping("/pushMoney")
public class PushMoneyController {

  private Logger logger=Logger.getLogger(PushMoneyController.class);
  
  @Resource
  private PushMoneyService pushMoneyService;
  
  @RequestMapping("show")
  public String toPushMoneySet(Model model){
    List<PriceScope> list=pushMoneyService.findPriceScopeAll();
    model.addAttribute("priceScopes", list);
    model.addAttribute("", "");
    return "ywsalary/push_money_set";
  }
}
