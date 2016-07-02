package com.wangge.buzmgt.monthTask.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wangge.buzmgt.monthTask.entity.MonthOdersData;

@RepositoryRestResource(path = "/monthdata")
public interface MonthOrdersDataRepository extends JpaRepository<MonthOdersData, Long> {
	/**
	 * 查找某一个地区或业务员的历史数据
	 * 
	 * @param salesmanid业务员
	 * @param regionId地区
	 * @param month月份
	 * 
	 * @return
	 */
	@RestResource(path = "defaultfinddata", rel = "defaultfinddata")
	@Query(" select m from MonthOdersData m where m.month=:month and  m.salesman.id=:salemanid   "
			+ " and m.regionId is not null and m.salesman is not null")
	public MonthOdersData findFirst1bySalesmanOrRegionId(@Param("salemanid") String salesmanid,
			 @Param("month") String month);
}
