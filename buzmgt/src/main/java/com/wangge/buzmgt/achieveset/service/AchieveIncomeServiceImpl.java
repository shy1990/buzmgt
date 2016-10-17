package com.wangge.buzmgt.achieveset.service;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.achieveset.repository.AchieveIncomeRepository;
import com.wangge.buzmgt.achieveset.vo.AchieveIncomeVo;
import com.wangge.buzmgt.achieveset.vo.service.AchieveIncomeVoService;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.common.PlanTypeEnum;
import com.wangge.buzmgt.income.main.entity.HedgeCost;
import com.wangge.buzmgt.income.main.repository.HedgeCostRepository;
import com.wangge.buzmgt.income.main.service.IncomeErrorService;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.plan.entity.GroupNumber;
import com.wangge.buzmgt.plan.entity.GroupUser;
import com.wangge.buzmgt.plan.entity.RewardPunishRule;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.iterators.ObjectArrayIterator;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
	@Autowired
	private HedgeCostRepository hedgeCostRepository;
	@Autowired
	private IncomeErrorService incomeErrorService;
	@Autowired
	private AchieveService achieveService;
	@Autowired
	private MainIncomeService mainIncomeService;
	@Autowired
	private AchieveIncomeVoService achieveIncomeVoService;



	@Override
	public Long countByAchieveId(Long achieveId) {
		Long count = air.countByAchieveId(achieveId);
		return null == count ? 0 : count;
	}

	@Override
	public Long countByAchieveIdAndUserId(Long achieveId, String userId) {
		Long count = air.countByAchieveIdAndUserId(achieveId, userId);
		return null == count ? 0 : count;
	}

	@Override
	public Long countByAchieveIdAndUserIdAndStatus(Long achieveId, String userId, AchieveIncome.PayStatusEnum status) {
		Long count = air.countByAchieveIdAndUserIdAndStatus(achieveId, userId, status);
		return null == count ? 0 : count;
	}

	@Override
	public Long countByAchieveIdAndStatus(Long achieveId, AchieveIncome.PayStatusEnum status) {
		Long count = air.countByAchieveIdAndStatus(achieveId, status);
		return null == count ? 0 : count;
	}

	@Override
	public List<AchieveIncome> findAll(Map<String, Object> searchParams, Sort sort) {
		Specification<AchieveIncome> spec = dispose(searchParams);
		return air.findAll(spec, sort);
	}

	public List<AchieveIncome> findAll(Map<String, Object> searchParams) {
		Specification<AchieveIncome> spec = dispose(searchParams);
		return air.findAll(spec);
	}

	/**
	 * 处理条件参数
	 */
	public Specification<AchieveIncome> dispose(Map<String, Object> searchParams) {
		// 过滤删除
		searchParams.put("EQ_flag", "NORMAL");
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<AchieveIncome> spec = achieveIncomeSpecification(filters.values(), AchieveIncome.class);
		return spec;
	}

	@Override
	public Page<AchieveIncome> findAll(Map<String, Object> searchParams, Pageable pageable) {
		Specification<AchieveIncome> spec = dispose(searchParams);
		return air.findAll(spec, pageable);
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
	 * @param @param UserId
	 * @param @param payStatus 0-已出库；1-已支付 @param
	 * @return boolean 返回类型
	 * @Title: createAchieveIncomeBy
	 * @Description: 单个数据处理
	 */
	public boolean createAchieveIncome(Achieve achieve, String orderNo, String userId, int num, String goodId,
	                                   int payStatus, Long planId, Float price, Date payDate) {
		try {
			AchieveIncome.PayStatusEnum statusEnum = AchieveIncome.PayStatusEnum.STOCK;
			if (payStatus == 1) {
				statusEnum = AchieveIncome.PayStatusEnum.PAY;
			}
			//查询收益金额
			Float money = disposeAchieveIncome(achieve, userId, statusEnum, num);
			AchieveIncome achieveIncome = new AchieveIncome();
			achieveIncome.setAchieveId(achieve.getAchieveId());
			achieveIncome.setUserId(userId);
			achieveIncome.setOrderNo(orderNo);
			achieveIncome.setFlag(FlagEnum.NORMAL);
			achieveIncome.setNum(num);
			achieveIncome.setGoodId(goodId);
			achieveIncome.setMoney(money);
			achieveIncome.setStatus(statusEnum);
			achieveIncome.setPlanId(planId);
			achieveIncome.setPrice(price);
			achieveIncome.setCreateDate(payDate);
			// 保存
			air.save(achieveIncome);

		} catch (Exception e) {
			LogUtil.error("xx", e);
			//计算收益异常
			incomeErrorService.save(orderNo,userId,e.getMessage(),goodId,0,achieve.getAchieveId());
			return false;
		}
		return true;
	}

	@Override
	public boolean createAchieveIncomeByStock(Achieve achieve, String orderNo, String userId, int num, String goodId,
	                                          int payStatus, Long planId, Float price, Date payDate) {
		return createAchieveIncome(achieve, orderNo, userId, num, goodId, payStatus, planId, price, payDate);
	}

	@Override
	public boolean createAchieveIncomeByPay(Achieve achieve, String orderNo, String userId, int num, String goodId,
	                                        int payStatus, Long planId, Float price, Date payDate) {
		return createAchieveIncome(achieve, orderNo, userId, num, goodId, payStatus, planId, price, payDate);
	}

	/**
	 * @param @param  ac
	 * @param @param  userId
	 * @param @param  num
	 * @param @return 设定文件
	 * @return Float 返回类型
	 * @throws @Title: disposeAchieveIncome
	 * @Description: 根据规则计算收益 1.查询此商品当前的销量， 2.根据销量匹配出提成金额 -- 查询是否有特殊分组，若有分组则在规则中
	 * 增加对应阶段区间量值; 3.根据数量计算提成金额
	 */
	private Float disposeAchieveIncome(Achieve ac, String userId, AchieveIncome.PayStatusEnum status, Integer num) {
		// 获取当前商品当前规则的销量；
		Integer nowNumber = Integer.valueOf(countByAchieveIdAndUserIdAndStatus(ac.getAchieveId(), userId, status).toString());
		//查询售后冲减的量
		Integer afterSaleNum = findAfterSaleNum(ac.getAchieveId(),userId);
		nowNumber +=afterSaleNum;
		// 计算后的收益
		Float money = 0f;
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
		Integer minAdd = null;
		Integer maxAdd = null;
		// rules已经排好序，阶段由低到高；
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
			Integer min = 0;
			Integer max = 0;//设置最大值
			min = rule.getMin() + minAdd;
			max = rule.getMax() + maxAdd;
			if ((nowNumber == 0 || nowNumber > min) && nowNumber <= max) {
				money = rule.getMoney();
				break;
			}
		}
		money = money * num;
		return money;
	}

	/**
	 * 创建达量收益，售后冲减
	 * 1.查询是否存在规则；
	 * 2.是否收益金额已发放
	 * 3.创建售后收益冲减
	 *
	 * @param userId     用户ID
	 * @param goodId     商品Id
	 * @param goodId     主方案Id
	 * @param payTime    支付时间
	 * @param acceptTime 售后日期
	 * @param num        单品数量
	 * @return
	 */
	@Override
	public boolean createAchieveIncomeAfterSale(String userId, String goodId, Long palnId, Long hedgeId, Date payTime, Date acceptTime, Integer num) {
		String orderNo = "";
		Long ruleId = null;
		try {
			Map<String, Object> searchParams = new HashedMap();
			searchParams.put("EQ_userId", userId);
			searchParams.put("EQ_goodId", goodId);
			searchParams.put("EQ_planId", palnId);
			searchParams.put("EQ_createDate", DateUtil.date2String(payTime));
			List<AchieveIncome> achieveIncomes = findAll(searchParams);
			if (achieveIncomes.size() < 1) {
				return false;
			}
			AchieveIncome achieveIncome = achieveIncomes.get(0);
			orderNo = achieveIncome.getOrderNo();
			ruleId = achieveIncome.getAchieveId();
			Float money = achieveIncome.getMoney();
			Integer afterSaleCount = findAfterSaleNum(ruleId,userId);
			Integer count = achieveIncome.getNum()-afterSaleCount;
			//售后冲减的金额
			Float AfterSaleMoney = 0f;
			//查询规则收益是否发放
			Achieve achieve = achieveService.findOne(ruleId);
			//TODO 收益已发放的情况
			if(achieve.getStatus()== Achieve.AchieveStatusEnum.ISSUED){

			}
			//售后冲减的金额
			AfterSaleMoney = new BigDecimal(Float.toString(money)).divide(new BigDecimal(count)).multiply(new BigDecimal(num)).floatValue();
			HedgeCost hedgeCost = new HedgeCost(hedgeId, ruleId, 2, userId, goodId, payTime, acceptTime, AfterSaleMoney);
			hedgeCostRepository.save(hedgeCost);
			//组装售后冲减信息
		} catch (Exception e) {
			LogUtil.error(e.getMessage(), e);
			incomeErrorService.saveHedgeError(orderNo,userId,e.getMessage(),goodId,0,ruleId);
			return false;
		}
		return true;
	}

	@Override
	public Long countAchieveAfterSale(Long achieveId) {
		return hedgeCostRepository.countByRuleIdAndRuleType(achieveId, 2);
	}

	@Override
	public Long countAchieveAfterSaleAndUserId(Long ahieveId, String userId) {
		return hedgeCostRepository.countByRuleIdAndRuleTypeAndUserId(ahieveId, 2, userId);
	}

	@Override
	public BigDecimal sumMoneyByAchieveIdAndStatus(Long achieveId, AchieveIncome.PayStatusEnum status) {
		Integer statusInteger = 0;
		if (status == AchieveIncome.PayStatusEnum.PAY) {
			statusInteger = 1;
		}
		return air.sumMoneyByAchieveIdAndStatus(achieveId, statusInteger);
	}

	@Override
	public BigDecimal sumMoneyByAchieveIdAndUserIdAndStatus(Long achieveId, String userId, AchieveIncome.PayStatusEnum status) {
		Integer statusInteger = 0;
		if (status == AchieveIncome.PayStatusEnum.PAY) {
			statusInteger = 1;
		}
		return air.sumMoneyByAchieveIdAndUserIdAndStatus(achieveId, userId, statusInteger);
	}

	@Override
	public String calculateAchieveIncomeTotal(Long planId, Long achieveId) {
		//1.查询规则
		Achieve achieve = achieveService.findByAchieveIdAndPlanId(achieveId, planId.toString());
		if(achieve.getStatus()== Achieve.AchieveStatusEnum.ISSUED){
			return "收益已发放";
		}
//		achieve.getGroupNumbers();
//		achieve.getRewardPunishRules();
		List<Map<String,Object>> userAchieves = new ArrayList<>();
		//2.查询收益人员列表
		List<AchieveIncomeVo> achieveIncomeVos = achieveIncomeVoService.findByAchieveIdAndStatus(achieveId, AchieveIncome.PayStatusEnum.PAY);
		if(achieveIncomeVos.size()<=0){
			LogUtil.info("此规则没有产生收益（planId="+planId+",achieveId= "+achieveId);
			return "此规则没有产生收益";
		}
		achieveIncomeVos.forEach(achieveIncomeVo -> {
			//组装参数
			Map<String,Object> parameters = new HashedMap();
			parameters.put("userId",achieveIncomeVo.getUserId());
			parameters.put("num",achieveIncomeVo.getNum());
			userAchieves.add(parameters);
		});
		//3.遍历 查询收益数量
		userAchieves.forEach(userAchieve->{
			try {
				String userId = (String) userAchieve.get("userId");
				Integer num = (Integer) userAchieve.get("num");
				//查询售后冲减的量
				Integer afterSaleNum = findAfterSaleNum(achieveId,userId);
				//4.计算收益(减去售后量)
				Double totalMoney =Double.parseDouble(String.valueOf(disposeAchieveIncome(achieve, userId, AchieveIncome.PayStatusEnum.PAY, num-afterSaleNum)));

				LogUtil.info(userId + "的收益金额 totalMoney：" + totalMoney);

				List<AchieveIncome> achieveIncomes = this.findByAchieveIdAndUserIdAndStatus(achieveId, userId, AchieveIncome.PayStatusEnum.PAY);
				this.save(achieveIncomes);

				//5.保存薪资
				mainIncomeService.updateAchieveIncome(userId,totalMoney);

				//6.更改状态
				achieve.setStatus(Achieve.AchieveStatusEnum.ISSUED);
				achieveService.save(achieve);
			}catch (Exception e){
				LogUtil.error(e.getMessage(),e);
				return;
			}
		});

		return "OK";
	}

	/**
	 * 根据achieveId和userId ，查询已付款收益
	 * @param achieveId
	 * @param userId
	 * @param pay
	 * @return
	 */
	private List<AchieveIncome> findByAchieveIdAndUserIdAndStatus(Long achieveId, String userId, AchieveIncome.PayStatusEnum pay) {
		Map<String, Object> searchParams =new HashedMap();
		searchParams.put("EQ_achieveId", achieveId);
		searchParams.put("EQ_userId", userId);
		searchParams.put("EQ_status", pay);
		return this.findAll(searchParams);
 	}

	//查询售后冲减的量
	public Integer findAfterSaleNum(Long achieveId, String userId){
		Long afterSaleNum = hedgeCostRepository.countByRuleIdAndRuleTypeAndUserId(achieveId,2,userId);
		return afterSaleNum.intValue();
	}
	public static Specification<AchieveIncome> achieveIncomeSpecification(final Collection<SearchFilter> filters,
	                                                                      final Class<AchieveIncome> entityClazz) {
		return new Specification<AchieveIncome>() {

			private final static String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss SSS";

			private final static String TIME_MIN = " 00:00:00 000";

			private final static String TIME_MAX = " 23:59:59 999";

			private final static String TYPE_PLAN_TYPE = "com.wangge.buzmgt.common.PlanTypeEnum";

			private final static String TYPE_FlAG_TYPE = "com.wangge.buzmgt.common.FlagEnum";

			private final static String TYPE_ACHIEVEINCOME_STATUS = "com.wangge.buzmgt.achieveset.entity.AchieveIncome$PayStatusEnum";

			private final static String TYPE_DATE = "java.util.Date";

			@SuppressWarnings({"unchecked", "rawtypes"})
			@Override
			public Predicate toPredicate(Root<AchieveIncome> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
								} else if (javaTypeName.equals(TYPE_ACHIEVEINCOME_STATUS)) {
									/**
									 * PayStatusEnum格式转换
									 */
									try {
										String status = filter.value.toString();
										AchieveIncome.PayStatusEnum statusEnum = AchieveIncome.PayStatusEnum.valueOf(status);
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
