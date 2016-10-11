package com.wangge.buzmgt.income.main.repository;

import com.wangge.buzmgt.income.main.entity.HedgeCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HedgeCostRepository extends JpaRepository<HedgeCost, Long> ,CustomRepository{
}
