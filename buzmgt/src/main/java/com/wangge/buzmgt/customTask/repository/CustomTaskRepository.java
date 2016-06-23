package com.wangge.buzmgt.customTask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.customTask.entity.CustomTask;

public interface CustomTaskRepository extends JpaRepository<CustomTask, Long>, JpaSpecificationExecutor<CustomTask> {

}
