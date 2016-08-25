package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.cash.entity.BankTrade;

public interface BankTradeService {
  /**
   * 查询银行交易记录列表
   * 
   * @param searchParams
   * @return
   */
  public List<BankTrade> findAll(Map<String, Object> searchParams);

  /**
   * 查询分页银行交易记录列表
   * 
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public Page<BankTrade> findAll(Map<String, Object> searchParams, Pageable pageRequest);

  /**
   * 保存导入excel文件内容
   * 
   * @param excelContent
   * @return
   */
  public List<BankTrade> save(Map<Integer, String> excelContent, String importDate);

  /**
   * 批量保存数据
   * 
   * @param bankTrades
   * @return
   */
  public List<BankTrade> save(List<BankTrade> bankTrades);

  /**
   * 批量删除数据
   * 
   * @param bankTrades
   * @return
   */
  public void delete(List<BankTrade> bankTrades);

  public void delete(BankTrade bankTrade);

  long countByIsArchiveAndImportDate(Integer tag, String importDate);

}
