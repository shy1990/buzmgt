package com.wangge.buzmgt.brandincome.repository;

import com.wangge.buzmgt.brandincome.entity.BrandIncomeSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandIncomeSubRepository extends JpaRepository<BrandIncomeSub, Long>,JpaSpecificationExecutor<BrandIncomeSub> {

}
