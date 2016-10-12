package com.wangge.buzmgt.superposition.repository;

import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by joe on 16-10-9.
 */
public interface SuperpositionRecordRepository extends JpaRepository<SuperpositionRecord,Long> {

    public SuperpositionRecord save(SuperpositionRecord superpositionRecord);

    public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperId(String userId,Long planId,Long superId);
}
