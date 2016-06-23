package com.wangge.buzmgt.salesman.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.salesman.entity.PunishSet;
import com.wangge.buzmgt.salesman.repository.PunishSetRepository;
import com.wangge.buzmgt.teammember.service.SalesManService;

@Service
public class PunishSetServiceImpl implements PunishSetService{
	@Autowired
	private PunishSetRepository punishSetRepository;
	@Autowired
	private SalesManService salesManService;
	@Override
	public void save(PunishSet punishSet) {
		punishSetRepository.save(punishSet);
		
	}
	@Override
	public List<PunishSet> findAll() {
		List<PunishSet> list = punishSetRepository.findAll();
		return list;
	}
	@Override
	public PunishSet findById(Long id) {
		PunishSet punishSet = punishSetRepository.findOne(id);
		return punishSet;
	}
	@Override
	public void delete(Long id) {
		punishSetRepository.delete(id);
	}
	@Override
	public PunishSet findByRegionId(String regionId) {
		PunishSet punishSet = punishSetRepository.fingByRegionId(regionId);
		return punishSet;
	}
  @Override
  public PunishSet findByUserId(String userId) {
    String regionId=salesManService.getRegionIdByUserId(userId);
    PunishSet ps= findByRegionId(regionId);
    if(ps==null){
      ps=findByRegionId("0");
    }
    return ps;
  }

}
