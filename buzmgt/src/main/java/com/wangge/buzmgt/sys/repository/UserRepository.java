package com.wangge.buzmgt.sys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.sys.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	 User findByUsername(String username);
}
