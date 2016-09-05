package com.wangge.buzmgt.brandincome.repository;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandIncomeRepository extends JpaRepository<BrandIncome, Long>{
  

}
