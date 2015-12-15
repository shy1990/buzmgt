package com.wangge.buzmgt.sys.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Resource.ResourceType;

public interface ResourceRepository extends JpaRepository<Resource, Long>{
	List<Resource> findByRolesUsersUsernameAndType(String username,ResourceType type);
	List<Resource> findByRolesUsersUsername(String username);
	
}
