package com.wangge.buzmgt.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.sys.entity.Role;
import com.wangge.buzmgt.sys.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	 User findByUsername(String username);
	 
//	 Page<User> findByRoles(Role role,Pageable page);
	 List<User> findByRoles(Role role);
}
