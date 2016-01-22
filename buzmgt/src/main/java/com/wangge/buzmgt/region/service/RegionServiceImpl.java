package com.wangge.buzmgt.region.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.util.RegionUtil;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.vo.OrganizationVo;
import com.wangge.buzmgt.sys.vo.RegionVo;

@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionRepository regionRepository;
	
	@Override
	public List<RegionTree> findTreeRegion(String id) {
    List<RegionTree> listRegionTree = new ArrayList<RegionTree>();
    for (Region region : regionRepository.findOne(id).getChildren()) {
      listRegionTree.add(RegionUtil.getRegionTree(region));
    }
    return listRegionTree;
  }
	@Override
	@Transactional
	public List<RegionVo> getRegionByPid(String id) {
		
		List<RegionVo> voList = new ArrayList<RegionVo>();
		
		List< Region> list = regionRepository.findByParentId(id);
		
		for(Region r : list){
			RegionVo vo = new RegionVo();
			if(r.getChildren().size() > 0){
				vo.setIsParent("true");
				vo.setIcon("../static/zTree/css/zTreeStyle/img/diy/10.png");
			}
			vo.setOpen("false");
			vo.setId(r.getId());
			vo.setPid(r.getParent().getId());
			vo.setName(r.getName());
			voList.add(vo);
		}
		     
		return voList;
	}
	@Override
	public Region getRegionById(String regionId) {
		
		return regionRepository.findById(regionId);
	}
	
  @Override
  public List<Region> findByRegion(String regionId) {
    return regionRepository.findByParentId(regionId);
  }

	@Override
	public Region findListRegionbyid(String id) {
		
		return regionRepository.findOne(id);
	}

	@Override
	public void saveRegion(Region region) {
		regionRepository.save(region);
	}

	@Override
	public void delete(Region region) {
		regionRepository.delete(region);
		
	}
  @Override
  public Region findByNameLike(String regionName) {
    return regionRepository.findByNameLike(regionName);
  }
	
	
}
