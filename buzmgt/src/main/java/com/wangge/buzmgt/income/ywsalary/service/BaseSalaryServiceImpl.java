package com.wangge.buzmgt.income.ywsalary.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.repository.MainIncomeRepository;
import com.wangge.buzmgt.income.main.service.IncomeErrorService;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalaryUser;
import com.wangge.buzmgt.income.ywsalary.repository.BaseSalaryRepository;
import com.wangge.buzmgt.income.ywsalary.repository.BaseSalaryUserRepository;
import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.EnvironmentUtils;

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
  @Autowired
  private LogService logService;
  @Autowired
  private IncomeErrorService errorService;
  
  /**
   * TODO 每次加载组织机构,无解
   * 
   */
  @Override
  public List<Map<String, Object>> findAll1(Map<String, Object> searchParams, Pageable pageRequest) {
    
    List<BaseSalary> baseSalarys = baseSalaryRepository.findAll((Specification<BaseSalary>) (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<Predicate>();
      // predicates.add(cb.equal(root.get("flag"), FlagEnum.NORMAL));
      
      // Join<BaseSalary, SalesMan> userJion = root
      // .join(root.getModel().getDeclaredSingularAttribute("user",
      // SalesMan.class), JoinType.LEFT);
      // Join<SalesMan, Region> regionJion = userJion.join("region",
      // JoinType.LEFT);
      return cb.and(predicates.toArray(new Predicate[] {}));
    }, pageRequest.getSort());
    return converIntoVo(baseSalarys, DateUtil.getDayOfCurrentMonth());
    
  }
  
  @Override
  public Map<String, Object> findAll(Map<String, Object> searchParams, Pageable pageRequest) {
    Map<String, Object> pageMap = new HashMap<String, Object>();
    
    try {
      Page<BaseSalary> baseSalaryPage = null;
      Object ltDate = searchParams.get("GT_newdate");
      
      double monthDays = 0;
      if (null != ltDate && ltDate.toString().length() > 1) {
        String month = ltDate.toString();
        monthDays = DateUtil.getDaysOfMonth2(Integer.valueOf(month.substring(0, 4)),
            Integer.valueOf(month.substring(5, 7)));
        Date nextMonth = DateUtil.string2Date(searchParams.get("LT_deldate").toString());
        Date thisMonth = DateUtil.getPreMonthDate(nextMonth, -1);
        String userId = searchParams.get("salesId").toString();
        baseSalaryPage = baseSalaryRepository.findbyMonthAndUser(nextMonth, FlagEnum.DEL, userId, thisMonth,
            pageRequest);
      } else {
        baseSalaryPage = baseSalaryRepository.findAll((Specification<BaseSalary>) (root, query, cb) -> {
          List<Predicate> predicates = new ArrayList<Predicate>();
          // 不区分状态
          // predicates.add(cb.equal(root.get("flag"), FlagEnum.NORMAL));
          Join<BaseSalary, SalesMan> userJion = root
              .join(root.getModel().getDeclaredSingularAttribute("user", SalesMan.class), JoinType.LEFT);
          if (null != searchParams.get("trueName")) {
            predicates.add(cb.like(userJion.get("truename"), "%" + searchParams.get("trueName").toString() + "%"));
          }
          
          Object regionName = searchParams.get("regionName");
          if (null != regionName && regionName.toString().length() > 1) {
            Join<SalesMan, Region> regionJion = userJion.join("region", JoinType.LEFT);
            
            /*
             * 县,市,省; id为省时的情况
             */
            predicates.add(cb.like(regionJion.get("namepath"), "%" + regionName + "%"));
          }
          PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
          return cb.and(predicates.toArray(new Predicate[] {}));
        }, pageRequest);
        monthDays = DateUtil.getDayOfCurrentMonth();
      }
      pageMap.put("content", converIntoVo(baseSalaryPage.getContent(), monthDays));
      pageMap.put("totalElements", baseSalaryPage.getTotalElements());
    } catch (Exception e) {
      LogUtil.error("查询薪资列表出错", e);
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
  private List<Map<String, Object>> converIntoVo(List<BaseSalary> content, double monthDays) {
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
      double daySalary = Double.valueOf(String.format("%.2f", base.getSalary() / monthDays));
      voMap.put("daySalary", daySalary);
      voMap.put("state", base.getFlag().getName());
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
    User author = EnvironmentUtils.getUser();
    try {
      SalesMan salesman = salesmanRep.findById(baseSalary.getUserId());
      baseSalary.setUser(salesman);
      baseSalary.setAuthorId(author.getId());
      baseSalary.setAuthorName(author.getUsername());
      baseSalary = baseSalaryRepository.save(baseSalary);
      calThisMonthWithNewSalaryPlan(baseSalary, salesman);
      logService.log(null, "用户" + author.getUsername() + "---" + author.getId() + "新建了一个薪资记录:" + baseSalary,
          EventType.SAVE);
    } catch (Exception e) {
      LogUtil.error("保存薪资新增记录出错", e);
      throw new Exception("保存薪资新增记录出错");
    }
    
    return baseSalary;
  }
  
  /**
   * 计算使用新工资方案后的基础薪资.只关注本月和上月 <br/>
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
    // 开始计算工资
    String nextMonthStr = DateUtil.getPreMonth(new Date(), 1);
    Date thisMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 0));
    Date nextMonth = DateUtil.sdf.parse(nextMonthStr);
    // 生效日期在本月内重新计算本月的工资
    if (baseSalary.getNewdate().getTime() < nextMonth.getTime()) {
      double presum = calcuThisMonthPerSalary(salesman.getId());
      presum = Double.valueOf(String.format("%.2f", presum));
      
      if (baseSalary.getNewdate().getTime() < thisMonth.getTime()) {
        String premonthStr = DateUtil.getPreMonth(new Date(), -1);
        MainIncome premain = incomeService.findIncomeMain(baseSalary.getUserId(), premonthStr);
        if (premain.getState().ordinal() == 0) {
          double premonthDays = DateUtil.getDaysOfMonth2(Integer.valueOf(premonthStr.substring(0, 4)),
              Integer.valueOf(premonthStr.substring(5, 7)));
          double preSum = calcuPreMonthSalary(salesman, premonthDays);
          preSum = Double.valueOf(String.format("%.2f", preSum));
          incomeRep.updatebasicSalaryOrPunish(preSum, 0, preSum - premain.getBasicSalary(), main.getId());
        }
      }
      incomeRep.updatebasicSalaryOrPunish(presum, 0, presum - main.getBasicSalary(), main.getId());
    }
  }
  
  private void delete(BaseSalary baseSalary) throws ParseException {
    User author = EnvironmentUtils.getUser();
    try {
      SalesMan man = baseSalary.getUser();
      // 先废弃掉工资方案
      baseSalary.setFlag(FlagEnum.DEL);
      baseSalary.setDeldate(new Date());
      baseSalary.setAuthorId(author.getId());
      baseSalary.setAuthorName(author.getUsername());
      baseSalaryRepository.save(baseSalary);
      // 计算所有历史方案
      double sum = calcuThisMonthPerSalary(man.getId());
      sum = Double.valueOf(String.format("%.2f", sum));
      // 工资存放
      MainIncome main = incomeService.findIncomeMain(baseSalary.getUserId());
      main.setBasicSalary(sum);
      main.reSetResult();
      incomeRep.save(main);
      LogUtil.info("用户" + author.getUsername() + "---" + author.getId() + "废弃了一个薪资记录:" + baseSalary);
    } catch (Exception e) {
      String truename = baseSalary.getUser().getTruename();
      LogUtil.error("删除" + truename + "时出错", e);
      throw new RuntimeException("删除" + truename + "时出错");
    }
  }
  
  /**
   * calEnableWorkDays:计算有效的本月工资方案使用时间. <br/>
   * 
   * @author yangqc
   * @param baseSalary
   * @param monthDays
   * @param thisMonth
   *          计算的某月
   * @param endDate
   *          计算的结束时间,一般是下月1号
   * @return 计算日期的原则:<br/>
   *         1.是否有结束日期,<br/>
   *         2.起效日期是否小于本月<br/>
   *         3.结束日期是否大于下个月;<br/>
   */
  private Double calEnableWorkDays(BaseSalary baseSalary, double monthDays, Date thisMonth, Date endDate) {
    Double between1 = 0D;
    Date startDate = baseSalary.getNewdate();
    Date delDate = baseSalary.getDeldate();
    if (null == delDate) {
      if (startDate.getTime() <= thisMonth.getTime()) {
        between1 = monthDays;
      } else {
        between1 = DateUtil.daysBetween(startDate, endDate) + 0D;
      }
    } else {
      // 起效日期小于本月的
      if (DateUtil.daysBetween(startDate, thisMonth) >= 0) {
        if (delDate.getTime() <= endDate.getTime()) {
          between1 = DateUtil.daysBetween(thisMonth, delDate) + 0D;
        } else {
          between1 = monthDays;
        }
      } else {
        if (delDate.getTime() >= endDate.getTime()) {
          between1 = DateUtil.daysBetween(startDate, endDate) + 0D;
        } else {
          between1 = DateUtil.daysBetween(startDate, delDate) + 0D;
        }
      }
    }
    return between1;
  }
  
  /**
   * calcuThisMOnthPreSalary:计算某人本月基础工资方案的工资. <br/>
   * 
   * @author yangqc
   * @param man
   * @return
   * @throws ParseException
   * @since JDK 1.8
   */
  private double calcuThisMonthPerSalary(String userId) throws ParseException {
    double monthDays = DateUtil.getDayOfCurrentMonth();
    Date thisMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 0));
    Date nextMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 1));
    
    List<BaseSalary> salaryList = baseSalaryRepository.findByFlagAndUser_Id(nextMonth, FlagEnum.DEL, userId, thisMonth);
    double sum = 0;
    // 当其在结束时间本月范围内则处理叠加
    if (null != salaryList) {
      for (BaseSalary sal : salaryList) {
        double salary = sal.getSalary();
        Date endDate = sal.getDeldate() == null ? nextMonth : sal.getDeldate();
        double days = calEnableWorkDays(sal, monthDays, thisMonth, endDate);
        sum += salary * (days / monthDays);
      }
    }
    return sum;
  }
  
  /**
   * calcuThisMOnthPreSalary:重新计算某人上月方案总的工资. <br/>
   * 
   * @author yangqc
   * @param man
   * @return
   * @throws ParseException
   * @since JDK 1.8
   */
  private double calcuPreMonthSalary(SalesMan man, double premonthDays) throws ParseException {
    String premonthStr = DateUtil.getPreMonth(new Date(), -1);
    Date preMonth = DateUtil.sdf.parse(premonthStr);
    Date thisMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 0));
    List<BaseSalary> salaryList = baseSalaryRepository.findByFlagAndUser_Id(thisMonth, FlagEnum.DEL, man.getId(),
        preMonth);
    double sum = 0;
    // 当其在结束时间本月范围内则处理叠加
    if (null != salaryList) {
      for (BaseSalary sal : salaryList) {
        // 计算生效时间为上个月和本月之间的薪资记录
        double salary = sal.getSalary();
        Date endDate = sal.getDeldate() == null ? thisMonth : sal.getDeldate();
        double days = calEnableWorkDays(sal, premonthDays, preMonth, endDate);
        sum += salary * (days / premonthDays);
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
  public void update(BaseSalary baseSalary, Double salary, String upDateStr) throws Exception {
    User author = EnvironmentUtils.getUser();
    String authorId = author.getId();
    String authorName = author.getUsername();
    try {
      if (null == baseSalary) {
        throw new NullPointerException("更新时找不到原工资记录");
      }
      Date upDate = DateUtil.string2Date(upDateStr);
      // 删除原来的记录
      baseSalary.setDeldate(upDate);
      baseSalary.setFlag(FlagEnum.DEL);
      baseSalary.setAuthorId(authorId);
      baseSalary.setAuthorName(authorName);
      baseSalaryRepository.save(baseSalary);
      // 添加新记录
      SalesMan man = baseSalary.getUser();
      BaseSalary newSalary = new BaseSalary(baseSalary.getUserId(), man, salary, upDate);
      newSalary.setAuthorId(authorId);
      newSalary.setAuthorName(authorName);
      baseSalaryRepository.save(newSalary);
      // 重新计算工资
      calThisMonthWithNewSalaryPlan(newSalary, man);
      logService.log(null, "用户" + authorName + "---" + authorId + "已修改一个记录:" + baseSalary + "/n 新工资为:" + salary,
          EventType.UPDATE);
    } catch (ParseException e) {
      LogUtil.error("更新工资时出错", e);
      throw new Exception("更新工资时因时间转化出错");
    } catch (Exception e) {
      LogUtil.error("更新工资时出错", e);
      throw new Exception("更新工资时出错");
    }
  }
  
  @Override
  public Double calculateThisMonthBasicSalary(String salesId) throws ParseException {
    
    return calcuThisMonthPerSalary(salesId);
  }
  
  /**
   * 计算一些需要重新计算的业务员的基本工资. <br/>
   * 
   * @throws Exception
   */
  @Override
  @Transactional(rollbackOn = Exception.class)
  public void calcuThisMonthSalarys() throws Exception {
    try {
      Date thisMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 0));
      Date nextMonth = DateUtil.sdf.parse(DateUtil.getPreMonth(new Date(), 1));
      
      List<Object> salaryList = baseSalaryRepository.findUserIds(nextMonth, thisMonth);
      for (Object o1 : salaryList) {
        try {
          String userId = o1.toString();
          Double baseSalary = calcuThisMonthPerSalary(userId);
          MainIncome main = incomeService.findIncomeMain(userId);
          incomeRep.updatebasicSalaryOrPunish(baseSalary, 0, baseSalary - main.getBasicSalary(), main.getId());
        } catch (Exception e) {
          LogUtil.error("月初计算本月工资出错", e);
          errorService.save(52, "计算基本工资出错!!" + o1);
          throw new Exception("月初计算本月工资出错");
        }
      }
    } catch (Exception e) {
      LogUtil.error("月初计算本月工资出错", e);
      errorService.save(52, "计算基本工资出错!!");
      throw new Exception("月初计算本月工资出错");
    }
  }
  
  @Override
  @Transactional
  public void calux() {
    BaseSalary basesal = baseSalaryRepository.findOne(1L);
    basesal.setSalary(5500D);
    baseSalaryRepository.save(basesal);
    throw new NullPointerException();
  }
}
