package com.wangge.buzmgt.salary.service;

import com.wangge.buzmgt.salary.entity.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * Created by 神盾局 on 2016/6/13.
 */
public interface SalaryService {
    public Page<Salary> findByPage(Pageable pageable, String startTime, String endTime);
    public Page<Salary> findByPage(Pageable pageable, String startTime, String endTime,String name);


    public void save(Map<Integer, String> map);
}
