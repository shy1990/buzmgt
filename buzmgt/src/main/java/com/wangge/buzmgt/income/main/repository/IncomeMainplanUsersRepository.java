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
  Optional<Long> findFirst(String userId, FlagEnum states);
  
  @Query("select max(u.fqtime)  from IncomeMainplanUsers u where u.salesmanId=?1")
  Optional<Date> findMaxFqtimeBySalesmanId(String salesmanId);
  
  /**
   * 根据日期和三际商场的用户id找到对应的业务员及其收益主计划信息 <br/>
   * 
   * @author yangqc
   * @param Date
   * @param member_id
   * @return
   * @since JDK 1.8
   */
  @Query(value = "SELECT iu.plain_id, iu.salesman_id, u.region_id\n" + "  FROM biz_order_signfor s\n"
      + "  left join sys_salesman u on s.user_id = u.user_id\n" + "  left join sys_user us on u.user_id = us.user_id\n"
      + "  left join sys_income_mainplan_users iu on u.user_id = iu.salesman_id\n"
      + "  left join sys_income_plan_main m on iu.plain_id = m.id\n" + " where (us.status = 0 or (u.fireddate >= ?1))\n"
      + "   and m.createtime <= ?1    and (m.fqtime is null or m.fqtime >= ?1)\n"
      + "   and (iu.fqtime is null or iu.fqtime >= ?1)\n" + "   and (m.fqtime is null or m.fqtime >= ?1)\n"
      + "   and s.order_no = ?2\n" + "   and rownum < 2", nativeQuery = true)
  Object findsaleByDateAndOrderNo(Date Date, String orderNO);
  
  @Query(value = "SELECT iu.plain_id  FROM sys_user us  left join sys_salesman u on us.user_id=u.user_id \n"
      + "  left join sys_income_mainplan_users iu on us.user_id = iu.salesman_id\n"
      + "  left join sys_income_plan_main m on iu.plain_id = m.id \n" + " where (us.status=0 or (u.fireddate>=?1 ) ) \n"
      + "   and m.createtime <=?1    and (m.fqtime is null or m.fqtime >= ?1)\n"
      + "   and (iu.fqtime is null or iu.fqtime >= ?1)   and m.state = 0  and us.user_id = ?2\n"
      + "   and rownum < 2", nativeQuery = true)
  Optional<Long> findBysalesmanAndDate(Date payDate, String userId);
  
  @Query(value = "SELECT iu.salesman_id,iu.createtime,iu.fqtime,m.fqtime planfqsj,s.fireddate  FROM sys_user us\n"
      + "              left join sys_income_mainplan_users iu on us.user_id = iu.salesman_id\n"
      + "              left join  sys_salesman s on s.user_id=us.user_id \n"
      + "              left join sys_income_plan_main m on iu.plain_id = m.id  where \n"
      + "        m.createtime <=?1   and   (m.fqtime is null or m.fqtime >=?1 )      "
      + "       and (iu.fqtime is null or iu.fqtime >=?1 )\n "
      + "               and   (us.status=0 or (s.fireddate>=?1 )) and m.id=?2", nativeQuery = true)
  List<Object> findEffectiveUsersTime(Date startDate, Long planId);
  
  @Query(value = "SELECT iu.salesman_id,iu.createtime,iu.fqtime,m.fqtime planfqsj,s.fireddate  FROM sys_user us\n"
      + "              left join sys_income_mainplan_users iu on us.user_id = iu.salesman_id\n"
      + "              left join  sys_salesman s on s.user_id=us.user_id \n"
      + "              left join sys_income_plan_main m on iu.plain_id = m.id  where \n"
      + "            m.createtime <=?1   and    (m.fqtime is null or m.fqtime >=?1 ) "
      + "     and (iu.fqtime is null or iu.fqtime >?1 )   \n"
      + "               and   (us.status=0 or (s.fireddate<=?1 )) and m.id=?2 and us.user_id=?3 ", nativeQuery = true)
  Object findEffectiveUserTime(Date startDate, Long planId, String userId);
}
