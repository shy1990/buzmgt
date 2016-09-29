package com.wangge.buzmgt.income.main.vo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.vo.HedgeVo;

public interface HedgeVoRepository extends JpaRepository<HedgeVo, Long>, JpaSpecificationExecutor<HedgeVo> {
  
}
