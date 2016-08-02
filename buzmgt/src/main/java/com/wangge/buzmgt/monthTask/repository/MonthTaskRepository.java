package com.wangge.buzmgt.monthTask.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.wangge.buzmgt.monthTask.entity.MonthTask;

public interface MonthTaskRepository extends JpaRepository<MonthTask, Long> {
  /**
   * 通过区域来获取业务员列表
   * 
   * @param month
   * @param regionId
   * @param page
   * @return
   */
  @EntityGraph("monthData")
  Page<MonthTask> findByMonthAndRegionidLike(String month, String regionId, Pageable page);
  
  /**
   * 通过业务员姓名和月份来查找主任务
   * 
   * @param month
   * @param salesManName
   * @param page
   * @return
   */
  @EntityGraph("monthData")
  Page<MonthTask> findByMonthAndMonthData_Salesman_TruenameLike(String month, String salesManName, Pageable page);
  
  /**
   * 通过月份,发布状态和区号获取查找列表
   * 
   * @param month
   * @param status
   * @param regionId
   * @param page
   * @return
   */
  @EntityGraph("monthData")
  Page<MonthTask> findByMonthAndStatusAndRegionidLike(String month, Integer status, String regionId, Pageable page);
  
  /**
   * findBySetFinished:通过月份和区号获取查找已完成设置的任务 <br/>
   * 
   * @author yangqc
   * @param month
   * @param regionId
   * @param page
   * @return
   * @since JDK 1.8
   */
  @Query("select m from MonthTask m where m.month=?1 and m.regionid like ?2 and m.status=1 and tal4set>=tal4goal and "
      + "tal7set>=tal7goal and tal10set>=tal10goal and tal15set>=tal15goal and tal20set>=tal20goal ")
  Page<MonthTask> findBySetFinished(String month, String regionId, Pageable page);
  
  /**
   * findBySetFinished:通过月份和区号获取查找未完成设置的任务 <br/>
   * 
   * @author yangqc
   * @param month
   * @param regionId
   * @param page
   * @return
   * @since JDK 1.8
   */
  @Query("select m from MonthTask m where m.month=?1 and m.regionid like ?2 and m.status=1 and( tal4set<tal4goal or "
      + "tal7set<tal7goal or tal10set<tal10goal or tal15set<tal15goal or tal20set<tal20goal )")
  Page<MonthTask> findBySetUnFinished(String month, String regionId, Pageable page);
  
  /** 
    * findByDoneUnFinished:查找某区域下某月的任务未完成的主任务列表. <br/> 
    * @author yangqc 
    * @param month
    * @param regionId
    * @param page
    * @return 
    * @since JDK 1.8 
    */  
  @Query("select m from MonthTask m where m.month=?1 and m.regionid like ?2 and m.status=1 and( tal4done<tal4goal or "
      + "tal7done<tal7goal or tal10done<tal10goal or tal15done<tal15goal or tal20done<tal20goal )")
  Page<MonthTask> findByDoneUnFinished(String month, String regionId, Pageable page);
  
  /** 
    * findByDoneUnFinished:查找某区域下某月的任务已完成的主任务列表. <br/> 
    * @author yangqc 
    * @param month
    * @param regionId
    * @param page
    * @return 
    * @since JDK 1.8 
    */  
  @Query("select m from MonthTask m where m.month=?1 and m.regionid like ?2 and m.status=1 and( tal4done>=tal4goal and "
      + "tal7done>=tal7goal and tal10done>=tal10goal and tal15done>=tal15goal and tal20done>=tal20goal )")
  Page<MonthTask> findByDoneFinished(String month, String regionId, Pageable page);
  
  /**
   * 通过月份,发布状态,业务员姓名获取列表
   * 
   * @param month
   * @param status
   * @param trueName
   * @param page
   * @return
   */
  Page<MonthTask> findByMonthAndStatusAndMonthData_Salesman_TruenameLike(String month, Integer status, String trueName,
      Pageable page);
  
  /**
   * 通过状态和业务员姓名查找
   * 
   * @param status
   * @param trueName
   * @param page
   * @return
   */
  @EntityGraph("monthData")
  Page<MonthTask> findByStatusAndMonthData_Salesman_TruenameLike(Integer status, String trueName, Pageable page);
  
  /**
   * 统计一个区域当月的已派发的任务
   * 
   * @param month
   * @param status
   * @return
   */
  Long countByMonthAndStatusAndRegionidLike(String month, Integer status, String regionId);
  
  /**
   * 通过id查找信息,返回相关历史数据
   * 
   * @param id
   * @return
   */
  @RestResource(path = "monthTask", rel = "monthTask")
  @EntityGraph("monthData")
  MonthTask findById(@Param("id") long id);
}
