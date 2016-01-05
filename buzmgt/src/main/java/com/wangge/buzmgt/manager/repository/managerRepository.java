package com.wangge.buzmgt.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.manager.entity.manager;
import com.wangge.buzmgt.sys.entity.User;

public interface managerRepository extends JpaRepository<manager, Long> {

	List<User> findByRegionId(String regionId);

}
