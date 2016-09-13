package com.wangge.buzmgt.achieveaward.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.achieveaward.entity.Award;
/**
 * 
* @ClassName: AwardRepository
* @Description: 达量设置的持久化层
* @author ChenGuop
* @date 2016年8月24日 下午1:48:29
*
 */
public interface AwardRepository extends JpaRepository<Award, Long>,
JpaSpecificationExecutor<Award>{
  @Override
//  @EntityGraph("graph.Award")
  List<Award> findAll(Specification<Award> spec, Sort sort);
  @Override
//  @EntityGraph("graph.Award")
  Page<Award> findAll(Specification<Award> spec, Pageable pageable);
  
}
