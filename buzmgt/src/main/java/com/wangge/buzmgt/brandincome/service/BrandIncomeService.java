package com.wangge.buzmgt.brandincome.service;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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
   * @Title: findAll
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
   * @Title: findAll
   * @Description: 条件查询,排序
   * @param @param spec
   * @param @param sort
   * @param @return    设定文件
   * @return List<BrandIncome>    返回类型
   * @throws
   */
  List<BrandIncome> findAll(Map<String, Object> spec, Sort sort);
}
