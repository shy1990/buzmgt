package com.wangge.buzmgt.salesman.repository;

import com.wangge.buzmgt.salesman.entity.MonthPunishUp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Created by 神盾局 on 2016/5/21.
 */
public interface MothPunishUpRepository extends PagingAndSortingRepository<MonthPunishUp,Integer> {

    public Page<MonthPunishUp> findAll(Specification specification, Pageable pageable);//分页查询
    @EntityGraph("graph.MonthPunishUp.salesMan")
    Page<MonthPunishUp> findAll(Pageable pageable);
    public List<MonthPunishUp> findAll();

    @Query(value="select sum(FINE_MONEY) from SYS_MONTH_PUNISH_RECORD",nativeQuery = true)
    public Float amerceSum();
}
