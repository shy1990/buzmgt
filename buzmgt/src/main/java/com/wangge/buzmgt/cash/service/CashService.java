package com.wangge.buzmgt.cash.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.cash.entity.Cash;

public interface CashService {
  
  /**
   * 生成收现金
  * @Title: save 
  * @Description: TODO(这里用一句话描述这个方法的作用) 
  * @param @param cash
  * @param @return    设定文件 
  * @return Cash    返回类型 
  * @throws
   */
  public Cash save(Cash cash);
  /**
   * 查询现金列表
   * @param searchParams
   * @return
   */
  public  List<Cash> findAll(Map<String, Object> searchParams);
  
  /**
   * 查询分页现金列表
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public  Page<Cash> findAll(Map<String, Object> searchParams, Pageable pageRequest);
  
  /**
   * 根据id查询现金订单
   * @param id
   * @return 
   */
  public Cash findById(Long id);

  /**
   * 处理状态
   * @param searchParams
   * @param pageRequest
   * @return
   */
  public List<Cash> findAllByParams(Map<String, Object> searchParams);
  
  /**
   * 购物车结算
   * 将所有未结算订单全部结算
   * 
   * 流程：1.根据userID和cashIds查询所要处理的现金订单cash
   * 2.生成流水单号-->使用：流水订单+流水订单详情
   * 3.组装流水单详情数据
   * 4.组装流水单数据
   * 5.保存流水单
   * 6.保存流水单详情列表
   * 7.修改现金订单列表中状态status改为1（已结算）
   * 8.返回状态
  * @Title: createWaterOrderByCash 
  * @Description: 购物车结算 
  * @param @param userId
  * @param @return    设定文件 
  * @return boolean    返回类型 
  * @throws
   */
  public boolean createWaterOrderByCash(String userId);
  
  /**
   * 
  * @Title: findByStatusGroupByUserId 
  * @Description: 查询未结算的收现金订单userId 
  * @param @return    设定文件 
  * @return List<String>    返回类型 
  * @throws
   */
  public List<String> findByStatusGroupByUserId();
  /**
   * 
   * @Title: findByStatusGroupByUserId 
   * @Description: 查询为过期未结算的收现金订单userId 
   * @param @return    设定文件 
   * @return List<String>    返回类型 
   * @throws
   */
  public List<String> findByStatusGroupByUserIdForSceduled(String searchDate);
  
}
