package com.wangge.buzmgt.brandincome.service;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.repository.BrandIncomeRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by peter on 16-8-31.
 * 品牌型号收益service
 */

@Service
public class BrandIncomeServiceImpl implements BrandIncomeService {
  @Resource
  private BrandIncomeRepository brandIncomeRepository;

  @Override
  public BrandIncome findById(Long id) {
    return brandIncomeRepository.findOne(id);
  }
}
