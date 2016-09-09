package com.wangge.buzmgt.income.main.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.income.main.entity.IncomeSub;

public interface IncomeSubRepository extends JpaRepository<IncomeSub, Long> {
  
  @Transactional
  @Query(value = "update sys_income_ticheng_sub s set s.flag = 1\n" + " where exists (select 1\n"
      + "          from sys_income_plan_main m\n"
      + "          left join sys_income_mainplan_users u on m.id = u.plain_id\n"
      + "         where u.salesman_id = s.user_id\n"
      + "           and m.id = ? 1) s.createtime >= ? 2", nativeQuery = true)
  int modifyFlag(String mainPlanId,Date date);
}
