package com.wangge.buzmgt.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.manager.entity.Manager;
import com.wangge.buzmgt.sys.entity.User;

public interface ManagerRepository extends JpaRepository<Manager, String> {

	List<Manager> findByRegionId(String regionId);

  Manager findById(String id);

}
