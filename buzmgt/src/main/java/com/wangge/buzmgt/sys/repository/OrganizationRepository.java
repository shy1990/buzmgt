package com.wangge.buzmgt.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.sys.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer>{

	public Organization findOrganizationByIdOrderById(int id);

	public List<Organization> findOrganizationByParentId(int id);
	
	@Modifying  
	@Query("delete from Organization  o where o.id=?") 
	public void deleteOrganization(int id);
}
