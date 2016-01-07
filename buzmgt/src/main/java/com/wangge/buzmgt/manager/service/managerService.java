package com.wangge.buzmgt.manager.service;

import java.util.List;

import com.wangge.buzmgt.manager.entity.manager;

public interface managerService {

	void addManager(manager m);

	List<manager> findByReginId(String regionId);

}
