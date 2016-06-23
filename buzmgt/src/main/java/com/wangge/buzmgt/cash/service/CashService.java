package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.cash.entity.Cash;

public interface CashService {
  /**
   * 查询现金列表
   * @param searchParams
   * @return
   */
  public  List<Cash> findAll(Map<String, Object> searchParams);
  
  /**
   * 查询分页现金列表
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public  Page<Cash> findAll(Map<String, Object> searchParams, Pageable pageRequest);
  
  /**
   * 根据id查询现金订单
   * @param id
   * @return 
   */
  public Cash findById(Long id);

  /**
   * 处理状态
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public List<Cash> findAllByParams(Map<String, Object> searchParams);
}
