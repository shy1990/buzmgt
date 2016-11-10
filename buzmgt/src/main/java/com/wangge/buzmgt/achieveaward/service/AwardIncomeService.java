package com.wangge.buzmgt.achieveaward.service;/**
 * Created by ChenGuop on 2016/10/19.
 */

import com.wangge.buzmgt.achieveaward.entity.AwardIncome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 达量奖励收益接口
 * AwardIncomeService
 *
 * @author ChenGuop
 * @date 2016/10/19
 */
public interface AwardIncomeService {

	/**
	 * 查询列表
	 * @param spec
	 * @param sort
	 * @return
	 */
	List<AwardIncome> findAll(Map<String,Object> spec, Sort sort);

	/**
	 * 分页查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	Page<AwardIncome> findAll(Map<String,Object> spec, Pageable pageable);

	/**
	 * 通过id查询
	 * @param id
	 * @return
	 */
	AwardIncome findOne(Long id);

	/**
	 * 创建达量奖励收益，售后冲减
	 * @param userId 用户ID
	 * @param goodId 商品Id
	 * @param palnId 主方案Id
	 * @param hedgeId 售后导入Id
	 * @param payTime 支付时间
	 * @param acceptTime 售后日期
	 * @param num 单品数量
	 * @return true-成功；false-失败
	 */
	boolean createAwardIncomeAfterSale(String userId, String goodId, Long palnId, Long hedgeId, Date payTime, Date acceptTime, Integer num);
	/**
	 * 计算达量奖励总收益
	 * @param planId
	 * @param awardId
	 * @return
	 */
	String calculateAwardIncomeTotal(Long planId,Long awardId);

}
