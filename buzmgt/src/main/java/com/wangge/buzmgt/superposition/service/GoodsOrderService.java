package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.GoodsOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by joe on 16-9-12.
 */
public interface GoodsOrderService {

    public Page<GoodsOrder> findAll(Pageable pageable);

    public Integer countNums(String startTime,String endTime,String regionId);
}
