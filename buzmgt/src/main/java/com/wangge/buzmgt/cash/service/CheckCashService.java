package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.alibaba.fastjson.JSONObject;
import com.wangge.buzmgt.cash.entity.BankTrade;
import com.wangge.buzmgt.cash.entity.CheckCash;

public interface CheckCashService {
  /**
   * 查询银行交易记录列表
   * @param searchParams
   * @return
   */
  public  List<CheckCash> findAll(Map<String, Object> searchParams);
  
  /**
   * 查询分页银行交易记录列表
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public  Page<CheckCash> findAll(Map<String, Object> searchParams, Pageable pageRequest);

  /**
   * 账单审核
   * @param userId
   * @param createDate
   * @return
   */
  public JSONObject checkPendingByUserIdAndCreateDate(String userId,String createDate);
  
  /**
   * 删除未匹配的银行交易记录
   * @param userId
   * @param createDate
   * @return
   */
  public JSONObject deleteUnCheckBankTrade(BankTrade bankTrade);

  public List<BankTrade> getUnCheckBankTrades();

  public void exportSetExecl(List<CheckCash> content, HttpServletRequest request, HttpServletResponse response);

}
