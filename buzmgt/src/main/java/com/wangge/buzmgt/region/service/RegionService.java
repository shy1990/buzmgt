package com.wangge.buzmgt.region.service;

import java.util.List;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.sys.vo.RegionVo;



public interface RegionService {
	
	
	public List<RegionTree> findTreeRegion(String id) ;

	public List<RegionVo> getRegionByPid(String id);

	public Region getRegionById(String regionId);
}
