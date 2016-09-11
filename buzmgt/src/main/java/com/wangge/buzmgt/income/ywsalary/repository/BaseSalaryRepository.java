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
  
  /**
   * findByFlagAndUser_Id:查找历史记录. <br/>
   * 
   * @author yangqc
   * @param id
   * @return
   * @since JDK 1.8
   */
  List<BaseSalary> findByFlagAndUser_Id(FlagEnum flag, String id);
  
  /*
   * select * from sys_income_basicsalay s where ((s.deldate is null and
   * s.newdate>=to_date('2016-08','yyyy-mm')) or (s.flag=1 and
   * s.deldate<to_date('2016-09','yyyy-mm'))) and s.user_id='A37152604120'
   */
  @Query("select s from BaseSalary s where ((s.deldate is null and s.newdate<?1)or "
      + "(s.flag=?2 and s.deldate<?1)) and  s.userId=?3")
  Page<BaseSalary>  findbyMonthAndUser(Date delDate,FlagEnum flag,String userId, Pageable page);
}
