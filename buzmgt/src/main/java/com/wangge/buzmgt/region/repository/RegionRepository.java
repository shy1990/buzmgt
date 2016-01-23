package com.wangge.buzmgt.region.repository;


import java.util.Iterator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.region.entity.Region;

public interface RegionRepository extends JpaRepository<Region, String> {
	public Region findById(String regionId);

	public List<Region> findByParentId(String id);

 // public List<String> findAll(Iterator<String> ids);


}
