package com.wangge.buzmgt.region.service;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.region.vo.RegionTree;
import com.wangge.buzmgt.sys.entity.Organization;
import com.wangge.buzmgt.sys.vo.OrganizationVo;
import com.wangge.buzmgt.sys.vo.RegionVo;

@Service
public class RegionServiceImpl implements RegionService {
	@Autowired
	private RegionRepository regionRepository;
	private static final String ONELEV="大区"; //除中国之外的级别
	private static final String TWOLEV="省"; 
	private static final String THREELEV="区"; 
	private static final String FOURLEV="市";
	private static final String FIRELEV="县";
	private static final String SIXLEV="镇";
	@Override
	public List<RegionTree> findTreeRegion(String id) {
		// TODO Auto-generated method stub
		List<RegionTree> listRegionTree = new ArrayList<RegionTree>();
		for (Region region : regionRepository.findOne(id).getChildren()) {
			RegionTree regionTree=new RegionTree();
			regionTree.setId(region.getId());
			regionTree.setName(region.getName());
			regionTree.setOpen(true);
			if(region.getChildren().size()>0){
				regionTree.setIsParent("true");
			}else{
				regionTree.setIsParent("false");
				
			}
			String imgUrl=null;
			if(region.getType().getName().equals(ONELEV)) {
				imgUrl="/static/img/region/quyu.png";
			}else if(region.getType().getName().equals(TWOLEV)) {
				imgUrl="/static/img/region/sheng.png";
			}else if(region.getType().getName().equals(THREELEV)){
				imgUrl="/static/img/region/qu.png";
			}else if(region.getType().getName().equals(FOURLEV)){
				imgUrl="/static/img/region/shi.png";
			}else if(region.getType().getName().equals(FIRELEV)){
				imgUrl="/static/img/region/shi.png";
			}else if(region.getType().getName().equals(SIXLEV)){
				imgUrl="/static/img/region/zhen.png";
			}
			regionTree.setIconOpen(imgUrl);
			regionTree.setIconClose(imgUrl);
			regionTree.setIcon(imgUrl);
			regionTree.setpId(region.getParent().getId());
			listRegionTree.add(regionTree);
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


}
