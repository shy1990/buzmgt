package com.wangge.buzmgt.monthTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.monthTask.entity.MonthTask;

public interface MonthTaskRepository extends JpaRepository<MonthTask, Long> {

}
