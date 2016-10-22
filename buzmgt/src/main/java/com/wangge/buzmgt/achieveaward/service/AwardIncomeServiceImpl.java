package com.wangge.buzmgt.achieveaward.service;/**
 * Created by ChenGuop on 2016/10/19.
 */

import com.wangge.buzmgt.achieveaward.entity.Award;
import com.wangge.buzmgt.achieveaward.entity.AwardIncome;
import com.wangge.buzmgt.achieveaward.repository.AwardIncomeRepository;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;
import com.wangge.buzmgt.income.main.repository.HedgeCostRepository;
import com.wangge.buzmgt.income.main.service.IncomeErrorService;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.GroupNumber;
import com.wangge.buzmgt.plan.entity.GroupUser;
import com.wangge.buzmgt.plan.entity.RewardPunishRule;
import com.wangge.buzmgt.util.SearchFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 达量奖励接口实现业务处理
 * AwardIncomeServiceImpl
 *
 * @author ChenGuop
 * @date 2016/10/19
 */
@Service
public class AwardIncomeServiceImpl implements AwardIncomeService {

	@Autowired
	private AwardIncomeRepository awardIncomeRepository;
	@Autowired
	private HedgeCostRepository hedgeCostRepository;

	@Autowired
	private AwardService awardService;

	@Autowired
	private MainPlanService mainPlanService;

	@Autowired
	private MainIncomeService mainIncomeService;

	@Autowired
	private IncomeErrorService incomeErrorService;

	@Override
	public List<AwardIncome> findAll(Map<String, Object> spec, Sort sort) {
		Specification searchParams = dispose(spec);
		return awardIncomeRepository.findAll(searchParams, sort);
	}

	@Override
	public Page<AwardIncome> findAll(Map<String, Object> spec, Pageable pageable) {
		Specification searchParams = dispose(spec);
		return awardIncomeRepository.findAll(searchParams, pageable);
	}

	@Override
	public AwardIncome findOne(Long id) {
		return awardIncomeRepository.findOne(id);
	}

	/**
	 * 计算达量奖励总收益
	 *
	 * @param planId
	 * @param awardId
	 * @return
	 */
	@Override
	public String calculateAwardIncomeTotal(Long planId, Long awardId) {
//		Map<String, Object> spec = new HashedMap();
//		spec.put("EQ_awardId",awardId);
//		awardService.findAll(spec,(Sort) null);
		Award award = awardService.findOne(awardId);
//		Long awardId = award.getAwardId();
		List<Map<String, Object>> userMaps = mainPlanService.findEffectUserDateList(planId, award.getStartDate(), award.getEndDate());
		if(userMaps.size()<1){
			LogUtil.info("没有查询到达量奖励规则的！");
			return "没有查询到达量奖励规则的！";
		}
		//Map {userId:String;endDate:Date;startDate:Date}
		userMaps.forEach(userMap -> {
			String userId = userMap.get("userId").toString();
			Date startDate = (Date) userMap.get("startDate");
			Date endDate = (Date) userMap.get("endDate");
			// 查询此规则用户下的订单
			disposeUserMap(award, userId, startDate, endDate);
		});
		return "操作成功！";
	}

	/**
	 * 处理每个用户的达量奖励收益
	 * @param award
	 * @param userId
	 * @param startDate
	 * @param endDate
	 */
	private void disposeUserMap(Award award, String userId, Date startDate, Date endDate) {
		List<String> goodIds = new ArrayList<>();
		Double incomeMoney = 0D;
		try {
			//查询goodId
			award.getAwardGoods().forEach(awardGood -> {
				goodIds.add(awardGood.getGoodId());
			});
			//查询达量奖励规则内的订单使用之定义查询（AwardIncome）
			String[] strings = new String[goodIds.size()];
			List<AwardIncome> awardIncomes = findOrderByUserIdAndGoodsAndPayDate(userId, goodIds.toArray(strings), startDate, endDate);
			//订单总数量
			Integer totalNum = 0;
			for (AwardIncome awardIncome : awardIncomes) {
				awardIncome.setPlanId(Long.valueOf(award.getPlanId()));
				awardIncome.setAwardId(award.getAwardId());

				totalNum += awardIncome.getNum();
			}
			//查询规则收益
			incomeMoney = disposeAwardIncome(award, userId, totalNum).doubleValue();
			if(awardIncomes.size()>0){
				awardIncomeRepository.save(awardIncomes);
//				mainIncomeService.updateAchieveIncome(userId, incomeMoney);
			}
		} catch (Exception e) {
			incomeErrorService.save(null, userId, "计算达量奖励收益出现问题："+e.getMessage(), goodIds.toString(), 4, award.getAwardId());
			LogUtil.error("计算达量奖励收益出现问题",e);
			return;
		}
	}

	/**
	 * 查询规则内的订单
	 * 使用规则收益来处理数据
	 * @param userId
	 * @param goodIds
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<AwardIncome> findOrderByUserIdAndGoodsAndPayDate(String userId, String[] goodIds, Date startDate, Date endDate) {
		return awardIncomeRepository.findOrderByUserIdAndGoodsAndPayDate(userId,goodIds,startDate,endDate);
	}

	/**
	 * 计算商品的收益单价 根据规则和userId状态
	 *
	 * @param @param  ac
	 * @param @param  userId
	 * @param @param  num
	 * @param @return 设定文件
	 * @return Float 返回类型 单个商品的收益
	 * @throws @Title: disposeAwardIncome
	 * @Description: 根据规则计算收益 1.查询此商品当前的销量， 2.根据销量匹配出提成金额 -- 查询是否有特殊分组，若有分组则在规则中
	 * 增加对应阶段区间量值; 3.根据数量计算提成金额
	 */
	private Float disposeAwardIncome(Award ac, String userId, Integer totalNum) {
		List<String> goodIds = new ArrayList<>();
		Float money = 0f;
		try {

			ac.getAwardGoods().forEach(awardGood -> {
				goodIds.add(awardGood.getGoodId());
			});
			//查询售后冲减的量
			Integer afterSaleNum = hedgeCostRepository.countByGoodIds(goodIds, userId);//findAfterSaleNum(ac.getAchieveId(), userId);
			//实际销量=规则销量+即将发生的销量-售后冲减量
			Integer nowNumber = totalNum - afterSaleNum;
			// 计算后的收益
			Integer firstAdd = 0;
			Integer secondAdd = 0;
			Integer thirdAdd = 0;
			// 查询特殊分组人员增量
			for (GroupNumber group : ac.getGroupNumbers()) {
				boolean flag = false;
				for (GroupUser user : group.getGroupUsers()) {
					// 存在这个组的userId
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
			List<RewardPunishRule> rules = new ArrayList<>(ac.getRewardPunishRules());
			Integer minAdd_ = null;
			Integer maxAdd_ = null;
			// rules已经排好序，阶段由低到高；
			for (int i = 0; i < rules.size(); i++) {
				switch (i) {
					case 0:
						minAdd_ = 0;
						maxAdd_ = firstAdd;
						break;
					case 1:
						minAdd_ = firstAdd;
						maxAdd_ = secondAdd;
						break;
					case 2:
						minAdd_ = secondAdd;
						maxAdd_ = thirdAdd;
						break;

					default:
						minAdd_ = thirdAdd;
						maxAdd_ = 0;
						break;
				}
				RewardPunishRule rule = rules.get(i);
				Integer min = 0;
				Integer max = 0;//设置最大值
				min = rule.getMin() + minAdd_;
				max = rule.getMax() + maxAdd_;
				if ((nowNumber == 0 || nowNumber > min) && nowNumber <= max) {
					money = rule.getMoney();
					break;
				}
			}
		}catch (Exception e){
			e.getMessage();
			return 0f;
		}
		return money;
	}

	/**
	 * 处理条件参数
	 */
	public Specification<AwardIncome> dispose(Map<String, Object> searchParams) {
		// 过滤删除
		searchParams.put("EQ_flag", "NORMAL");
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<AwardIncome> spec = awardIncomeSpecification(filters.values(), AwardIncome.class);
		return spec;
	}

	public static Specification<AwardIncome> awardIncomeSpecification(final Collection<SearchFilter> filters,
	                                                                  final Class<AwardIncome> entityClazz) {
		return new Specification<AwardIncome>() {

			private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

			private final static String TIME_MIN = " 00:00:00 000";

			private final static String TIME_MAX = " 23:59:59 999";

			private final static String TYPE_PLAN_TYPE = "com.wangge.buzmgt.common.PlanTypeEnum";

			private final static String TYPE_FlAG_TYPE = "com.wangge.buzmgt.common.FlagEnum";

			private final static String TYPE_AWARDINCOME_STATUS = "com.wangge.buzmgt.achieveset.entity.AwardIncome$PayStatusEnum";

			private final static String TYPE_DATE = "java.util.Date";

			@SuppressWarnings({"unchecked", "rawtypes"})
			@Override
			public Predicate toPredicate(Root<AwardIncome> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (CollectionUtils.isNotEmpty(filters)) {
					List<Predicate> predicates = new ArrayList<>();
					for (SearchFilter filter : filters) {
						// nested path translate, 如Task的名为"user.name"的filedName,
						// 转换为Task.user.name属性
						String[] names = StringUtils.split(filter.fieldName, ".");
						Path expression = root.get(names[0]);
						for (int i = 1; i < names.length; i++) {
							expression = expression.get(names[i]);
						}
						String javaTypeName = expression.getJavaType().getName();
						// logic operator
						switch (filter.operator) {
							case EQ:
								// 日期相等,小于等于最大值,大于等于最小值
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										predicates.add(cb.greaterThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
										predicates.add(cb.lessThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else if (javaTypeName.equals(TYPE_PLAN_TYPE)) {
									/**
									 * PlanTypeEnum格式转换
									 */
									try {
										String status = filter.value.toString();
										PlanTypeEnum planTypeEnum = PlanTypeEnum.valueOf(status);
										filter.value = planTypeEnum;
									} catch (Exception e) {
										LogUtil.info(e.getMessage(), e);
										break;
									}
									predicates.add(cb.equal(expression, filter.value));
								} else if (javaTypeName.equals(TYPE_FlAG_TYPE)) {
									/**
									 * FlagEnum格式转换
									 */
									try {
										String status = filter.value.toString();
										FlagEnum flagEnum = FlagEnum.valueOf(status);
										filter.value = flagEnum;
									} catch (Exception e) {
										LogUtil.error(e.getMessage(), e);
										break;
									}
									predicates.add(cb.equal(expression, filter.value));
								} else if (javaTypeName.equals(TYPE_AWARDINCOME_STATUS)) {
									/**
									 * PayStatusEnum格式转换
									 */
									try {
										String status = filter.value.toString();
										AwardIncome.PayStatusEnum statusEnum = AwardIncome.PayStatusEnum.valueOf(status);
										filter.value = statusEnum;
									} catch (Exception e) {
										LogUtil.error(e.getMessage(), e);
										break;
									}
									predicates.add(cb.equal(expression, filter.value));
								} else {
									predicates.add(cb.equal(expression, filter.value));
								}

								break;
							case IN:
								predicates.add(cb.in(expression).value(filter.value));

								break;
							case LIKE:
								predicates.add(cb.like(expression, "%" + filter.value + "%"));

								break;
							case GT:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 大于最大值
										predicates.add(cb.greaterThan(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else {
									predicates.add(cb.greaterThan(expression, (Comparable) filter.value));
								}

								break;
							case LT:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 小于最小值
										predicates.add(cb.lessThan(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else {
									predicates.add(cb.lessThan(expression, (Comparable) filter.value));
								}

								break;
							case GTE:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 大于等于最小值
										predicates.add(cb.greaterThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MIN)));
									} catch (ParseException e) {
										e.printStackTrace();
									}
								} else {
									predicates.add(cb.greaterThanOrEqualTo(expression, (Comparable) filter.value));
								}

								break;
							case LTE:
								if (javaTypeName.equals(TYPE_DATE)) {
									try {
										// 小于等于最大值
										predicates.add(cb.lessThanOrEqualTo(expression,
														new SimpleDateFormat(DATE_FORMAT).parse(filter.value.toString() + TIME_MAX)));
									} catch (ParseException e) {
										throw new RuntimeException("日期格式化失败!");
									}
								} else {
									predicates.add(cb.lessThanOrEqualTo(expression, (Comparable) filter.value));
								}

								break;
							case NOTEQ:

								predicates.add(cb.notEqual(expression, filter.value));

								break;
							case ISNULL:
								boolean value = Boolean.parseBoolean("true");
								if (value)
									predicates.add(cb.isNull(expression));
								else
									predicates.add(cb.isNotNull(expression));

								break;
							case ORMLK:
								/**
								 * sc_ORMLK_userId = 370105,3701050,3701051 用于区域选择
								 */
								String[] parameterValue = ((String) filter.value).split(",");
								Predicate[] pl = new Predicate[parameterValue.length];

								for (int n = 0; n < parameterValue.length; n++) {
									pl[n] = (cb.like(expression, "%" + parameterValue[n] + "%"));
								}

								Predicate p_ = cb.or(pl);
								predicates.add(p_);
								break;
							case ORE:
								/**
								 * 'sc_ORE_order.shopName = orderNo_A37010506130
								 */
								String[] parameter = ((String) filter.value).split("_");
								Path expression_ = root.get(parameter[0]);
								String value_ = parameter[1];
								Predicate p = cb.or(cb.equal(expression_, value_), cb.like(expression, "%" + value_ + "%"));
								predicates.add(p);

								break;

							default:
								break;

						}
					}
					// 将所有条件用 and 联合起来
					if (!predicates.isEmpty()) {
						return cb.and(predicates.toArray(new Predicate[predicates.size()]));
					}
				}
				return cb.conjunction();
			}
		};
	}
}
