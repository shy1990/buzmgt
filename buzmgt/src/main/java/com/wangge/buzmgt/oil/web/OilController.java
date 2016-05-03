package com.wangge.buzmgt.oil.web;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.oil.entity.OilParameters;
import com.wangge.buzmgt.oil.service.OilService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.service.ManagerService;

/**
 * 油补信息
 * @author 神盾局
 *
 */
@Controller
@RequestMapping("/oil")
public class OilController {
	@Autowired
	private ManagerService managerService;
	@Autowired
	private OilService oilService;
	
	/**
	 * 查询全部的信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/toOilSetList",method=RequestMethod.GET)
	public String oilSet(Model model){
		//oil_subsidy
		
		//获取user
		Subject subject = SecurityUtils.getSubject();
		User user=(User) subject.getPrincipal();
		Manager manager = managerService.getById(user.getId());
		model.addAttribute("regionId", manager.getRegion().getId());
		
		//查询全部的OilParameters
		List<OilParameters> lists = oilService.findAll();
		model.addAttribute("lists", lists);
		System.out.println("*********"+manager.getRegion().getId());
		return "oil/oil_subsidy";
	}
	/**
	 * 设置区域公里系数(添加)
	 * @param ratio：公里系数
	 * @param regionId：区域id
	 * @return
	 */
	@RequestMapping(value="/toOilSet",method=RequestMethod.POST)
	public @ResponseBody String addOilSet(@RequestParam("ratio")String ratio,@RequestParam("regionId")String regionId){
		/*
		 * 添加公里系数设置
		 */
		OilParameters oilParameter = new OilParameters();
		oilParameter.getRegion().setId(regionId);
		oilParameter.setKmRatio(Float.parseFloat(ratio));
		String result = oilService.save(oilParameter);
		return result;
	}
	/**
	 * 修改公里系数
	 * @return
	 */
	@RequestMapping(value="modifyOilParameter",method=RequestMethod.POST)
	
	public @ResponseBody String modifyOilParameter(OilParameters oilParameters,@RequestParam("regionId")String regionId ){
		System.out.println("...."+regionId);
		oilParameters.getRegion().setId(regionId);;
		String result = oilService.modify(oilParameters);
		
		return result;
	}
	
	/**
	 * 删除自定义区域的里程系数
	 * @return
	 */
	@RequestMapping(value="/delteOilParameterByRegionId",method=RequestMethod.POST)
	public@ResponseBody String delteOilParameterByRegionId(@RequestParam String regionId){
		System.out.println("****************************"+regionId);
		String result  = oilService.deletekmRatio(regionId);
		return result;
		
	}
	
	
	/**
	 * 添加自定义油补区域
	 * @param ratio
	 * @param regionId
	 * @return
	 */
	@RequestMapping(value="/toOilMoney",method=RequestMethod.POST)
	public @ResponseBody String addMoney(@RequestParam("kmOilSubsidy")String kmOilSubsidy,@RequestParam("regionId")String regionId){
		/*
		 * 添加油补
		 */
		OilParameters oilParameter = new OilParameters();
		oilParameter.getRegion().setId(regionId);;
		oilParameter.setKmOilSubsidy(Float.parseFloat(kmOilSubsidy));
		String result = oilService.saveMoney(oilParameter);
		return result;
	}
	
	
	/**
	 * 删除自定义区域油补金额
	 * @return
	 */
	@RequestMapping(value="/delteOilParameterByRegionId1",method=RequestMethod.POST)
	public@ResponseBody String delteOilParameterByRegionId1(@RequestParam String regionId){
		System.out.println("****************************"+regionId);
		String result  = oilService.deletekmOilSubsidy(regionId);
		return result;
		
	}
	
}
