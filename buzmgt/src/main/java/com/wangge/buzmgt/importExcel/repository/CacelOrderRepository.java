package com.wangge.buzmgt.importExcel.repository;

import com.wangge.buzmgt.importExcel.entity.CacelOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CacelOrderRepository extends JpaRepository<CacelOrder, String>, JpaSpecificationExecutor<CacelOrder> {

}
