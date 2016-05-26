package com.wangge.buzmgt.salesman.service;

import java.util.List;

import com.wangge.buzmgt.salesman.entity.PunishSet;

public interface PunishSetService {
	public void save(PunishSet punishSet);
	public List<PunishSet> findAll();
	public PunishSet findById(Long id);
	public void delete(Long id);
	public PunishSet findByRegionId(String regionId);
  public PunishSet findByUserId(String userId);
	

}
