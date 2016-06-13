package com.wangge.buzmgt.region.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.util.RegionUtil;

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
	public List<RegionTree> getRegionByPid(String id) {
		
		List<RegionTree> voList = new ArrayList<RegionTree>();
		
		List< Region> list = regionRepository.findByParentId(id);
		
		for(Region r : list){
		/*	RegionVo vo = new RegionVo();
			if(r.getChildren().size() > 0){
				vo.setIsParent("true");
				//vo.setIcon("../static/zTree/css/zTreeStyle/img/diy/10.png");
			}
			
			vo.setOpen("false");
			vo.setId(r.getId());
			vo.setPid(r.getParent().getId());
			vo.setName(r.getName());
			voList.add(vo);*/
		  voList.add(RegionUtil.getRegionTree(r));
		}
		     
		return voList;
	}
	@Override
	public Region getRegionById(String regionId) {
		
		return regionRepository.findById(regionId);
	}

	@Override
	public Region findListRegionbyid(String id) {
		
		return regionRepository.findOne(id);
	}

	@Override
	@Transactional
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
	
	public List<Region> findByRegion(String regionId) {
    return regionRepository.findByParentId(regionId);
  }
	
  public List<Region> getListByIds(SalesMan  salesman) {
    
    List<Region> regonList  = new ArrayList<Region>();
    List<String>  ids = new ArrayList<String>();
     if(salesman.getTowns() != null && !"".equals(salesman.getTowns())){
      
       String[]  towns = salesman.getTowns().split(" ");
       
       for(int i = 0;i<towns.length;i++){
        
        ids.add(towns[i]);
       }
       regonList =  regionRepository.findAll(ids);
        
     }else{
        regonList = regionRepository.findByParentId(salesman.getRegion().getId()) ;
     }
    return regonList;
  }
  @Override
  public List<Region> findByTypeOrderById(RegionType type) {
    return regionRepository.findByTypeOrderById(type);
  }


 
}
