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
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;
import com.wangge.buzmgt.income.main.entity.IncomeSub;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.IncomeSubRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.income.main.vo.OrderGoods;
import com.wangge.buzmgt.income.main.vo.repository.MainIncomeVoRepository;
import com.wangge.buzmgt.income.main.vo.repository.OrderGoodsRepository;
import com.wangge.buzmgt.income.main.vo.repository.PlanUserVoRepository;
import com.wangge.buzmgt.income.ywsalary.service.BaseSalaryService;
import com.wangge.buzmgt.log.util.LogUtil;
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
  IncomeSubRepository incomeSubRep;
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
  public void caculateOutedOrder(String orderNo, String memberId, String payStatus, Date payDate) {
    Object userO = mainPlanUserRep.findsaleByMemberId(memberId);
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
    // 计算付款订单
    if (payStatus.equals("1")) {
      caculatePayedOrder(userId, planId, payDate, new ArrayList<>(goodList), regionId);
    }
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
    // 查找计算品牌
    subList = brandIncomeService.findRuleByGoods(goodIdList, planId, userId, new Date());
    
    for (Map<String, Object> ruleMap : subList) {
      String subgoodId = ruleMap.get("goodId").toString();
      OrderGoods subgood = pollGoodFromList(subgoodId, goodList);
      goodIdList.remove(subgoodId);
      brandIncomeService.realTimeBrandIncomeOut((BrandIncome) ruleMap.get("rule"), subgood.getNums(), orderNo,
          subgoodId, userId, regionId);
    }
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
    goodList.remove(i);
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
  public void caculatePayedOrder(String userId, Long planId, Date payDate, List<OrderGoods> goodList, String regionId) {
    Set<String> goodIdList = new HashSet<>();
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
        /*
         * TODO 缺点 1.没有保存计算日期(支付日期)
         */
        achieveIncomeService.createAchieveIncomeByPay((Achieve) ruleMap.get("rule"), subgood.getOrderNo(), userId,
            subgood.getNums(), subgoodId, 1, planId, subgood.getPrice(), payDate);
      }
      goodIdList.remove(subgoodId);
      
    }
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
            subgood.getOrderNo(), subgoodId, userId, payDate, regionId);
      }
      goodIdList.remove(subgoodId);
    }
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
    
    Optional<IncomeMainplanUsers> userOpt = mainPlanUserRep.findFirst(userId, FlagEnum.NORMAL);
    if (!userOpt.isPresent()) {
      return;
    }
    IncomeMainplanUsers user = userOpt.get();
    Long planId = user.getPlanId();
    if (null == planId) {
      return;
    }
    List<OrderGoods> goodList = orderGoodsRep.findByorderNo(orderNo);
    caculatePayedOrder(userId, planId, new Date(), goodList, regionId);
    
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
    mainIncomeRep.delAchieveIncome(planId, userId, startDate);
    mainIncomeRep.delBrandIncome(planId, userId, startDate);
    mainIncomeRep.delPriceIncome(planId, userId, startDate);
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void deleteSubIncomeByPlanId(Long planId, Date startDate) throws Exception {
    mainIncomeRep.delAchieveIncomeByPlanId(planId, startDate);
    mainIncomeRep.delBrandIncomeByPlanId(planId, startDate);
    mainIncomeRep.delPriceIncomeByPlanId(planId, startDate);
  }
  
}
