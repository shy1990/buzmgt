package com.wangge.buzmgt.areaattribute.repository;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaAttributeRepository extends JpaRepository<AreaAttribute, Long>{
  
  AreaAttribute findByRegionAndRuleIdAndTypeAndDisabled(Region region, Long ruleId, PlanType type,int disabled);

  List<AreaAttribute> findByRuleIdAndTypeAndDisabled(Long id,PlanType type,int disabled);

  public AreaAttribute findByRegionIdAndRuleId(String regionId,Long ruleId);

  /**
   * 查找对应的区域属性
   * @param regionId
   * @param ruleId
   * @param type
   * @return
   */
  public AreaAttribute findByRegionIdAndRuleIdAndType(String  regionId, Long ruleId, PlanType type);
}
