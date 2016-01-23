package com.wangge.buzmgt.teammember.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.teammember.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, String> {

	List<Manager> findByRegionId(String regionId);

  Manager findById(String id);

}
