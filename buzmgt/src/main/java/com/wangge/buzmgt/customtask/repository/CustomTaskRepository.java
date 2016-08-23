package com.wangge.buzmgt.customtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.customtask.entity.CustomTask;

public interface CustomTaskRepository extends JpaRepository<CustomTask, Long>, JpaSpecificationExecutor<CustomTask> {

}
