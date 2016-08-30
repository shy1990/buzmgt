package com.wangge.buzmgt.goods.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.goods.entity.Brand;
import com.wangge.buzmgt.goods.repository.BrandRepository;
/**
 * 
* @ClassName: BrandServiceImpl
* @Description: 品牌业务逻辑处理
* @author ChenGuop
* @date 2016年8月29日 下午5:31:50
*
 */
@Service
public class BrandServiceImpl implements BrandService {

  @Autowired
  private BrandRepository brandRepository;
  @Override
  public List<String> findByNameLike(String name) {
    List<String> brandIds = new ArrayList<>();
    List<Brand> brands = brandRepository.findByNameLike(name);
    brands.forEach(brand->{
      brandIds.add(brand.getId()); 
    });
    return brandIds;
  }

}
