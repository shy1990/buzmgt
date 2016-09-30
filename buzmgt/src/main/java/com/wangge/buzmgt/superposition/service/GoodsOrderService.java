package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.superposition.entity.GoodsOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by joe on 16-9-12.
 */
public interface GoodsOrderService {

  public Page<GoodsOrder> findAll(Pageable pageable);

  public Integer countNums(String startTime, String endTime, String regionId);

  /**
   * @param @param  request
   * @param @param  region
   * @param @param  pageable
   * @param @return 设定文件
   * @return Page<GoodsOrder>    返回类型
   * @throws
   * @Title: findAll
   * @Description: 根据条件统计销量订单(品牌型号)
   */
  Page<GoodsOrder> findAll(HttpServletRequest request, Region region, Pageable pageable);

  /**
   *
   * @Title: findAll
   * @Description: 根据条件统计退货冲减订单(品牌型号)
   * @param @param request
   * @param @param region
   * @param @return    设定文件
   * @return Page<GoodsOrder>    返回类型
   * @throws
   */
  List<GoodsOrder> findAll(HttpServletRequest request, Region region);
}
