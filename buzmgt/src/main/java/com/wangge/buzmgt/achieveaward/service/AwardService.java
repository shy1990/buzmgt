package com.wangge.buzmgt.achieveaward.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.wangge.buzmgt.brandincome.entity.BrandIncomeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.wangge.buzmgt.achieveaward.entity.Award;

import javax.servlet.http.HttpServletRequest;

/**
 * 达量收益业务层接口
* @ClassName: AwardService
* @Description: 达量奖励收益业务层接口
* @author ChenGuop
* @date 2016年9月14日 上午11:17:43
*
 */
public interface AwardService {
  /**
   * 
  * @Title: findAll 
  * @Description: 条件查询,排序
  * @param @param spec
  * @param @param sort
  * @param @return    设定文件 
  * @return List<Award>    返回类型 
  * @throws
   */
  List<Award> findAll(Map<String, Object> spec,Sort sort);
  /**
   * 
  * @Title: findAll 
  * @Description: 分页条件查询
  * @param @param spec
  * @param @return    设定文件 
  * @return Page<Award>    返回类型 
  * @throws
   */
  Page<Award> findAll(Map<String, Object> searchParams, Pageable pageable);
  /**
   * 
  * @Title: findByMachineType 
  * @Description: 根据机型和方案查询
  * @param @param machineType
  * @param @return    设定文件 
  * @return List<Award>    返回类型 
  * @throws
   */
  List<Award> findByMachineTypeAndPlanId(String machineType,String planId);
  /**
   * 
  * @Title: save 
  * @Description: 保存达量提成
  * @param @param achieve    设定文件 
  * @return void    返回类型 
  * @throws
   */
  void save(Award achieve);
  /**
   * 
  * @Title: findOne 
  * @Description: 查询Award
  * @param @param achieveId
  * @param @return    设定文件 
  * @return Award    返回类型 
  * @throws
   */
  Award findOne(Long achieveId);

  /**
   *
   * @Title: findCycleSales
   * @Description: 根据goodId统计该周期内的提货量
   * @param @param award
   * @param @return    设定文件
   * @return int    返回类型
   * @throws
   */
  int findCycleSales(List<String> goodIds);

  /**
   *
   * @Title: findAll(达量奖励进程列表)
   * @Description: 条件查询,分页
   * @param @param request
   * @param @param award
   * @param @param pageable
   * @param @return    设定文件
   * @return Page<BrandIncomeVo>    返回类型
   * @throws
   */
  Page<BrandIncomeVo> findAll(HttpServletRequest request, Award award, Pageable pageable);

  /**
   *
   * @Title: findAll(达量奖励收益进程)
   * @Description: 条件查询,排序
   * @param @param request
   * @param @param award
   * @param @return    设定文件
   * @return List<BrandIncomeVo>    返回类型
   * @throws
   */
  List<BrandIncomeVo> findAll(HttpServletRequest request, Award award);
}
