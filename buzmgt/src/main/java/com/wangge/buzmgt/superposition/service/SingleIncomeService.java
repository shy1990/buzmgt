package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.SingleIncome;

/**
 * Created by joe on 16-10-15.
 */
public interface SingleIncomeService {

  public SingleIncome save(SingleIncome singleIncome);

  public SingleIncome findByUserIdAndPlanIdAndSuperIdAndStatus(String userId, Long planId, Long superId, String status, String orderId);

  public Boolean isCompare(Long planId, Long superId, String status);

  public SingleIncome findOffsetNums(String userId, Long planId, Long superId, String status);

}
