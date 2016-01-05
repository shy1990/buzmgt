package com.wangge.buzmgt.sys.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.sys.entity.Resource;
import com.wangge.buzmgt.sys.entity.Resource.ResourceType;
import com.wangge.buzmgt.sys.util.SortUtil;

public interface ResourceRepository extends JpaRepository<Resource, Long>{
	
	List<Resource> findByRolesUsersUsernameAndType(String username,ResourceType type,Sort s);
	
	List<Resource> findByRolesUsersUsername(String username);
	
	List<Resource> findByRolesId(Long id);
	
	Page<Resource> findAll(Pageable pageRequest);
	
	List<Resource> findAll(Sort sort);
	
	List<Resource> findByParentId(Long id);
	
	Resource findByName(String name);
}