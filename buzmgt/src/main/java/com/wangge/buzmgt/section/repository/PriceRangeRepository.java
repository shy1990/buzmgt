package com.wangge.buzmgt.section.repository;

import com.wangge.buzmgt.section.entity.PriceRange;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by joe on 16-8-29.
 */
public interface PriceRangeRepository extends JpaRepository<PriceRange,Long>{

    //根据id查询
    public PriceRange findByPriceRangeId(Long id);
}
