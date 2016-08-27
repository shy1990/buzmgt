package com.wangge.buzmgt.goods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
