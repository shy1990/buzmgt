package com.wangge.buzmgt.region.repository;


import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, String> {
	public Region findById(String regionId);
	
	public Region findByNameLike(String regionName);
	
	public List<Region> findByParentId(String id);
	
	public List<Region> findByTypeOrderById(RegionType type);


	
}
