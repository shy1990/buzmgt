package com.wangge.buzmgt.salesman.web;

import com.wangge.buzmgt.salesman.entity.PunishSet;
import com.wangge.buzmgt.salesman.repository.PunishSetRepository;
import com.wangge.buzmgt.salesman.service.PunishSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/punish")
public class PunishSetController {
	@Autowired
	private PunishSetService punishSetService;
	@Autowired
	PunishSetRepository punishSetRepository;
	
	@RequestMapping(value="/list")
	public String show(){
		
		return "punishset/punish_set";
	}
	/**
	 * 查询全部
	 * @return
	 */
	@RequestMapping(value="/punishs",method=RequestMethod.GET)
	public String show1(Model model){
		List<PunishSet> list = punishSetService.findAll();
		PunishSet punishSet = punishSetService.findByRegionId("0");//默认的设置
		 model.addAttribute("list", list);
		 model.addAttribute("punishSet1", punishSet);
		 return "punishset/punish_set";
	}
	/**
	 * 添加资源
	 * @param
	 * @return
	 */
	@RequestMapping(value="/punishs",method=RequestMethod.POST)
	public @ResponseBody String save(@RequestParam("punishNumber")Float punishNumber,@RequestParam("regionId")String regionId){
		if(punishSetService.findByRegionId(regionId) != null){
			return "不能重复添加";
		}
		PunishSet punishSet = new PunishSet();
		punishSet.getRegion().setId(regionId.trim());
		System.out.println("********:"+punishSet.getRegion().getId());
		punishSet.setPunishNumber(punishNumber);
		punishSetService.save(punishSet);
		return "添加成功";
	}
	/**
	 * 修改
	 * @param id
	 * @param punishNumber
	 * @return
	 */
	@RequestMapping(value="/punishs/{id}",method=RequestMethod.PUT)
	//@PathVariable("id") Long id,
	public @ResponseBody String update(@PathVariable("id") Long id,Float punishNumber){
//		System.out.println(punishNumber);
		PunishSet punishSet = punishSetService.findById(id);
		punishSet.setPunishNumber(punishNumber);
		punishSetService.save(punishSet);
		return "修改成功";
	}
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/punishs/{id}",method=RequestMethod.DELETE)
	//@PathVariable("id") Long id,
	public @ResponseBody String delete(@PathVariable("id") Long id){
//		System.out.println(punishNumber);
		PunishSet punishSet = punishSetService.findById(id);
		punishSetService.delete(id);
		return "删除成功";
	}
	/**
	 * 区域默认  增加/修改
	 * @param
	 * @return
	 */
	@RequestMapping(value="/punishs/modify/{regionId}",method=RequestMethod.POST)
	public @ResponseBody String  defaultPunishSet(@PathVariable("regionId") String regionId,@RequestParam("punishNumber")Float punishNumber){
		PunishSet punishSet = punishSetService.findByRegionId(regionId);
		if(punishSet != null){
			System.out.println("not null");
			punishSet.setPunishNumber(punishNumber);
			punishSetService.save(punishSet);
			return "保存成功";
		}
		System.out.println("shi null");
		punishSet = new PunishSet();
		punishSet.getRegion().setId(regionId);
		punishSet.setPunishNumber(punishNumber);
		punishSetService.save(punishSet);
		
		return "保存成功";
	}
	
}
