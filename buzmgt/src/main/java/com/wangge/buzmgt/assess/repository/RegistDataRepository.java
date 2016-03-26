package com.wangge.buzmgt.assess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.assess.entity.RegistData;

@Repository
public interface RegistDataRepository extends JpaRepository<RegistData, Long>{
  public RegistData findRegistDataById(Long registId);
}
