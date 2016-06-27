package com.wangge.buzmgt.monthTarget.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wangge.buzmgt.util.JsonResponse;

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
  
  @RequestMapping(value = "/toUpdate")
  public String toUpdate(){
    
    return "monthTarget/update";
  }
  
  @RequestMapping(value = "/save",method = RequestMethod.PUT)
  public JsonResponse save(){
    JsonResponse jr = new JsonResponse();
    
    return jr;
  }
}
