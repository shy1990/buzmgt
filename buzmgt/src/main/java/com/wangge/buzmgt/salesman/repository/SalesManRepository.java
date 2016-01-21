package com.wangge.buzmgt.salesman.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.salesman.entity.SalesMan;
import com.wangge.buzmgt.sys.entity.User;

public interface SalesManRepository extends JpaRepository<SalesMan,String>{

	List<User> findByRegionId(String regionId);

	@Query("select s from SalesMan s where salesmanStatus=0")
	List<SalesMan> gainSaojieMan();
	
	SalesMan findById(String id);
	
	Page<SalesMan> findAll(Specification<SalesMan> spec, Pageable pageable);
	
}
