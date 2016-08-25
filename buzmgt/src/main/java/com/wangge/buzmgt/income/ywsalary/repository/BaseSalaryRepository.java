package com.wangge.buzmgt.income.ywsalary.repository;

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
  
  @Query("select max(s.times) from BaseSalary s where s.userId=?1")
  Integer findMaxTimesByUserId(String userId);
  
}
