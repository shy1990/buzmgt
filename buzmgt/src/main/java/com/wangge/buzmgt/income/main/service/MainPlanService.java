package com.wangge.buzmgt.income.main.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.income.main.vo.MachineType;

import net.sf.json.JSONArray;

/** 
  * ClassName: MainPlanService <br/> 
  * Function: 提供计划主表的服务 <br/> 
  * 1.各种主计划查寻服务
  * 2.主计划创建时的查询服务
  * 3.新增,修改人员,删除服务
  * date: 2016年8月22日 上午10:51:16 <br/> 
  * 
  * @author yangqc 
  * @version  
  * @since JDK 1.8 
  */  
public interface MainPlanService {
  
  Page<?> findAll(String regionId, Pageable pageReq);
  List<Object> findByUser();
  void modifyUser();
  void deletePlan();
  void assemblebeforeNew();
  /** 
   * getAllMachineType:获得所有的机型分类. <br/> 
   * @author yangqc 
   * @return 
   * @since JDK 1.8 
   */  
 List<MachineType> getAllMachineType();
 /** 
   * getAllBrandType:获得所有的品牌. <br/> 
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
 List<BrandType> findCodeByMachineType(String machineType);
}
