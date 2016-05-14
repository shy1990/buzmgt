package com.wangge.buzmgt.monthTask.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.monthTask.entity.MonthTask;

public interface MonthTaskRepository extends JpaRepository<MonthTask, Long> {
	@Query(" select id,regionid,TAL20goal,TAL15set, m.month  from MonthTask m where m.month=?1")
	List<Map<String, Object>> findByMonth(String month);
}
