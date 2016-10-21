package com.wangge.buzmgt.superposition.repository;

import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by joe on 16-10-9.
 */
public interface SuperpositionRecordRepository extends JpaRepository<SuperpositionRecord,Long> {

    public SuperpositionRecord save(SuperpositionRecord superpositionRecord);

    public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperId(String userId,Long planId,Long superId);


//    @Query(nativeQuery = true,value = "select nvl(sum(rd.OFFSET_NUMS),0) as offset_nums,rd.SALESMAN_ID,rd.SUPER_ID,rd.PLAN_ID from SYS_SUPERPOSITION_RECORD rd\n" +
//            "where rd.PLAN_ID = ?\n" +
//            "and rd.SALESMAN_ID = ?\n" +
//            "and rd.SUPER_ID = ?\n" +
//            "and rd.STATUS = ? \n" +
//            "group by \n" +
//            "rd.SALESMAN_ID,rd.SUPER_ID,rd.PLAN_ID")
    public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperIdAndStatus(String userId,Long planId,Long superId,String status);

    //判断是否已经计算
    public List<SuperpositionRecord> findByPlanIdAndSuperIdAndStatus(Long planId,Long superId,String status);





}
