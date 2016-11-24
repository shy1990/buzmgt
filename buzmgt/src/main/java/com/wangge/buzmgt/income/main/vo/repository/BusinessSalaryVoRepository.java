package com.wangge.buzmgt.income.main.vo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.vo.BusinessSalaryVo;
import com.wangge.buzmgt.income.main.vo.HedgeVo;

public interface BusinessSalaryVoRepository
    extends JpaRepository<BusinessSalaryVo, String>, JpaSpecificationExecutor<HedgeVo> {
}
