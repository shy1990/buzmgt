package com.wangge.buzmgt.sys.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangge.buzmgt.sys.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  List<Role> findByUsersUsername(String username);

  Page<Role> findAll(Pageable pageRequest);

  Role findByName(String name);
}
