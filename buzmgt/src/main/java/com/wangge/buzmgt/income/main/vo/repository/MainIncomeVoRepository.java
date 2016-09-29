package com.wangge.buzmgt.income.main.vo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.income.main.vo.MainIncomeVo;


public interface MainIncomeVoRepository extends JpaRepository<MainIncomeVo, Long>, JpaSpecificationExecutor<MainIncomeVo> {
}
