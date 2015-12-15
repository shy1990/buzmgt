package com.wangge.buzmgt.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.sys.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	List<Role> findByUsersUsername(String username);
}
