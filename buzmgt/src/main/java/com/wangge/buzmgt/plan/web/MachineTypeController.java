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
  /**
   * 查询所有的种类（机型）
  * @Title: findAll 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @return    设定文件 
  * @return List<MachineType>    返回类型 
  * @throws
   */
  @RequestMapping(name="",method=RequestMethod.GET)
  @ResponseBody
  public List<MachineType> findAll(){
    return machineTypeServer.findAll();
  }
}
