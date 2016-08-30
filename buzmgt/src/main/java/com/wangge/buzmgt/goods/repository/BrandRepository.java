package com.wangge.buzmgt.goods.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.goods.entity.Brand;

public interface BrandRepository extends JpaRepository<Brand, String> {

  public List<Brand> findByNameLike(String name);
}
