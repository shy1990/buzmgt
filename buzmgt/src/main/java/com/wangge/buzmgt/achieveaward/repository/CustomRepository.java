package com.wangge.buzmgt.achieveaward.repository;

import com.wangge.buzmgt.achieveaward.entity.AwardIncome;

import java.util.Date;
import java.util.List;

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
	 * 查询符合规则的订单
	 * @param userId
	 * @param goodIds
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<AwardIncome> findOrderByUserIdAndGoodsAndPayDate(String userId, String[] goodIds, Date startDate, Date endDate);
}
