package com.wangge.buzmgt.income.main.vo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.income.main.vo.OrderGoods;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, String> {
  List<OrderGoods> findByorderNo(String orderNo);
  
  @Query("select v  from OrderGoods v    where v.userId = ?1 \n" + "   and v.signTime >= ?2   and v.signTime <=?3 ")
  List<OrderGoods> findByorderNoByDateAndSalesman(String userId, Date startDate, Date endDate);
}
