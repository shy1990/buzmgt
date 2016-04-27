package com.wangge.buzmgt.oilcost.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.oilcost.entity.OilCost;

public interface OilCostService {

//  public ReceiptRemark findByOrder(OrderSignfor orderNo);

  public List<OilCost> findAll();

  public Long findCount();

  /**
   * 查询油补记录
   * @param searchParams
   * @return
   */
  public List<OilCost> findAll(Map<String, Object> searchParams);

  /**
   * 查询油补记录
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public Page<OilCost> findAll(Map<String, Object> searchParams, Pageable pageRequest);

  /**
   * 查询油补统计（根据user_id分组统计计算）
   * 根据时间段计算油补费用and总里程
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public List<OilCost> findGroupByUserId(Map<String, Object> searchParams);
  
  /**
   * 处理 oilCostRecord
   * @param oilCost l
   */
  public void disposeOilCostRecord(OilCost oc);

  /**
   * 根据OilCost id 查询
   * @param id
   * @return
   */
  public OilCost findOne(Long id);

  /**
   * 处理握手顺序的导出数据
   * @param oilCostlist
   */
  public void recordSortUtil(List<OilCost> oilCostlist);
  
}

  
