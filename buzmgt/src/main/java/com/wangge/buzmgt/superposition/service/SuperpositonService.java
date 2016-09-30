package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.pojo.SuperpositionProgress;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-9-7.
 */
public interface SuperpositonService {

    public Page<SuperpositionProgress> searchDetail(Long planId,Long superId,String userId,String startDate,String endDate,String name,Integer page,Integer size);

    public Page<SuperpositionProgress> findAll(Long planId,Long superId,String startDate,String endDate,String name,Integer page,Integer size);

    public Superposition save(Superposition superposition);

    public void delete(Long id);

    public Superposition checkMember(String memberId);

    public Superposition findById(Long id);

    public Page<Superposition> findAll(Pageable pageable,String type,String sign,Long planId);

    public Page<PlanUserVo> findMainPlanUsers(Pageable pageReq, Map<String, Object> searchParams) throws Exception;

    public String compute(Superposition superposition);//计算收益
}
