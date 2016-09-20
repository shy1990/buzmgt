package com.wangge.buzmgt.region.service;


import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.RegionType;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.region.repository.RegionTypeRepository;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.util.RegionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionRepository regionRepository;
	@Autowired
	private LogService logService;

  @Autowired
  private RegionTypeRepository regionTypeRepository;

//  @Autowired
//  private RegionTypeRepository regionTypeRepository;
	@Override
	public List<RegionTree> findTreeRegion(String id) {
    List<RegionTree> listRegionTree = new ArrayList<RegionTree>();
    for (Region region : regionRepository.findOne(id).getChildren()) {
      listRegionTree.add(RegionUtil.getRegionTree(region));
    }
    return listRegionTree;
  }
	@Override
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
		region = regionRepository.save(region);
		logService.log(null, region, EventType.UPDATE);
	}

	@Override
	@Transactional
	public void delete(Region region) {
		regionRepository.delete(region);
		logService.log(region, null,EventType.DELETE);
	}
	
  @Override
  public Region findByNameLike(String regionName) {
    return regionRepository.findByNameLike(regionName);
  }
	
	@Override
  public List<Region> findByRegion(String regionId) {
    return regionRepository.findByParentId(regionId);
  }
	
  @Override
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
  /**
   * 处理条件参数
   * 区域选择（油补统计）
   * @param Column 所要检索的字段
   * @param searchParams
   */
  @Override
  public void disposeSearchParams(String Column,Map<String, Object> searchParams){
    String regionId = (String) searchParams.get("regionId");
    String regionType = (String) searchParams.get("regionType");
//    COUNTRY("国"), PARGANA("大区"), PROVINCE("省"), AREA("区"), CITY("市"), COUNTY("县"), TOWN("镇"), OTHER("其他")
    String regionArr="";
    if(StringUtils.isNotEmpty(regionType)){
      
      switch (regionType) {
      case "COUNTRY":
        break;
      case "PARGANA":
        
      case "PROVINCE":
        regionArr = disposeRegionId(regionId);
        regionArr=regionArr.substring(0, regionArr.length()-1);
        break;
      case "AREA":
        regionArr = regionId.substring(0, 4);
        break;
        
      default:
        regionArr =regionId;
        break;
      }
      searchParams.put("ORMLK_"+Column, regionArr);
      searchParams.remove("regionId");
      searchParams.remove("regionType");
    }
    
  }
  
  /**
   * 根据每一个regionType判断 regionId截取的位数
   * type-->count:国家-->all
   * 
   * 
   * @param
   * @return String 格式 "3701,3702,xxxx,xxx"
   */
  public String disposeRegionId(String regionId){
    //3701,
    String regionArr="";
    List<Region> regionList=findByRegion(regionId); 
    for(int n=0;n<regionList.size();n++){
      Region region= regionList.get(n);
      String regionId1=region.getId();
      if("6".equals(region.getType().getName())){
        regionArr+=regionId1.substring(0, 4)+",";
        continue;
      }
      regionArr+=disposeRegionId(regionId1);
    }
      
    return regionArr;
  }

  @Override
  public List<Region> findByTypeOrderById(RegionType type) {

    return regionRepository.findByTypeOrderById(type);
  }
  @Override
  public List<Map<String, Object>> getAllRegion(int type) {
   // RegionType regionType= RegionType.values()[type];
    RegionType regionType=regionTypeRepository.findOne(type);
    List<Region> relist=regionRepository.findByTypeOrderById(regionType);
    List<Map<String, Object>> voList=new ArrayList<Map<String, Object>>();
    for(Region r:relist){
      Map<String, Object> remap=new HashMap<String, Object>();
      remap.put("name", r.getName());
      remap.put("id", r.getId());
      voList.add(remap);
    }
    return voList;
  }

  @Override
  public List<RegionType> findALlRegionType() {
   // Sort s=new Sort(Sort.Direction.ASC, "parentsType");
    return regionTypeRepository.findAllRegion() ;
  }

  @Override
  public RegionType findByRegionTypeName(String name) {
    return regionTypeRepository.findByName(name);
  }

  @Override
  public RegionType findRegionType(int id) {
    return regionTypeRepository.findOne(id);
  }

  @Override
  public int findMaxId() {
    return regionTypeRepository.findMaxId();
  }

  @Override
  public void saveRegionType(RegionType regionType) {
     regionTypeRepository.save(regionType);
  }

  @Override
  public void deleteRegionType(RegionType regionType) {
    regionTypeRepository.delete(regionType);
  }


}
