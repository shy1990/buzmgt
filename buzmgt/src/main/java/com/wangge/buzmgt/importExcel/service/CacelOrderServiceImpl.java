package com.wangge.buzmgt.importExcel.service;

import com.wangge.buzmgt.importExcel.repository.CacelOrderRepository;
import com.wangge.buzmgt.importExcel.entity.CacelOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 取消订单商家接口
 */
@Service
public class CacelOrderServiceImpl implements CacelOrderService{
  @Resource
  private CacelOrderRepository cacelOrderRepository;

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
  @Override
  public List<CacelOrder> findAll() {
    return cacelOrderRepository.findAll();
  }
}
