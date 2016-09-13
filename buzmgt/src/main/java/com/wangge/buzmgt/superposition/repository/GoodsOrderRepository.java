package com.wangge.buzmgt.superposition.repository;

import com.wangge.buzmgt.superposition.entity.GoodsOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by joe on 16-9-12.
 */
public interface GoodsOrderRepository extends JpaRepository<GoodsOrder,String>{

    public Page<GoodsOrder> findAll(Specification<GoodsOrder> specification, Pageable pageable);

    @Query(nativeQuery = true,value = "select nvl(sum(o.nums),0) nums from SYS_GOODS_ORDER o  where o.pay_time between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') and region_id = ? " +
            " GROUP BY o.REGION_ID ")
    public Integer countNums(String startTime,String endTime,String regionId);//计算每个业务员的实际提货量
}
