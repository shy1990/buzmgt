package com.wangge.buzmgt.salesman.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.wangge.buzmgt.salesman.entity.SalesmanData;

public interface SalesmanDataService {
	public List<SalesmanData> findAll();
	public SalesmanData save(SalesmanData salesmanData);
	public Page<SalesmanData> findAll(Specification specification,Pageable pageable);
	public Page<SalesmanData> findAll(String name,Pageable pageable);
	public SalesmanData findById(Long id);
	public SalesmanData findByNameAndCard_cardNumber(String name,String cardNu);
	public void deleteById(Long id);
}
