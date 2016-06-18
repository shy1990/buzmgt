package com.wangge.buzmgt.pushmoney.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.pushmoney.entity.Category;
import com.wangge.buzmgt.pushmoney.entity.PriceScope;
import com.wangge.buzmgt.pushmoney.entity.PushMoney;

public interface PushMoneyService {

  /**
   * 查询价格区间列表
   * @return
   */
  public List<PriceScope> findPriceScopeAll();

  /**
   * 查询类别
   * @return
   */
  List<Category> findCategoryAll();

  /**
   * 查询提成列表分页
   * @param searchParams
   * @param pageable
   * @return
   */
  Page<PushMoney> findAll(Map<String, Object> searchParams, Pageable pageable);
  /**
   * 查询提成列表
   * @param searchParams
   * @return
   */
  List<PushMoney> findAll(Map<String, Object> searchParams);
  
  /**
   * 保存修改
   * @param pushMoney
   * @return
   */
  PushMoney save(PushMoney pushMoney);
  
  // TODO 设置区域属性
  void delete (Integer id);

  
  
  
}
