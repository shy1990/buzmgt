package com.wangge.buzmgt.region.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	@Autowired
	private RegionService regionService;
	
	@RequestMapping("/initRegion")
	public String initRegion(String test,Model model){
		return "region/region_view";
	}
	
	
	@RequestMapping(value = "/findOneRegion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<RegionTree>> findOneRegion(HttpServletRequest request) {
		 List<RegionTree> listTreeVo =new ArrayList<RegionTree>();
		
		
		return new ResponseEntity<List<RegionTree>>(listTreeVo,HttpStatus.OK);
	}
	
}
