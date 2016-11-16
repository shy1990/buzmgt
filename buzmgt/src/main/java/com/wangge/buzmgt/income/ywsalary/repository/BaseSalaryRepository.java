package com.wangge.buzmgt.income.ywsalary.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;

public interface BaseSalaryRepository extends JpaRepository<BaseSalary, Long>, JpaSpecificationExecutor<BaseSalary> {
  @Override
  @EntityGraph("user")
  Page<BaseSalary> findAll(Specification<BaseSalary> spec, Pageable page);
  
  // findByFlagAndUser_Id:查找历史记录. <br/>
  List<BaseSalary> findByFlagAndUser_Id(FlagEnum flag, String id);
  
  /**
   * 查找delDate上个月的有效工资记录 <br/>
   * 
   * @param delDate
   *          有效月份的下个月
   * @param flag
   * @param id
   * @return
   * @since JDK 1.8
   */
  @Query("select s from BaseSalary s where ((s.deldate is null and s.newdate<?1)or "
      + "(s.flag=?2 and s.deldate<?1 and s.deldate>?4)) and  s.userId=?3")
  List<BaseSalary> findByFlagAndUser_Id(Date delDate, FlagEnum flag, String id, Date startDate);
  
  @Query(value = "select  s1.user_id   from (select s.*        from sys_income_basicsalay s\n"
      + "         where (s.deldate is null and          s.newdate < ?1)\n"
      + "            or (s.flag = 1 and s.deldate < ?1 and \n"
      + "               s.deldate > ?2 )) s1 group by s1.user_id\n"
      + "having count(s1.id) > 1", nativeQuery = true)
  List<Object> findUserIds(Date delDate,  Date startDate);
  
  /**
   * 查找delDate上个月的有效工资记录 <br/>
   * 
   * @param delDate
   *          有效月份的下个月
   * @param flag
   * @param id
   * @return
   * @since JDK 1.8
   */
  @Query("select s from BaseSalary s where ((s.deldate is null and s.newdate>?4)or "
      + "(s.flag=?2 and s.deldate<?1 and s.deldate>?4)) and  s.userId=?3")
  Page<BaseSalary> findbyMonthAndUser(Date delDate, FlagEnum flag, String userId, Date startDate, Pageable page);
}
