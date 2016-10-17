package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.SingleIncome;
import com.wangge.buzmgt.superposition.repository.SingleIncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by joe on 16-10-15.
 */
@Service
public class SingleIncomeServiceImpl implements SingleIncomeService {

    @Autowired
    private SingleIncomeRepository singleIncomeRepository;
    @Override
    public SingleIncome save(SingleIncome singleIncome) {

        SingleIncome singleIncome1 = singleIncomeRepository.save(singleIncome);
        return singleIncome1;
    }
}
