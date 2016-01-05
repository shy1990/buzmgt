package com.wangge.buzmgt.sys.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.sys.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Long>{

	public Organization findOrganizationById(Long id);

	public List<Organization> findOrganizationByParentId(Long id);
}
