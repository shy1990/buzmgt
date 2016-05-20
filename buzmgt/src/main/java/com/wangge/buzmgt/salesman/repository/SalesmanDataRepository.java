package com.wangge.buzmgt.salesman.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.wangge.buzmgt.salesman.entity.SalesmanData;
/**
 * dao接口
 * @author 
 *
 */
@Transactional
public interface SalesmanDataRepository extends PagingAndSortingRepository<SalesmanData,Long>{
	public List<SalesmanData> findAll();//查询全部
	public Page<SalesmanData> findAll(Pageable pageable);//分页查询
	public Page<SalesmanData> findAll(Specification specification,Pageable pageable);//分页查询
	public SalesmanData save(SalesmanData salesmanData);//添加
	
	public int deleteById(Long id);//根据删除
	
	public SalesmanData findById(Long id);
}
