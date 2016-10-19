package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.SingleIncome;
import com.wangge.buzmgt.superposition.repository.SingleIncomeRepository;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by joe on 16-10-15.
 */
@Service
public class SingleIncomeServiceImpl implements SingleIncomeService {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SingleIncomeRepository singleIncomeRepository;
    @Override
    public SingleIncome save(SingleIncome singleIncome) {

        SingleIncome singleIncome1 = singleIncomeRepository.save(singleIncome);
        return singleIncome1;
    }

    @Override
    public SingleIncome findByUserIdAndPlanIdAndSuperIdAndStatus(String userId, Long planId, Long superId, String status,String orderId) {
        SingleIncome singleIncome = singleIncomeRepository.findByUserIdAndPlanIdAndSuperIdAndStatusAndOrderId(userId,planId,superId,status,orderId);
        return singleIncome;
    }

    @Override
    public Boolean isCompare(Long planId, Long superId, String status) {
        List<SingleIncome> singleIncomeList = singleIncomeRepository.findByPlanIdAndSuperIdAndStatus(planId,superId,status);
        Boolean flag = false;
        if(CollectionUtils.isNotEmpty(singleIncomeList)){
            flag = true;
        }

        return flag;
    }

    /**
     * 查询冲减量 计算前(status-"1")/计算后(status-"2")
     * @param userId
     * @param planId
     * @param superId
     * @param status
     * @return
     */
    @Override
    public SingleIncome findOffsetNums(String userId, Long planId, Long superId, String status) {
        String sql = "SELECT NVL(SUM(rd.OFFSET_NUMS),0) AS offset_nums,\n" +
                "  rd.USER_ID,\n" +
                "  rd.SUPER_ID,\n" +
                "  rd.PLAN_ID,\n" +
                "  rd.ORDER_ID\n" +
                "FROM SYS_SINGLE_RECORD rd\n" +
                "WHERE rd.PLAN_ID = 12\n" +
                "and rd.USER_ID = ?\n" +
                "AND rd.SUPER_ID = 1\n" +
                "AND rd.status   = 1\n" +
                "GROUP BY rd.USER_ID,\n" +
                "  rd.SUPER_ID,\n" +
                "  rd.PLAN_ID,\n" +
                "  rd.ORDER_ID";
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        int e = 4;
        Query query = entityManager.createNativeQuery(sql);
        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setParameter(a, planId);//方案id
        sqlQuery.setParameter(b, userId);//业务员id
        sqlQuery.setParameter(c, superId);//叠加方案id
        sqlQuery.setParameter(d, status);//状态
        sqlQuery.setParameter(e,userId);
        List<Object[]> list = sqlQuery.list();
        SingleIncome singleIncome = new SingleIncome();
        if (CollectionUtils.isNotEmpty(list)) {
            Object[] o = list.get(0);
            if(o != null){
                singleIncome.setOffsetNums(((BigDecimal) o[0]).intValue());
                singleIncome.setUserId(userId);
                singleIncome.setSuperId(superId);
                singleIncome.setPlanId(planId);
            }
        }
        return singleIncome;
    }
}
