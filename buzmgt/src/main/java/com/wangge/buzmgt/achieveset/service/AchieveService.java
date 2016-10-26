package com.wangge.buzmgt.achieveset.service;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
* @ClassName: AchieveServer
* @Description: 达量收益业务层接口
* @author ChenGuop
* @date 2016年8月24日 下午6:01:50
*
 */
public interface AchieveService {
  /**
   * 
  * @Title: findAll 
  * @Description: 条件查询,排序
  * @param @param spec
  * @param @param sort
  * @param @return    设定文件 
  * @return List<Achieve>    返回类型 
  * @throws
   */
  List<Achieve> findAll(Map<String, Object> spec,Sort sort);
  /**
   * 
  * @Title: findAll 
  * @Description: 分页条件查询
  * @param @param spec
  * @param @return    设定文件 
  * @return Page<Achieve>    返回类型 
  * @throws
   */
  Page<Achieve> findAll(Map<String, Object> searchParams, Pageable pageable);
  /**
   * 
  * @Title: findByMachineType 
  * @Description: 根据机型和方案查询
  * @param @param machineType
  * @param @return    设定文件 
  * @return List<Achieve>    返回类型 
  * @throws
   */
  List<Achieve> findByMachineTypeAndPlanId(String machineType,String planId);
  /**
   * 
  * @Title: save 
  * @Description: 保存达量提成
  * @param @param achieve    设定文件 
  * @return void    返回类型 
  * @throws
   */
  void save(Achieve achieve);
  /**
   * 
  * @Title: findOne 
  * @Description: 查询Achieve
  * @param @param achieveId
  * @param @return    设定文件 
  * @return Achieve    返回类型 
  * @throws
   */
  Achieve findOne(Long achieveId);

  Achieve findByAchieveIdAndPlanId(Long achieveId,String planId);

  /**
   * 已出库——查询规则
  * @Title: findRuleByGoods 
  * @Description: 通过商品id查询其对应的规则
  * @param @param goodIds
  * @param @param mainPlanId
  * @param @param userId
  * @param @param 计算时间
  * @param @return    设定文件
  * @return List<Map<String,Object>>    返回类型 
  * @throws
   */
  List<Map<String,Object>> findRuleByGoods(List<String> goodIds,Long mainPlanId,String userId);

	/**
	 * 已付款——查询规则
	 * @param goodIds
	 * @param mainPlanId
	 * @param userId
	 * @param payDate
	 * @return
	 */
  List<Map<String,Object>> findRuleByGoods(List<String> goodIds, Long mainPlanId, String userId, Date payDate);

}
