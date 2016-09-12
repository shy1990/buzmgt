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
public interface ProductionRepository extends JpaRepository<Production,Long> {

    //查找审核通过并且没有结束时间的(用于添加另一套的时候为这套方案添加结束时间)
    //结束时间是null 状态是 3--通过,type-手机类型(只有一套是这样的)
    public Production findByEndTimeIsNullAndProductStatusAndProductionType(String productStatus, String type);

    public Production findByProductionId(Long id);//根据id查询

    //查询当前正在使用的
    @Query(nativeQuery = true, value = "select *from sys_production \n" +
            " where \n" +
            "   (((to_date(?,'yyyy-MM-dd')>=IMPL_DATE and to_date(?,'yyyy-MM-dd')<=end_time)) \n" +
            " or \n" +
            "   ((to_date(?,'yyyy-MM-dd')>=IMPL_DATE and (end_time is null)) )) " +
            " and product_status = '3' " +
            " and  production_type = ? ")
    public Production findNow(String time1, String time2, String time3, String type);


    //查询没有过期的(不用管正在进行中,包括:待审核,被驳回,已经审核的)
    @Query(nativeQuery = true, value = "select * from sys_production p " +
            " where p.IMPL_DATE >= to_date(?,'yyyy-mm-dd') " +
            " and p.status = 0 and p.production_type = ? order by p.PRODUCTION_ID desc")
    public List<Production> findNotExpired(String today, String type);


    /**
     * 查询当期进行的(审核通过-未来要使用的,正在审核的,被驳回的)
     * @param type
     * @return
     */
    @Query(nativeQuery = true,value = "select * from sys_production p " +
            " where p.status = 0" +
            " and p.production_type = ? " +
            " and (p.product_status in ('0','1','2') or(p.product_status = '3' " +
            " and (p.END_TIME is null or p.END_TIME >=to_date(?,'yyyy-mm-dd')))) ")
    public List<Production> findStatus(String type, String today);




    public Page<Production> findAll(Specification specification, Pageable pageable);


}