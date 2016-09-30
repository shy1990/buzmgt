package com.wangge.buzmgt.income.main.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;

public interface IncomeMainplanUsersRepository
    extends JpaRepository<IncomeMainplanUsers, Long>, JpaSpecificationExecutor<IncomeMainplanUsers> {
  List<IncomeMainplanUsers> findByMainplan_IdAndState(Long pid, FlagEnum flag);
  
  List<IncomeMainplanUsers> findByMainplan_Id(Long pid);
  
  @Query("update IncomeMainplanUsers u set u.state=?1 where u.id=?2")
  @Modifying(clearAutomatically = true)
  @Transactional
  void updateFlagById(FlagEnum flag, Long id);
  
  @Query("select u.planId from IncomeMainplanUsers u where u.salesmanId=?1 and u.state=?2")
  Optional<IncomeMainplanUsers> findFirst(String userId, FlagEnum states);
  
  @Query("select max(u.fqtime)  from IncomeMainplanUsers u where u.salesmanId=?1")
  Optional<Date> findMaxFqtimeBySalesmanId(String salesmanId);
  
  /**
   * 根据三际商场的用户id找到对应的业务员及其收益主计划信息 <br/>
   * 
   * @author yangqc
   * @param member_id
   * @return
   * @since JDK 1.8
   */
  @Query(value = "SELECT  iu.plain_id,iu.salesman_id,u.region_id\n" + "  FROM sys_registdata s\n"
      + "  left join sys_region r on r.region_id = s.region_id\n"
      + "  left join sys_salesman u on u.region_id = r.parent_id\n"
      + "  left join sys_user us on u.user_id = us.user_id\n"
      + "  left join sys_income_mainplan_users iu on u.user_id = iu.salesman_id\n"
      + "  left join sys_income_plan_main m on iu.plain_id = m.id\n" + " where us.status = 0\n"
      + "   and iu.state = 0\n" + "   and m.state = 0 \n" + "   and s.member_id =?1 and rownum<2 ", nativeQuery = true)
  Object findsaleByMemberId(String member_id);
  
  /**
   * 根据日期和三际商场的用户id找到对应的业务员及其收益主计划信息 <br/>
   * 
   * @author yangqc
   * @param Date
   * @param member_id
   * @return
   * @since JDK 1.8
   */
  @Query(value =
  "SELECT  iu.plain_id,iu.salesman_id,u.region_id   FROM sys_registdata s\n"
      + "        left join sys_region r on r.region_id = s.region_id\n"
      + "        left join sys_salesman u on u.region_id = r.parent_id\n"
      + "        left join sys_user us on u.user_id = us.user_id\n"
      + "        left join sys_income_mainplan_users iu on u.user_id = iu.salesman_id\n"
      + "        left join sys_income_plan_main m on iu.plain_id = m.id  where us.status = 0\n"
      + "         and m.createtime<?1 and (m.fqtime is null or m.fqtime>?1)\n"
      + "         and (iu.fqtime is null or  iu.fqtime<?1) and m.state = 0\n"
      + "          and s.member_id =?2 and rownum<2", nativeQuery = true)
  Object findsaleByDateAndMemberId(Date Date, String member_id);
}
