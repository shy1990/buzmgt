package com.wangge.buzmgt.region.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.region.entity.Region;

public interface RegionRepository extends JpaRepository<Region, String> {
	public Region findById(String regionId);
	
	public Region findByNameLike(String regionName);
	
	public List<Region> findByParentId(String id);

}
