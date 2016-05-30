package com.wangge.buzmgt.monthTask.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.monthTask.entity.MonthTaskExecution;

public interface MonthTaskExecutionRepository extends JpaRepository<MonthTaskExecution, Long> {
	/**
	 * 通过月份和店铺ID来查找访问记录
	 * 
	 * @param taskMonth
	 * @param memberId 店铺Id
	 * @return
	 */
	@EntityGraph("monthExecution.graph")
	List<MonthTaskExecution> findByTaskmonthAndRegistData_idOrderByTimeDesc(String taskMonth, Long memberId);
	
}
