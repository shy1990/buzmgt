
package com.wangge.buzmgt.pushmoney.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.pushmoney.entity.PushMoney;

public interface PushMoneyRepository extends JpaRepository<PushMoney, Integer>,
JpaSpecificationExecutor<PushMoney> {

  @Override
  @EntityGraph("graph.PushMoney.category")
  Page<PushMoney> findAll(Specification<PushMoney> spec, Pageable pageable);
}
