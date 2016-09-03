package com.wangge.buzmgt.income.main.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.income.main.vo.MachineType;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.income.main.vo.repository.PlanUserVoRepository;
import com.wangge.buzmgt.income.ywsalary.entity.BaseSalary;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.service.RoleService;

import net.sf.json.JSONArray;

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
  
  @Override
  public List<Object> findByUser() {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public void modifyUser() {
    // TODO Auto-generated method stub
    
  }
  
  @Override
  public void deletePlan() {
    // TODO Auto-generated method stub
    
  }
  
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
    try {
      plan = mainPlanRep.save(plan);
      List<IncomeMainplanUsers> usList = plan.getUsers();
      for (IncomeMainplanUsers u : usList) {
        u.setMainplan(plan);
      }
    } catch (Exception e) {
      LogUtil.error("保存主计划失败", e);
      throw new RuntimeException("保存主计划失败");
    }
  }
  
  /**
   * 1.删除主方案从本天生效 TODO 2.重新计算当天收益
   */
  @Override
  @Transactional(rollbackFor = Exception.class)
  public void delete(MainIncomePlan plan) throws Exception {
    try {
      plan.setState(FlagEnum.DEL);
      plan.setFqtime(new Date());
    } catch (Exception e) {
      LogUtil.error("删除方案出错!!", e);
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
      rList.add(remap);
    }
    return rList;
    
  }
  
  /**
   * TODO 将事件存入数据库,定时执行 1.删除日期是否要大于今天;
   * 
   */
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteUser(IncomeMainplanUsers user) {
    IncomeMainplanUsers standardUser = planUserRep.findOne(user.getId());
    standardUser.setFqtime(user.getFqtime());
    // standardUser.set(user.getFqtime());
    planUserRep.delete(user.getId());
  }
  
  @Override
  public void assembleBeforeUpdate(Model model) {
    model.addAttribute("regions", regionService.findByTypeOrderById(RegionType.PROVINCE));
//    model.addAttribute("roles", roleService.findAll());
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
    try {
      for (IncomeMainplanUsers usr : ulist) {
        usr.setMainplan(plan);
      }
      planUserRep.save(ulist);
      
    } catch (Exception e) {
      LogUtil.error("保存收益主计划人员出错", e);
      throw new Exception("保存收益主计划人员出错");
    }
    return remap;
  }
}
