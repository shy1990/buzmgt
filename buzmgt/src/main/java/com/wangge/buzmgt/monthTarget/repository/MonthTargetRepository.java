package com.wangge.buzmgt.monthTarget.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.wangge.buzmgt.monthTarget.entity.MonthTarget;

import java.util.List;

@Repository
public interface MonthTargetRepository extends JpaRepository<MonthTarget, Long>{

    public Page<MonthTarget> findAll(Specification specification, Pageable pageable);//分页查询
}
