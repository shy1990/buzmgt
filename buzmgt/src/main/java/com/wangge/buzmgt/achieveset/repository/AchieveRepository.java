package com.wangge.buzmgt.achieveset.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.achieveset.entity.Achieve;
/**
 * 
* @ClassName: AchieveRepository
* @Description: 达量设置的持久化层
* @author ChenGuop
* @date 2016年8月24日 下午1:48:29
*
 */
public interface AchieveRepository extends JpaRepository<Achieve, Long>,
JpaSpecificationExecutor<Achieve>{
  @Override
//  @EntityGraph("graph.Achieve.groupNumbers")
  List<Achieve> findAll(Specification<Achieve> spec, Sort sort);
	@Override
	@EntityGraph("graph.Achieve.groupNumbers")
	Page<Achieve> findAll(Specification<Achieve> spec, Pageable pageable);

	@Override
	@EntityGraph("graph.Achieve.groupNumbers")
	Achieve findOne(Long achieveId);

	@EntityGraph("graph.Achieve.groupNumbers")
	Achieve findByAchieveIdAndPlanId(Long achieveId, String planId);
}