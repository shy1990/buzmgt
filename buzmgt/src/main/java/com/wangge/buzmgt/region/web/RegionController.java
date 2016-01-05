package com.wangge.buzmgt.region.web;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.UserService;
import com.wangge.buzmgt.sys.vo.RegionVo;

@Controller
@RequestMapping(value = "/region")
public class RegionController {
	private static final Logger logger = Logger
			.getLogger(RegionController.class);
	@Autowired
	private RegionService regionService;
	@Resource
	private UserService userService;
	
	private static final String ONELEAVE="0";
	@RequestMapping("/initRegion")
	public String initRegion(String test,Model model){
		return "region/region_view";
	}
	
	
	@RequestMapping(value = "/findOneRegion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionTree>> findOneRegion(HttpServletRequest request) {
		 List<RegionTree> listTreeVo =new ArrayList<RegionTree>();
		
		  listTreeVo=regionService.findTreeRegion(ONELEAVE);
		return new ResponseEntity<List<RegionTree>>(listTreeVo,HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/findRegionByid", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionTree>> findRegionByid(HttpServletRequest request,String id) {
		 List<RegionTree> listTreeVo =new ArrayList<RegionTree>();
		
		  listTreeVo=regionService.findTreeRegion(id);
		return new ResponseEntity<List<RegionTree>>(listTreeVo,HttpStatus.OK);
	}
	
	@RequestMapping(value="/getRegionById",method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionVo>> getRegionById(String id,HttpServletRequest request){
		String regionId = "0";
		if(id != null && !"".equals(id)){
			regionId = id;
		}else{
			Subject subject = SecurityUtils.getSubject();
			User user=(User) subject.getPrincipal();
			user = userService.getById(user.getId());
			regionId = String.valueOf(user.getOrganization().getId());
		}
		   List<RegionVo> regionList = regionService.getRegionByPid(regionId);
		return new ResponseEntity<List<RegionVo>>(regionList,HttpStatus.OK);
	}
	
}
