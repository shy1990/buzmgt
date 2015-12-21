package com.wangge.buzmgt.region.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.region.vo.RegionTree;

@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionRepository regionRepository;
	
	@Override
	public List<RegionTree> findTreeRegion() {
		// TODO Auto-generated method stub
		List<RegionTree> listRegionTree = new ArrayList<RegionTree>();
		
		return listRegionTree;
	}


}
