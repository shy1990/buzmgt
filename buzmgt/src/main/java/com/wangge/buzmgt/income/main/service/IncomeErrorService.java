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
  
  void saveScheduleError(int type, long jobId, String message);

  /** 
    * save:保存各种规则计算出错. <br/> 
    * @author yangqc 
    * @param orderno
    * @param userId 业务id
    * @param errorInfo  出错信息
    * @param goodId 商品id
    * @param type  规则类型  0 达量设置 1 品牌型号 2 价格区间3叠加奖励 4.达量奖励
    * @param keyId  规则id
    * @since JDK 1.8 
    */  
  void save(String orderno, String userId, String errorInfo, String goodId, Integer type, Long keyId);
}
