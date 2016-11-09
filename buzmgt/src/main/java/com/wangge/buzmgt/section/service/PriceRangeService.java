package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;

/**
 * Created by joe on 16-8-29.
 */
public interface PriceRangeService {

    //修改区间
    public void modifyPriceRange(Long productionId, String auditor, Double percentage, String implDate, PriceRange priceRange,String userId);

    //审核小区间
    public void reviewPriceRange(PriceRange priceRange, String status);

    public PriceRange stopPriceRange(PriceRange priceRange);

    PriceRange findById(Long id);


}
