package com.wangge.buzmgt.income.main.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import com.wangge.buzmgt.income.main.entity.IncomeMainplanUsers;
import com.wangge.buzmgt.income.main.entity.MainIncomePlan;
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.income.main.vo.MachineType;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;

import net.sf.json.JSONArray;

/**
 * ClassName: MainPlanService <br/>
 * Function: 提供计划主表的服务 <br/>
 * 1.各种主计划查寻服务 2.主计划创建时的查询服务 3.新增,修改人员,删除服务 date: 2016年8月22日 上午10:51:16 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
public interface MainPlanService {
  
  Map<String, Object> findAll(String regionId, Pageable pageReq);
  
  List<Object> findByUser();
  
  void modifyUser();
  
  /**
   * getAllMachineType:获得所有的机型分类. <br/>
   * 
   * @author yangqc
   * @return
   * @since JDK 1.8
   */
  List<MachineType> getAllMachineType();
  
  /**
   * getAllBrandType:获得所有的品牌. <br/>
   * 
   * @author yangqc
   * @return
   * @since JDK 1.8
   */
  JSONArray getAllBrandType();
  
  /** 
   * getAllBrandType:根据机型查询品牌. <br/> 
   * @author ChenGoup
   * @return 
   * @since JDK 1.8 
   */  
  List<BrandType> findCodeByMachineType(String machineType);  /**
   * save:保存方案. <br/>
   * 
   * @author yangqc
   * @param plan
   * @throws Exception
   * @since JDK 1.8
   */
  void save(MainIncomePlan plan) throws Exception;
  
  /**
   * delete:删除主方案 <br/>
   * 作废,加时间限制.
   * 
   * @param plan
   * @throws Exception
   * @since JDK 1.8
   */
  void delete(MainIncomePlan plan) throws Exception;
  
  /**
   * 查找主计划的业务员列表 <br/>
   * 
   * @author yangqc
   * @param pid
   * @return
   * @since JDK 1.8
   */
  List<Map<String, Object>> findUserList(Long pid);
  
  void assembleBeforeUpdate(Model model);
  
  /**
   * 搜索业务员收益方案信息列表 <br/>
   * 
   * @author yangqc
   * @param pageReq
   * @param searchParams
   * @return
   * @throws Exception 
   * @since JDK 1.8
   */
  Page<PlanUserVo> getUserpage(Pageable pageReq, Map<String, Object> searchParams) throws Exception;

  /** 
    * 保存主计划的用户 <br/> 
    * @author yangqc 
    * @param plan
    * @param ulist 
   * @throws Exception 
    * @since JDK 1.8 
    */  
  Map<String, Object> saveUser(MainIncomePlan plan, List<IncomeMainplanUsers> ulist) throws Exception;

  void deleteUser(Map<String, Object> user) throws Exception;

 
}
