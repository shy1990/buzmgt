package com.wangge.buzmgt.rejection.repository;

import com.wangge.buzmgt.rejection.entity.Rejection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RejectionRepository extends JpaRepository<Rejection, Long>, JpaSpecificationExecutor<Rejection> {

  Rejection findByOrderno(String orderno);
}
