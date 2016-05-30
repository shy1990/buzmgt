package com.wangge.buzmgt.monthTask.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wangge.buzmgt.monthTask.entity.MonthTask;

public interface MonthTaskRepository extends JpaRepository<MonthTask, Long> {
	@EntityGraph("monthData")
	Page<MonthTask> findByMonthAndRegionidLike(String month, String regionId, Pageable page);

	@EntityGraph("monthData")
	Page<MonthTask> findByMonthAndStatusAndRegionidLike(String month, Integer status, String regionId, Pageable page);

	@EntityGraph("monthData")
	Page<MonthTask> findByMonthAndStatusAndMonthData_Salesman_TruenameLike(String month, Integer status,
			String trueName, Pageable page);

	@EntityGraph("monthData")
	Page<MonthTask> findByStatusAndMonthData_Salesman_TruenameLike(Integer status, String trueName, Pageable page);

	/**
	 * 统计一个区域当月的已派发的任务
	 * 
	 * @param month
	 * @param status
	 * @return
	 */
	Long countByMonthAndStatusAndRegionidLike(String month, Integer status, String regionId);

	/**
	 * 通过id查找信息,返回相关历史数据
	 * 
	 * @param id
	 * @return
	 */
	@RestResource(path = "monthTask", rel = "monthTask")
	@EntityGraph("monthData")
	MonthTask findById(@Param("id") long id);
}
