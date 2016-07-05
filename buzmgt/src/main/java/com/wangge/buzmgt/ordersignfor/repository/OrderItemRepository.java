package com.wangge.buzmgt.ordersignfor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.ordersignfor.entity.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
  List<OrderItem> findByOrderNum(String orderNum);
}
