package com.wangge.buzmgt.monthTask.repository;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.monthTask.entity.MonthTaskSub;

public interface MonthTaskSubRepository extends JpaRepository<MonthTaskSub, Long> {

	@EntityGraph("monthTaskSub.monthsd")
	Page<MonthTaskSub> findByMonthTask_IdOrderByGoalDesc(Long id, Pageable page);

	@EntityGraph("monthTaskSub.monthsd")
	Page<MonthTaskSub> findByMonthTask_IdAndGoalOrderByGoalDesc(Long id, Integer goal, Pageable page);
	@Query("select goal, monthsd.registData.shopName  from MonthTaskSub t where t.monthTask.id=?1 order by goal desc")
	Page<Object> findByMonthTask(Long id, Pageable page);
	@Query("select goal, monthsd.registData.shopName  from MonthTaskSub t where t.monthTask.id=?1 order by goal desc")
	List<Object> findByMonthTask_Id(Long id);
}
