package com.wangge.buzmgt.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.sys.entity.ChildAccount;

public interface ChildAccountRepository extends JpaRepository<ChildAccount, Long>{
	
	List<ChildAccount> findByParentIdOrderByIdDesc(String parentId);
  
}
