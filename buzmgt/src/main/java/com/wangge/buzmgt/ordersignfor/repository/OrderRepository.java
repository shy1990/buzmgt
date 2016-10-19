package com.wangge.buzmgt.ordersignfor.repository;

import com.wangge.buzmgt.ordersignfor.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
  Order findByOrderNum(String orderNum);
}
