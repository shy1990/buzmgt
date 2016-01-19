package com.wangge.buzmgt.teammember.service;

import java.util.List;

import com.wangge.buzmgt.teammember.entity.Manager;

public interface ManagerService {

	void addManager(Manager m);

	List<Manager> findByReginId(String regionId);

  Manager getById(String id);

}
