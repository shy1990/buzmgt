package com.wangge.buzmgt.achieveset.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Sort;

import com.wangge.buzmgt.achieveset.entity.AchieveIncome;

/**
 * 
* @ClassName: AchieveIncomeService
* @Description: 达量奖励业务层接口
* @author ChenGuop
* @date 2016年9月23日 上午9:43:37
*
 */
public interface AchieveIncomeService {
  /**
   * 
  * @Title: countByAchieveId 
  * @Description: 根据AchieveId查询数量
  * @param @param achieveId
  * @param @return    设定文件 
  * @return Long    返回类型 
  * @throws
   */
  Long countByAchieveId(Long achieveId);
  
  /**
   * 
  * @Title: countByAchieveIdAndUserId 
  * @Description: 根据AchieveId和userId查询数量
  * @param @param achieveId
  * @param @param userId
  * @param @return    设定文件 
  * @return Long    返回类型 
  * @throws
   */
  Long countByAchieveIdAndUserId(Long achieveId, String userId);
  
  /**
   * 
  * @Title: findAll 
  * @Description: 条件查询列表
  * @param @param spec
  * @param @param sort
  * @param @return    设定文件 
  * @return List<AchieveIncome>    返回类型 
  * @throws
   */
  List<AchieveIncome> findAll(Map<String, Object> spec, Sort sort);
  
  /**
   * 
  * @Title: save 
  * @Description: 保存列表
  * @param @param achieveIncomes    设定文件 
  * @return void    返回类型 
  * @throws
   */
  List<AchieveIncome> save(List<AchieveIncome> achieveIncomes);
  /**
   * 
  * @Title: save 
  * @Description: 
  * @param @param achieveIncomes    设定文件 
  * @return void    返回类型 
  * @throws
   */
  AchieveIncome save(AchieveIncome achieveIncomes);
  
  /**
   * 
  * @Title: createAchieveIncomeBy 
  * @Description: 
  * @param @param List<Map<String,Object>> achieves
  * map={"goodId":"", "rule": achieve, "num": int}
  * 
  * @param @param orderNo
  * @param @param UserId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  boolean createAchieveIncomeBy(List<Map<String,Object>> maps, String orderNo, String UserId);
}
