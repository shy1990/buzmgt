package com.wangge.buzmgt.areaattribute.repository;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaAttributeRepository extends JpaRepository<AreaAttribute, Long>{
  
  AreaAttribute findByRegionAndRuleIdAndType(Region region, Long ruleId, PlanType type);

  List<AreaAttribute> findByRuleIdAndTypeAndDisabled(Long id,PlanType type,int disabled);
}
