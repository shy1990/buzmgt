package com.wangge.buzmgt.region.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.util.RegionUtil;

@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionRepository regionRepository;
	
	@Override
	public List<RegionTree> findTreeRegion(String id) {
		List<RegionTree> listRegionTree = new ArrayList<RegionTree>();
		for (Region region : regionRepository.findOne(id).getChildren()) {
			listRegionTree.add(RegionUtil.getRegionTree(region));
		}
		return listRegionTree;
	}

	@Override
	public Region findListRegionbyid(String id) {
		
		return regionRepository.findOne(id);
	}

	@Override
	public void saveRegion(Region region) {
		regionRepository.save(region);
	}

	@Override
	public void delete(Region region) {
		regionRepository.delete(region);
		
	}

	
	
	
}
