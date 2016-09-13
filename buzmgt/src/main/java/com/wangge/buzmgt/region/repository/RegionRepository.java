package com.wangge.buzmgt.region.repository;


import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.RegionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, String> {
	 Region findById(String regionId);
	
	 Region findByNameLike(String regionName);
	
	 List<Region> findByParentId(String id);

	List<Region> findByTypeOrderById(RegionType type);
	
}
