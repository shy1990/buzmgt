package com.wangge.buzmgt.salary.repository;


import com.wangge.buzmgt.salary.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by 神盾局 on 2016/6/13.
 */
public interface SalaryRespository extends PagingAndSortingRepository<Salary,Long> {
    public Page<Salary> findAll(Specification specification, Pageable pageable);//分页查询
}
