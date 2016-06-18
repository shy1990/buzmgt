
package com.wangge.buzmgt.pushmoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wangge.buzmgt.pushmoney.entity.PushMoney;

public interface PushMoneyRepository extends JpaRepository<PushMoney, Integer>,
JpaSpecificationExecutor<PushMoney> {

}
