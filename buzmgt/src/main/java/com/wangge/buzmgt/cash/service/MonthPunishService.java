package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.cash.entity.MonthPunish;

public interface MonthPunishService {
  /**
   * 查询银行交易记录列表
   * @param searchParams
   * @return
   */
  public  List<MonthPunish> findAll(Map<String, Object> searchParams);
  
  /**
   * 查询分页银行交易记录列表
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public  Page<MonthPunish> findAll(Map<String, Object> searchParams, Pageable pageRequest);

  public void save(MonthPunish mp);

  public void save(List<MonthPunish> monthPunishs);

  public List<MonthPunish> findAllISNotCash(Map<String, Object> spec);

  /**
   * 
  * @Title: findByUserIdAndCreateDate 
  * @Description: 查询某一用户某一天的扣罚列表
  * @param @param userId
  * @param @param date2String
  * @param @return    设定文件 
  * @return List<MonthPunish>    返回类型 
  * @throws
   */
  public List<MonthPunish> findByUserIdAndCreateDate(String userId, String date2String);

  
}
