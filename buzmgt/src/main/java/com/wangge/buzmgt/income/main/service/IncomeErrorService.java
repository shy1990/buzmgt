package com.wangge.buzmgt.income.main.service;

/**
 * 出错记录服务<br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
public interface IncomeErrorService {
  /**
   * 使用于一般保存
   */
  void save(int type, String message);
  
  /**
   * 使用于具体规则计算的保存
   */
  void save(String orderno, String userId, String errorInfo, String goodId, Integer type);

  void saveScheduleError(int type, long jobId, String message);
}
