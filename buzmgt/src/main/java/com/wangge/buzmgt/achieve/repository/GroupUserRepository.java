package com.wangge.buzmgt.achieve.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wangge.buzmgt.achieve.entity.GroupUser;

/**
 * 
* @ClassName: GroupUserRepository
* @Description: 分组人员的持久化层
* @author ChenGuop
* @date 2016年8月24日 下午1:45:14
*
 */
public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {

}
