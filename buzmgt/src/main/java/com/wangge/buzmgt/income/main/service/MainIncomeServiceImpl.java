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
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void caculatePayedOrder() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void caculateSalesman() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  @Transactional(rollbackForClassName = "Exception")
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
    List<MainIncomeVo> rlist= voRep.findAll((Specification<MainIncomeVo>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      return cb.and(predicates.toArray(new Predicate[] {}));
    });
    return rlist;
  }
  
}
