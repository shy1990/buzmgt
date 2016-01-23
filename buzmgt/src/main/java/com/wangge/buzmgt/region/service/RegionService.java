package com.wangge.buzmgt.region.service;

import java.util.Iterator;
import java.util.List;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.sys.vo.RegionVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;



public interface RegionService {
	
	
	public List<RegionTree> findTreeRegion(String id) ;
	
	
	public Region findListRegionbyid(String id);
	
	
	public void saveRegion(Region region); 
	
	public void delete(Region region);

	public List<RegionTree> getRegionByPid(String id);

	public Region getRegionById(String regionId);

	public List<Region> findByRegion(String regionId);


  public List<Region> getListByIds(SalesMan salesman);

}
