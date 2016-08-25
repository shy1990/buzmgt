package com.wangge.buzmgt.oil.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.oil.entity.OilParameters;
import com.wangge.buzmgt.oil.repository.OilRepository;
import com.wangge.buzmgt.region.repository.RegionRepository;

@Service
public class OilServiceImpl implements OilService {
	@Autowired
	private OilRepository oilRepository;
	@Autowired
	private RegionRepository regionRepository;
	

	/*
	 * 自定义公里系数设置信息
	 */
	@Override
	public String save(OilParameters oilParameters) {
		OilParameters oilParameter1 = oilRepository.findOilParametersByRegionId(oilParameters.getRegion().getId());
		//OilParameters存在，并且KmRatio是空
		if(oilParameter1 != null && oilParameter1.getKmRatio() == null){
			System.out.println("ration is null");
			oilParameter1.setKmRatio(oilParameters.getKmRatio());
			oilRepository.save(oilParameter1);
			return "添加成功";
		}
		//OilParameters存在，并且KmRatio存在
		if(oilParameter1 != null && oilParameter1.getKmRatio() != null){
			return "不能重复添加";
		}
		//数据库中没有记录
		oilRepository.save(oilParameters);
		return "添加成功";
	}

	/*
	 *查询全部的 OilParameters信息
	 */
	@Override
	public List<OilParameters> findAll() {
		List<OilParameters> lists = oilRepository.findAll();
		return lists;
	}
	/*
	 *修改 OilParameters
	 */
	@Override
	public String modify(OilParameters oilParameters) {
		oilRepository.save(oilParameters);
		return "修改成功";
	}
	/*
	 *删除自定义公里系数设置信息 ,根据regionId
	 */
	@Override
	public String deletekmRatio(String regionId) {
		OilParameters oilParameters = oilRepository.findOilParametersByRegionId(regionId);
		if(oilParameters.getKmOilSubsidy() != null){
			oilParameters.setKmRatio(null);
			oilRepository.save(oilParameters);
			return "删除成功";
		}
		oilRepository.deleteByRegionId(regionId);
		return "删除成功";
	}
	/**
	 * 添加油补
	 */
	@Override
	public String saveMoney(OilParameters oilParameters) {
		OilParameters oilParameter1 = oilRepository.findOilParametersByRegionId(oilParameters.getRegion().getId());
		//OilParameters存在，KmOilSubsidy没有数据
		if(oilParameter1 !=null && oilParameter1.getKmOilSubsidy()==null){
			oilParameter1.setKmOilSubsidy(oilParameters.getKmOilSubsidy());//设置setKmOilSubsidy
			oilRepository.save(oilParameter1);
			return "添加成功";
		}
		//OilParameters存在，KmOilSubsidy有数据
		if(oilParameter1 != null && oilParameter1.getKmOilSubsidy() != null){
			return "不能重复添加";
		}
		//OilParameters不存在，KmOilSubsidy没有数据
		oilRepository.save(oilParameters);
		return "添加成功";
	}
	/*
	 *删除自定义油补金额系数设置信息 ,根据regionId
	 */
	@Override
	public String deletekmOilSubsidy(String regionId) {
		OilParameters oilParameters = oilRepository.findOilParametersByRegionId(regionId);
		if(oilParameters.getKmRatio() != null){//如果“公里系数”存在的话，就将KmOilSubsidy设置为null
			oilParameters.setKmOilSubsidy(null);
			oilRepository.save(oilParameters);
			return "删除成功";
		}
		oilRepository.deleteByRegionId(regionId);//否则：删除
		return "删除成功";
	}
	
	/**
	 * 默认的设置
	 */
	@Override
	public String modifyDefault(OilParameters oilParameters) {
		
		OilParameters oilParameters1 = oilRepository.findDefaultOilParameters();
		if(oilParameters1 != null ){//数据库中存在就是修改
			oilParameters1.setKmRatio(oilParameters.getKmRatio());
			oilParameters1.setKmOilSubsidy(oilParameters.getKmOilSubsidy());
    		oilRepository.save(oilParameters1);
    		return "修改成功";
        }
		oilRepository.save(oilParameters);
		
		return "添加成功";
		

	}

	@Override
	public OilParameters findByRegionId(String regionId) {
		OilParameters oilParameters =oilRepository.findOilParametersByRegionId(regionId);
		return oilParameters;
	}
	
	
	
	
}
