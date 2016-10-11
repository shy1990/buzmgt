package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;

/**
 * Created by joe on 16-10-9.
 */
public interface SuperpositionRecordService {

    public SuperpositionRecord save(SuperpositionRecord superpositionRecord);

    public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperId(String userId,Long planId,Long superId);
}
