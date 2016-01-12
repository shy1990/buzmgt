package com.wangge.buzmgt.region.service;

import java.util.List;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.sys.vo.RegionVo;



public interface RegionService {
	
	
	public List<RegionTree> findTreeRegionById(String id) ;
	
	
	public Region findListRegionbyid(String id);
	
	
	public void saveRegion(Region region); 
	
	public void delete(Region region);

	public List<RegionVo> getRegionByPid(String id);

	public Region getRegionById(String regionId);


  public List<RegionTree> findTreeRegion(String id, String returnId);


}
