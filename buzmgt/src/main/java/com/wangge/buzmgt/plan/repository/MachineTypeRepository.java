package com.wangge.buzmgt.plan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.plan.entity.MachineType;

/**
 * 
* @ClassName: MachineTypeRepository
* @Description: 机型持久化层
* @author ChenGuop
* @date 2016年8月17日 下午3:59:15
*
 */
public interface MachineTypeRepository extends JpaRepository<MachineType, Long>{

  MachineType findByCode(String code);
}
