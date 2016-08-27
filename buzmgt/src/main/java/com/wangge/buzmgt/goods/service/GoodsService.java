package com.wangge.buzmgt.goods.service;

import java.util.List;

import com.wangge.buzmgt.goods.entity.Goods;

/**
 * 
* @ClassName: GoodsService
* @Description: 商品业务处理 
* @author ChenGuop
* @date 2016年8月27日 下午1:54:42
*
 */
public interface GoodsService {
  /**
   * 
  * @Title: findByBrandId 
  * @Description: 根据品牌查询型号
  * @param @param brandId
  * @param @return    设定文件 
  * @return List<Goods>    返回类型 
  * @throws
   */
  List<Goods> findByBrandId(String brandId);
  /**
   * 
  * @Title: findByCatId 
  * @Description: 根据分类查询品牌 
  * @param @param catId
  * @param @return    设定文件 
  * @return List<Goods>    返回类型 
  * @throws
   */
  List<Goods> findByCatId(String catId);
  /**
   * 
  * @Title: findOne 
  * @Description: 查询型号 
  * @param @param Id
  * @param @return    设定文件 
  * @return Goods    返回类型 
  * @throws
   */
  Goods findOne(String Id);
  
}
