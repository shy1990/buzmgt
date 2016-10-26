package com.wangge.buzmgt.achieveaward.repository;/**
 * Created by ChenGuop on 2016/10/19.
 */

import com.wangge.buzmgt.achieveaward.entity.AwardIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 达量奖励收益数据操作
 * AwardIncomeRepository
 *
 * @author ChenGuop
 * @date 2016/10/19
 */
public interface AwardIncomeRepository extends JpaRepository<AwardIncome, Long>,
				JpaSpecificationExecutor<AwardIncome>, CustomRepository {
}
