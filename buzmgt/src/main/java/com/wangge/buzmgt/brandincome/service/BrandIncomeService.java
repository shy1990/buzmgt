package com.wangge.buzmgt.brandincome.service;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.entity.BrandIncomeVo;
import org.springframework.boot.logging.logback.LogbackLoggingSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 品牌型号收益接口
 */
public interface BrandIncomeService {

  /**
   *
   * @Title: findById
   * @Description: 根据id查询
   * @param @param id
   * @param @return    设定文件
   * @return BrandIncome    返回类型
   * @throws
   */
  BrandIncome findById(Long id);

  /**
   *
   * @Title: save
   * @Description: 保存
   * @param @param brandIncome
   * @param @return    设定文件
   * @return BrandIncome    返回类型
   * @throws
   */
  BrandIncome save(BrandIncome brandIncome);

  /**
   *
   * @Title: findAll(品牌型号收益列表)
   * @Description: 条件查询,分页
   * @param @param searchParams
   * @param @param pageable
   * @param @return    设定文件
   * @return Page<BrandIncome>    返回类型
   * @throws
   */
  Page<BrandIncome> findAll(Map<String, Object> searchParams, Pageable pageable);

  /**
   *
   * @Title: findAll(品牌型号收益)
   * @Description: 条件查询,排序
   * @param @param spec
   * @param @param sort
   * @param @return    设定文件
   * @return List<BrandIncome>    返回类型
   * @throws
   */
  List<BrandIncome> findAll(Map<String, Object> spec, Sort sort);

  /**
   *
   * @Title: findAll(品牌型号进程列表)
   * @Description: 条件查询,分页
   * @param @param request
   * @param @param brandIncome
   * @param @param pageable
   * @param @return    设定文件
   * @return Page<BrandIncomeVo>    返回类型
   * @throws
   */
  Page<BrandIncomeVo> findAll(HttpServletRequest request, BrandIncome brandIncome, Pageable pageable);

  /**
   *
   * @Title: findCycleSales
   * @Description: 根据goodId统计该周期内的提货量
   * @param @param brandIncome
   * @param @return    设定文件
   * @return int    返回类型
   * @throws
   */
  int findCycleSales(BrandIncome brandIncome);

  /**
   *
   * @Title: findAll(品牌型号收益进程)
   * @Description: 条件查询,排序
   * @param @param request
   * @param @param brandIncome
   * @param @return    设定文件
   * @return List<BrandIncomeVo>    返回类型
   * @throws
   */
  List<BrandIncomeVo> findAll(HttpServletRequest request, BrandIncome brandIncome);

  /**
   * findRuleByGoods:(这里用一句话描述这个方法的作用). <br/>
   * 通过商品id查询其对应的规则
   *
   * @author yangqc
   * @param goodIds
   * @param mainPlanId
   * @param userId
   * @param payDate
   * @return
   * @since JDK 1.8
   */
  List<Map<String,Object>> findRuleByGoods(List<String> goodIds, Long mainPlanId, String userId, Date payDate);

  /**
   *
   * @Title: realTimeBrandIncomePay
   * @Description: 已付款品牌型号实时收益计算
   * @param @param brandIncome
   * @param @param num
   * @param @param orderNo
   * @param @param goodId
   * @param @param userId
   * @param @param payDate
   * @param @return    设定文件
   * @return Boolean    返回类型
   * @throws
   */
  Boolean realTimeBrandIncomePay(BrandIncome brandIncome,int num,String orderNo,String goodId, String userId,Date payDate,String regionId);

  /**
   *
   * @Title: realTimeBrandIncomeOut
   * @Description: 已出库品牌型号实时收益计算
   * @param @param brandIncome
   * @param @param num
   * @param @param orderNo
   * @param @param goodId
   * @param @param userId
   * @param @return    设定文件
   * @return Boolean    返回类型
   * @throws
   */
  Boolean realTimeBrandIncomeOut(BrandIncome brandIncome,int num,String orderNo,String goodId, String userId,String regionId);
}
