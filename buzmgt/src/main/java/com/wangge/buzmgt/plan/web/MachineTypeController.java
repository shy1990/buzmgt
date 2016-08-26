package com.wangge.buzmgt.plan.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.plan.entity.MachineType;
import com.wangge.buzmgt.plan.server.MachineTypeServer;

@Controller
@RequestMapping("/machineType")
public class MachineTypeController {

  @Autowired
  private MachineTypeServer machineTypeServer;
  
  @RequestMapping(name="",method=RequestMethod.GET)
  @ResponseBody
  public List<MachineType> findAll(){
    return machineTypeServer.findAll();
  }
}
