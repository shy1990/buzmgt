package com.wangge.buzmgt.goods.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.goods.entity.Goods;

public interface GoodsRepository extends JpaRepository<Goods, String> {

}
