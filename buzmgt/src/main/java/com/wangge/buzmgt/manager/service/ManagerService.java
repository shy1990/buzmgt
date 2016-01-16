package com.wangge.buzmgt.manager.service;

import java.util.List;

import com.wangge.buzmgt.manager.entity.Manager;

public interface ManagerService {

	void addManager(Manager m);

	List<Manager> findByReginId(String regionId);

  Manager getById(String id);

}
