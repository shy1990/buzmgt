package com.wangge.buzmgt.section.repository;

import com.wangge.buzmgt.section.entity.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by joe on 16-8-19.
 */
public interface ProductionRepository extends JpaRepository<Production, Long> {


  //查询没有结束时间的(审核通过的)
  @Query(nativeQuery = true, value = " select PRODUCTION_ID,CREATE_TIME,END_TIME,IMPL_DATE,PRODUCT_STATUS,PRODUCTION_AUDITOR,PRODUCTION_TYPE,STATUS,PLAN_ID,USER_ID,PRODUCTION_NAME from sys_production  " +
          " where end_time is null  " +
          " and plan_id = ? " +
          " and production_type = ?" +
          " and product_status = '3' " +
          " and status = 0 ")
  public Production findUseForEndTimeIsNull(Long planId, String productionType);

  public Production findByProductionId(Long id);//根据id查询

  /**
   * 根据付款时间查询出使用的哪个价格区间大方案
   *  当有结束时间的时候: 开始时间<= 付款时间 <= 结束时间
   *  没有结束时间的时候(上边的就不符合了): 开始时间 <=付款时间,并且结束时间是空
   * @param time1
   * @param time2
   * @param time3
   * @param type
   * @param planId
   * @return
   */
  @Query(nativeQuery = true, value = "select PRODUCTION_ID,CREATE_TIME,END_TIME,IMPL_DATE,PRODUCT_STATUS,PRODUCTION_AUDITOR,PRODUCTION_TYPE,STATUS,PLAN_ID,USER_ID,PRODUCTION_NAME " +
          " from sys_production \n" +
          " where \n" +
          "   (((to_date(?,'yyyy-MM-dd')>=IMPL_DATE and to_date(?,'yyyy-MM-dd')<=end_time)) \n" +
          " or \n" +
          "   ((to_date(?,'yyyy-MM-dd')>=IMPL_DATE and (end_time is null)) )) " +
          " and product_status = '3' " +
          " and  production_type = ? " +
          " and plan_id = ? " +
          " and status = 0 ")
  public Production findNow(String time1, String time2, String time3, String type, Long planId);


  //查询当前正在使用的
  @Query(nativeQuery = true, value = "select PRODUCTION_ID,CREATE_TIME,END_TIME,IMPL_DATE,PRODUCT_STATUS,PRODUCTION_AUDITOR,PRODUCTION_TYPE,STATUS,PLAN_ID,USER_ID,PRODUCTION_NAME " +
          " from sys_production \n" +
          " where \n" +
          "   (((to_date(?,'yyyy-MM-dd')>=IMPL_DATE and to_date(?,'yyyy-MM-dd')<=end_time)) \n" +
          " or \n" +
          "   ((to_date(?,'yyyy-MM-dd')>=IMPL_DATE and (end_time is null)) )) " +
          " and product_status = '3' " +
          " and  production_type in ? " +
          " and plan_id = ? " +
          " and status = 0 ")
  public Production findNow(String time1, String time2, String time3, List<String> types, Long planId);

  /**
   * 查询当期进行的(审核通过-未来要使用的,正在审核的,被驳回的)
   *
   * @param type
   * @return
   */
  @Query(nativeQuery = true, value = "select p.PRODUCTION_ID,p.CREATE_TIME,p.END_TIME,IMPL_DATE,p.PRODUCT_STATUS,p.PRODUCTION_AUDITOR,p.PRODUCTION_TYPE,p.STATUS,p.PLAN_ID,p.USER_ID,p.PRODUCTION_NAME from sys_production p " +
          " where p.status = 0" +
          " and p.plan_id=?" +
          " and p.production_type = ? " +
          " and (p.product_status in ('0','1','2') or(p.product_status = '3' " +
          " and (p.END_TIME is null or p.END_TIME >=to_date(?,'yyyy-mm-dd')))) " +
          " order by p.create_time desc")
  public List<Production> findStatus(Long planId, String type, String today);

  /**
   * 查询当前进行的(审核通过-未来要使用的,正在审核的,被驳回的)
   *
   * @param type
   * @return
   */
  @Query(nativeQuery = true, value = "select p.PRODUCTION_ID,p.CREATE_TIME,p.END_TIME,IMPL_DATE,p.PRODUCT_STATUS,p.PRODUCTION_AUDITOR,p.PRODUCTION_TYPE,p.STATUS,p.PLAN_ID,p.USER_ID,p.PRODUCTION_NAME from sys_production p " +
          " where p.status = 0" +
          " and p.plan_id=?" +
          " and p.production_type = ? " +
          " and (p.product_status in ('1','2') or(p.product_status = '3' " +
          " and (p.END_TIME is null or p.END_TIME >=to_date(?,'yyyy-mm-dd')))) " +
          " order by p.create_time desc")
  public List<Production> findStatus2Qd(Long planId, String type, String today);


  public Page<Production> findAll(Specification specification, Pageable pageable);

  //----------------------------------- 代码优化 ------------------------------------------------
  @Query(nativeQuery = true, value = "select p.PRODUCTION_ID,p.CREATE_TIME,p.END_TIME,IMPL_DATE,p.PRODUCT_STATUS,p.PRODUCTION_AUDITOR,p.PRODUCTION_TYPE,p.STATUS,p.PLAN_ID,p.USER_ID,p.PRODUCTION_NAME from sys_production p " +
          " where p.status = 0" +
          " and p.plan_id=?" +
          " and p.production_type = ? " +
          " and (p.product_status in ('0','1','2') or(p.product_status = '3' " +
          " and (p.END_TIME is null or p.END_TIME >=to_date(?,'yyyy-mm-dd')))) " +
          " order by p.create_time desc")
  public List<Production> findOnGoing(Long planId, String type, String today);//进行中的



}