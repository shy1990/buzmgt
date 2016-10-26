package com.wangge.buzmgt.common;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: 收益公用类;
 * 
 * <br/>
 * date: 2016年10月14日 下午3:22:07 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
public class IncomeCommon {
  /**
   * 收益计算用线程池,暂定30个
   */
  public static ExecutorService EXECUTORSERVICEPOOL = Executors.newFixedThreadPool(30);
 
  /**
   * key: 规则id+业务id; value :数量
   */
  public static Map<String, Integer> achieveCachedMap = new HashMap<>();

  static Integer getAchievAmount(Long ruleId, String userId, int num) {
    synchronized (IncomeCommon.achieveCachedMap) {
      String key = ruleId + "+" + userId;
      Integer val = IncomeCommon.achieveCachedMap.get(key);
      if (val == null) {
        // 查询总数量

      }
      val += num;
      IncomeCommon.achieveCachedMap.put(key, val);
      return val;
    }

  }

  /**
   * key:规则id+业务id; value :数量
   */
  private static Map<String, Integer> superCachedMap = new HashMap<>();
  
}
