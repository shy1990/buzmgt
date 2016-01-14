package com.wangge.buzmgt.salesman.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.sys.entity.User;

public interface salesManRepository extends JpaRepository<salesMan, Long>{

	List<User> findByRegionId(String regionId);

	@Query("select s from salesMan s where salesmanStatus=0")
	List<salesMan> gainSaojieMan();
	
	salesMan findById(String id);
}
