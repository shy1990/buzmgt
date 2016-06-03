package com.wangge.buzmgt.teammember.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesmanStatus;

public interface SalesManRepository extends JpaRepository<SalesMan,String>{

	List<User> findByRegionId(String regionId);
	
	Page<SalesMan> findAll(Specification<SalesMan> spec, Pageable pageable);

  SalesMan findById(String id);
  
  @Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s where s.status=?1")
  List<Object> getSaojieMan(SalesmanStatus status);
  @Query("select s.id from SalesMan s where s.truename = ?1")
  String getIdByTurename(String truename);
  @Query("select s.region.id from SalesMan s where s.id =?1")
  String getRegionIdByUserId(String userId);
  
}
