package com.wangge.buzmgt.areaattribute.service;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.entity.AreaAttribute.PlanType;
import com.wangge.buzmgt.areaattribute.repository.AreaAttributeRepository;
import com.wangge.buzmgt.region.entity.Region;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by peter on 16-8-30.
 * 提成区域属性service
 */

@Service
public class AreaAttributeServiceImpl implements AreaAttributeService {
  @Resource
  private AreaAttributeRepository areaAttributeRepository;

  @Override
  public String save(double commission, Region region, String ruleId,String type) {
    Long ruleid = Long.valueOf(ruleId);
    AreaAttribute areaAttribute = new AreaAttribute();
    areaAttribute.setRuleId(ruleid);
    areaAttribute.setRegion(region);
    areaAttribute.setCommissions(commission);
    if (PlanType.BRANDMODEL.name().equals(type)){
      AreaAttribute brandAttr = areaAttributeRepository.findByRegionAndRuleIdAndTypeAndDisabled(region,ruleid,PlanType.BRANDMODEL,0);
      if(ObjectUtils.notEqual(brandAttr,null)){
        return "不能重复添加!";
      }
      areaAttribute.setType(PlanType.BRANDMODEL);
    }else {
      AreaAttribute rangeAttr = areaAttributeRepository.findByRegionAndRuleIdAndTypeAndDisabled(region,ruleid,PlanType.PRICERANGE,0);
      if(ObjectUtils.notEqual(rangeAttr,null)){
        return "不能重复添加!";
      }
      areaAttribute.setType(PlanType.PRICERANGE);
    }
    areaAttributeRepository.save(areaAttribute);
    return "添加成功!";
  }

  @Override
  public AreaAttribute save(AreaAttribute areaAttribute) {
    return areaAttributeRepository.save(areaAttribute);
  }

  @Override
  public AreaAttribute findById(Long id) {
    return areaAttributeRepository.findOne(id);
  }

  @Override
  public void delete(AreaAttribute areaAttribute) {
    areaAttribute.setDisabled(1);
    areaAttributeRepository.save(areaAttribute);
  }

  @Override
  public List<AreaAttribute> findByRuleIdAndTypeAndDisabled(Long id, PlanType type) {
    return areaAttributeRepository.findByRuleIdAndTypeAndDisabled(id,type,0);
  }

  @Override
  public AreaAttribute findByRegionIdAndRuleId(String regionId, Long ruleId) {
    AreaAttribute areaAttribute = areaAttributeRepository.findByRegionIdAndRuleId(regionId,ruleId);
    return areaAttribute;
  }
}
