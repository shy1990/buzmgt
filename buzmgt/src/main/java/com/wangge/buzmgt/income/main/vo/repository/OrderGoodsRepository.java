package com.wangge.buzmgt.income.main.vo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.income.main.vo.OrderGoods;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, String> {
  List<OrderGoods> findByorderNo(String orderNo);
}
