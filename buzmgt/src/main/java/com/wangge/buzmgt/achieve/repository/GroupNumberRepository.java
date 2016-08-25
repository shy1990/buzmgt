package com.wangge.buzmgt.achieve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.achieve.entity.GroupNumber;

/**
 * 
* @ClassName: GroupNumberRepository
* @Description: 分组设置
* @author ChenGuop
* @date 2016年8月24日 下午1:47:55
*
 */
public interface GroupNumberRepository extends  JpaRepository<GroupNumber, Long> ,JpaSpecificationExecutor<GroupNumber>{

}
