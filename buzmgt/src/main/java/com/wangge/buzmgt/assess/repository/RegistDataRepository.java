package com.wangge.buzmgt.assess.repository;

import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistDataRepository extends JpaRepository<RegistData, Long>{
  RegistData findRegistDataById(Long registId);


  List<RegistData> findByRegion(Region region);

  @Query("select count(1) from RegistData r where r.region.id like %?1%")
  int findCountByRegionIdlike(String regionId);

  @Query("select r.loginAccount from RegistData r where r.loginAccount = ?1")
  String findLoginAccountByLoginAccount(String loginAccount);
}
