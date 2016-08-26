package com.wangge.buzmgt.income.main.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.UserService;

@Controller
@RequestMapping("/mainPlan")
public class MainPlanController {
  @Autowired
  RegionService regionService;
  @Autowired
  MainPlanService mainPlanService;
  @Resource
  private UserService userService;
  
  @RequestMapping("/index")
  public String init(Model model) {
    model.addAttribute("regions", regionService.findByTypeOrderById(RegionType.PROVINCE));
    return "/income/main/index";
  }
  
  @RequestMapping(value = "/queryPlan")
  public ResponseEntity<?> queryUsers(@RequestParam(name = "regionId", required = false) String regionId,
      HttpServletRequest request, HttpServletResponse response, Pageable pageReq) {
    Page<?> page = mainPlanService.findAll(regionId, pageReq);
    return new ResponseEntity<Page<?>>(page, HttpStatus.OK);
  }
  
  @RequestMapping(value = "/newPlan")
  public String initNew(Model model) {
    model.addAttribute("regions", regionService.findByTypeOrderById(RegionType.PROVINCE));
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    user = userService.getById(user.getId());
    model.addAttribute("organization", user.getOrganization().getChildren());
    model.addAttribute("machineType", mainPlanService.getAllMachineType());
    model.addAttribute("allBrand", mainPlanService.getAllBrandType());
    return "/income/main/newPlan";
  }
}
