package com.wangge.buzmgt.income.schedule.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.income.schedule.entity.Jobtask;

public interface JobRepository extends JpaRepository<Jobtask, Long>{
  @Query("select t from Jobtask t where t.exectime<?1 and t.flag=0")
  List<Jobtask> defaltfindAll(Date date);
}
