package com.wangge.buzmgt.monthTask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.wangge.buzmgt.monthTask.entity.MonthTaskSub;

public interface MonthTaskSubRepository extends JpaRepository<MonthTaskSub, Long> {
	Page<MonthTaskSub> findByParentid(@Param("parentid") String parentId, Pageable pagReq);
}
