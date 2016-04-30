package com.wangge.buzmgt.oil.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.oil.entity.OilParameters;
@Transactional//增、删、改的时候都要加这个标记，代表是置于统一的事务中 
public interface OilRepository extends JpaRepository<OilParameters, Long>{
	
	/*
	 *添加自定义设置区域：公里系数设置
	 */
	public OilParameters save(OilParameters oilParameters);
	 /*
	  *查询全部自定义里程系数的信息 
	  */
//	@Query(" from OilParameters a where a.kmRatio != null")
	public List<OilParameters> findAll();
	/*
	 * 根据regionId查询
	 */
	@Query("select a from OilParameters a where a.region.id = ?1")
	public OilParameters findOilParametersByRegionId(String regionId);
	
	
	
	/*
	 * 根据id查询信息
	 */
	public OilParameters findOilParametersById(String id);
	
	/*
	 * 根据id删除信息 
	 */
//	public String delete(String id);
	
	/*
	 * 根据id删除信息
	 */
	@Modifying
	@Query("delete  from OilParameters a where a.region.id= ?1")
	public int deleteByRegionId(String regionId);
		
}
