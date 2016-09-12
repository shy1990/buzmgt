package com.wangge.buzmgt.income.main.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.income.main.vo.repository.MainIncomeVoRepository;
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
  
  @Override
  public void caculateOutedOrder() {
/** 1.查出已出库订单详情:订单号,业务员,
 *  2.根据品牌型号,sku进行分组,查询是否有相应的方案
 *  3.方案优先级:达量>品牌型号>价格区间;叠加计算累计;
 *  4.按订单方案存储计算结果
 *  5.更新收益主表
 * */
  }
  
  @Override
  public void caculatePayedOrder() {
    // TODO Auto-generated method stub
    /**1.查出已出库订单的订单号和出库时的计算结果
     * 2.判断受益方案是否相同(计算一整天或单个订单)
     * 3.相同就更改记录的日期,状态;
     * 4.不相同就重新计算结果,并更改记录的日期,状态;
     * 5.更新收益主表
     * */
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
}
