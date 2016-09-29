package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-8-19.
 */
public interface ProductionService {


    public Production addProduction(List<PriceRange> priceRanges, String productType, String implementationDate, String status);

    public Production findByEndTimeIsNullAndProductStatus(String productStatus, String type);

    public void review(Long id, String status);//改变状态(渠道审核:通过/驳回)

    //渠道审核
    public void toReview(Long id, String status, String auditorId);

    public Production findNow(String type);

    public Production findById(Long id);//根据id查找

    public List<Production> findNotExpired(String type);//查询未过期的(使用中,审核中,被驳回)

    public Page<Production> findExpired(String type, String status, Integer page, Integer size);

    public Page<Production> findAll(String type, Pageable pageable);

    public Map<String,Object> findNowCW(String type);

    public Production save(Production production);

    public List<PriceRange> findReview(String type);//查询正在审核中的小区间

    public Production findNow(String type, String time);//用于接口调用


}
