package com.wangge.buzmgt.goods.service;

import java.util.List;
/**
 * 
* @ClassName: BrandService
* @Description: 品牌业务逻辑层接口
* @author ChenGuop
* @date 2016年8月29日 下午5:36:05
*
 */
public interface BrandService {

  /**
   * 
  * @Title: findByNameLike 
  * @Description: 根据品牌名称模糊查询
  * @param @param name
  * @param @return    设定文件 
  * @return List<String>    返回品牌ID
  * @throws
   */
  List<String> findByNameLike(String name);
  
}
