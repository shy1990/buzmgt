package com.wangge.buzmgt.superposition.repository;

import com.wangge.buzmgt.superposition.entity.SingleIncome;
import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by joe on 16-10-15.
 */
public interface SingleIncomeRepository extends JpaRepository<SingleIncome,Long> {

    @Query(nativeQuery = true,value = "SELECT NVL(SUM(rd.OFFSET_NUMS),0) AS offset_nums,\n" +
            "  rd.USER_ID,\n" +
            "  rd.SUPER_ID,\n" +
            "  rd.PLAN_ID,\n" +
            "  rd.ORDER_ID\n" +
            "FROM SYS_SINGLE_RECORD rd\n" +
            "WHERE rd.PLAN_ID = ?\n" +
            "AND rd.USER_ID = ?\n" +
            "AND rd.SUPER_ID = ?\n" +
            "AND rd.status   = ?\n" +
            "AND rd.ORDER_ID   = ?\n" +
            "GROUP BY rd.USER_ID,\n" +
            "  rd.SUPER_ID,\n" +
            "  rd.PLAN_ID,\n" +
            "  rd.ORDER_ID")
    public SingleIncome findByUserIdAndPlanIdAndSuperIdAndStatusAndOrderId(String userId, Long planId, Long superId,String status,String orderId);

    //用于判断是否已经计算
    public List<SingleIncome> findByPlanIdAndSuperIdAndStatus(Long planId, Long superId, String status);

}
