package com.wangge.buzmgt.monthTarget.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/monthTarget")
@Controller
public class MonthTargetController {
	
  @RequestMapping("/monthTarget")
  public String toMonthTarget(){
    
    return "monthTarget/mouth_target";
  }
  
  
  @RequestMapping("/monthSetting")
  public String toMonthSetting(){
    
    return "monthTarget/mouth_setting";
  }
}
