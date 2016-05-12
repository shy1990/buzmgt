package com.wangge.buzmgt.monthTask.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wangge.buzmgt.monthTask.service.MonthTaskService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;

@Controller
@RequestMapping("/monthTask")
public class TaskController {
	@Resource
	MonthTaskService monthTaskService;
	@Resource
	RegionRepository regoinRep;
	@Autowired
	private SalesManRepository salesRep;

	@RequestMapping("/list")
	public String init(String regoinId) {
		// monthTaskService.getList();
		return "month/month_task_list";
	}

	@RequestMapping("/handle")
	public String handle(String regoinId, Model model) {
		// monthTaskService.getList();
		return "month/task_month";
	}

	@RequestMapping("/addTask")
	public String addTask(String regionId, Model model) {
		if (regionId == null || regionId.isEmpty()) {
			regionId = "0";
		}
		Set<SalesMan> salesSet = new HashSet<SalesMan>();
		Region region = regoinRep.findById(regionId);
		if (!regionId.equals("0")) {
			salesSet.addAll(salesRep.readAllByRegionId(regionId));
		}
		model.addAttribute("salesList", salesSet);
		model.addAttribute("region", region);
		return "month/month_task_add";
	}

	/**
	 * 找出一个地区下面所有的业务员,速度太慢
	 * 
	 * @param salesSet
	 * @param region
	 */
	// private void findSubSaleMan(Set<SalesMan> salesSet, Region region) {
	// Collection<Region> regList = region.getChildren();
	// for (Region r1 : regList) {
	// if (!r1.getType().getName().equals(RegionType.TOWN)) {
	// salesSet.addAll(salesRep.readByRegionId(r1.getId()));
	// if (null != r1.getChildren()) {
	// findSubSaleMan(salesSet, r1);
	// }
	// }else{
	// return;
	// }
	// }
	// }
}
