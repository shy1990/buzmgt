package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.cash.entity.CheckCash;

public interface CheckCashService {
  /**
   * 查询银行交易记录列表
   * @param searchParams
   * @return
   */
  public  List<CheckCash> findAll(Map<String, Object> searchParams);
  
  /**
   * 根据创建日期查询银行交易记录
   * @param createDate
   * @return
   */
  public List<CheckCash> findByCreateDate(String createDate);
  /**
   * 查询分页银行交易记录列表
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public  Page<CheckCash> findAll(Map<String, Object> searchParams, Pageable pageRequest);

  
  
}
