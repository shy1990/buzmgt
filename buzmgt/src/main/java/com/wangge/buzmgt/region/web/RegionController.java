package com.wangge.buzmgt.region.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.region.vo.RegionTree;

@Controller
@RequestMapping(value = "/region")
public class RegionController {
	private static final Logger logger = Logger
			.getLogger(RegionController.class);
	@Autowired
	private RegionService regionService;
	
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
	
}
