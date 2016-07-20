package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.cash.entity.WaterOrderCash;
import com.wangge.buzmgt.cash.entity.WaterOrderDetail;

public interface WaterOrderCashService {

  
  public List<WaterOrderCash> findAll();

  /**
   * 查询流水单号列表
   * @param searchParams
   * @return
   */
  public List<WaterOrderCash> findAll(Map<String, Object> searchParams);

  /**
   * 
  * @Title: findByUserIdAndCreateDateForPunish 
  * @Description: 查询某人某天流水单号是否有扣罚 
  * @param @param createDate
  * @param @param isPunish
  * @param @param userId
  * @param @return    设定文件 
  * @return List<WaterOrderCash>    返回类型 
  * @throws
   */
  public List<WaterOrderCash> findByUserIdAndCreateDateForPunish(String createDate,Integer isPunish,String userId);
  /**
   * 查询分页流水单号记录
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public Page<WaterOrderCash> findAll(Map<String, Object> searchParams, Pageable pageRequest);
  
  public WaterOrderCash findBySerialNo(String serialNo);

  public void save(List<WaterOrderCash> waterOrders);

  public void save(WaterOrderCash waterOrders);
  
  public void ExportSetExcel(List<WaterOrderCash> waterOrders, HttpServletRequest request,
      HttpServletResponse response);
  
}

  
