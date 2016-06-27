package com.wangge.buzmgt.monthTarget.service;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.monthTarget.repository.MonthTargetRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.service.ManagerService;

@Service
public class MonthTargetServiceImpl implements MonthTargetService {
  @Resource
  private MonthTargetRepository mtr;
  @Resource
  private ManagerService managerService;
  @Resource
  private RegionService regionService;
  
  
  @Override
  public Region getRegion() {
    // 获取user
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    String regionId = manager.getRegion().getId();
    return regionService.getRegionById(regionId);
  }
  
}
