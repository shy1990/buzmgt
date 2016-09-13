package com.wangge.buzmgt.income.main.repository;

import java.util.Date;
import java.util.List;

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
      + "           and m.id = ? 1) and s.createtime >= ? 2", nativeQuery = true)
  int modifyFlag(Long mainPlanId, Date date);
  
  /**
   * 查询某主方案下某用户已付款的某月且大于某个特定日期 订单收益列表<br/>
   * 
   * @return
   * @since JDK 1.8
   */
  @Query(value = "select s.PLAN_TYPE, sum(s.INCOME) money\n" + "  from (select s1.*\n"
      + "          from sys_income_ticheng_sub s1\n" + "         where s1.mainplan_id = ?1 \n"
      + "           and s1.countdate >= ?2 \n" + "           and s1.user_id = ?3 \n"
      + "           and to_char(s1.countdate, 'yyyy-mm') = ?4 \n" + "           and s1.orderflag = 1) s \n"
      + " group by s.PLAN_TYPE ", nativeQuery = true)
  List<Object> getMainPlainOrdersByUserAndDate(Long mainPlanId, Date date, String userId, String month);
}
