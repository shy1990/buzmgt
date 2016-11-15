package com.wangge.buzmgt.importExcel.service;

import com.wangge.buzmgt.importExcel.entity.CacelOrder;

import java.util.List;

/**
 * 取消订单商家接口
 */
public interface CacelOrderService {


  /**
   *
   * @Title: findAll(取消订单商家)
   * @Description: 条件查询,排序
   * @param @param spec
   * @param @param sort
   * @param @return    设定文件
   * @return List<CacelOrder>    返回类型
   * @throws
   */
  List<CacelOrder> findAll();
}
