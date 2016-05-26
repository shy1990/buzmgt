package com.wangge.buzmgt.teammember.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface SalesManRepository extends JpaRepository<SalesMan,String>{

	List<User> findByRegionId(String regionId);
	
	Page<SalesMan> findAll(Specification<SalesMan> spec, Pageable pageable);

  SalesMan findById(String id);
  
  //@Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s where s.SalesmanStatus=1")
  @Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s")
  List<Object> gainSaojieMan();
  
  @Query("select s.region.id from SalesMan s where s.id =1?")
  String getRegionIdByUserId(String userId);
  
}
