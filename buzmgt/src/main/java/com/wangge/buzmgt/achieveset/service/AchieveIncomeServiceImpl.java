package com.wangge.buzmgt.achieveset.service;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.achieveset.repository.AchieveIncomeRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.GroupNumber;
import com.wangge.buzmgt.plan.entity.GroupUser;
import com.wangge.buzmgt.plan.entity.RewardPunishRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ChenGuop
 * @ClassName: AchieveIncomeServiceImpl
 * @Description: 达量收益业务处理实现
 * @date 2016年9月23日 下午3:34:53
 */
@Service
public class AchieveIncomeServiceImpl implements AchieveIncomeService {

	@Autowired
	private AchieveIncomeRepository air;

	@Override
	public Long countByAchieveId(Long achieveId) {
		return air.countByAchieveId(achieveId);
	}

	@Override
	public Long countByAchieveIdAndUserId(Long achieveId, String userId) {
		return air.countByAchieveIdAndUserId(achieveId, userId);
	}

	@Override
	public List<AchieveIncome> findAll(Map<String, Object> spec, Sort sort) {
		return air.findAll();
	}

	@Override
	public List<AchieveIncome> save(List<AchieveIncome> achieveIncomes) {
		return air.save(achieveIncomes);
	}

	@Override
	public AchieveIncome save(AchieveIncome ai) {
		return air.save(ai);
	}

	/**
	 *
	 * @Title: createAchieveIncomeBy
	 * @Description: 单个数据处理
	 * @param @param orderNo
	 * @param @param UserId
	 * @param @param payStatus 0-已出库；1-已支付
	 * @param @return    设定文件
	 * @return boolean    返回类型
	 * @throws
	boolean createAchieveIncomeBy(Achieve achieve, String orderNo, String UserId, int num, String goodId,int payStatus);
	 */
	public boolean createAchieveIncome(Achieve achieve, String orderNo, String userId, int num, String goodId, int payStatus) {
		try {
			AchieveIncome.PayStatusEnum statusEnum =AchieveIncome.PayStatusEnum.STOCK;
			if(payStatus==1){
				statusEnum = AchieveIncome.PayStatusEnum.PAY;
			}
			Float money = disposeAchieveIncome(achieve, userId, num);
			AchieveIncome achieveIncome = new AchieveIncome();
			achieveIncome.setAchieveId(achieve.getAchieveId());
			achieveIncome.setUserId(userId);
			achieveIncome.setOrderNo(orderNo);
			achieveIncome.setFlag(FlagEnum.NORMAL);
			achieveIncome.setNum(num);
			achieveIncome.setGoodId(goodId);
			achieveIncome.setMoney(money);
			achieveIncome.setStatus(statusEnum);
			achieveIncome.setCreateDate(new Date());
			//保存
			air.save(achieveIncome);

		}catch (Exception e){
			LogUtil.info(e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean createAchieveIncomeByStock(Achieve achieve, String orderNo, String userId, int num, String goodId, int payStatus) {
		return createAchieveIncome(achieve, orderNo, userId, num, goodId, payStatus);
	}

	@Override
	public boolean createAchieveIncomeByPay(Achieve achieve, String orderNo, String userId, int num, String goodId, int payStatus) {
		return createAchieveIncome(achieve, orderNo, userId, num, goodId, payStatus);
	}

	/**
	 * @param @param  ac
	 * @param @param  userId
	 * @param @param  num
	 * @param @return 设定文件
	 * @return Float    返回类型
	 * @throws
	 * @Title: disposeAchieveIncome
	 * @Description: 根据规则计算收益
	 * 1.查询此商品当前的销量，
	 * 2.根据销量匹配出提成金额
	 * -- 查询是否有特殊分组，若有分组则在规则中 增加对应阶段区间量值;
	 * 3.根据数量计算提成金额
	 */
	private Float disposeAchieveIncome(Achieve ac, String userId, Integer num) {
		//获取当前商品当前规则的销量；
		Integer nowNumber = Integer.valueOf(countByAchieveIdAndUserId(ac.getAchieveId(), userId).toString());
		//计算后的收益
		Float money = 0f;
		Integer firstAdd = 0;
		Integer secondAdd = 0;
		Integer thirdAdd = 0;
		//查询特殊分组人员增量
		for (GroupNumber group : ac.getGroupNumbers()) {
			boolean flag = false;
			for (GroupUser user : group.getGroupUsers()) {
				//存在这个组的userId
				if (user.getUserId() != null && user.getUserId().equals(userId)) {
					firstAdd = group.getNumberFirstAdd();
					secondAdd = group.getNumberSecondAdd();
					thirdAdd = group.getNumberThirdAdd();
					flag = true;
					break;
				}
			}
			if (flag) {
				break;
			}
		}
		List<RewardPunishRule> rules = ac.getRewardPunishRules();
		Integer minAdd = null;
		Integer maxAdd = null;
		//rules已经排好序，阶段由低到高；
		for (int i = 0; i < rules.size(); i++) {
			switch (i) {
				case 0:
					minAdd = 0;
					maxAdd = firstAdd;
					break;
				case 1:
					minAdd = firstAdd;
					maxAdd = secondAdd;
					break;
				case 2:
					minAdd = secondAdd;
					maxAdd = thirdAdd;
					break;

				default:
					minAdd = thirdAdd;
					maxAdd = 0;
					break;
			}
			RewardPunishRule rule = rules.get(i);
			Integer min = null;
			if (rule.getMin() != null) {
				min = rule.getMin() + minAdd;
			}
			Integer max = null;
			if(rule.getMax() != null){
				max = rule.getMax() + maxAdd;
			}
			if ((min == null && max >= nowNumber) ||
							(nowNumber > rule.getMin() && nowNumber <= rule.getMax()) ||
							(max == null && min < nowNumber)) {
				money = rule.getMoney();
				break;
			}
		}
		money = money * num;
		return money;
	}

}
