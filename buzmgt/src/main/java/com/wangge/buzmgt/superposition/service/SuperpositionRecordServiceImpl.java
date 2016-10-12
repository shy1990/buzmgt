package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;
import com.wangge.buzmgt.superposition.repository.SuperpositionRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by joe on 16-10-9.
 */
@Service
public class SuperpositionRecordServiceImpl implements SuperpositionRecordService {
    @Autowired
    private SuperpositionRecordRepository recordRepository;

    @Override
    public SuperpositionRecord save(SuperpositionRecord superpositionRecord) {
        SuperpositionRecord superpositionRecord1 = recordRepository.save(superpositionRecord);
        return superpositionRecord1;
    }

    @Override
    public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperId(String userId, Long planId, Long superId) {
        SuperpositionRecord superpositionRecord = recordRepository.findBySalesmanIdAndPlanIdAndSuperId(userId, planId, superId);
        return superpositionRecord;
    }
}
