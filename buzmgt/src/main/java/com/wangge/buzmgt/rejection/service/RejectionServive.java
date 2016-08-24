package com.wangge.buzmgt.rejection.service;

import com.wangge.buzmgt.rejection.entity.Rejection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface RejectionServive {

    /**
     * 查询分页拒收记录
     * @param searchParams
     * @param pageRequest
     * @return
     */
    Page<Rejection> findAll(Map<String, Object> searchParams, Pageable pageRequest);

    /**
     * 查询拒收记录
     * @param searchParams
     * @return List
     */
    List<Rejection> findAll(Map<String, Object> searchParams);

    /**
     * 更新
     * @param rejection
     * @return
     */
    Rejection save(Rejection rejection);
}
