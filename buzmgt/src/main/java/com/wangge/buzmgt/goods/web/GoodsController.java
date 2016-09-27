package com.wangge.buzmgt.goods.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.goods.service.BrandService;
import com.wangge.buzmgt.goods.service.GoodsService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.BrandType;
/**
 * 
* @ClassName: GoodsController
* @Description: 
* @author ChenGuop
* @date 2016年8月27日 下午4:20:41
*
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

  @Autowired
  private GoodsService goodsService;
  @Autowired
  private MainPlanService mainPlanService;
  @Autowired
  private BrandService brandService;
  /**
   * 
  * @Title: findGoodsByBrandId 
  * @Description: 根据机型查询品牌
  * @param @param brandId
  * @param @return    设定文件 
  * @return List<Goods>    返回类型 
  * @throws
   */
  @RequestMapping("/getBrand")
  @ResponseBody
  public List<BrandType> findBrandByMachineType(String code){
    return mainPlanService.findCodeByMachineType(code);
  }
  /**
   * 
  * @Title: findByNameLike 
  * @Description: 根据商品模糊查询
  * @param @param name
  * @param @return    设定文件 
  * @return List<String>    返回类型 
  * @throws
   */
  @RequestMapping("/likeName")
  @ResponseBody
  public List<String> findByNameLike(String name){
    return brandService.findByNameLike(name);
  }
  /**
   * 
   * @Title: findByBrandNameLike 
   * @Description: 根据品牌模糊查询
   * @param @param name
   * @param @return    设定文件 
   * @return List<String>    返回类型 
   * @throws
   */
  @RequestMapping("/likeBrandName")
  @ResponseBody
  public List<String> findByBrandNameLike(String name){
    return brandService.findByNameLike(name);
  }
  /**
   * 
  * @Title: findGoodsByBrandId 
  * @Description: 根据品牌查询型号；
  * @param @param brandId
  * @param @return    设定文件 
  * @return List<Goods>    返回类型 
  * @throws
   */
  @RequestMapping("/{brandId}")
  @ResponseBody
  public List<Goods> findGoodsByBrandId(@PathVariable(value="brandId") String brandId){
    return goodsService.findByBrandId(brandId);
  }
  /**
   * 
   * @Title: findGoodsByBrandId 
   * @Description: 根据品牌查询型号；
   * @param @param brandId
   * @param @return    设定文件 
   * @return List<Goods>    返回类型 
   * @throws
   */
  @RequestMapping("/{machineType}/{brandId}")
  @ResponseBody
  public List<Goods> findGoodsByMachineTypeAndBrandId(@PathVariable(value="machineType") String machineType,@PathVariable(value="brandId") String brandId){
    return goodsService.findByMachineTypeAndBrandId(machineType, brandId);
  }
  
}
