package com.wangge.buzmgt.goods.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.goods.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, String> {
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
  /**
   * 
  * @Title: findByNameLike 
  * @Description: 根据名称模糊查询ID
  * @param @param name
  * @param @return    设定文件 
  * @return List<String>    返回类型 
  * @throws
   */
  @Query("select good.id from Goods good where good.name like ?1")
  List<String> findByNameLike(String name);
  /**
   * 
  * @Title: findByBrandId 
  * @Description: 根据品牌和机型查询型号
  * @param @param brandId
  * @param @return    设定文件 
  * @return List<Goods>    返回类型 
  * @throws
   */
  List<Goods> findByMachineTypeAndBrandId(String machineType, String brandId);
}
