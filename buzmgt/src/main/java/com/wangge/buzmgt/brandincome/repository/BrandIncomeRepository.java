package com.wangge.buzmgt.brandincome.repository;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandIncomeRepository extends JpaRepository<BrandIncome, Long>,JpaSpecificationExecutor<BrandIncome> {

  @Override
  Page<BrandIncome> findAll(Specification<BrandIncome> spec, Pageable pageable);

  @Override
  List<BrandIncome> findAll(Specification<BrandIncome> spec, Sort sort);
}
