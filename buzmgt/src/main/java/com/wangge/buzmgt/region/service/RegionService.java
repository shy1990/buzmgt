package com.wangge.buzmgt.region.service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.RegionType;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.teammember.entity.SalesMan;

import java.util.List;
import java.util.Map;



public interface RegionService {
	
	
	public List<RegionTree> findTreeRegion(String id) ;
	
	
	public Region findListRegionbyid(String id);
	
	public Region findByNameLike(String reigonName);
	
	public void saveRegion(Region region); 
	
	public void delete(Region region);

	public List<RegionTree> getRegionByPid(String id);

	public Region getRegionById(String regionId);

	public List<Region> findByRegion(String regionId);

  public List<Region> getListByIds(SalesMan salesman);
  
  /**
   * 处理条件参数
   * 区域选择
   * @param
   * @param searchParams
   */
  public void disposeSearchParams(String Column, Map<String, Object> searchParams);

  public List<Region> findByTypeOrderById(RegionType type);


 
  /** 
    * getProvince:根据区域类型获取所有区域. <br/> 
    * @author yangqc 
    * @param type
    * @return 
    * @since JDK 1.8 
    */  
  public List<Map<String, Object>> getAllRegion(int type);


	List<RegionType> findALlRegionType();

	RegionType findByRegionTypeName(String name);


	RegionType findRegionType(int id);

	int findMaxId();

	void saveRegionType(RegionType regionType);

	void deleteRegionType(RegionType regionType);
}
