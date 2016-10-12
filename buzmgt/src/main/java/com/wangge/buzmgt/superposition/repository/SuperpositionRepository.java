package com.wangge.buzmgt.superposition.repository;

import com.wangge.buzmgt.superposition.entity.Superposition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by joe on 16-9-7.
 */
public interface SuperpositionRepository extends JpaRepository<Superposition,Long> {

    public Page<Superposition> findAll(Specification specification,Pageable pageable);

    @Query(nativeQuery = true,value = "select s.su_po_id,s.task_one,s.task_two,s.task_three,s.production,s.remark, \n" +
            "s.auditor,s.create_date,s.end_date,s.give_date,s.impl_date,s.plan_id,s.check_status,s.single_one,s.single_two,s.single_three from SYS_SUPERPOSITION s\n" +
            "where s.impl_date <= TO_DATE(?,'yyyy-mm-dd')\n" +
            "and s.end_date >= TO_DATE(?,'yyyy-mm-dd')\n " +
            "and s.plan_id = ? \n" +
            "and s.CHECK_STATUS = '3'")
    public Superposition findUseByTime(String payTime,String payTime1,Long planId);

    public List<Superposition> findByPlanIdAndCheckStatus(Long planId,String status);

}
