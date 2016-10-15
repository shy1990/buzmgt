package com.wangge.buzmgt.achieveset.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.vo.AchieveIncomeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	 * 根据AchieveId和userId+支付状态查询数量
	 * @param achieveId
	 * @param userId
	 * @param status
	 * @return
	 */
	Long countByAchieveIdAndUserIdAndStatus(Long achieveId, String userId, AchieveIncome.PayStatusEnum status);

	/**
	 * 根据AchieveId+支付状态 查询数量
	 * @param achieveId
	 * @param status
	 * @return
	 */
	Long countByAchieveIdAndStatus(Long achieveId, AchieveIncome.PayStatusEnum status);
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
	 * @Title: findAll
	 * @Description: 条件查询列表
	 * @param @param spec
	 * @param @param sort
	 * @param @return    设定文件
	 * @return List<AchieveIncome>    返回类型
	 */
  Page<AchieveIncome> findAll(Map<String, Object> spec, Pageable pageable);

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
	 * 已付款的调用的产生达量收益
	 * @param achieve
	 * @param orderNo
	 * @param UserId
	 * @param num
	 * @param goodId
	 * @param payStatus
	 * @param planId
	 * @param price
	 * @param payDate
	 * @return
	 */
	boolean createAchieveIncomeByPay(Achieve achieve, String orderNo, String UserId, int num, String goodId, int payStatus, Long planId, Float price, Date payDate);

	/**
	 * 已出库请款下调用的实时
	 * @param achieve
	 * @param orderNo
	 * @param UserId
	 * @param num
	 * @param goodId
	 * @param payStatus
	 * @param planId
	 * @param price
	 * @param payDate
	 * @return
	 */
  boolean createAchieveIncomeByStock (Achieve achieve, String orderNo, String UserId, int num, String goodId, int payStatus, Long planId, Float price, Date payDate);

	/**
	 * 创建达量收益，售后冲减
	 * @param userId 用户ID
	 * @param goodId 商品Id
	 * @param palnId 主方案Id
	 * @param hedgeId 售后导入Id
	 * @param payTime 支付时间
	 * @param acceptTime 售后日期
	 * @param num 单品数量
	 * @return true-成功；false-失败
	 */
	boolean createAchieveIncomeAfterSale(String userId,String goodId, Long palnId, Long hedgeId, Date payTime, Date acceptTime, Integer num);

	/**
	 * 查询达量售后冲减量
	 * @param achieveId
	 * @return
	 */
	Long countAchieveAfterSale(Long achieveId);

	/**
	 * 根据userId和AchieveId查询售后冲减量
	 * @param ahieveId
	 * @param userId
	 * @return
	 */
	Long countAchieveAfterSaleAndUserId(Long ahieveId, String userId);

	/**
	 * 根据AchieveId和Status查询收益金额
	 * @param achieveId
	 * @param status
	 * @return
	 */
	BigDecimal sumMoneyByAchieveIdAndStatus(Long achieveId,AchieveIncome.PayStatusEnum status);

	/**
	 * 根据AchieveId和UserId和Status查询收益金额
	 * @param achieveId
	 * @param status
	 * @return
	 */
	BigDecimal sumMoneyByAchieveIdAndUserIdAndStatus(Long achieveId,String userId, AchieveIncome.PayStatusEnum status);

	/**
	 * 计算达量总收益
	 * @param planId
	 * @param achieveId
	 * @return
	 */
	String calculateAchieveIncomeTotal(Long planId,Long achieveId);
}
