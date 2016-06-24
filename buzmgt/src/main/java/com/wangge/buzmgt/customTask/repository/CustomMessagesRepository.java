package com.wangge.buzmgt.customTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.customTask.entity.CustomMessages;

public interface CustomMessagesRepository extends JpaRepository<CustomMessages, Long> {
	@Query(value = "select t1.sum1, t2.sum2\n" + "  from (select count(1) sum2\n"
			+ "          from sys_custom_message t\n" + "         where t.customtask_id = ?1\n"
			+ "           and t.roletype = 1) t2\n" + "  left join (select count(1) sum1\n"
			+ "               from sys_custom_message t \n" + "              where t.customtask_id = ?1\n"
			+ "                and t.status = 0\n"
			+ "                and t.roletype = 1) t1 on 1 = 1", nativeQuery = true)
	Object countByRoleType(Long customtaskId);
	@Query("")
	Long countBySalesmanId();
}
