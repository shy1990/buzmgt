package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.GoodsOrder;
import com.wangge.buzmgt.superposition.repository.GoodsOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by joe on 16-9-12.
 */
@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    @Autowired
    private GoodsOrderRepository goodsOrderRepository;



    @Override
    public Page<GoodsOrder> findAll(Pageable pageable) {
        Page<GoodsOrder> page = goodsOrderRepository.findAll(new Specification<GoodsOrder>() {
            @Override
            public Predicate toPredicate(Root<GoodsOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        },pageable);
        return page;
    }

    @Override
    public Integer countNums(String startTime, String endTime, String regionId) {

        Integer o = goodsOrderRepository.countNums(startTime,endTime,regionId);

        return o;
    }
}
