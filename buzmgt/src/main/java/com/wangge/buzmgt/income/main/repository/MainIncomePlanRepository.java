package com.wangge.buzmgt.income.main.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.income.main.entity.MainIncomePlan;

public interface MainIncomePlanRepository
    extends JpaRepository<MainIncomePlan, Long> {
 
  Page<MainIncomePlan> findByRegionIdAndState(String regionId,FlagEnum flag,  Pageable pageReq);
  Page<MainIncomePlan> findByState(FlagEnum flag,  Pageable pageReq);
  /** 
    *获取分类列表
    * @author yangqc 
    * @return 
    * @since JDK 1.8 
    */  
  @Query(value="select t.name,t.code from sys_machine_type t",nativeQuery=true)
  List<Object> findAllMachineType();
  
  /** 
    * 通过分类获取某种分类下的品牌 
    * @author yangqc 
    * @param machineCode
    * @return 
    * @since JDK 1.8 
    */  
  @Query(value="select  c.BRAND_ID brandId,c.MACHINE_TYPE machineType,c.name from sys_sj_brand_code c where c.MACHINE_TYPE = ?1",nativeQuery=true)
  List<Object> findCodeByMachineType(String machineCode);
  /** 
    * findAllBrandCode:获得所有的品牌型号. <br/> 
    * @author yangqc 
    * @return 
    * @since JDK 1.8 
    */  
  @Query(value="select  c.BRAND_ID brandId,c.MACHINE_TYPE machineType,c.name from sys_sj_brand_code c ",nativeQuery=true)
  List<Object> findAllBrandCode();
  
  
}
