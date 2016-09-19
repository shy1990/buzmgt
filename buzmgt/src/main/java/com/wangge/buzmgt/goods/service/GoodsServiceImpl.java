package com.wangge.buzmgt.goods.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.goods.entity.Brand;
import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.goods.repository.GoodsRepository;

@Service
public class GoodsServiceImpl implements GoodsService {
  @Autowired
  private GoodsRepository goodsRepository;
  
  

  @Override
  public List<Goods> findByBrandId(String brandId) {
    return goodsRepository.findByBrandId(brandId);
  }

  @Override
  public List<Goods> findByCatId(String catId) {
    return goodsRepository.findByCatId(catId);
  }

  @Override
  public Goods findOne(String Id) {
    return goodsRepository.findOne(Id);
  }

  @Override
  public List<String> findByNameLike(String name) {
    name="%"+name+"%";
    return goodsRepository.findByNameLike(name);
  }

  @Override
  public List<Goods> findByMachineTypeAndBrandId(String machineType, String brandId) {
    return goodsRepository.findByMachineTypeAndBrandId(machineType, brandId);
  }

}
