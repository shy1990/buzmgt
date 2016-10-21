package com.wangge.buzmgt.achieveset.vo.repository;/**
 * Created by ChenGuop on 2016/10/14.
 */

import com.wangge.buzmgt.achieveset.vo.OrderVo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单展示数据处理
 * OrderVoRepository
 *
 * @author ChenGuop
 * @date 2016/10/14
 */
public interface OrderVoRepository extends JpaRepository<OrderVo,Long> {
}
