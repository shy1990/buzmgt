package com.wangge.buzmgt.income.ywsalary.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.Resource;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalaryUser;
import com.wangge.buzmgt.income.ywsalary.repository.BaseSalaryRepository;
import com.wangge.buzmgt.income.ywsalary.repository.BaseSalaryUserRepository;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.SearchFilter;

@Service
public class BaseSalaryServiceImpl implements BaseSalaryService {
  
  @Resource
  private BaseSalaryRepository baseSalaryRepository;
  
  @Resource
  private BaseSalaryUserRepository salaryUserRepository;
  
  @Resource
  private RegionService regionService;
  @Resource
  private MainIncomeService incomeService;
  @Resource
  private MainIncomeRepository incomeRep;
  @Autowired
  SalesManRepository salesmanRep;
  
  /** 
    * TODO 每次加载组织机构,无解 
    *  
    */ 
  @Override
  public List<Map<String, Object>> findAll(Map<String, Object> searchParams) {
    
    List<BaseSalary> baseSalarys = baseSalaryRepository.findAll((Specification<BaseSalary>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      predicates.add(cb.equal(root.get("flag"), FlagEnum.NORMAL));
//      Join<BaseSalary, SalesMan> userJion = root
//          .join(root.getModel().getDeclaredSingularAttribute("user", SalesMan.class), JoinType.LEFT);
//      Join<SalesMan, Region> regionJion = userJion.join("region", JoinType.LEFT);
      return cb.and(predicates.toArray(new Predicate[] {}));
    });
    return converIntoVo(baseSalarys);
    
  }
  
  @Override
  public Map<String, Object> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, Object> pageMap = new HashMap<String, Object>();
    try {
      Page<BaseSalary> baseSalaryPage = null;
      baseSalaryPage = baseSalaryRepository.findAll((Specification<BaseSalary>) (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<Predicate>();
        predicates.add(cb.equal(root.get("flag"), FlagEnum.NORMAL));
        Join<BaseSalary, SalesMan> userJion = root
            .join(root.getModel().getDeclaredSingularAttribute("user", SalesMan.class), JoinType.LEFT);
        if (null != searchParams.get("trueName")) {
          predicates.add(cb.like(userJion.get("truename"), "%" + searchParams.get("truename") + "%"));
        }
        Object regionId = searchParams.get("regionId");
        if (null != regionId) {
          Join<SalesMan, Region> regionJion = userJion.join("region", JoinType.LEFT);
          Join<Region, Region> parentJion = regionJion.join("parent", JoinType.LEFT);
          Join<Region, Region> superJion = parentJion.join("parent", JoinType.LEFT);
          /*
           * 县,市,省; id为省时的情况
           */
          predicates
              .add(cb.or(cb.or(cb.equal(regionJion.get("id"), regionId), cb.equal(parentJion.get("id"), regionId)),
                  cb.or(cb.equal(superJion.get("id"), regionId), cb.equal(parentJion.get("id"), regionId))));
        }
        return cb.and(predicates.toArray(new Predicate[] {}));
      }, pageRequest);
      pageMap.put("content", converIntoVo(baseSalaryPage.getContent()));
      pageMap.put("totalElements", baseSalaryPage.getTotalElements());
    } catch (Exception e) {
      LogUtil.info(e.getMessage());
    }
    return pageMap;
  }
  
  /**
   * converIntoVo:将po组装为vo. <br/>
   * 
   * @author yangqc
   * @param content
   * @return
   * @since JDK 1.8
   */
  private List<Map<String, Object>> converIntoVo(List<BaseSalary> content) {
    List<Map<String, Object>> voList = new ArrayList<Map<String, Object>>();
    if (content == null) {
      return voList;
    }
    for (BaseSalary base : content) {
      Map<String, Object> voMap = new HashMap<String, Object>();
      voMap.put("id", base.getId());
      SalesMan man = base.getUser();
      voMap.put("userName", man.getTruename());
      voMap.put("region", man.getRegion().getNamepath());
      voMap.put("newdate", base.getNewdate());
      voMap.put("salary", base.getSalary());
      double monthDays = DateUtil.getDayOfCurrentMonth();
      double daySalary = Double.valueOf(String.format("%.2f", base.getSalary() / monthDays));
      voMap.put("daySalary", daySalary );
      voList.add(voMap);
    }
    return voList;
  }
  
  /**
   * 通过计算注册日期和本月的时间差来计算薪资日期.将其算入薪资表中.
   * 
   * @see com.wangge.buzmgt.income.ywsalary.service.BaseSalaryService#save(com.wangge.buzmgt.income.ywsalary.entity.BaseSalary)
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public BaseSalary save(BaseSalary baseSalary) throws Exception {
    try {
      SalesMan salesman = salesmanRep.findById(baseSalary.getUserId());
      baseSalary.setUser(salesman);
      Optional<Integer> times = baseSalaryRepository.findMaxTimesByUserId(baseSalary.getUserId());
      if (times.isPresent()) {
        baseSalary.setTimes(times.get() + 1);
      } else {
        baseSalary.setTimes(1);
      }
      
      baseSalary = baseSalaryRepository.save(baseSalary);
      calThisMonthWithNewSalaryPlan(baseSalary, salesman);
    } catch (Exception e) {
      LogUtil.error("保存薪资新增记录出错", e);
      throw new Exception("保存薪资新增记录出错");
    }
    return baseSalary;
  }
  
  /**
   * 计算使用本月新工资方案后的基础薪资. <br/>
   * 
   * @author yangqc
   * @param baseSalary
   * @param salesman
   * @throws ParseException
   * @throws NumberFormatException
   * @since JDK 1.8
   */
  private void calThisMonthWithNewSalaryPlan(BaseSalary baseSalary, SalesMan salesman)
      throws ParseException, NumberFormatException {
    MainIncome main = incomeService.findIncomeMain(baseSalary.getUserId());
    Double salary = baseSalary.getSalary();
    // 开始计算工资
    Date hireDate = salesman.getRegdate();
    double monthDays = DateUtil.getDayOfCurrentMonth();
    Date thisMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 0));
    Date nextMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 1));
    double between = calEnableWorkDays(baseSalary, hireDate, monthDays, thisMonth, nextMonth);
    between--;
    double presum = main.getBasicSalary();
    presum += salary * (between / monthDays);
    presum = Double.valueOf(String.format("%.2f", presum));
    main.setBasicSalary(presum);
    incomeRep.save(main);
  }
  
  private void delete(BaseSalary baseSalary) throws ParseException {
    try {
      SalesMan man = baseSalary.getUser();
      // 先废弃掉工资方案
      baseSalary.setFlag(FlagEnum.DEL);
      baseSalary.setDeldate(new Date());
      baseSalaryRepository.save(baseSalary);
      // 计算所有历史方案
      double sum = calcuThisMonthPreSalary(man);
      sum = Double.valueOf(String.format("%.2f", sum));
      // 工资存放
      MainIncome main = incomeService.findIncomeMain(baseSalary.getUserId());
      main.setBasicSalary(sum);
      incomeRep.save(main);
      
    } catch (Exception e) {
      String truename = baseSalary.getUser().getTruename();
      LogUtil.error("删除" + truename + "时出错", e);
      throw new RuntimeException("删除" + truename + "时出错");
    }
  }
  
  /**
   * calEnableWorkDays:计算有效的本月方案使用时间. <br/>
   * 
   * @author yangqc
   * @param baseSalary
   * @param hireDate
   * @param monthDays
   * @param thisMonth
   * @return
   * @since JDK 1.8
   */
  private Double calEnableWorkDays(BaseSalary baseSalary, Date hireDate, double monthDays, Date thisMonth,
      Date endDate) {
    Double between1 = 0D;
    Date startDate = baseSalary.getNewdate();
    // 从月初算统一加1,从入职开始算也加1
    if (baseSalary.getTimes() == 1) {
      /**
       * 1.入职时间小于本月,则计算月初到今日的工资 2.入职时间大于本月,则计算入职时间到今天的.
       */
      if (DateUtil.daysBetween(hireDate, thisMonth) >= 0) {
        between1 = DateUtil.daysBetween(thisMonth, endDate) + 1D;
      } else {
        between1 = DateUtil.daysBetween(hireDate, endDate) + 1D;
      }
    } else {
      /*
       * 1.工资方案如果开始时间小于本月,则从本月初算起, 2.如果开始时间大约本月初,则从开始时间算起
       */
      if (DateUtil.daysBetween(startDate, thisMonth) >= 0) {
        between1 = DateUtil.daysBetween(thisMonth, endDate) + 1D;
      } else {
        between1 = DateUtil.daysBetween(startDate, endDate) + 0D;
      }
    }
    return between1;
  }
  
  /**
   * calcuThisMOnthPreSalary:计算某人本月已废弃的方案总的工资. <br/>
   * 
   * @author yangqc
   * @param man
   * @return
   * @throws ParseException
   * @since JDK 1.8
   */
  private double calcuThisMonthPreSalary(SalesMan man) throws ParseException {
    double monthDays = DateUtil.getDayOfCurrentMonth();
    Date hireDate = man.getRegdate();
    Date thisMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 0));
    List<BaseSalary> salaryList = baseSalaryRepository.findByFlagAndUser_Id(FlagEnum.DEL, man.getId());
    double sum = 0;
    // 当其在结束时间本月范围内则处理叠加
    if (null != salaryList) {
      for (BaseSalary sal : salaryList) {
        if (DateUtil.daysBetween(thisMonth, sal.getDeldate()) >= 0) {
          double salary = sal.getSalary();
          double days = calEnableWorkDays(sal, hireDate, monthDays, thisMonth, sal.getDeldate());
          sum += salary * (days / monthDays);
        }
      }
    }
    return sum;
  }
  
  @Override
  public List<BaseSalaryUser> getStaySetSalesMan() {
    
    return salaryUserRepository.findAll();
  }
  
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void deleteSalaryByUser(String userId) throws ParseException {
    List<BaseSalary> bList = baseSalaryRepository.findByFlagAndUser_Id(FlagEnum.NORMAL, userId);
    if (null != bList && bList.size() > 0) {
      delete(bList.get(0));
    }
  }
  
  /**
   * update:更新薪资记录. 调用之前的服务<br/>
   * 1.删除旧记录,计算之前工资 2.添加新记录,计算之后工资
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void update(BaseSalary baseSalary, Double salary) throws Exception {
    try {
      if (null == baseSalary) {
        throw new NullPointerException("更新时找不到原工资记录");
      }
      delete(baseSalary);
      // 添加新记录
      SalesMan man = baseSalary.getUser();
      BaseSalary newSalary = new BaseSalary(baseSalary.getUserId(), man, salary, baseSalary.getTimes() + 1);
      baseSalaryRepository.save(newSalary);
      calThisMonthWithNewSalaryPlan(newSalary, man);
    } catch (ParseException e) {
      LogUtil.error("更新工资是出错", e);
      throw e;
    } catch (Exception e) {
      LogUtil.error("更新工资是出错", e);
      throw e;
    }
    
  }
  
}


