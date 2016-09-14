package com.wangge.buzmgt.income.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  OrderGoodsRepository orderGoodsRep;
  @Autowired
  IncomeSubRepository incomeSubRep;
  
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
  public void caculateOutedOrder(String orderNo, String userId, String payStatus) {
    
    Optional<IncomeMainplanUsers> userOpt = mainPlanUserRep.findFirst(userId, FlagEnum.NORMAL);
    if (!userOpt.isPresent()) {
      return;
    }
    IncomeMainplanUsers user = userOpt.get();
    List<OrderGoods> goodList = orderGoodsRep.findByorderNo(orderNo);
    for (OrderGoods goods : goodList) {
      IncomeSub sub = findSubPlan(user.getPlanId(), goods.getGoodId(), userId);
    }
  }
  
  /**
   * findSubPlan:找到三个并行子方案中正确的一个. <br/>
   * 编写一个CalculatePlan的接口,然后这三个方案实现它<br/>
   * 
   * @return
   * @since JDK 1.8
   */
  private IncomeSub findSubPlan(Long planId, String goodId, String userId) {
    // Long subPlanId=xx;
    // if(null!=subPlanId){
    // return new IncomeSub(2, subPlanId);
    // }
    // subPlanId=xx;
    // if(null!=subPlanId){
    // return new IncomeSub(1, subPlanId);
    // }
    // subPlanId=xx;
    // if(null!=subPlanId){
    // return new IncomeSub(0, subPlanId);
    // }
    return new IncomeSub(0, 4);
  }
  
  /**
   * 该功能可以app-interface里完成,通过订单接口调用; 收现金和pos调用该接口<br/>
   * // TODO Auto-generated method stub<br/>或者可以直接算,省事
   * 1.查出已出库订单的订单号和出库时的计算结果<br/>
   * 2.判断受益方案是否相同(计算一整天或单个订单) <br/>
   * 3.相同就更改记录的日期,状态;<br/>
   * 4.不相同就重新计算结果,并更改记录的日期,状态;<br/>
   * 5.更新收益主表<br/>
   */
  @Override
  public void caculatePayedOrder(String orderNo, String userId) {
    Optional<IncomeMainplanUsers> userOpt = mainPlanUserRep.findFirst(userId, FlagEnum.NORMAL);
    if (!userOpt.isPresent()) {
      return;
    }
    IncomeMainplanUsers user = userOpt.get();
    List<IncomeSub> subList = incomeSubRep.findByOrdernoAndOrderflag(orderNo, 0);
    List<IncomeSub> payedList = new ArrayList<>();
    String todayStr = DateUtil.date2String(new Date());
    
    if (subList.size() > 0 && DateUtil.date2String(subList.get(0).getCountDate()).equals(todayStr)) {
      for (IncomeSub incomeSub : subList) {
        
        IncomeSub newsub = ObjectUtils.clone(incomeSub);
        newsub.setId(null);
        newsub.setOrderflag(1);
        payedList.add(newsub);
      }
    } else {
      List<OrderGoods> goodList = orderGoodsRep.findByorderNo(orderNo);
      //如果其子方案的和以前一样,就不用计算了
      for (OrderGoods goods : goodList) {
        IncomeSub sub = findSubPlan(user.getPlanId(), goods.getGoodId(), userId);
      }
    }
  }
  
  @Override
  public void caculateSalesman() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public MainIncome findIncomeMain(String salesmanId, String month) {
    MainIncome main = mainIncomeRep.findBySalesman_IdAndMonth(salesmanId, month);
    if (null == main) {
      main = mainIncomeRep.save(new MainIncome(salesmanRep.findById(salesmanId), month));
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
   * 待讨论:计算油补 <br/>
   * 方案两种:1.实时计算,参数(业务员和油补金额) 2.每天晚上定时计算,计算每天的;
   * 
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void calculateOil() {
    mainIncomeRep.calculateOilCost();
  }
  
}
