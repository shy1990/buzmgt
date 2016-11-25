package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.SuperpositionRecord;
import com.wangge.buzmgt.superposition.repository.SuperpositionRecordRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

  /**
   * 查找冲减商品
   *
   * @param userId
   * @param planId
   * @param superId
   * @param status
   * @return
   */
  @Override
  public SuperpositionRecord findBySalesmanIdAndPlanIdAndSuperIdAndStatus(String userId, Long planId, Long superId, String status) {

    SuperpositionRecord superpositionRecords = recordRepository.findBySalesmanIdAndPlanIdAndSuperIdAndStatus(userId, planId, superId, status);
    return superpositionRecords;
  }

  @Override
  public Boolean isCompare(Long planId, Long superId, String status) {
    List<SuperpositionRecord> superpositionRecords = recordRepository.findByPlanIdAndSuperIdAndStatus(planId, superId, status);
    Boolean flag = false;
    if (CollectionUtils.isNotEmpty(superpositionRecords)) {
      flag = true;
    }
    return flag;
  }

  /**
   * 根据发放日期和业务员id查找记录
   *
   * @param time :yyyy-MM
   * @param salesmanId
   * @return
   */
  @Override
  public List<SuperpositionRecord> findByGiveDateAndSalesmanId(String time, String salesmanId) {

    return recordRepository.findByGiveDateLikeAndSalesmanId("%" + time + "%", salesmanId);
  }
}
