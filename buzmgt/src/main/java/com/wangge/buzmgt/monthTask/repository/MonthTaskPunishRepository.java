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
import com.wangge.buzmgt.monthTask.entity.MonthTaskPunish;

public interface MonthTaskPunishRepository extends JpaRepository<MonthTaskPunish, Long> {
	/**查找默认设置  1
	 * @param id
	 * @return
	 */
	MonthTaskPunish findById(long id);
	/**查找不包含某个id值得列表
	 * @param id
	 * @param page
	 * @return
	 */
	Page<MonthTaskPunish> findByIdNot(long id,Pageable page);
}
