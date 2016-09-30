package com.wangge.buzmgt.achieveset.repository;

import java.math.BigDecimal;

/**
 * 
* @ClassName: AchieveIncomeRepositoryCustom
* @Description: 达量提成自定义sql执行
* @author ChenGuop
* @date 2016年9月23日 上午9:58:22
*
 */
public interface CustomRepository {

  /**
   * 
  * @Title: sumByAchieveid 
  * @Description: 根据Achieveid查询达量提成总金额
  * @param @param achieveId
  * @param @return    设定文件 
  * @return BigDecimal    返回类型 
  * @throws
   */
  BigDecimal sumMoneyByAchieveId(Long achieveId);
  /**
   * 
  * @Title: sumByAchieveIdAndUserId 
  * @Description: 根据AchieveId和UserId算达量提成总金额
  * @param @param achieveId
  * @param @param userId
  * @param @return    设定文件 
  * @return BigDecimal    返回类型 
  * @throws
   */
  BigDecimal sumMoneyByAchieveIdAndUserId(Long achieveId,String userId);

}
