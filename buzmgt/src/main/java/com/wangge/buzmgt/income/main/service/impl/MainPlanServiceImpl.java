package com.wangge.buzmgt.income.main.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.customtask.util.PredicateUtil;
import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;
import com.wangge.buzmgt.income.main.entity.MainIncomePlan;
import com.wangge.buzmgt.income.main.repository.IncomeMainplanUsersRepository;
import com.wangge.buzmgt.income.main.repository.MainIncomePlanRepository;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.income.main.vo.MachineType;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.income.main.vo.repository.PlanUserVoRepository;
import com.wangge.buzmgt.income.schedule.entity.Jobtask;
import com.wangge.buzmgt.income.schedule.repository.JobRepository;
import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.RoleService;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.EnvironmentUtils;

import net.sf.json.JSONArray;

/**
 * 人员的删除和添加都从当日算起,有个插入时间; 计算执行时间--插入时间之间的东西 ClassName: MainPlanServiceImpl <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
@Service
public class MainPlanServiceImpl implements MainPlanService {
  @Autowired
  MainIncomePlanRepository mainPlanRep;
  @Autowired
  IncomeMainplanUsersRepository planUserRep;
  @Autowired
  RegionService regionService;
  @Autowired
  RoleService roleService;
  @Autowired
  PlanUserVoRepository planUserVorep;
  @Autowired
  JobRepository jobRep;
  @Autowired
  MainIncomeService mainIncomeService;
  @Autowired
  SalesManRepository salesmanRep;
  @Autowired
  private LogService logService;
  
  @Override
  public Map<String, Object> findAll(String regionId, Pageable pageReq) {
    Page<MainIncomePlan> repage = null;
    Map<String, Object> remap = new HashMap<String, Object>();
    if (!StringUtils.isBlank(regionId)) {
      repage = mainPlanRep.findByRegionIdAndState(regionId, FlagEnum.NORMAL, pageReq);
    } else {
      repage = mainPlanRep.findByState(FlagEnum.NORMAL, pageReq);
    }
    remap.put("number", repage.getTotalElements());
    remap.put("content", convertToView(repage.getContent()));
    return remap;
  }
  
  private Object convertToView(List<MainIncomePlan> cList) {
    List<Map<String, Object>> vList = new ArrayList<>();
    for (MainIncomePlan mp : cList) {
      Map<String, Object> remap = new HashMap<String, Object>();
      remap.put("id", mp.getId());
      remap.put("maintitle", mp.getMaintitle());
      vList.add(remap);
    }
    return vList;
  }
  
  @Override
  public List<MachineType> getAllMachineType() {
    List<Object> list = mainPlanRep.findAllMachineType();
    List<MachineType> mList = new ArrayList<MachineType>();
    for (Object o : list) {
      Object[] ob = (Object[]) o;
      mList.add(getMachine(ob));
    }
    return mList;
  }
  
  @Override
  public List<BrandType> findCodeByMachineType(String machineType) {
    List<Object> list = mainPlanRep.findCodeByMachineType(machineType);
    List<BrandType> mList = new ArrayList<BrandType>();
    for (Object o : list) {
      Object[] ob = (Object[]) o;
      mList.add(getType(ob));
    }
    return mList;
  }
  
  private MachineType getMachine(Object[] ob) {
    String name = null == ob[0] ? "" : ob[0].toString();
    String code = null == ob[1] ? "" : ob[1].toString();
    return new MachineType(name, code);
  }
  
  @Override
  public JSONArray getAllBrandType() {
    List<Object> list = mainPlanRep.findAllBrandCode();
    List<BrandType> bList = new ArrayList<BrandType>();
    for (Object o : list) {
      Object[] ob = (Object[]) o;
      bList.add(getType(ob));
    }
    JSONArray json = JSONArray.fromObject(bList);
    return json;
  }
  
  private BrandType getType(Object[] ob) {
    String brandId = null == ob[0] ? "" : ob[0].toString();
    String machineType = null == ob[1] ? "" : ob[1].toString();
    String name = null == ob[2] ? "" : ob[2].toString();
    return new BrandType(brandId, machineType, name);
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> save(MainIncomePlan plan) throws Exception {
    User author = EnvironmentUtils.getUser();
    String authorId = author.getId();
    String authorName = author.getUsername();
    Map<String, Object> remap = new HashMap<>();
    try {
      plan.setAuthorId(authorId);
      plan.setAuthorName(authorName);
      plan = mainPlanRep.save(plan);
      List<IncomeMainplanUsers> usList = plan.getUsers();
      filterCheckedFailedUser(usList, remap);
      String userIds = "";
      Date now = new Date();
      List<Jobtask> jobList = new ArrayList<>();
      for (IncomeMainplanUsers u : usList) {
        u.setMainplan(plan);
        if (now.getTime() >= u.getCreatetime().getTime()) {
          // 计算之前的订单收益
          Jobtask jobtask = new Jobtask(12, u.getSalesmanId(), plan.getId(), u.getCreatetime());
          jobList.add(jobtask);
        }
        userIds += u.getSalesmanId() + ",";
        u.setAuthorId(authorId);
      }
      if (jobList.size() > 1)
        jobRep.save(jobList);
      LogUtil.info("用户" + author.getUsername() + "---" + author.getId() + "创建了一个主方案--" + plan.getMaintitle()
          + "并添加如下人员:" + userIds);
      logService.log(null, "区间方案单品出库计算: " + plan, Log.EventType.SAVE);
    } catch (Exception e) {
      LogUtil.error("保存主计划失败", e);
      throw new RuntimeException("保存主计划失败");
    }
    return remap;
  }
  
  /**
   * 1.删除主方案从本天生效<br/>
   * 2.达量叠加到此日截止,查询用户有效时间
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(MainIncomePlan plan) throws Exception {
    User author = EnvironmentUtils.getUser();
    String authorId = author.getId();
    String authorName = author.getUsername();
    try {
      plan.setAuthorId(authorId);
      plan.setAuthorName(authorName);
      plan.setState(FlagEnum.DEL);
      plan.setFqtime(new Date());
      
      Jobtask task = new Jobtask(0, plan.getId(), new Date());
      jobRep.save(task);
      LogUtil.info("用户" + authorName + "---" + authorId + "删除了一个主方案--" + plan.getMaintitle());
    } catch (Exception e) {
      LogUtil.error("删除收益主方案出错!!", e);
      throw new Exception("删除收益主方案出错!!");
    }
  }
  
  @Override
  public List<Map<String, Object>> findUserList(Long pid) {
    List<IncomeMainplanUsers> uList = planUserRep.findByMainplan_IdAndState(pid, FlagEnum.NORMAL);
    List<Map<String, Object>> rList = new ArrayList<>();
    for (IncomeMainplanUsers inc : uList) {
      Map<String, Object> remap = new HashMap<>();
      remap.put("salesId", inc.getSalesmanId());
      remap.put("name", inc.getSalesmanname());
      remap.put("id", inc.getId());
      remap.put("fqtime", inc.getFqtime());
      rList.add(remap);
    }
    return rList;
    
  }
  
  /**
   * 将事件存入数据库,定时执行 1.删除日期大于今天;
   * 
   * @throws Exception
   * 
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteUser(Map<String, Object> user) throws Exception {
    User author = EnvironmentUtils.getUser();
    String authorId = author.getId();
    String authorName = author.getUsername();
    Long id = Long.valueOf(user.get("id").toString());
    try {
      Date fqtime = DateUtil.string2Date(user.get("fqtime").toString());
      IncomeMainplanUsers standardUser = planUserRep.findOne(id);
      standardUser.setFqtime(fqtime);
      standardUser.setAuthorId(authorId);
      Date now = new Date();
      standardUser.setUptime(now);
      Jobtask task = null;
      if (fqtime.getTime() <= now.getTime()) {
        standardUser.setState(FlagEnum.DEL);
        task = new Jobtask(10, standardUser.getSalesmanId(), standardUser.getPlanId(), fqtime);
      } else {
        task = new Jobtask(11, standardUser.getSalesmanId(), standardUser.getPlanId(), fqtime);
      }
      task.setKeyid(id);
      jobRep.save(task);
      LogUtil.info("用户" + authorName + "---" + authorId + "删除收益主方案用户,其ID为:" + standardUser.getId());
    } catch (Exception e) {
      LogUtil.error("删除用户失败", e);
      throw new Exception("失败:删除收益主方案" + "用户,其ID为:" + id);
    }
    
  }
  
  @Override
  public void assembleBeforeUpdate(Model model) {
    model.addAttribute("regions", regionService.findByTypeOrderById(regionService.findByRegionTypeName("省")));
    // model.addAttribute("roles", roleService.findAll());
  }
  
  @Override
  public Page<PlanUserVo> getUserpage(Pageable pageReq, Map<String, Object> searchParams) throws Exception {
    Page<PlanUserVo> page = null;
    try {
      page = planUserVorep.findAll((Specification<PlanUserVo>) (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<Predicate>();
        PredicateUtil.createPedicateByMap(searchParams, root, cb, predicates);
        return cb.and(predicates.toArray(new Predicate[] {}));
      }, pageReq);
    } catch (Exception e) {
      LogUtil.error("查询主计划人员失败", e);
      throw new Exception("查询主计划人员失败");
    }
    return page;
  }
  
  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> saveUser(MainIncomePlan plan, List<IncomeMainplanUsers> ulist) throws Exception {
    Map<String, Object> remap = new HashMap<String, Object>();
    User author = EnvironmentUtils.getUser();
    String authorId = author.getId();
    String authorName = author.getUsername();
    try {
      String users = "";
      filterCheckedFailedUser(ulist, remap);
      Date now = new Date();
      List<Jobtask> jobList = new ArrayList<>();
      for (IncomeMainplanUsers usr : ulist) {
        usr.setMainplan(plan);
        usr.setAuthorId(authorId);
        usr.setPlanId(plan.getId());
        // 计算以前的订单
        if (now.getTime() >= usr.getCreatetime().getTime()) {
          Jobtask jobtask = new Jobtask(12, usr.getSalesmanId(), plan.getId(), usr.getCreatetime());
          jobList.add(jobtask);
        }
        users += usr.getSalesmanId() + ",";
      }
      planUserRep.save(ulist);
      if (jobList.size() > 0)
        jobRep.save(jobList);
      
      logService.log(null,
          "用户" + authorName + "---" + authorId + "给主方案--" + plan.getMaintitle() + "添加了" + ulist.size() + "个人员:" + users,
          EventType.UPDATE);
    } catch (Exception e) {
      LogUtil.error("保存收益主方案人员出错", e);
      throw new Exception("保存收益主方案人员出错");
    }
    return remap;
  }
  
  /**
   * filterCheckedFailedUser:剔除新建时间上无法通过校验的用户. <br/>
   * 
   * @author yangqc
   * @param ulist
   * @param remap
   * @since JDK 1.8
   */
  private void filterCheckedFailedUser(List<IncomeMainplanUsers> ulist, Map<String, Object> remap) {
    String msgs = "";
    List<String> msgList = new ArrayList<>();
    List<IncomeMainplanUsers> cancleList = new ArrayList<>();
    // 将创建日期小于上个删除日期的业务员剔除
    for (IncomeMainplanUsers usr : ulist) {
      String userId = usr.getSalesmanId();
      Optional<Date> fqtimeOp = planUserRep.findMaxFqtimeBySalesmanId(userId);
      
      fqtimeOp.ifPresent(fqtime -> {
        if (usr.getCreatetime().getTime() < fqtime.getTime()) {
          msgList.add(usr.getSalesmanname() + "最新删除日期:" + DateUtil.date2String(fqtime));
          cancleList.add(usr);
        }
      });
      // 添加时间必须小于入职时间
      if (!fqtimeOp.isPresent()) {
        Date regDate = salesmanRep.findById(userId).getRegdate();
        if (usr.getCreatetime().getTime() < regDate.getTime()) {
          msgList.add(usr.getSalesmanname() + "的入职时间为:" + DateUtil.date2String(regDate));
          cancleList.add(usr);
        }
      }
    }
    for (String msg : msgList) {
      msgs += msg + "->";
    }
    ulist.removeAll(cancleList);
    if (msgs.length() > 2)
      msgs = msgs.substring(0, msgs.length() - 2);
    String finalMsg = "";
    if (cancleList.size() > 0) {
      finalMsg = "新增" + ulist.size() + "个用户,然" + cancleList.size() + "个业务员存在着时间冲突,请重新添加!!冲突如下:" + msgs;
    }
    remap.put("errMsg", finalMsg);
  }
  
  /**
   * getCheckFailedUserList:. <br/>
   * 
   * @author yangqc
   * @param ulist
   * @return
   * @since JDK 1.8
   */
  
  @Override
  public void alterUserFlag(Long planUserId) {
    planUserRep.updateFlagById(FlagEnum.DEL, planUserId);
  }
  
  @Override
  public MainIncomePlan findById(Long id) {
    return mainPlanRep.findOne(id);
  }
  
  /**
   * 0:删除主方案:不在计算收益;<br/>
   * 1.删除删除日期当天的收益<br/>
   * 将叠加,达量的计算截止到今天. 每个业务员来删除,每个人查东西
   * 
   * @since JDK 1.8
   */
  @Override
  public void deleteIncomeMainPlan(Jobtask jobtask) throws Exception {
    Long planId = jobtask.getPlanId();
    Date delDate = jobtask.getExectime();
    try {
      List<IncomeMainplanUsers> userList = planUserRep.findByMainplan_Id(planId);
      for (IncomeMainplanUsers usr : userList) {
        mainIncomeService.deleteSubIncome(planId, usr.getSalesmanId(), delDate);
      }
    } catch (Exception e) {
      LogUtil.error("删除主方案" + planId + "下的订单收益失败");
    }
  }
  
  @Override
  public List<Map<String, Object>> findEffectUserDateList(Long planId, Date startDate, Date endDate) {
    // iu.salesman_id,iu.createtime,iu.fqtime,m.fqtime
    List<Object> orList = planUserRep.findEffectiveUsersTime(startDate, planId);
    List<Map<String, Object>> usrList = new ArrayList<>();
    for (Object or : orList) {
      Object[] user = (Object[]) or;
      Map<String, Object> userMap = new HashMap<>();
      getEffectiveDate(startDate, endDate, user, userMap);
      usrList.add(userMap);
    }
    return usrList;
    
  }
  
  /**
   * getEffectiveDate:获取一个用户的规则有效时间. <br/>
   * 
   * @author yangqc
   * @param startDate
   * @param endDate
   * @param user
   * @param userMap
   * @since JDK 1.8
   */
  private void getEffectiveDate(Date startDate, Date endDate, Object[] user, Map<String, Object> userMap) {
    userMap.put("userId", user[0].toString());
    Date userCreate = (Date) user[1];
    /***
     * userCreate的时间一定小于endDate时间 <br/>
     * 要去最小区间值:即最大开始时间,最小结束时间.
     */
    if (userCreate.getTime() > startDate.getTime()) {
      userMap.put("startDate", userCreate);
    } else {
      userMap.put("startDate", startDate);
    }
    // 求最小结束时间 iu.fqtime,方案用户删除m.fqtime planfqsj主方案删除,s.fireddate 业务员辞退时间
    Date endDate1 = null;
    Date userEnd = user[2] == null ? null : (Date) user[2];
    Date planEnd = user[3] == null ? null : (Date) user[3];
    Date salesmanEnd = user[3] == null ? null : (Date) user[4];
    endDate1 = compareDate(userEnd, planEnd);
    endDate1 = compareDate(endDate1, salesmanEnd);
    userMap.put("endDate", compareDate(endDate1, endDate));
  }
  
  /**
   * 比较两个日期的大小,求最小值<br/>
   * 单个为空则返回不为空的,<br/>
   * 都为空则返回null,<br/>
   * 不为空则返回最小的那个.
   */
  private Date compareDate(Date userEnd1, Date userEnd2) {
    if (userEnd1 == null && userEnd2 != null) {
      return userEnd2;
    } else if (userEnd2 == null && userEnd1 != null) {
      return userEnd1;
    } else if (userEnd2 != null && userEnd1 != null) {
      if (userEnd2.getTime() <= userEnd1.getTime()) {
        return userEnd2;
      } else {
        return userEnd1;
      }
    }
    return null;
  }
  
  @Override
  public List<Map<String, Object>> findEffectUserDate(Long planId, Date startDate, Date endDate, String userId) {
    List<Object> orList = planUserRep.findEffectiveUserTime(startDate, planId, userId);
    List<Map<String, Object>> usrList = new ArrayList<>();
    for (Object or : orList) {
      Object[] user = (Object[]) or;
      Map<String, Object> userMap = new HashMap<>();
      getEffectiveDate(startDate, endDate, user, userMap);
      usrList.add(userMap);
    }
    return usrList;
  }
}
