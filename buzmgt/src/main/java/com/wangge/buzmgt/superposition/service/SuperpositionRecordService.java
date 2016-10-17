package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;

import java.util.List;

/**
 * Created by joe on 16-10-9.
 */
public interface SuperpositionRecordService {

    public SuperpositionRecord save(SuperpositionRecord superpositionRecord);

    public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperId(String userId,Long planId,Long superId);

    //查找冲减商品
    public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperIdAndStatus(String userId, Long planId, Long superId, String status );


    //判断是否已经计算
    public Boolean isCompare(Long planId,Long superId,String status);
}
