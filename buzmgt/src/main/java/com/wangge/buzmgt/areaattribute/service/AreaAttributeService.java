package com.wangge.buzmgt.areaattribute.service;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.region.entity.Region;

import java.util.List;

/**
 * 提成区域属性接口
 */
public interface AreaAttributeService {

  String save(Double commission, Region region, String ruleId,String type);

  AreaAttribute save(AreaAttribute areaAttribute);

  AreaAttribute findById(Long id);

  void delete(AreaAttribute areaAttribute);

  List<AreaAttribute> findByRuleIdAndTypeAndDisabled(Long id, PlanType type);
}
