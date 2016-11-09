package com.wangge.buzmgt.income.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.income.schedule.entity.Jobtask;

public interface JobRepository extends JpaRepository<Jobtask, Long> {
  @Query("select t from Jobtask t where t.exectime<?1 and t.flag=0")
  List<Jobtask> defaltfindAll(Date date);
  
  @Query("select t from Jobtask t where t.exectime<?1 and t.flag=0 and t.type=11")
  List<Jobtask> findUserDel(Date date);
  
  @Transactional
  @Modifying(clearAutomatically = true)
  @Query("update Jobtask set flag=1 where id=?1")
  void updateFlag(Long id);
  @Transactional
  @Modifying(clearAutomatically = true)
  int deleteByTypeAndKeyid(Integer type,Long keyId);
}
