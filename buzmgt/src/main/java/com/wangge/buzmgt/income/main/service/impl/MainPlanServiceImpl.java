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
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.sys.service.RoleService;
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
  public void save(MainIncomePlan plan) throws Exception {
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
    } catch (Exception e) {
      LogUtil.error("保存主计划失败", e);
      throw new RuntimeException("保存主计划失败");
    }
  }
  
  /**
   * 1.删除主方案从本天生效<br/>
   * TODO 达量叠加到此日截止,无相应方案
   * 
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
          // TODO 计算之前的订单
          Jobtask jobtask = new Jobtask(12, usr.getSalesmanId(), plan.getId(), usr.getCreatetime());
          jobList.add(jobtask);
        }
        users += usr.getSalesmanId() + ",";
      }
      planUserRep.save(ulist);
      if (jobList.size() > 0)
        jobRep.save(jobList);
      
      LogUtil.info("用户" + authorName + "---" + authorId + "给主方案--" + plan.getMaintitle() + "添加了" + ulist.size() + "个人员:"
          + users);
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
      Optional<Date> fqtimeOp = planUserRep.findMaxFqtimeBySalesmanId(usr.getSalesmanId());
      fqtimeOp.ifPresent(fqtime -> {
        if (usr.getCreatetime().getTime() < fqtime.getTime()) {
          msgList.add(usr.getSalesmanname() + "最新删除日期:" + DateUtil.date2String(fqtime));
          cancleList.add(usr);
        }
      });
    }
    for (String msg : msgList) {
      msgs += msg + ",";
    }
    ulist.removeAll(cancleList);
    if (msgs.length() > 2)
      msgs = msgs.substring(0, msgs.length() - 1);
    String finalMsg = "";
    if (cancleList.size() > 0) {
      finalMsg = "新增" + ulist.size() + "个用户,然" + cancleList.size() + "个业务员的新增时间与最新的删除时间冲突,请重新添加!!最新删除日期如下:" + msgs;
    } else {
      finalMsg = "新增" + ulist.size() + "个用户";
    }
    remap.put("msg", finalMsg);
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
    List<Object> orList = planUserRep.findEffectiveUserTime(startDate, planId);
    List<Map<String, Object>> usrList = new ArrayList<>();
    for (Object or : orList) {
      Object[] user = (Object[]) or;
      Map<String, Object> userMap = new HashMap<>();
      userMap.put("userId", user[0].toString());
      Date userCreate = (Date) user[1];
      /***
       * userCreate的时间一定小于endDate时间 <br/>
       * 要去最小区间值:即最大开始时间,最小结束时间.
       */
      if (userCreate.getTime() > startDate.getTime()) {
        userMap.put("startDate", userCreate);
      }
      // 求最小结束时间
      Date endDate1 = null;
      if (null != user[2] && null == user[3]) {
        endDate1 = (Date) user[2];
      } else if (null == user[2] && null != user[3]) {
        endDate1 = (Date) user[3];
      } else if (null != user[2] && null != user[3]) {
        Date userEndDate = (Date) user[2];
        Date planEndDate = (Date) user[3];
        endDate1 = userEndDate.getTime() > planEndDate.getTime() ? planEndDate : userEndDate;
      }
      if (endDate1 == null || endDate1.getTime() > endDate.getTime()) {
        userMap.put("endDate", endDate);
      } else {
        userMap.put("endDate", endDate1);
      }
      usrList.add(userMap);
    }
    return usrList;
    
  }
}
