package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.income.main.repository.HedgeCostRepository;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.income.schedule.service.JobService;
import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.superposition.entity.*;
import com.wangge.buzmgt.superposition.pojo.SuperpositionProgress;
import com.wangge.buzmgt.superposition.pojo.SuperpositionRecordDetails;
import com.wangge.buzmgt.superposition.pojo.UserGoodsNum;
import com.wangge.buzmgt.superposition.repository.SuperpositionRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-9-7.
 */
@Service

public class SuperpositionServiceImpl implements SuperpositonService {

  private static final Logger logger = Logger.getLogger(SuperpositionServiceImpl.class);
  @PersistenceContext
  private EntityManager entityManager;
  @Autowired
  private SuperpositionRepository repository;

  @Resource
  private ManagerService managerService;
  //
  // @Autowired
  // private GoodsOrderService goodsOrderService;

  @Autowired
  private MainPlanService mainPlanService;

  @Autowired
  private SuperpositionRecordService recordService;

  @Autowired
  private LogService logService;

  @Autowired
  private JobService jobService;

  @Autowired
  private HedgeCostRepository hedgeCostRepository;

  @Autowired
  private SingleIncomeService singleIncomeService;

  @Autowired
  private MainIncomeService mainIncomeService;

  /**
   * 收益详情
   * @param userId
   * @param superId
   * @param startTime
   * @param endTime
   * @return
   */
  @Override
  public List<SuperpositionRecordDetails> showDetails(String userId, Long superId, Date startTime, Date endTime) {
    String sql = " select oder.nums,oder.goods_name,oder.shop_name,oder.order_num,oder.namepath,oder.pay_time  from \n" + "  sys_goods_order oder \n" + "inner join \n"
            + "  sys_super_goods_type goods \n" + " on oder.goods_id  = goods.good_id \n" + " \n"
            + " where oder.user_id = ? \n"
            + " and oder.pay_time >= ? \n "
            + " and oder.pay_time<= ? \n"
//            + " and goods.su_id = ?"
            ;
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    Query query = entityManager.createNativeQuery(sql);
    SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a,userId);// 方案id
    sqlQuery.setParameter(b,startTime );//开始时间
    sqlQuery.setParameter(c,endTime);// 结束时间
//    sqlQuery.setParameter(d,superId);//  叠加方案id
    List<Object[]> list = sqlQuery.list();
    List<SuperpositionRecordDetails> detailsList = new ArrayList<>();
    list.forEach(object -> {
      SuperpositionRecordDetails details = new SuperpositionRecordDetails();
      details.setAmount(((BigDecimal) object[0]).intValue());//数量
      details.setProductionName((String) object[1]);
      details.setShopName((String) object[2]);
      details.setOrderNo((String) object[3]);
      details.setNamePath((String) object[4]);
      details.setPayTime(DateUtil.date2String((Date) object[5],"yyyy-MM-dd"));
      detailsList.add(details);
    });
    return detailsList;
  }

  /**
   * 显示收益
   * @param time:发放日期
   * @param salesmanId:业务员id
   * @return
   */
  @Override
  public List<SuperpositionRecord> findRecord(String time, String salesmanId) {

    return recordService.findByGiveDateAndSalesmanId(time,salesmanId);
  }

  /**
   * 逻辑删除
   *
   * @param superposition
   */
  @Override
  public void changeStatus(Superposition superposition, String checkStatus) {
    superposition.setCheckStatus(checkStatus);
    repository.save(superposition);
    // 修改完成后添加定时任务
    if ("3".equals(checkStatus)) {
      jobService.saveJobTask(20, superposition.getPlanId(), superposition.getId(),
              DateUtil.getPreMonthAndDay(superposition.getGiveDate(), 1));
    }
    logService.log(null, "修改叠加方案状态: " + checkStatus + "(注:4-终止/逻辑删除,2-驳回,3-审核通过)", Log.EventType.UPDATE);
  }

  /**
   * 叠加收益计算 注: 收益表中:3-最终收益,0-原始记录,1-没计算之前冲减数据,2-计算之后的冲减数据
   *
   * @param planId
   * @param superId
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void superIncomeCompute(Long planId, Long superId) {
    // 查找叠加规则
    Superposition superposition = repository.findById(superId);
    List<Map<String, Object>> userList = mainPlanService.findEffectUserDateList(planId, superposition.getImplDate(),
            superposition.getEndDate());
    for (Map<String, Object> userData : userList) {
      String userId = (String) userData.get("userId");// 获取userId
      Date startTime = (Date) userData.get("startDate");//开始时间
      Date endTime = (Date) userData.get("endDate");// 结束时间

      List<UserGoodsNum> userGoodsNumList = findByUserId(userId, superId, startTime, endTime);
      Integer nums = 0;//总数
      for (UserGoodsNum u : userGoodsNumList) {
        nums += u.getNums();// 获取总数
      }
      // 获取规则
      List<SuperpositionRule> ruleList = superposition.getRuleList();
      // //获取用户组
      Map<String, Object> map = getUse(superposition, userId);
      if ((Boolean) map.get("flag")) { // 判断是否是特殊用户组
        Group group = (Group) map.get("group");
        if (superposition.getTaskThree() != null) {
          Integer oneAdd = group.getOneAdd();
          Integer twoAdd = group.getTwoAdd();
          Integer threeAdd = group.getThreeAdd();
          ruleList.get(0).setMax(oneAdd);
          ruleList.get(1).setMin(oneAdd);
          ruleList.get(1).setMax(twoAdd);
          ruleList.get(2).setMin(twoAdd);
          ruleList.get(2).setMax(threeAdd);
          ruleList.get(3).setMin(threeAdd);
        } else if (superposition.getTaskTwo() != null) {
          Integer oneAdd = group.getOneAdd();
          Integer twoAdd = group.getTwoAdd();
          ruleList.get(0).setMax(oneAdd);
          ruleList.get(1).setMin(oneAdd);
          ruleList.get(1).setMax(twoAdd);
          ruleList.get(2).setMin(twoAdd);
        } else {
          Integer oneAdd = group.getOneAdd();
          ruleList.get(0).setMax(oneAdd);
          ruleList.get(1).setMin(oneAdd);
        }

      }
      // 计算这个人的叠加收益
      // 获取冲减的数量(没有计算的时候的数量)
      SuperpositionRecord record = getBySalesmanIdAndPlanIdAndSuperIdAndStatus(userId, planId, superId, "1", DateUtil.date2String(startTime, "yyyy-MM-dd"), DateUtil.date2String(endTime, "yyyy-MM-dd"));
      logger.info(record);
      Integer sum = 0;
      if (record != null) {
        sum = record.getOffsetNums();
      }
      // 判断符合的价格区间段
      for (SuperpositionRule su : ruleList) {
        if ((nums - sum) >= su.getMin() && (nums - sum) < su.getMax()) {
          SuperpositionRecord superpositionRecord = new SuperpositionRecord();
          // 计算用户收益的时候去掉计算之前的冲减的商品
          superpositionRecord.setSalesmanId(userId);
          superpositionRecord.setPlanId(planId);
          superpositionRecord.setSuperId(superposition.getId());
          superpositionRecord.setAmount((nums - sum) * (su.getPercentage()));// 计算的时候直接减去冲减数量
          superpositionRecord.setRecord(nums);
          superpositionRecord.setStatus("0");// 总收益已经计算,原始记录
          superpositionRecord.setGiveDate(DateUtil.date2String(superposition.getGiveDate(),"yyyy-MM-dd"));//工资发放日期
          superpositionRecord.setComputeDate(DateUtil.date2String(new Date(),"yyyy-MM-dd"));//工资计算日期
          superpositionRecord.setStartTime(DateUtil.date2String(startTime, "yyyy-MM-dd"));
          superpositionRecord.setEndTime(DateUtil.date2String(endTime, "yyyy-MM-dd"));
          superpositionRecord.setOffsetNums(sum);//冲减的数量
          SuperpositionRecord superpositionRecord1 = recordService.save(superpositionRecord);

          try {
            mainIncomeService.updateSuperIncome(superpositionRecord1.getSalesmanId(), superpositionRecord1.getAmount());
          } catch (Exception e) {
            e.printStackTrace();
          }
          logService.log(null, "叠加收益计算保存: " + superpositionRecord, Log.EventType.SAVE);
          break;
        }
      }

    }
  }

  /**
   * 计算个人所有的叠加产品
   *
   * @param userId
   * @param superId
   * @param startTime
   * @param endTime
   * @return
   */
  public List<UserGoodsNum> findByUserId(String userId, Long superId, Date startTime, Date endTime) {
    String sql = " select oder.nums,oder.goods_id,oder.user_id  from \n" + "  sys_goods_order oder \n" + "inner join \n"
            + "  sys_super_goods_type goods \n" + " on oder.goods_id  = goods.good_id \n" + " \n"
            + " where oder.user_id = ?\n" + " and goods.su_id = ?\n" + " and oder.pay_time >= ? and oder.pay_time<= ? ";
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    Query query = entityManager.createNativeQuery(sql);
    SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, userId);// 方案id
    sqlQuery.setParameter(b, superId);// 叠加方案id
    sqlQuery.setParameter(c, startTime);// 开始时间
    sqlQuery.setParameter(d, endTime);// 结束时间
    List<Object[]> list = sqlQuery.list();
    logger.info(list);
    // 用于存储方案中所有人员的一单提货量
    List<UserGoodsNum> userGoodsNumList = new ArrayList<UserGoodsNum>();
    list.forEach(o -> {
      UserGoodsNum userGoodsNum = new UserGoodsNum();
      userGoodsNum.setNums(((BigDecimal) o[0]).intValue());
      userGoodsNum.setGoodsId((String) o[1]);
      userGoodsNum.setUserId((String) o[2]);
      userGoodsNumList.add(userGoodsNum);
    });

    return userGoodsNumList;
  }

  /**
   * 计算叠加收益(先暂时保留,最初按照查询全部的来处理的) 注: 收益表中:3-最终收益,0-原始记录,1-没计算之前冲减数据,2-计算之后的冲减数据
   *
   * @param planId 方案id
   * @param
   * @return
   */
  /*
   * @Override public List<SuperpositionProgress> compute(Long planId, Long
   * superId) throws Exception { //根据主方案,查处有几个叠加规则 Superposition superposition =
   * repository.findById(superId); //发放日期一个月之后计算 // if
   * (DateUtil.currentDateToString().equals(DateUtil.date2String(DateUtil.
   * getPreMonthDate(superposition.getGiveDate(), -1), "yyyy-MM-dd"))) {
   * List<SuperpositionProgress> progressList1 = findRole(superposition,
   * planId);//获取提货量以及所对应的规则 //判断属于哪个区间,去掉不符合的记录
   * 
   * if (CollectionUtils.isNotEmpty(progressList1)) {
   * Iterator<SuperpositionProgress> progressListIterator =
   * progressList1.iterator(); while (progressListIterator.hasNext()) {
   * SuperpositionProgress su = progressListIterator.next(); logger.info(su);
   * //获取冲减的数量(没有计算的时候的数量) SuperpositionRecord record =
   * getBySalesmanIdAndPlanIdAndSuperIdAndStatus(su.getUserId(), planId,
   * superId, "1"); logger.info(record); Integer sum = 0; if (record != null) {
   * sum = record.getOffsetNums(); } //判断符合的价格区间段 if
   * (Integer.parseInt(su.getNums()) - sum >= su.getMin() &&
   * (Integer.parseInt(su.getNums()) - sum) < su.getMax()) { SuperpositionRecord
   * superpositionRecord = new SuperpositionRecord(); //计算用户收益的时候去掉计算之前的冲减的商品
   * superpositionRecord.setSalesmanId(su.getUserId());
   * superpositionRecord.setPlanId(planId);
   * superpositionRecord.setSuperId(superposition.getId());
   * superpositionRecord.setAmount(((Integer.parseInt(su.getNums()) - sum) *
   * (su.getPercentage())));//计算的时候直接减去冲减数量
   * superpositionRecord.setRecord(Integer.parseInt(su.getNums()) - sum);
   * superpositionRecord.setStatus("0");//总收益已经计算,原始记录 SuperpositionRecord
   * superpositionRecord1 = recordService.save(superpositionRecord);
   * mainIncomeService.updateSuperIncome(superpositionRecord1.getSalesmanId(),
   * superpositionRecord1.getAmount()); logService.log(null, "叠加收益计算保存: " +
   * superpositionRecord, Log.EventType.SAVE); } } // //计算收益 // for (int i = 0;
   * i < progressList1.size(); i++) { // SuperpositionRecord superpositionRecord
   * = new SuperpositionRecord(); // if
   * (!"0".equals(progressList1.get(i).getNums())) { // //计算用户收益的时候去掉计算之前的冲减的商品
   * // superpositionRecord.setSalesmanId(progressList1.get(i).getUserId()); //
   * superpositionRecord.setPlanId(planId); //
   * superpositionRecord.setSuperId(superposition.getId()); //
   * superpositionRecord.setAmount(((Integer.parseInt(progressList1.get(i).
   * getNums()) - sum) *
   * (progressList1.get(i).getPercentage())));//计算的时候直接减去冲减数量 //
   * superpositionRecord.setRecord(Integer.parseInt(progressList1.get(i).getNums
   * ())); // superpositionRecord.setStatus("0");//总收益已经计算,原始记录 //
   * SuperpositionRecord superpositionRecord1 =
   * recordService.save(superpositionRecord); //
   * mainIncomeService.updateSuperIncome(superpositionRecord1.getSalesmanId(),
   * superpositionRecord1.getAmount()); // logService.log(null, "叠加收益计算保存: " +
   * superpositionRecord, Log.EventType.SAVE); // // } // }
   * 
   * }
   * 
   * // progressList1.forEach(su -> { // SuperpositionRecord superpositionRecord
   * = new SuperpositionRecord(); // if (!"0".equals(su.getNums())) { //
   * //计算用户收益的时候去掉冲减的商品 // superpositionRecord.setSalesmanId(su.getUserId()); //
   * superpositionRecord.setPlanId(planId); //
   * superpositionRecord.setSuperId(superposition.getId()); //
   * superpositionRecord.setAmount(((Integer.parseInt(su.getNums()) - sum) *
   * (su.getPercentage())));//计算的时候直接减去冲减数量 //
   * superpositionRecord.setRecord(Integer.parseInt(su.getNums())); //
   * recordService.save(superpositionRecord); // logService.log(null,
   * "叠加收益计算保存: " + superpositionRecord, Log.EventType.SAVE); // // } // }); //
   * } return null; }
   */
  /*
   * 获取规则
   */
  public List<SuperpositionProgress> findRole(Superposition superposition, Long planId) {
    List<SuperpositionProgress> progressList = getAll(planId, superposition);
    logger.info("progressList:  " + progressList);// 获取规则
    List<SuperpositionProgress> progressList1 = new ArrayList<SuperpositionProgress>();
    if (CollectionUtils.isNotEmpty(progressList)) {
      if (superposition.getTaskThree() != null) {// 3个指标
        progressList.forEach(progress -> {
          if (progress.getOneAdd() != null) {
            if ("1".equals(progress.getSign())) {
              progress.setMax(Integer.parseInt(progress.getOneAdd()));
            } else if ("2".equals(progress.getSign())) {
              progress.setMin(Integer.parseInt(progress.getOneAdd()));
              progress.setMax(Integer.parseInt(progress.getTwoAdd()));
            } else if ("3".equals(progress.getSign())) {
              progress.setMin(Integer.parseInt(progress.getTwoAdd()));
              progress.setMax(Integer.parseInt(progress.getTaskThree()));

            } else if ("4".equals(progress.getSign())) {
              progress.setMin(Integer.parseInt(progress.getThreeAdd()));
            }
            progressList1.add(progress);
          } else {
            progressList1.add(progress);
          }
        });
      } else if (superposition.getTaskTwo() != null) {// 两个指标
        progressList.forEach(progress -> {
          if (progress.getOneAdd() != null) {
            if ("1".equals(progress.getSign())) {
              progress.setMax(Integer.parseInt(progress.getOneAdd()));
            } else if ("2".equals(progress.getSign())) {
              progress.setMin(Integer.parseInt(progress.getOneAdd()));
              progress.setMax(Integer.parseInt(progress.getTwoAdd()));
            } else if ("3".equals(progress.getSign())) {
              progress.setMin(Integer.parseInt(progress.getTwoAdd()));
            }
            progressList1.add(progress);
          } else {
            progressList1.add(progress);
          }

        });
      } else if (superposition.getTaskOne() != null) {// 一个指标
        progressList.forEach(progress -> {
          if (progress.getOneAdd() != null) {
            if ("1".equals(progress.getSign())) {
              progress.setMax(Integer.parseInt(progress.getOneAdd()));
            } else if ("2".equals(progress.getSign())) {
              progress.setMin(Integer.parseInt(progress.getOneAdd()));
            }
            progressList1.add(progress);
          } else {
            progressList1.add(progress);
          }

        });
      }
    }

    logger.info("progressList1:  " + progressList1);
    return progressList1;
  }

  /**
   * 售后冲减:
   * 判断退货商品属于哪个叠加规则并记录到叠加收益表中
   *
   * @param userId        业务员id
   * @param goodsId       商品id
   * @param payTime       签收时间("yyyy-MM-dd")
   * @param num           退货数量
   * @param planId        主方案id
   * @param receivingTime 售后收货时间
   * @return
   */
  @Override
  public Superposition computeAfterReturnGoods(String userId, String goodsId, String payTime, Integer num, Long planId,
                                               String receivingTime, Long hedgeId) {
    Superposition superposition = repository.findUseByTime(payTime, payTime, planId);// 获取此商品使用的方案
    List<GoodsType> goodsTypeList = superposition.getGoodsTypeList();// 获取所有叠加的商品
    if (containsGoods(goodsTypeList, goodsId)) {// 判断是否有这个商品
      // 有这个商品就保存到收益表中,记录字段是没有计算(售后冲减)
      // todo 这里判断改成用是否有记录来计算 ,  取业务员有效时间来计算
      if (!recordService.isCompare(planId, superposition.getId(), "0")) {
        // if (DateUtil.compareDate(DateUtil.string2Date(payTime, "yyyy-mm-dd"),
        // DateUtil.getPreMonthDate(superposition.getGiveDate(), -1))) {
        SuperpositionRecord superpositionRecord = new SuperpositionRecord();
        superpositionRecord.setPlanId(planId);// 主方案id
        superpositionRecord.setSuperId(superposition.getId());// 叠加方案id
        superpositionRecord.setOffsetNums(num);// 售后冲减数量
        superpositionRecord.setSalesmanId(userId);// 业务员id
        superpositionRecord.setStatus("1");// 售后冲减商品,计算之前的
        superpositionRecord.setGoodsId(goodsId);// 售后冲减商品id
        superpositionRecord.setPayTime(payTime);//下单时间
        recordService.save(superpositionRecord);
      }
//      else {// 计算完之后又有冲减的商品
//        SuperpositionRecord superpositionRecord3 = new SuperpositionRecord();
//        superpositionRecord3.setPlanId(planId);// 主方案id
//        superpositionRecord3.setSuperId(superposition.getId());// 叠加方案id
//        superpositionRecord3.setOffsetNums(num);// 售后冲减数量
//        superpositionRecord3.setSalesmanId(userId);// 业务员id
//        superpositionRecord3.setStatus("2");// 售后冲减商品,计算之后的
//        superpositionRecord3.setGoodsId(goodsId);// 售后冲减商品id
//        recordService.save(superpositionRecord3);
//        // ------------------------------------ 以下是已经计算之后又有冲减的计算
//        // ----------------------------------------------
//        // 获取规则
//        List<SuperpositionRule> ruleList = superposition.getRuleList();
//        // 获取用户组
//        Map<String, Object> map = getUse(superposition, userId);
//        if ((Boolean) map.get("flag")) { // 判断是否是特殊用户组
//          Group group = (Group) map.get("group");
//          if (superposition.getTaskThree() != null) {
//            Integer oneAdd = group.getOneAdd();
//            Integer twoAdd = group.getTwoAdd();
//            Integer threeAdd = group.getThreeAdd();
//            ruleList.get(0).setMax(oneAdd);
//            ruleList.get(1).setMin(oneAdd);
//            ruleList.get(1).setMax(twoAdd);
//            ruleList.get(2).setMin(twoAdd);
//            ruleList.get(2).setMax(threeAdd);
//            ruleList.get(3).setMin(threeAdd);
//          } else if (superposition.getTaskTwo() != null) {
//            Integer oneAdd = group.getOneAdd();
//            Integer twoAdd = group.getTwoAdd();
//            ruleList.get(0).setMax(oneAdd);
//            ruleList.get(1).setMin(oneAdd);
//            ruleList.get(1).setMax(twoAdd);
//            ruleList.get(2).setMin(twoAdd);
//          } else {
//            Integer oneAdd = group.getOneAdd();
//            ruleList.get(0).setMax(oneAdd);
//            ruleList.get(1).setMin(oneAdd);
//          }
//
//        }
//        // 获取已经保存的叠加提成(原始记录),每一次计算都是从原始开始计算
//        SuperpositionRecord superpositionRecord = recordService.findBySalesmanIdAndPlanIdAndSuperIdAndStatus(userId,
//            planId, superposition.getId(), "0");
//        // 获取计算以后的 冲减数量
//        SuperpositionRecord record = getBySalesmanIdAndPlanIdAndSuperIdAndStatus(userId, planId, superposition.getId(),
//            "2");
//        Integer newNum = superpositionRecord.getRecord() - record.getOffsetNums();// 计算出冲减之后的数量
//        ruleList.forEach(superpositionRule -> {
//          if (newNum >= superpositionRule.getMin() && newNum <= superpositionRule.getMax()) {
//            SuperpositionRecord superpositionRecord1 = new SuperpositionRecord();
//            superpositionRecord1.setPlanId(planId);// 主方案id
//            superpositionRecord1.setSuperId(superposition.getId());// 叠加方案id
//            superpositionRecord1.setAmount(newNum * superpositionRule.getPercentage());// 重新计算的金额
//            superpositionRecord1.setSalesmanId(userId);// 业务员id
//            superpositionRecord1.setRecord(newNum);
//            superpositionRecord1.setStatus("3");// 计算之后有冲减,重新计算的状态
//            // 获取上一次冲减保存的记录
//            SuperpositionRecord superpositionRecord2 = recordService
//                .findBySalesmanIdAndPlanIdAndSuperIdAndStatus(userId, planId, superposition.getId(), "3");
//            if (superpositionRecord2 != null) {
//              HedgeCost hedgeCost = new HedgeCost(hedgeId, superpositionRecord.getSuperId(), 3, userId, goodsId,
//                  DateUtil.string2Date(payTime), DateUtil.string2Date(receivingTime),
//                  superpositionRecord2.getAmount() - superpositionRecord1.getAmount());
//              hedgeCostRepository.save(hedgeCost);
//              logService.log(null, "叠加冲减保存:  " + hedgeCost, Log.EventType.SAVE);
//              superpositionRecord2.setStatus("4");// 将上一条记录设置为已经过期
//              recordService.save(superpositionRecord2);
//            } else {
//              HedgeCost hedgeCost = new HedgeCost(hedgeId, superpositionRecord.getSuperId(), 3, userId, goodsId,
//                  DateUtil.string2Date(payTime), DateUtil.string2Date(receivingTime),
//                  superpositionRecord.getAmount() - superpositionRecord1.getAmount());
//              hedgeCostRepository.save(hedgeCost);
//              logService.log(null, "叠加冲减保存:  " + hedgeCost, Log.EventType.SAVE);
//            }
//            recordService.save(superpositionRecord1);// 保存记录
//
//          }
//        });
//      }
    }
    return superposition;
  }

  /**
   * 查找叠加规则计算前冲减总数量 (1)/计算后冲减总数量 (2)
   *
   * @param userId
   * @param planId
   * @param superId
   * @param status
   * @return
   */
  @Override
  public SuperpositionRecord getBySalesmanIdAndPlanIdAndSuperIdAndStatus(String userId, Long planId, Long superId,
                                                                         String status, String startTime, String endTime) {
    String sql = "select nvl(sum(rd.OFFSET_NUMS),0) as offset_nums,rd.SALESMAN_ID,rd.SUPER_ID,rd.PLAN_ID from SYS_SUPERPOSITION_RECORD rd\n"
            + "where rd.PLAN_ID = ?\n" + "and rd.SALESMAN_ID = ?\n" + "and rd.SUPER_ID = ?\n" + "and rd.STATUS = ? \n " +
            " and to_date(rd.pay_time,'yyyy-MM-dd') >= to_date(?,'yyyy-MM-dd') and to_date(rd.pay_time,'yyyy-MM-dd') <= to_date(?,'yyyy-MM-dd') "
            + " group by \n" + "rd.SALESMAN_ID,rd.SUPER_ID,rd.PLAN_ID";
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    int e = 4;
    int f = 5;
    Query query = entityManager.createNativeQuery(sql);
    SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, planId);// 方案id
    sqlQuery.setParameter(b, userId);// 业务员id
    sqlQuery.setParameter(c, superId);// 叠加方案id
    sqlQuery.setParameter(d, status);// 状态
    sqlQuery.setParameter(e, startTime);// 开始时间
    sqlQuery.setParameter(f, endTime);// 结束时间
    List<Object[]> list = sqlQuery.list();
    SuperpositionRecord superpositionRecord = null;
    if (CollectionUtils.isNotEmpty(list)) {
      Object[] o = list.get(0);
      logger.info(list);
      superpositionRecord = new SuperpositionRecord();
      if (o != null) {
        superpositionRecord.setOffsetNums(((BigDecimal) o[0]).intValue());
        superpositionRecord.setSalesmanId(userId);
        superpositionRecord.setSuperId(superId);
        superpositionRecord.setPlanId(planId);
      }
    }

    return superpositionRecord;
  }

  /**
   * 导出进程
   *
   * @param planId
   * @param superId
   * @param startDate
   * @param endDate
   * @return
   */
  @Override
  public List<SuperpositionProgress> exportProgress(Long planId, Long superId, String startDate, String endDate) {
    String sql = "\n" + "select \n" + "  nvl(sum(progress.NUMS),0) as NUMS,\n" + "  progress.USER_ID,\n"
            + "  progress.TRUENAME,\n" + "  progress.REGION_ID, \n" + "  progress.TASK_ONE,\n" + "  progress.TASK_TWO,\n"
            + "  progress.TASK_THREE,\n" + "  progress.IMPL_DATE,\n" + "  progress.END_DATE,\n" + "  progress.ONE_ADD,\n"
            + "  progress.TWO_ADD,\n" + "  progress.THREE_ADD,\n " + "  progress.NAMEPATH,\n" + "  progress.NAME "
            + "from \n" + "  (select \n" + "    usr.TRUENAME,\n" + "    usr.REGION_ID,\n" + "    usr.USER_ID,\n"
            + "    usr.NAMEPATH,\n" + "    usr.PLAN_ID,\n" + "    oder.SHOP_NAME,\n" + "    oder.PAY_TIME,\n"
            + "    oder.NUMS,\n" + "    oder.NAMEPATH as SHOP_ADDRESS,\n" + "    super.SU_PO_ID,\n"
            + "    super.TASK_ONE,\n" + "    super.TASK_TWO,\n" + "    super.TASK_THREE,\n" + "    super.IMPL_DATE,\n"
            + "    super.END_DATE,\n" + "    t.ONE_ADD,\n" + "    t.TWO_ADD,\n" + "    t.THREE_ADD,\n" + "    t.NAME \n"
            + "  from \n" + "    VIEW_INCOME_MAIN_PLAN_USER usr\n" + "  left join\n" + "    SYS_SUPERPOSITION super\n"
            + "  on\n" + "    super.PLAN_ID = usr.PLAN_ID \n" + "  left join\n" + "    SYS_SUPER_GOODS_TYPE goods\n"
            + "  on\n" + "   goods.SU_ID = super.SU_PO_ID\n" + "  left join \n" + "    SYS_GOODS_ORDER oder\n" + "  on\n"
            + "    oder.REGION_ID = usr.REGION_ID and oder.GOODS_ID = goods.GOOD_ID and replace(oder.machine_type,',') = goods.MACHINE_TYPE\n"
            + "  left join\n"
            + "   (select grop.GROUP_ID,grop.NAME,grop.ONE_ADD,grop.TWO_ADD,grop.THREE_ADD,grop.SU_ID, meber.USER_ID from \n"
            + "    SYS_SUPER_GROUP grop\n" + "  left join \n" + "    sys_super_member meber\n" + "  on\n"
            + "    meber.group_id = grop.group_id) t\n" + "  on\n"
            + "    t.SU_ID = super.SU_PO_ID and t.USER_ID = usr.USER_ID\n" + "  where \n" + "        usr.PLAN_ID = ?   \n"
            + "    and \n" + "        super.SU_PO_ID = ? \n " + "    and super.CHECK_STATUS = '3' \n" + "    and \n"
            + "      (to_char(oder.pay_time,'yyyy-mm-dd') between ? and ? or oder.pay_time is null)) progress\n" + "     \n"
            + "GROUP by  \n" + "    progress.REGION_ID,\n" + "    progress.TRUENAME,\n" + "    progress.USER_ID,\n"
            + "    progress.TRUENAME,\n" + "    progress.REGION_ID, \n" + "    progress.TASK_ONE,\n"
            + "    progress.TASK_TWO,\n" + "    progress.TASK_THREE,\n" + "    progress.IMPL_DATE,\n"
            + "    progress.END_DATE,\n" + "    progress.ONE_ADD,\n" + "    progress.TWO_ADD,\n"
            + "    progress.THREE_ADD,\n " + "    progress.NAMEPATH,\n" + "    progress.NAME";
    Query query = null;
    SQLQuery sqlQuery = null;
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    query = entityManager.createNativeQuery(sql);
    sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, planId);// 方案id
    sqlQuery.setParameter(b, superId);// 叠加方案id
    sqlQuery.setParameter(c, startDate);// 开始日期
    sqlQuery.setParameter(d, endDate);// 结束日期
    int count = sqlQuery.list().size();// 分页查询出总条数(不是分页之后的)
    List<SuperpositionProgress> progressList = new ArrayList<SuperpositionProgress>();
    List<Object[]> ret = sqlQuery.list();
    if (CollectionUtils.isNotEmpty(ret)) {
      ret.forEach(r -> {
        SuperpositionProgress progress = new SuperpositionProgress();
        if (r[0] != null) {
          progress.setNums(((BigDecimal) r[0]).toString());
        }
        progress.setUserId((String) r[1]);
        progress.setTrueName((String) r[2]);
        progress.setRegionId((String) r[3]);
        progress.setTaskOne((String) r[4]);
        progress.setTaskTwo((String) r[5]);
        progress.setTaskThree((String) r[6]);
        progress.setImplDate(startDate);
        progress.setEndDate(endDate);
        progress.setOneAdd((String) r[9]);
        progress.setTwoAdd((String) r[10]);
        progress.setThreeAdd((String) r[11]);
        progress.setNamePath((String) r[12]);
        progress.setName((String) r[13]);
        progressList.add(progress);
      });
    }

    return progressList;
  }

  /**
   * 导出详情
   *
   * @param planId
   * @param superId
   * @param userId
   * @param startDate
   * @param endDate
   * @return
   */
  @Override
  public List<SuperpositionProgress> exportDetail(Long planId, Long superId, String userId, String startDate,
                                                  String endDate) {
    String sql = " select \n" + "    usr.PLAN_ID,\n" + "    oder.SHOP_NAME,\n" + "    oder.PAY_TIME,\n"
            + "    oder.ORDER_NUM,\n" + "    sum(oder.NUMS) as nums,\n" + "    oder.goods_name,\n"
            + "    oder.NAMEPATH as SHOP_ADDRESS,\n" + "    super.SU_PO_ID,\n" + "    t.NAME,\n"
            + "    oder.BUSINESS_REGION_ID\n" + "  from \n" + "    VIEW_INCOME_MAIN_PLAN_USER usr\n" + "  left join\n"
            + "    SYS_SUPERPOSITION super\n" + "  on\n" + "    super.PLAN_ID = usr.PLAN_ID \n" + "  left join\n"
            + "    SYS_SUPER_GOODS_TYPE goods\n" + "  on\n" + "   goods.SU_ID = super.SU_PO_ID\n" + "  left join \n"
            + "    SYS_GOODS_ORDER oder\n" + "  on\n"
            + "    oder.REGION_ID = usr.REGION_ID and oder.GOODS_ID = goods.GOOD_ID and replace(oder.machine_type,',') = goods.MACHINE_TYPE\n"
            + "  left join\n" + "   -- SYS_SUPER_GROUP grop\n"
            + "   (select grop.GROUP_ID,grop.NAME,grop.ONE_ADD,grop.TWO_ADD,grop.THREE_ADD,grop.SU_ID, meber.USER_ID from \n"
            + "    SYS_SUPER_GROUP grop\n" + "  left join \n" + "    sys_super_member meber\n" + "  on\n"
            + "    meber.group_id = grop.group_id) t\n" + "  on\n"
            + "    t.SU_ID = super.SU_PO_ID and t.USER_ID = usr.USER_ID\n" + "  where \n" + "        usr.PLAN_ID = ?   \n"
            + "    and \n" + "        super.SU_PO_ID = ?\n" + "    and\n" + "        usr.user_id = ?\n" + "    and \n"
            + "      (to_char(oder.pay_time,'yyyy-mm-dd') between ? and ? or oder.pay_time is null)\n" + " group by\n"
            + "    oder.BUSINESS_REGION_ID,\n" + "    usr.PLAN_ID,\n" + "    oder.SHOP_NAME,\n" + "    oder.PAY_TIME,\n"
            + "    oder.NAMEPATH,\n" + "    super.SU_PO_ID,\n" + "    t.NAME,\n" + "    oder.ORDER_NUM,\n"
            + "    oder.goods_name ";

    Query query = null;
    SQLQuery sqlQuery = null;
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    int e = 4;
    query = entityManager.createNativeQuery(sql);
    sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, planId);// 方案id
    sqlQuery.setParameter(b, superId);// 叠加方案id
    sqlQuery.setParameter(c, userId);// 业务员id
    sqlQuery.setParameter(d, startDate);// 开始日期
    sqlQuery.setParameter(e, endDate);// 结束日期
    int count = sqlQuery.list().size();// 分页查询出总条数(不是分页之后的)
    List<SuperpositionProgress> progressList = new ArrayList<SuperpositionProgress>();
    List<Object[]> ret = sqlQuery.list();
    if (CollectionUtils.isNotEmpty(ret)) {
      ret.forEach(r -> {
        SuperpositionProgress progress = new SuperpositionProgress();
        progress.setShopName((String) r[1]);
        progress.setPayTime(DateUtil.date2String((Date) r[2]));
        progress.setOrderNum((String) r[3]);
        if (r[4] != null) {
          progress.setNums(((BigDecimal) r[4]).toString());
        }
        progress.setGoodsName((String) r[5]);
        progress.setShopAddress((String) r[6]);
        progressList.add(progress);
      });

    }
    return progressList;
  }

  /**
   * 一单达量收益计算
   *
   * @param planId
   * @param superId
   */
  @Override
  @Transactional
  public void computeOneSingle(Long planId, Long superId) {
    Superposition superposition = repository.findById(superId);
    // 获取每个人的开始结束时间
    List<Map<String, Object>> userList = mainPlanService.findEffectUserDateList(planId, superposition.getImplDate(),
            superposition.getEndDate());
    for (Map<String, Object> userData : userList) {
      String userId = (String) userData.get("userId");// 获取userId
      Date startTime = (Date) userData.get("startDate");// 获取开始时间
      Date endTime = (Date) userData.get("endDate");// 结束时间

      String sql = " select  nvl(sum(nums),0) as sum,order_id,user_id from(\n" + "\n"
              + "select oder.nums,oder.order_Id,oder.user_id,oder.pay_time from \n" + "  sys_goods_order oder\n"
              + "inner join\n" + "  ( select * from \n" + "      sys_superposition super\n" + "    left join \n"
              + "      sys_super_goods_type goods     \n" + "    on super.su_po_id = goods.su_id\n"
              + "    where super.plan_id = ? \n" + "    ) su  --关联叠加商品\n" + "on oder.goods_id = su.good_id \n"
              + "where su.check_status = 3   and su.su_po_id = ?  and oder.user_id = ?\n"
              + "and to_date(to_char(oder.pay_time,'yyyy-mm-dd'),'yyyy-mm-dd') >= ? \n"
              + "and to_date(to_char(oder.pay_time,'yyyy-mm-dd'),'yyyy-mm-dd') <= ? ) \n"
              + " group by nums,order_id,user_id";
      int a = 0;
      int b = 1;
      int c = 2;
      int d = 3;
      int e = 4;
      Query query = entityManager.createNativeQuery(sql);
      SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
      sqlQuery.setParameter(a, planId);// 方案id
      sqlQuery.setParameter(b, superId);// 叠加方案id
      sqlQuery.setParameter(c, userId);// 业务员id
      sqlQuery.setParameter(d, startTime);// 开始时间
      sqlQuery.setParameter(e, endTime);// 结束时间
      List<Object[]> list = sqlQuery.list();
      SingleIncome singleIncome = null;
      if (CollectionUtils.isNotEmpty(list)) {
        Object[] o = list.get(0);
        if (o != null) {
          singleIncome = new SingleIncome();
          singleIncome.setRecord(((BigDecimal) o[0]).intValue());
          singleIncome.setOrderId((String) o[1]);
          singleIncome.setUserId((String) o[2]);
          // singleIncome.setPayTime((Date) o[3]);
          List<SingleRule> singleRules = superposition.getSingleRules();// 获得一单达量设置规则
          // 获取计算前的冲减量
          SingleIncome singleIncomeFront = getByUserIdAndPlanIdAndSuperIdAndStatusAndOrderId(singleIncome.getUserId(),
                  planId, superposition.getId(), "1", singleIncome.getOrderId(), DateUtil.date2String(startTime, "yyyy-MM-dd"), DateUtil.date2String(endTime, "yyyy-MM-dd"));
          Integer offsetNum = 0;
          if (singleIncomeFront != null) {
            offsetNum = singleIncomeFront.getOffsetNums();
          }
          for (int i = 0; i < singleRules.size(); i++) {
            if ((singleIncome.getRecord() - offsetNum) > singleRules.get(i).getMin()
                    && (singleIncome.getRecord() - offsetNum) <= singleRules.get(i).getMax()) {
              SingleIncome singleIncomeSave = new SingleIncome();
              singleIncomeSave.setPlanId(planId);
              singleIncomeSave.setOrderId(singleIncome.getOrderId());
              singleIncomeSave.setSuperId(superId);
              singleIncomeSave.setAmount(singleRules.get(i).getReward());
              singleIncomeSave.setUserId(singleIncome.getUserId());
              singleIncomeSave.setStatus("0");// 初始数据
              singleIncomeSave.setRecord(singleIncome.getRecord() - offsetNum);
              singleIncomeService.save(singleIncomeSave);
              logService.log(null, "一单达量保存" + singleIncomeSave, Log.EventType.SAVE);
              // 保存到奇才表中
              try {
                mainIncomeService.updateSuperIncome(singleIncome.getUserId(), singleIncomeSave.getAmount());
              } catch (Exception e1) {
                e1.printStackTrace();
              }
              break;
            }
          }
        }
      }
    }

    // /*
    // * 获取每个人每单的提货量
    // */
    // String sql = "select nvl(sum(nums),0) as sum,order_id,user_id,pay_time
    // from (\n"
    // + "select oder.nums,oder.order_Id,usr.user_id,oder.pay_time \n "
    // + " from sys_goods_order oder \n" + "inner join\n" + "( select * from
    // sys_superposition super\n"
    // + " left join sys_super_goods_type goods \n" + " on super.su_po_id =
    // goods.su_id\n"
    // + " where super.plan_id = ? \n" + " ) su\n" + "on oder.goods_id =
    // su.good_id \n"
    // + "left join view_income_main_plan_user usr\n" + "on usr.region_id =
    // oder.region_id\n"
    // + "where usr.plan_id = ? \n" + "and su.check_status = 3 \n" + "and
    // su.su_po_id = ? \n"
    // + "and to_date(to_char(oder.pay_time,'yyyy-mm-dd'),'yyyy-mm-dd') >=
    // su.impl_date \n"
    // + "and to_date(to_char(oder.pay_time,'yyyy-mm-dd'),'yyyy-mm-dd') <=
    // su.end_date )\n"
    // + " group by order_id,user_id,pay_time ";
    //
    // int a = 0;
    // int b = 1;
    // int c = 2;
    // Query query = entityManager.createNativeQuery(sql);
    // SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
    // sqlQuery.setParameter(a, planId);// 方案id
    // sqlQuery.setParameter(b, planId);// 叠加方案id
    // sqlQuery.setParameter(c, superId);// 叠加方案id
    // List<Object[]> list = sqlQuery.list();
    // logger.info(list);
    // // 用于存储方案中所有人员的一单提货量
    // List<SingleIncome> singleIncomeList = new ArrayList<SingleIncome>();
    // list.forEach(o -> {
    // SingleIncome singleIncome = new SingleIncome();
    // singleIncome.setRecord(((BigDecimal) o[0]).intValue());
    // singleIncome.setOrderId((String) o[1]);
    // singleIncome.setUserId((String) o[2]);
    // singleIncome.setPayTime((Date) o[3]);
    // singleIncomeList.add(singleIncome);
    //
    // });
    // logger.info(singleIncomeList);
    // List<SingleRule> singleRules = superposition.getSingleRules();//
    // 获得一单达量设置规则
    // singleIncomeList.forEach(singleIncome -> {// 遍历方案中所有人员的一单提货量
    // // 获取计算前的冲减量
    // SingleIncome singleIncomeFront =
    // getByUserIdAndPlanIdAndSuperIdAndStatusAndOrderId(singleIncome.getUserId(),
    // planId, superposition.getId(), "1", singleIncome.getOrderId());
    // Integer offsetNum = 0;
    // if (singleIncomeFront != null) {
    // offsetNum = singleIncomeFront.getOffsetNums();
    // }
    // for (int i = 0; i < singleRules.size(); i++) {
    // if ((singleIncome.getRecord() - offsetNum) > singleRules.get(i).getMin()
    // && (singleIncome.getRecord() - offsetNum) <= singleRules.get(i).getMax())
    // {
    // SingleIncome singleIncomeSave = new SingleIncome();
    // singleIncomeSave.setPlanId(planId);
    // singleIncomeSave.setOrderId(singleIncome.getOrderId());
    // singleIncomeSave.setSuperId(superId);
    // singleIncomeSave.setAmount(singleRules.get(i).getReward());
    // singleIncomeSave.setUserId(singleIncome.getUserId());
    // singleIncomeSave.setStatus("0");// 初始数据
    // singleIncomeSave.setRecord(singleIncome.getRecord() - offsetNum);
    // singleIncomeService.save(singleIncomeSave);
    // logService.log(null, "一单达量保存" + singleIncomeSave, Log.EventType.SAVE);
    // // 保存到奇才表中
    // try {
    // mainIncomeService.updateSuperIncome(singleIncome.getUserId(),
    // singleIncomeSave.getAmount());
    // } catch (Exception e) {
    // e.printStackTrace();
    //
    // }
    // break;
    // }
    //
    // }
    // });

  }

  /**
   * 一单达量冲减计算
   *
   * @param userId:业务员id
   * @param planId:方案id
   * @param orderId:订单id
   * @param goodsId:商品id
   * @param payTime:支付时间
   * @param receivingTime:退货时间
   * @param nums:退货数量
   */
  @Override
  public void computeOneSingleAfterReturnGoods(String userId, Long planId, String orderId, String goodsId,
                                               String payTime, String receivingTime, Integer nums) {
    // 获取对应的规则
    Superposition superposition = repository.findUseByTime(payTime, payTime, planId);// 获取此商品使用的方案
    if (superposition != null) {
      List<GoodsType> goodsTypeList = superposition.getGoodsTypeList();// 获取所有叠加的商品
      if (containsGoods(goodsTypeList, goodsId)) {// 判断是否有这个商品
        // todo 有记录说明已经计算
        if (!singleIncomeService.isCompare(planId, superposition.getId(), "0")) {// 没有计算操作:冲减数量保存
          SingleIncome singleIncome = new SingleIncome();
          singleIncome.setSuperId(superposition.getId());
          singleIncome.setGoodsId(goodsId);
          singleIncome.setPlanId(planId);
          singleIncome.setOffsetNums(nums);
          singleIncome.setUserId(userId);
          singleIncome.setOrderId(orderId);
          singleIncome.setStatus("1");
          singleIncome.setPayTime(payTime);
          singleIncomeService.save(singleIncome);
        }
//        else {// 已经计算
//          SingleIncome singleIncome = new SingleIncome();
//          singleIncome.setSuperId(superposition.getId());
//          singleIncome.setGoodsId(goodsId);
//          singleIncome.setPlanId(planId);
//          singleIncome.setOffsetNums(nums);
//          singleIncome.setUserId(userId);
//          singleIncome.setOrderId(orderId);
//          singleIncome.setStatus("2");
//          singleIncomeService.save(singleIncome);
//          // 获取原始记录
//          SingleIncome singleIncomeSave = singleIncomeService.findByUserIdAndPlanIdAndSuperIdAndStatus(userId, planId,
//              superposition.getId(), "0", orderId);
//          // 获取计算以后的冲减数量
//          SingleIncome singleIncomeOffset = getByUserIdAndPlanIdAndSuperIdAndStatusAndOrderId(userId, planId,
//              superposition.getId(), "2", orderId);
//          Integer afterOffsetNums = singleIncomeSave.getRecord() - singleIncomeOffset.getOffsetNums();
//
//          List<SingleRule> singleRules = superposition.getSingleRules();
//          if (CollectionUtils.isNotEmpty(singleRules)) {
//            singleRules.forEach(singleRule -> {
//              if (afterOffsetNums > singleRule.getMin() && afterOffsetNums <= singleRule.getMax()) {
//                SingleIncome singleIncomeNew = new SingleIncome();
//                singleIncomeNew.setPlanId(planId);
//                singleIncomeNew.setOrderId(orderId);
//                singleIncomeNew.setSuperId(superposition.getId());
//                singleIncomeNew.setAmount(singleRule.getReward());
//                singleIncomeNew.setUserId(singleIncome.getUserId());
//                singleIncomeNew.setStatus("3");// 重新保存数据
//                singleIncomeNew.setRecord(afterOffsetNums);
//                // 获取上一次计算的
//                SingleIncome singleIncomeHistory = singleIncomeService.findByUserIdAndPlanIdAndSuperIdAndStatus(userId,
//                    planId, superposition.getId(), "3", orderId);
//                if (singleIncomeHistory != null) {
//                  HedgeCost hedgeCost = new HedgeCost(1l, superposition.getId(), 5, userId, goodsId,
//                      DateUtil.string2Date(payTime), DateUtil.string2Date(receivingTime),
//                      singleIncomeHistory.getAmount() - singleIncomeNew.getAmount());
//                  hedgeCostRepository.save(hedgeCost);
//                  logService.log(null, "叠加冲减保存:  " + hedgeCost, Log.EventType.SAVE);
//                  singleIncomeHistory.setStatus("4");// 将上一条记录设置为已经过期
//                  singleIncomeService.save(singleIncomeHistory);
//                } else {
//                  HedgeCost hedgeCost = new HedgeCost(1l, superposition.getId(), 5, userId, goodsId,
//                      DateUtil.string2Date(payTime), DateUtil.string2Date(receivingTime),
//                      singleIncomeSave.getAmount() - singleIncomeNew.getAmount());
//                  hedgeCostRepository.save(hedgeCost);
//                  logService.log(null, "叠加冲减保存:  " + hedgeCost, Log.EventType.SAVE);
//                }
//                singleIncomeService.save(singleIncomeNew);
//              } else if (afterOffsetNums == 0) {
//                // 获取上一次计算的
//                SingleIncome singleIncomeHistory = singleIncomeService.findByUserIdAndPlanIdAndSuperIdAndStatus(userId,
//                    planId, superposition.getId(), "3", orderId);
//                if (singleIncomeHistory != null) {
//                  HedgeCost hedgeCost = new HedgeCost(1l, superposition.getId(), 5, userId, goodsId,
//                      DateUtil.string2Date(payTime), DateUtil.string2Date(receivingTime),
//                      singleIncomeHistory.getAmount());
//                  singleIncomeHistory.setStatus("4");// 将上一条记录设置为已经过期
//                  singleIncomeService.save(singleIncomeHistory);
//                  hedgeCostRepository.save(hedgeCost);
//                } else {
//                  HedgeCost hedgeCost = new HedgeCost(1l, superposition.getId(), 5, userId, goodsId,
//                      DateUtil.string2Date(payTime), DateUtil.string2Date(receivingTime), singleIncomeSave.getAmount());
//                  hedgeCostRepository.save(hedgeCost);
//                }
//
//              }
//            });
//          }
//        }

      }

    }
  }

  /**
   * 计算冲减的量 计算前(1)/计算后(2)
   *
   * @param userId
   * @param planId
   * @param superId
   * @param status
   * @param orderId
   * @return
   */

  public SingleIncome getByUserIdAndPlanIdAndSuperIdAndStatusAndOrderId(String userId, Long planId, Long superId,
                                                                        String status, String orderId, String startTime, String endTime) {
    String sql = "SELECT NVL(SUM(rd.OFFSET_NUMS),0) AS offset_nums,\n" + "  rd.USER_ID,\n" + "  rd.SUPER_ID,\n"
            + "  rd.PLAN_ID,\n" + "  rd.ORDER_ID\n" + "FROM SYS_SINGLE_RECORD rd\n" + "WHERE rd.PLAN_ID = ?\n"
            + "AND rd.USER_ID = ?\n" + "AND rd.SUPER_ID = ?\n" + "AND rd.status   = ?\n" + "AND rd.ORDER_ID   = ?\n"
            + " and to_date(rd.pay_time,'yyyy-MM-dd') >= to_date(?,'yyyy-MM-dd') and to_date(rd.pay_time,'yyyy-MM-dd') <= to_date(?,'yyyy-MM-dd') "
            + "GROUP BY rd.USER_ID,\n" + "  rd.SUPER_ID,\n" + "  rd.PLAN_ID,\n" + "  rd.ORDER_ID";
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    int e = 4;
    int f = 5;
    int g = 6;
    Query query = entityManager.createNativeQuery(sql);
    SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, planId);// 方案id
    sqlQuery.setParameter(b, userId);// 业务员id
    sqlQuery.setParameter(c, superId);// 叠加方案id
    sqlQuery.setParameter(d, status);// 状态
    sqlQuery.setParameter(e, orderId);// 订单id
    sqlQuery.setParameter(f, startTime);// 开始时间
    sqlQuery.setParameter(g, endTime);// 结束时间

    List<Object[]> list = sqlQuery.list();
    SingleIncome singleIncome = null;
    if (CollectionUtils.isNotEmpty(list)) {
      Object[] o = list.get(0);
      logger.info(list);
      singleIncome = new SingleIncome();
      if (o != null) {
        singleIncome.setUserId(userId);
        singleIncome.setOffsetNums(((BigDecimal) o[0]).intValue());
        singleIncome.setSuperId(superId);
        singleIncome.setPlanId(planId);
        singleIncome.setOrderId((String) o[4]);
        // singleIncome.setUserId((String) o[2]);
        // singleIncome.setPayTime((Date) o[3]);
      }
    }

    return singleIncome;

  }

  /*
   * 判断是否是特殊组,是返回这个组
   */
  public Map<String, Object> getUse(Superposition superposition, String userId) {
    Group groupUse = null;
    // 判断是特殊用户组
    List<Group> groupList = superposition.getGroupList();
    flag:
    for (Group group : groupList) {
      for (Member member : group.getMembers()) {
        if (userId.equals(member.getSalesmanId())) {
          groupUse = group;
          break flag;
        }
      }
    }
    Map<String, Object> map = new HashedMap();
    map.put("group", groupUse);
    if (groupUse == null)
      map.put("flag", false);
    else
      map.put("flag", true);

    return map;
  }

  /*
   * 判断规则中是否有这个商品
   */
  public Boolean containsGoods(List<GoodsType> goodsTypeList, String goodsId) {
    Boolean flag = false;
    // 判断是否包含这个产品
    List<String> goodsIds = new ArrayList<String>();

    if (CollectionUtils.isNotEmpty(goodsTypeList)) {
      goodsTypeList.forEach(goodsType -> {
        goodsIds.add(goodsType.getGoodId());
      });
    }
    if (goodsIds.size() > 0 && goodsIds.contains(goodsId)) {
      flag = true;
    }
    return flag;
  }

  /**
   * 获取所有的方案中的人员提货量
   *
   * @param planId
   * @param superposition
   * @return
   */
  public List<SuperpositionProgress> getAll(Long planId, Superposition superposition) {
    String sql = "SELECT NVL(SUM(progress.NUMS),0) AS NUMS,\n" + "  progress.USER_ID,\n" + "  progress.TRUENAME,\n"
            + "  progress.REGION_ID,\n" + "  progress.TASK_ONE,\n" + "  progress.TASK_TWO,\n" + "  progress.TASK_THREE,\n"
            + "  progress.IMPL_DATE,\n" + "  progress.END_DATE,\n" + "  progress.ONE_ADD,\n" + "  progress.TWO_ADD,\n"
            + "  progress.THREE_ADD,\n" + "  progress.NAMEPATH,\n" + "  progress.NAME,\n" + "  progress.min,\n"
            + "  progress.max,\n" + "  progress.percentage,\n " + "  progress.sign \n" + "FROM\n"
            + "  (SELECT usr.TRUENAME,\n" + "    usr.REGION_ID,\n" + "    usr.USER_ID,\n" + "    usr.NAMEPATH,\n"
            + "    usr.PLAN_ID,\n" + "    oder.SHOP_NAME,\n" + "    oder.PAY_TIME,\n" + "    oder.NUMS,\n"
            + "    oder.NAMEPATH AS SHOP_ADDRESS,\n" + "    super.SU_PO_ID,\n" + "    super.TASK_ONE,\n"
            + "    super.TASK_TWO,\n" + "    super.TASK_THREE,\n" + "    super.IMPL_DATE,\n" + "    super.END_DATE,\n"
            + "    t.ONE_ADD,\n" + "    t.TWO_ADD,\n" + "    t.THREE_ADD,\n" + "    t.NAME,\n" + "    superrule.min,\n"
            + "    superrule.max,\n" + "    superrule.percentage, \n " + "    superrule.serial_number sign \n"
            + "  FROM VIEW_INCOME_MAIN_PLAN_USER usr\n" + "  LEFT JOIN SYS_SUPERPOSITION super\n"
            + "  ON super.PLAN_ID = usr.PLAN_ID\n" + "  LEFT JOIN SYS_SUPERPOSITION_RULE superrule\n"
            + "  ON superrule.SU_ID = super.SU_PO_ID\n" + "  LEFT JOIN SYS_SUPER_GOODS_TYPE goods\n"
            + "  ON goods.SU_ID = super.SU_PO_ID\n" + "  LEFT JOIN SYS_GOODS_ORDER oder\n"
            + "  ON oder.REGION_ID                  = usr.REGION_ID\n"
            + "  AND oder.GOODS_ID                  = goods.GOOD_ID\n"
            + "  AND REPLACE(oder.machine_type,',') = goods.MACHINE_TYPE\n" + "  LEFT JOIN\n"
            + "    (SELECT grop.GROUP_ID,\n" + "      grop.NAME,\n" + "      grop.ONE_ADD,\n" + "      grop.TWO_ADD,\n"
            + "      grop.THREE_ADD,\n" + "      grop.SU_ID,\n" + "      meber.USER_ID\n"
            + "    FROM SYS_SUPER_GROUP grop\n" + "    LEFT JOIN sys_super_member meber\n"
            + "    ON meber.group_id    = grop.group_id\n" + "    ) t ON t.SU_ID       = super.SU_PO_ID\n"
            + "  AND t.USER_ID          = usr.USER_ID\n" + "  WHERE usr.PLAN_ID      = ?\n"
            + "  AND super.SU_PO_ID     = ?\n" + "  AND super.CHECK_STATUS = '3'\n"
            + "  AND (TO_CHAR(oder.pay_time,'yyyy-mm-dd') BETWEEN ? AND ? \n" + "  OR oder.pay_time IS NULL)\n"
            + "  ) progress\n" + "GROUP BY progress.REGION_ID,\n" + "  progress.TRUENAME,\n" + "  progress.USER_ID,\n"
            + "  progress.TRUENAME,\n" + "  progress.REGION_ID,\n" + "  progress.TASK_ONE,\n" + "  progress.TASK_TWO,\n"
            + "  progress.TASK_THREE,\n" + "  progress.IMPL_DATE,\n" + "  progress.END_DATE,\n" + "  progress.ONE_ADD,\n"
            + "  progress.TWO_ADD,\n" + "  progress.THREE_ADD,\n" + "  progress.NAMEPATH,\n" + "  progress.NAME,\n"
            + "  progress.min,\n" + "  progress.max,\n" + "  progress.percentage,\n " + "  progress.sign ";
    Query query = null;
    SQLQuery sqlQuery = null;
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    query = entityManager.createNativeQuery(sql);
    sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, planId);// 方案id
    sqlQuery.setParameter(b, superposition.getId());// 叠加方案id
    sqlQuery.setParameter(c, DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"));// 开始日期
    sqlQuery.setParameter(d, DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"));// 结束日期
    List<SuperpositionProgress> progressList = new ArrayList<SuperpositionProgress>();
    List<Object[]> ret = sqlQuery.list();
    if (CollectionUtils.isNotEmpty(ret)) {
      ret.forEach(r -> {
        SuperpositionProgress progress = new SuperpositionProgress();
        if (r[0] != null) {
          progress.setNums(((BigDecimal) r[0]).toString());
        }
        progress.setUserId((String) r[1]);
        progress.setTrueName((String) r[2]);
        progress.setRegionId((String) r[3]);
        progress.setTaskOne((String) r[4]);
        progress.setTaskTwo((String) r[5]);
        progress.setTaskThree((String) r[6]);
        progress.setImplDate(DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"));
        progress.setEndDate(DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"));
        progress.setOneAdd((String) r[9]);
        progress.setTwoAdd((String) r[10]);
        progress.setThreeAdd((String) r[11]);
        progress.setNamePath((String) r[12]);
        progress.setName((String) r[13]);
        progress.setMin(Integer.parseInt(((String) (r[14]))));
        progress.setMax(Integer.parseInt(((String) (r[15]))));
        if (r[16] != null) {
          progress.setPercentage(((BigDecimal) r[16]).floatValue());
        }
        progress.setSign((String) r[17]);
        progressList.add(progress);
      });
    }
    return progressList;
  }

  /**
   * 查看详情
   *
   * @param planId
   * @param superId
   * @param userId
   * @param startDate
   * @param endDate
   * @param name
   * @param page
   * @param size
   * @return
   */
  @Override
  public Page<SuperpositionProgress> searchDetail(Long planId, Long superId, String userId, String startDate,
                                                  String endDate, String name, Integer page, Integer size) {
    String sql = " select \n" + "    usr.PLAN_ID,\n" + "    oder.SHOP_NAME,\n" + "    oder.PAY_TIME,\n"
            + "    oder.ORDER_NUM,\n" + "    sum(oder.NUMS) as nums,\n" + "    oder.goods_name,\n"
            + "    oder.NAMEPATH as SHOP_ADDRESS,\n" + "    super.SU_PO_ID,\n" + "    t.NAME,\n"
            + "    oder.BUSINESS_REGION_ID\n" + "  from \n" + "    VIEW_INCOME_MAIN_PLAN_USER usr\n" + "  left join\n"
            + "    SYS_SUPERPOSITION super\n" + "  on\n" + "    super.PLAN_ID = usr.PLAN_ID \n" + "  left join\n"
            + "    SYS_SUPER_GOODS_TYPE goods\n" + "  on\n" + "   goods.SU_ID = super.SU_PO_ID\n" + "  left join \n"
            + "    SYS_GOODS_ORDER oder\n" + "  on\n"
            + "    oder.REGION_ID = usr.REGION_ID and oder.GOODS_ID = goods.GOOD_ID and replace(oder.machine_type,',') = goods.MACHINE_TYPE\n"
            + "  left join\n" + "   -- SYS_SUPER_GROUP grop\n"
            + "   (select grop.GROUP_ID,grop.NAME,grop.ONE_ADD,grop.TWO_ADD,grop.THREE_ADD,grop.SU_ID, meber.USER_ID from \n"
            + "    SYS_SUPER_GROUP grop\n" + "  left join \n" + "    sys_super_member meber\n" + "  on\n"
            + "    meber.group_id = grop.group_id) t\n" + "  on\n"
            + "    t.SU_ID = super.SU_PO_ID and t.USER_ID = usr.USER_ID\n" + "  where \n" + "        usr.PLAN_ID = ?   \n"
            + "    and \n" + "        super.SU_PO_ID = ?\n" + "    and\n" + "        usr.user_id = ?\n" + "    and \n"
            + "      (to_char(oder.pay_time,'yyyy-mm-dd') between ? and ?)\n" + " group by\n"
            + "    oder.BUSINESS_REGION_ID,\n" + "    usr.PLAN_ID,\n" + "    oder.SHOP_NAME,\n" + "    oder.PAY_TIME,\n"
            + "    oder.NAMEPATH,\n" + "    super.SU_PO_ID,\n" + "    t.NAME,\n" + "    oder.ORDER_NUM,\n"
            + "    oder.goods_name ";

    Query query = null;
    SQLQuery sqlQuery = null;
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    int e = 4;
    query = entityManager.createNativeQuery(sql);
    sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, planId);// 方案id
    sqlQuery.setParameter(b, superId);// 叠加方案id
    sqlQuery.setParameter(c, userId);// 业务员id
    sqlQuery.setParameter(d, startDate);// 开始日期
    sqlQuery.setParameter(e, endDate);// 结束日期
    int count = sqlQuery.list().size();// 分页查询出总条数(不是分页之后的)
    sqlQuery.setFirstResult(page * size);// 设置开始位置
    sqlQuery.setMaxResults(size);// 每页显示条数
    List<SuperpositionProgress> progressList = new ArrayList<SuperpositionProgress>();
    List<Object[]> ret = sqlQuery.list();
    if (CollectionUtils.isNotEmpty(ret)) {
      ret.forEach(r -> {
        SuperpositionProgress progress = new SuperpositionProgress();
        progress.setShopName((String) r[1]);
        progress.setPayTime(DateUtil.date2String((Date) r[2]));
        progress.setOrderNum((String) r[3]);
        if (r[4] != null) {
          progress.setNums(((BigDecimal) r[4]).toString());
        }
        progress.setGoodsName((String) r[5]);
        progress.setShopAddress((String) r[6]);
        progressList.add(progress);
      });

    }
    Page<SuperpositionProgress> pageResult = new PageImpl<SuperpositionProgress>(progressList,
            new PageRequest(page, size), count);
    return pageResult;
  }

  /**
   * 查看进程
   *
   * @param planId
   * @param superId
   * @return
   */
  @Override
  public Page<SuperpositionProgress> findAll(Long planId, Long superId, String startDate, String endDate, String name,
                                             Integer page, Integer size) {
    logger.info(startDate + "  : " + endDate);
    String sql = "\n" + "select \n" + "  nvl(sum(progress.NUMS),0) as NUMS,\n" + "  progress.USER_ID,\n"
            + "  progress.TRUENAME,\n" + "  progress.REGION_ID, \n" + "  progress.TASK_ONE,\n" + "  progress.TASK_TWO,\n"
            + "  progress.TASK_THREE,\n" + "  progress.IMPL_DATE,\n" + "  progress.END_DATE,\n" + "  progress.ONE_ADD,\n"
            + "  progress.TWO_ADD,\n" + "  progress.THREE_ADD,\n " + "  progress.NAMEPATH,\n" + "  progress.NAME "
            + "from \n" + "  (select \n" + "    usr.TRUENAME,\n" + "    usr.REGION_ID,\n" + "    usr.USER_ID,\n"
            + "    usr.NAMEPATH,\n" + "    usr.PLAN_ID,\n" + "    oder.SHOP_NAME,\n" + "    oder.PAY_TIME,\n"
            + "    oder.NUMS,\n" + "    oder.NAMEPATH as SHOP_ADDRESS,\n" + "    super.SU_PO_ID,\n"
            + "    super.TASK_ONE,\n" + "    super.TASK_TWO,\n" + "    super.TASK_THREE,\n" + "    super.IMPL_DATE,\n"
            + "    super.END_DATE,\n" + "    t.ONE_ADD,\n" + "    t.TWO_ADD,\n" + "    t.THREE_ADD,\n" + "    t.NAME \n"
            + "  from \n" + "    VIEW_INCOME_MAIN_PLAN_USER usr\n" + "  left join\n" + "    SYS_SUPERPOSITION super\n"
            + "  on\n" + "    super.PLAN_ID = usr.PLAN_ID \n" + "  left join\n" + "    SYS_SUPER_GOODS_TYPE goods\n"
            + "  on\n" + "   goods.SU_ID = super.SU_PO_ID\n" + "  left join \n" + "    SYS_GOODS_ORDER oder\n" + "  on\n"
            + "    oder.REGION_ID = usr.REGION_ID and oder.GOODS_ID = goods.GOOD_ID and replace(oder.machine_type,',') = goods.MACHINE_TYPE\n"
            + "  left join\n"
            + "   (select grop.GROUP_ID,grop.NAME,grop.ONE_ADD,grop.TWO_ADD,grop.THREE_ADD,grop.SU_ID, meber.USER_ID from \n"
            + "    SYS_SUPER_GROUP grop\n" + "  left join \n" + "    sys_super_member meber\n" + "  on\n"
            + "    meber.group_id = grop.group_id) t\n" + "  on\n"
            + "    t.SU_ID = super.SU_PO_ID and t.USER_ID = usr.USER_ID\n" + "  where \n" + "        usr.PLAN_ID = ?   \n"
            + "    and \n" + "        super.SU_PO_ID = ? \n " + "    and super.CHECK_STATUS = '3' \n" + "    and \n"
            + "      (to_char(oder.pay_time,'yyyy-mm-dd') between ? and ?)) progress\n" + "     \n"
            + "GROUP by  \n" + "    progress.REGION_ID,\n" + "    progress.TRUENAME,\n" + "    progress.USER_ID,\n"
            + "    progress.TRUENAME,\n" + "    progress.REGION_ID, \n" + "    progress.TASK_ONE,\n"
            + "    progress.TASK_TWO,\n" + "    progress.TASK_THREE,\n" + "    progress.IMPL_DATE,\n"
            + "    progress.END_DATE,\n" + "    progress.ONE_ADD,\n" + "    progress.TWO_ADD,\n"
            + "    progress.THREE_ADD,\n " + "    progress.NAMEPATH,\n" + "    progress.NAME";
    Query query = null;
    SQLQuery sqlQuery = null;
    int a = 0;
    int b = 1;
    int c = 2;
    int d = 3;
    query = entityManager.createNativeQuery(sql);
    sqlQuery = query.unwrap(SQLQuery.class);
    sqlQuery.setParameter(a, planId);// 方案id
    sqlQuery.setParameter(b, superId);// 叠加方案id
    sqlQuery.setParameter(c, startDate);// 开始日期
    sqlQuery.setParameter(d, endDate);// 结束日期
    int count = sqlQuery.list().size();// 分页查询出总条数(不是分页之后的)
    sqlQuery.setFirstResult(page * size);// 设置开始位置
    sqlQuery.setMaxResults(size);// 每页显示条数
    List<SuperpositionProgress> progressList = new ArrayList<SuperpositionProgress>();
    List<Object[]> ret = sqlQuery.list();
    if (CollectionUtils.isNotEmpty(ret)) {
      ret.forEach(r -> {
        SuperpositionProgress progress = new SuperpositionProgress();
        if (r[0] != null) {
          progress.setNums(((BigDecimal) r[0]).toString());
        }
        progress.setUserId((String) r[1]);
        progress.setTrueName((String) r[2]);
        progress.setRegionId((String) r[3]);
        progress.setTaskOne((String) r[4]);
        progress.setTaskTwo((String) r[5]);
        progress.setTaskThree((String) r[6]);
        progress.setImplDate(startDate);
        progress.setEndDate(endDate);
        progress.setOneAdd((String) r[9]);
        progress.setTwoAdd((String) r[10]);
        progress.setThreeAdd((String) r[11]);
        progress.setNamePath((String) r[12]);
        progress.setName((String) r[13]);
        progressList.add(progress);
      });
    }
    logger.info(progressList);
    Page<SuperpositionProgress> pageResult = new PageImpl<SuperpositionProgress>(progressList,
            new PageRequest(page, size), count);
    return pageResult;
  }

  /**
   * 添加叠加任务设置
   *
   * @param superposition
   * @return
   */
  @Override
  public Superposition save(Superposition superposition) {
    List<Group> groupList = superposition.getGroupList();
    List<SuperpositionRule> ruleList = superposition.getRuleList();
    // Integer.parseInt("ss");
    Superposition superposition1 = repository.save(superposition);
    return superposition1;
  }

  /**
   * 根据id查询
   *
   * @param id
   * @return
   */
  @Override
  public Superposition findById(Long id) {
    Superposition superposition = repository.findOne(id);
    return superposition;
  }

  /**
   * 查询全部的
   *
   * @param pageable
   * @return
   */
  @Override
  public Page<Superposition> findAll(Pageable pageable, String type, String sign, Long planId) {
    String statTime = "";// 开始时间
    String endTime = "";// 结束时间
    Page<Superposition> page = repository.findAll(new Specification() {
      @Override
      public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
        List<Predicate> list = new ArrayList<Predicate>();
        if ("pass".equals(sign)) {// 查询正在使用/审核通过/被驳回/正在审核
          Predicate p1 = cb.equal(root.get("checkStatus").as(String.class), "2");
          Predicate p2 = cb.equal(root.get("checkStatus").as(String.class), "1");
          Predicate p3 = cb.lessThanOrEqualTo(root.get("implDate").as(Date.class), new Date());
          Predicate p4 = cb.greaterThanOrEqualTo(root.get("endDate").as(Date.class), new Date());
          Predicate p5 = cb.equal(root.get("planId").as(Long.class), planId);
          Predicate p6 = cb.equal(root.get("checkStatus").as(String.class), "3");
          Predicate p7 = cb.greaterThan(root.get("implDate").as(Date.class), new Date());
          Predicate p = cb.and(p5, cb.or(cb.and(cb.or(p1, p2, p7)), cb.and(p3, p4, p6)));
          return p;
        } else if ("expired".equals(sign)) {// 查询已经过期的
          if (statTime != null && !"".equals(statTime) && endTime != null && !"".equals(endTime)) {
            list.add(cb.greaterThanOrEqualTo(root.get("implDate").as(Date.class),
                    DateUtil.string2Date(statTime, "yyyy-MM-dd")));
            list.add(
                    cb.lessThanOrEqualTo(root.get("implDate").as(Date.class), DateUtil.string2Date(endTime, "yyyy-MM-dd")));
          }
          list.add(cb.lessThanOrEqualTo(root.get("endDate").as(Date.class), new Date()));// 大于结束日期
          list.add(cb.equal(root.get("checkStatus").as(String.class), "3"));// 通过审核的
          list.add(cb.equal(root.get("planId").as(Long.class), planId));// 方案id
          return cb.and(list.toArray(new Predicate[list.size()]));
        }
        return null;
      }
    }, pageable);
    return page;
  }

  /**
   * 根据id删除
   *
   * @param id
   */
  @Override
  public void delete(Long id) {
    repository.delete(id);
  }

  /**
   * 判断使用哪个叠加任务周期量
   *
   * @param memberId
   */
  @Override
  public Superposition checkMember(Superposition superposition, String memberId) {
    //
    // 1.先根据时间判断出使用的哪一个
    // Superposition superposition = ------
    // Superposition superposition =
    // repository.findOne(Long.parseLong("1"));//模拟查出的数据
    List<SuperpositionRule> ruleList = superposition.getRuleList();// 获取公有的周期量任务
    int length = 0;
    if (superposition.getTaskThree() != null) {
      length = 3;
    } else if (superposition.getTaskTwo() != null) {
      length = 2;
    } else {
      length = 1;
    }
    
    /*
     * 只有一个指标
     */
    if (length == 1) {
      List<Group> groupList = superposition.getGroupList();// 获取分组的
      groupList.forEach(group -> {
        group.getMembers().forEach(member -> {
          if (memberId.equals(member.getSalesmanId())) {
            Integer oneAdd = group.getOneAdd();
            superposition.setTaskOne(oneAdd);
            ruleList.get(0).setMax(oneAdd);
            ruleList.get(1).setMin(oneAdd);
          }
        });

      });

      return superposition;
    }
    
    /*
     * 有两个指标
     */
    if (length == 2) {
      List<Group> groupList = superposition.getGroupList();// 获取分组的
      groupList.forEach(group -> {
        group.getMembers().forEach(member -> {
          if (memberId.equals(member.getSalesmanId())) {
            Integer oneAdd = group.getOneAdd();
            Integer twoAdd = group.getTwoAdd();
            superposition.setTaskOne(oneAdd);
            superposition.setTaskTwo(twoAdd);
            ruleList.get(0).setMax(oneAdd);
            ruleList.get(1).setMin(oneAdd);
            ruleList.get(1).setMax(twoAdd);
            ruleList.get(2).setMin(twoAdd);
          }
        });

      });
      return superposition;

    }
    /*
     * 有三个指标
     */
    if (length == 3) {
      List<Group> groupList = superposition.getGroupList();// 获取分组的
      groupList.forEach(group -> {
        group.getMembers().forEach(member -> {
          if (memberId.equals(member.getSalesmanId())) {
            Integer oneAdd = group.getOneAdd();
            Integer twoAdd = group.getTwoAdd();
            Integer threeAdd = group.getThreeAdd();
            superposition.setTaskOne(oneAdd);
            superposition.setTaskTwo(twoAdd);
            superposition.setTaskThree(threeAdd);
            ruleList.get(0).setMax(oneAdd);
            ruleList.get(1).setMin(oneAdd);
            ruleList.get(1).setMax(twoAdd);
            ruleList.get(2).setMin(twoAdd);
            ruleList.get(2).setMax(threeAdd);
            ruleList.get(3).setMin(threeAdd);
          }
        });

      });

      return superposition;
    }

    return null;
  }

  /**
   * 查询方案人员
   *
   * @param pageReq
   * @param searchParams
   */
  @Override
  public Page<PlanUserVo> findMainPlanUsers(Pageable pageReq, Map<String, Object> searchParams) throws Exception {
    return mainPlanService.getUserpage(pageReq, searchParams);
  }

  // /**
  // * 计算收益
  // *
  // * @param superposition
  // * @return
  // */
  // @Override
  // public String compute(Superposition superposition) {
  // //1.方案中的
  //
  //
  // return null;
  // }

  /**
   * 获取用户的id
   *
   * @return
   */
  public String getUserID() {
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    return user.getId();
  }

}
