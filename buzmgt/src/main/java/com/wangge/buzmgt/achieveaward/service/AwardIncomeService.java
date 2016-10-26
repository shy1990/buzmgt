package com.wangge.buzmgt.achieveaward.service;/**
 * Created by ChenGuop on 2016/10/19.
 */

import com.wangge.buzmgt.achieveaward.entity.AwardIncome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
	 * 计算达量奖励总收益
	 * @param planId
	 * @param awardId
	 * @return
	 */
	String calculateAwardIncomeTotal(Long planId,Long awardId);
}
