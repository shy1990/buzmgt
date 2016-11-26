package com.wangge.buzmgt.income.main.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.achieveset.entity.Achieve;
import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.achieveset.service.AchieveService;
import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.service.BrandIncomeService;
import com.wangge.buzmgt.common.CheckedEnum;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.IncomeErrorService;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.vo.BusinessSalaryVo;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.income.main.vo.OrderGoods;
import com.wangge.buzmgt.income.main.vo.repository.BusinessSalaryVoRepository;
import com.wangge.buzmgt.income.main.vo.repository.MainIncomeVoRepository;
import com.wangge.buzmgt.income.main.vo.repository.OrderGoodsRepository;
import com.wangge.buzmgt.income.main.vo.repository.PlanUserVoRepository;
import com.wangge.buzmgt.income.ywsalary.service.BaseSalaryService;
import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.ordersignfor.entity.OrderItem;
import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import com.wangge.buzmgt.ordersignfor.repository.OrderSignforRepository;
import com.wangge.buzmgt.ordersignfor.service.OrderItemService;
import com.wangge.buzmgt.section.service.ProductionService;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class MainIncomeServiceImpl implements MainIncomeService {
  @Autowired
  MainIncomeRepository mainIncomeRep;
  @Autowired
  SalesManRepository salesmanRep;
  @Autowired
  MainIncomeVoRepository voRep;
  @Autowired
  IncomeMainplanUsersRepository mainPlanUserRep;
  @Autowired
  PlanUserVoRepository PlanUserVoRep;
  @Autowired
  OrderGoodsRepository orderGoodsRep;
  @Autowired
  BaseSalaryService baseSalaryService;
  @Autowired
  AchieveService achieveService;
  @Autowired
  AchieveIncomeService achieveIncomeService;
  @Autowired
  BrandIncomeService brandIncomeService;
  @Autowired
  ProductionService productionService;
  @Autowired
  IncomeErrorService errorService;
  @Autowired
  private LogService logService;
  @Autowired
  private BusinessSalaryVoRepository businessVoRep;
  @Autowired
  private OrderSignforRepository orderSignforRepository;
  @Autowired
  private OrderItemService orderItemService;
  
  /**
   * 要避免多线程冲突 <br/>
   * 手机要展示当天的订单预计收益详情和当天的订单预计收益总和统计<br/>
   * 1.查出已出库订单详情:订单号,业务员,<br/>
   * 2.根据品牌型号,sku进行分组,查询是否有相应的方案<br/>
   * 3.方案优先级:达量>品牌型号>价格区间;叠加计算累计 ;返回计算结果 <br/>
   * 4.按订单方案存储计算结果<br/>
   * 5.调用叠加处理逻辑<br/>
   */
  @Override
  public void caculateOutedOrder(String orderNo) {
    Object userO = mainPlanUserRep.findsaleByDateAndOrderNo(new Date(), orderNo);
    if (null == userO) {
      return;
    }
    Object[] uers = (Object[]) userO;
    Long planId = Long.valueOf(uers[0].toString());
    String userId = uers[1].toString();
    String regionId = uers[2].toString();
    if (null == planId) {
      return;
    }
    List<OrderGoods> goodList = orderGoodsRep.findByorderNo(orderNo);
    if (goodList.size() < 1) {
      return;
    }
    // // 计算付款订单
    // if (payStatus.equals("1")) {
    // userO = mainPlanUserRep.findsaleByDateAndMemberId(payDate, memberId);
    // if (null != userO) {
    // uers = (Object[]) userO;
    // Long planId1 = Long.valueOf(uers[0].toString());
    // String userId1 = uers[1].toString();
    // String regionId1 = uers[2].toString();
    // if (null != planId) {
    // caculatePayedOrder(userId1, planId1, payDate, new ArrayList<>(goodList),
    // regionId1, 1);
    // }
    // }
    // }
    // 计算出库订单
    List<String> goodIdList = new ArrayList<>();
    for (OrderGoods good : goodList) {
      goodIdList.add(good.getGoodId());
    }
    
    // 查找计算达量
    List<Map<String, Object>> subList = achieveService.findRuleByGoods(goodIdList, planId, userId);
    
    for (Map<String, Object> ruleMap : subList) {
      String subgoodId = ruleMap.get("goodId").toString();
      OrderGoods subgood = pollGoodFromList(subgoodId, goodList);
      goodIdList.remove(subgoodId);
      achieveIncomeService.createAchieveIncomeByStock((Achieve) ruleMap.get("rule"), orderNo, userId, subgood.getNums(),
          subgoodId, 0, planId, subgood.getPrice(), new Date());
    }
    if (goodIdList.size() < 1)
      return;
    // 查找计算品牌
    subList = brandIncomeService.findRuleByGoods(goodIdList, planId, userId, new Date());
    
    for (Map<String, Object> ruleMap : subList) {
      String subgoodId = ruleMap.get("goodId").toString();
      OrderGoods subgood = pollGoodFromList(subgoodId, goodList);
      goodIdList.remove(subgoodId);
      brandIncomeService.realTimeBrandIncomeOut((BrandIncome) ruleMap.get("rule"), subgood.getNums(), orderNo,
          subgoodId, userId, regionId, subgood.getPrice());
    }
    if (goodIdList.size() < 1)
      return;
    // 查找计算价格区间
    for (OrderGoods good : goodList) {
      productionService.compute(orderNo, good.getPrice() + 0D, userId, good.getGoodId(), good.getMachineType(), planId,
          good.getNums(), regionId);
    }
    
  }
  
  /**
   * 根据goodId查找订单里 的商品详情. <br/>
   *
   * @author yangqc
   * @param subgoodId
   * @param goodList
   * @return
   * @since JDK 1.8
   */
  private OrderGoods pollGoodFromList(String subgoodId, List<OrderGoods> goodList) {
    OrderGoods good = null;
    // subgoodId一定在list中
    int i = 0;
    
    for (OrderGoods good1 : goodList) {
      if (good1.getGoodId().equals(subgoodId)) {
        good = good1;
        break;
      }
      i++;
    }
    if (null != good) {
      goodList.remove(i);
    }
    return good;
  }
  
  /**
   * 计算已付款的订单的单品方法 <br/>
   * 
   * @param goodList
   * @param regionId
   * @since JDK 1.8
   */
  @Override
  public void caculatePayedOrder(String userId, Long planId, Date payDate, List<OrderGoods> goodList, String regionId,
      int achieveFlag) {
    Set<String> goodIdList = new HashSet<>();
    if (null == goodList || goodList.size() < 1) {
      return;
    }
    for (OrderGoods good : goodList) {
      goodIdList.add(good.getGoodId());
    }
    // 查找计算达量
    List<Map<String, Object>> subList = achieveService.findRuleByGoods(new ArrayList<>(goodIdList), planId, userId,
        payDate);
    
    for (Map<String, Object> ruleMap : subList) {
      String subgoodId = ruleMap.get("goodId").toString();
      for (;;) {
        OrderGoods subgood = pollGoodFromList(subgoodId, goodList);
        if (null == subgood) {
          break;
        }
        if (achieveFlag == 1) {
          achieveIncomeService.createAchieveIncomeByPay((Achieve) ruleMap.get("rule"), subgood.getOrderNo(), userId,
              subgood.getNums(), subgoodId, 1, planId, subgood.getPrice(), payDate);
        }
      }
      goodIdList.remove(subgoodId);
      
    }
    if (goodIdList.size() < 1)
      return;
    // 查找计算品牌
    subList = brandIncomeService.findRuleByGoods(new ArrayList<>(goodIdList), planId, userId, payDate);
    
    for (Map<String, Object> ruleMap : subList) {
      String subgoodId = ruleMap.get("goodId").toString();
      for (;;) {
        OrderGoods subgood = pollGoodFromList(subgoodId, goodList);
        if (null == subgood) {
          break;
        }
        brandIncomeService.realTimeBrandIncomePay((BrandIncome) ruleMap.get("rule"), subgood.getNums(),
            subgood.getOrderNo(), subgoodId, userId, payDate, regionId, subgood.getPrice());
      }
      goodIdList.remove(subgoodId);
    }
    
    if (goodIdList.size() < 1)
      return;
    // 查找计算价格区间
    /**
     * 数据库中商品id字段长度太小
     * 
     */
    for (OrderGoods good : goodList) {
      productionService.compute(good.getOrderNo(), payDate, good.getPrice() + 0D, userId, good.getGoodId(),
          good.getMachineType(), planId, good.getNums(), regionId);
    }
  }
  
  /**
   * 该功能可以app-interface里完成,通过订单接口调用; 收现金和pos调用该接口<br/>
   * 
   * 或者可以直接算,省事 1.查出已出库订单的订单号和出库时的计算结果<br/>
   * 2.判断受益方案是否相同(计算一整天或单个订单) <br/>
   * 3.相同就更改记录的日期,状态;<br/>
   * 4.不相同就重新计算结果,并更改记录的日期,状态;<br/>
   * 5.更新收益主表<br/>
   */
  @Override
  public void caculatePayedOrder(String orderNo, String userId, String regionId) {
    
    Optional<Long> userOpt = mainPlanUserRep.findFirst(userId, FlagEnum.NORMAL);
    if (!userOpt.isPresent()) {
      return;
    }
    Long planId = userOpt.get();
    List<OrderGoods> goodList = orderGoodsRep.findByorderNo(orderNo);
    caculatePayedOrder(userId, planId, new Date(), goodList, regionId, 1);
  }
  
  /*
   * 在新建时加上月工资<br/>
   */
  @Override
  public MainIncome findIncomeMain(String salesmanId, String month) {
    MainIncome main = mainIncomeRep.findBySalesman_IdAndMonth(salesmanId, month);
    try {
      if (null == main) {
        double basicSalary = baseSalaryService.calculateThisMonthBasicSalary(salesmanId);
        main = mainIncomeRep.save(new MainIncome(salesmanRep.findById(salesmanId), month, basicSalary));
      }
    } catch (ParseException e) {
      LogUtil.error("初始化月工资出错!!", e);
    }
    return main;
  }
  
  @Override
  @Transactional(rollbackForClassName = "Exception")
  public MainIncome findIncomeMain(String salesmanId) {
    String month = DateUtil.getPreMonth(new Date(), 0);
    return findIncomeMain(salesmanId, month);
  }
  
  @Override
  public Page<MainIncomeVo> getVopage(Pageable pageReq, Map<String, Object> searchParams) throws Exception {
    Page<MainIncomeVo> voPage = voRep.findAll((Specification<MainIncomeVo>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      return cb.and(predicates.toArray(new Predicate[] {}));
    }, pageReq);
    return voPage;
  }
  
  @Override
  public List<MainIncomeVo> findAll(Map<String, Object> searchParams) {
    List<MainIncomeVo> rlist = voRep.findAll((Specification<MainIncomeVo>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      return cb.and(predicates.toArray(new Predicate[] {}));
    });
    return rlist;
  }
  
  /**
   * 每天晚上定时计算,计算每天的;
   *
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void calculateOil() {
    mainIncomeRep.calculateOilCost();
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteSubIncome(Long planId, String userId, Date startDate) throws Exception {
    startDate = getEffectiveStartTime(startDate, userId);
    mainIncomeRep.delAchieveIncome(planId, userId, startDate);
    mainIncomeRep.delBrandIncome(planId, userId, startDate);
    mainIncomeRep.delPriceIncome(planId, userId, startDate);
  }
  
  @Override
  public void calIncomePerMonth() {
    try {
      mainIncomeRep.calIncomePerMonth();
    } catch (Exception e) {
      LogUtil.info("每月计算基本工资出错!!");
      errorService.save(50, "每月计算基本工资出错!!问题很严重");
    }
  }
  
  /**
   * getEffectiveStartTime:(这里用一句话描述这个方法的作用). <br/>
   */
  @Override
  public Date getEffectiveStartTime(Date startTime, String userId) {
    String execMonth = DateUtil.getPreMonth(startTime, 0);
    String thisMonth = DateUtil.getPreMonth(new Date(), 0);
    String premonth = DateUtil.getPreMonth(new Date(), -1);
    
    // 如果既不是本月,也不是上个月,那就将起始时间设为上个月的月初;
    if (!execMonth.equals(thisMonth)) {
      // 查到上月的工资记录
      MainIncome income = mainIncomeRep.findBySalesman_IdAndMonth(userId, premonth);
      
      if (null != income && income.getState() == CheckedEnum.UNCHECKED) {
        // 开始时间为上两月,设为从上个月开始
        if (!premonth.equals(execMonth)) {
          startTime = DateUtil.getPreMonthDate(new Date(), -1);
        }
      } else {
        // 本月月初开始计算
        startTime = DateUtil.getPreMonthDate(new Date(), 0);
      }
    }
    return startTime;
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateAchieveIncome(String userId, double achieveIncome) throws Exception {
    try {
      String preMonth = DateUtil.getPreMonth(new Date(), -1);
      MainIncome main = findIncomeMain(userId, preMonth);
      main.setReachIncome(main.getReachIncome() + achieveIncome);
      mainIncomeRep.save(main);
    } catch (Exception e) {
      throw new Exception("保存达量记录出错");
    }
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void updateSuperIncome(String userId, double superPositionIncome) throws Exception {
    try {
      String preMonth = DateUtil.getPreMonth(new Date(), -1);
      MainIncome main = findIncomeMain(userId, preMonth);
      main.setOverlyingIncome(main.getOverlyingIncome() + superPositionIncome);
      mainIncomeRep.save(main);
    } catch (Exception e) {
      throw new Exception("保存达量记录出错");
    }
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteManinPlanIncome(Long planId, Date startDate) throws Exception {
    try {
      mainIncomeRep.delAchieveIncomeByPlanId(planId, startDate);
      mainIncomeRep.delBrandIncomeByPlanId(planId, startDate);
      mainIncomeRep.delPriceIncomeByPlanId(planId, startDate);
    } catch (Exception e) {
      throw new Exception("删除" + planId + "的主方案收益出错,删除日期:" + startDate);
    }
    
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void check(Long id) throws Exception {
    try {
      mainIncomeRep.updateById(CheckedEnum.CHECKED, id);
      logService.log(null, "审批id为" + id + "的工资", EventType.UPDATE);
    } catch (Exception e) {
      throw new Exception("审批id为" + id + "的工资出错");
    }
  }
  
  @Override
  public Page<BusinessSalaryVo> findBusinessSalaryVo(Map<String, Object> searchParams, Pageable pageReq) {
    Page<BusinessSalaryVo> voPage = businessVoRep.findAll((Specification<BusinessSalaryVo>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      return cb.and(predicates.toArray(new Predicate[] {}));
    }, pageReq);
    return voPage;
  }
  
  @Override
  public List<BusinessSalaryVo> findAllBusines(Map<String, Object> searchParams) {
    List<BusinessSalaryVo> voList = businessVoRep.findAll((Specification<BusinessSalaryVo>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      return cb.and(predicates.toArray(new Predicate[] {}));
    });
    return voList;
  }
  
  @Override
  public OrderSignfor disposeIncomeForOrderItem(String orderno) {
    OrderSignfor orderSignfor = orderSignforRepository.findByOrderNo(orderno);
    orderItemService.disposeOrderSignfor(orderSignfor);
    List<OrderItem> orderItems = orderSignfor.getItems();
    List<Object> valList = businessVoRep.findByOrder(orderSignfor.getOrderNo());
    orderItems.forEach(orderItem -> {
      String goodName = orderItem.getName();
      for (Object val : valList) {
        Object[] vals = (Object[]) val;
        String goodsName = vals[0].toString();
        Object income = vals[2];
        if (null != income) {
          Integer num = Integer.valueOf(vals[1].toString());
          if (goodsName.equals(goodName) && num == orderItem.getNums()) {
            orderItem.setIncomeMoney(Float.valueOf(income.toString()));
            break;
          }
        }
      }
    });
    return orderSignfor;
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void calBusinessSal() {
    mainIncomeRep.calIncomeDaily();
    
  }
}
