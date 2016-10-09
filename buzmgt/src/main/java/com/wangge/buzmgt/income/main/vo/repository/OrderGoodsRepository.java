package com.wangge.buzmgt.income.main.vo.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.income.main.vo.OrderGoods;

public interface OrderGoodsRepository extends JpaRepository<OrderGoods, String> {
  List<OrderGoods> findByorderNo(String orderNo);
  
  @Query(value = "select v.*  from view_order_shouji_brandsku v \n"
      + "  left join biz_order_signfor b on v.order_no = b.order_no \n" + " where b.user_id = ?1 \n"
      + "   and v.pay_date >= ?2   and v.pay_date <=?3 ", nativeQuery = true)
  List<OrderGoods> findByorderNoByDateAndSalesman(String userId, Date startDate, Date endDate);
}

