package com.wangge.buzmgt.areaattribute.service;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.region.entity.Region;

import java.util.List;

/**
 * 提成区域属性接口
 */
public interface AreaAttributeService {

  String save(double commission, Region region, String ruleId,String type);

  AreaAttribute save(AreaAttribute areaAttribute);

  AreaAttribute findById(Long id);

  void delete(AreaAttribute areaAttribute);

  List<AreaAttribute> findByRuleIdAndTypeAndDisabled(Long id, PlanType type);

  public AreaAttribute findByRegionIdAndRuleId(String regionId,Long ruleId);//用于价格区间计算
}
