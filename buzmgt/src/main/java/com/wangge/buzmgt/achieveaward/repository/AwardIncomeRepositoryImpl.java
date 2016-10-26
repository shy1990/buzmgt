package com.wangge.buzmgt.achieveaward.repository;/**
 * Created by ChenGuop on 2016/10/20.
 */

import com.wangge.buzmgt.achieveaward.entity.AwardIncome;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 达量奖励收益自定义数据操作
 * AwardIncomeRepositoryImpl
 *
 * @author ChenGuop
 * @date 2016/10/20
 */
@Repository
public class AwardIncomeRepositoryImpl implements CustomRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<AwardIncome> findOrderByUserIdAndGoodsAndPayDate(String userId, String[] goodIds, Date startDate, Date endDate) {
		String startDate_ = DateUtil.date2String(startDate);
		String endDate_ = DateUtil.date2String(endDate);
		String goodIds_ = StringUtils.join(goodIds, "','");
		String sql = "SELECT oi.GOOD_ID, os.ORDER_NO,oi.NAME,os.USER_ID, oi.NUMS, os.CUSTOM_SIGNFOR_TIME as pay_Time, oi.PRICE \n" +
						"FROM SYS_ORDER_ITEM oi \n" +
						"inner JOIN BIZ_ORDER_SIGNFOR os on os.ORDER_NO = oi.ORDER_NUM\n" +
						"where os.CUSTOM_SIGNFOR_TIME is not null \n" +
						"and os.user_id = '" + userId + "' \n" +
						"and oi.good_id in ('" + goodIds_ + "') \n" +
						"and os.CUSTOM_SIGNFOR_TIME >= TO_DATE('" + startDate_ + "', 'YYYY-MM-DD') \n" +
						"and os.CUSTOM_SIGNFOR_TIME <= TO_DATE('" + endDate_ + "', 'YYYY-MM-DD')\n" +
						"ORDER BY os.CUSTOM_SIGNFOR_TIME DESC";

		Query query = em.createNativeQuery(sql);
		List<Object[]> objs = query.getResultList();
		List<AwardIncome> awardIncomes = new ArrayList<>();
		objs.forEach(obj -> {
			AwardIncome awardIncome = new AwardIncome();
			awardIncome.setGoodId((String) obj[0]);//goodId
			awardIncome.setOrderNo((String) obj[1]);//orderNo
			awardIncome.setUserId((String) obj[3]);//userId
			awardIncome.setNum(Integer.valueOf(obj[4].toString()));//nums
			awardIncome.setCreateDate((Date) obj[5]);//createDate
			awardIncome.setPrice(Float.valueOf(obj[6].toString()));
			awardIncome.setStatus(AwardIncome.PayStatusEnum.PAY);
			awardIncome.setFlag(FlagEnum.NORMAL);

//			awardIncome.setAwardId(FlagEnum.NORMAL);
//			awardIncome.setPlanId(FlagEnum.NORMAL);

			awardIncomes.add(awardIncome);
		});
		return awardIncomes;
	}
}
