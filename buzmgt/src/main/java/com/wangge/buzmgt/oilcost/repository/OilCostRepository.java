package com.wangge.buzmgt.oilcost.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.oilcost.entity.OilCost;
@Repository
public interface OilCostRepository extends JpaRepository<OilCost, Long>,
JpaSpecificationExecutor<OilCost>{
  public OilCost findOne(Long id);

}
