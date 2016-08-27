package com.wangge.buzmgt.goods.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.goods.service.GoodsService;
import com.wangge.buzmgt.income.main.service.MainPlanService;
@Controller
@RequestMapping("/goods")
public class GoodsController {

  @Autowired
  private GoodsService goodsService;
  @Autowired
  private MainPlanService mainPlanService;
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
  
}
