package com.wangge.buzmgt.oil.service;

import java.util.List;


import com.wangge.buzmgt.oil.entity.OilParameters;

public interface OilService {
	
	public String deletekmOilSubsidy(String regionId);
	
	public String saveMoney(OilParameters oilParameters);
	
	public String save(OilParameters oilParameters);
	
	public List<OilParameters> findAll();
	
	public String modify(OilParameters oilParameters);
	
	public String deletekmRatio(String regionId);
	
	public String modifyDefault(OilParameters oilParameters);
	
	public OilParameters findByRegionId(String regionId);
	
}
