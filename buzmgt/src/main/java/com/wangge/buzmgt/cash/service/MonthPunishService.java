package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.Cash;
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

  
}
