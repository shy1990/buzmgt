package com.wangge.buzmgt.salesman.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.salesman.entity.PunishSet;
public interface PunishSetRepository extends JpaRepository<PunishSet, Long>{
	public PunishSet save(PunishSet punishSet);
	@Query("select a from PunishSet a where a.region.id = ?1")
	public PunishSet fingByRegionId(String regionId);
}
