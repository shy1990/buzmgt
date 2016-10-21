package com.wangge.buzmgt.income.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.achieveset.service.AchieveIncomeService;
import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.Hedge;
import com.wangge.buzmgt.income.main.repository.HedgeRepository;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.service.HedgeService;
import com.wangge.buzmgt.income.main.vo.HedgeVo;
import com.wangge.buzmgt.income.main.vo.repository.HedgeVoRepository;
import com.wangge.buzmgt.income.schedule.entity.Jobtask;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.superposition.service.SuperpositonService;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class HedgeServiceImpl implements HedgeService {
  @Autowired
  private HedgeRepository hedgeRep;
  @Autowired
  private HedgeVoRepository hedgeVOrep;
  @Autowired
  private JobRepository jobRep;
  @Autowired
  private IncomeMainplanUsersRepository IncomeUserRep;
  @Autowired
  private SuperpositonService superService;
  @Autowired
  private AchieveIncomeService achieveService;
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void saveHedgeFromExcle(Map<Integer, String> excelContent) throws Exception {
    List<Hedge> uList = new ArrayList<>();
    try {
      excelContent.forEach((key, val) -> {
        String[] vals = val.split("-->");
        //订单号不能为空
        if (!vals[0].toString().equals("空")) {
          Hedge hedge = new Hedge(vals[0], vals[1], vals[2], Integer.valueOf(vals[4]), Double.valueOf(vals[3]),
              DateUtil.string2Date(vals[5]), vals[6]);
          uList.add(hedge);
        }
      });
      if (uList.size() > 0) {
        hedgeRep.save(uList);
        Jobtask task = new Jobtask(60, 0L, new Date());
        jobRep.save(task);
      }
    } catch (Exception e) {
      LogUtil.error("导入售后冲减表出错", e);
      throw new Exception("导入售后冲减表出错");
    }
  }
  
  @Override
  public Page<HedgeVo> getVopage(Pageable pageReq, Map<String, Object> searchParams) {
    Page<HedgeVo> page = hedgeVOrep.findAll((Specification<HedgeVo>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
      return cb.and(predicates.toArray(new Predicate[] {}));
    }, pageReq);
    return page;
  }
  
  @Override
  public int countByGoodId(String goodId) {
    return hedgeRep.countByGoodId(goodId);
  }
  
  @Override
  public int countByGoodId(List<String> goodIds) {
    return hedgeRep.countByGoodId(goodIds);
  }
  
  @Override
  public Page<HedgeVo> findAll(HttpServletRequest request, Region region, Pageable pageable) {
    Sort s = new Sort(Sort.Direction.DESC, "shdate");
    pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), s);
    Page<HedgeVo> page = hedgeVOrep.findAll((root, query, cb) -> {
      List<Predicate> predicates = getPredicate(root, cb, request, region);
      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }, pageable);
    return page;
  }
  
  @Override
  public List<HedgeVo> findAll(HttpServletRequest request, Region region) {
    Sort s = new Sort(Sort.Direction.DESC, "shdate");
    List<HedgeVo> list = hedgeVOrep.findAll((root, query, cb) -> {
      List<Predicate> predicates = getPredicate(root, cb, request, region);
      return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }, s);
    return list;
  }
  
  /**
   * 获取Predicate条件
   * 
   * @param root
   * @param cb
   * @param request
   * @param region
   * @return
   */
  public List<Predicate> getPredicate(Root<HedgeVo> root, CriteriaBuilder cb, HttpServletRequest request,
      Region region) {
    Date startDate = DateUtil.string2Date(request.getParameter("startDate"));
    Date endDate = DateUtil.string2Date(request.getParameter("endDate"));
    List<Predicate> predicates = new ArrayList<Predicate>();
    Predicate predicate = cb.between(root.get("shdate").as(Date.class), startDate, endDate);
    Predicate predicate1;
    if ("镇".equals(region.getType().getName())) {
      predicate1 = cb.equal(root.get("shopRegionId").as(String.class), region.getId());
    } else {
      predicate1 = cb.equal(root.get("regionId").as(String.class), region.getId());
    }
    predicates.add(cb.and(predicate, predicate1));
    String terms = request.getParameter("terms");
    if (StringUtils.isNotBlank(terms)) {
      Predicate predicate2 = cb.equal(root.get("orderno").as(String.class), terms);
      Predicate predicate3 = cb.like(root.get("shopName").as(String.class), "%" + terms + "%");
      predicates.add(cb.or(predicate2, predicate3));
    }
    return predicates;
  }
  
  @Override
  public void calculateHedge() {
    hedgeRep.calSectionAndBrandGood();
    hedgeRep.calShouhouHege();
  }
  
  @Override
  public void calculateAchieveHedge(Date exectime) {
    List<Object> usergoodList = hedgeRep.findByDate(exectime);
    usergoodList.stream().forEach(object -> {
      Object[] Ordergood = (Object[]) object;
      
      Date payTime = DateUtil.string2Date(Ordergood[3].toString());
      String userId = Ordergood[4].toString();
      Long hedgeId = Long.valueOf(Ordergood[5].toString());
      // 当查出主方案时调用达量和叠加的冲减算法
      // TODO 达量奖励的计算方法
      IncomeUserRep.findBysalesmanAndDate(payTime, userId).ifPresent(planId -> {
        String goodsId = Ordergood[1].toString();
        String orderNo = Ordergood[0].toString();
        int sum = Integer.valueOf(Ordergood[2].toString());
        Date acceptTime = (Date) Ordergood[6];
        // 叠加计算
        superService.computeAfterReturnGoods(userId, goodsId, DateUtil.date2String(payTime, "yyyy-MM-dd"), sum, planId,
            DateUtil.date2String(acceptTime, "yyyy-MM-dd"), hedgeId);
        // 叠加:一单达量
        superService.computeOneSingleAfterReturnGoods(userId, planId, orderNo, goodsId,
            DateUtil.date2String(payTime, "yyyy-MM-dd"), DateUtil.date2String(acceptTime, "yyyy-MM-dd"), sum);
        // 达量冲减
        achieveService.createAchieveIncomeAfterSale(userId, goodsId, planId, hedgeId, payTime, acceptTime, sum);
      });
    });
  }
  
}
