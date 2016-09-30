package com.wangge.buzmgt.achieveset.repository;

import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface AchieveIncomeRepository extends JpaRepository<AchieveIncome, Long> ,
JpaSpecificationExecutor<AchieveIncome> ,CustomRepository {

	/**
	 *
	 * @Title: CountByAchieveIdAndUserId
	 * @Description: 根据达量设置Id和用户id查询数量
	 * @param @param achieveId
	 * @param @param userId
	 * @param @return    设定文件
	 * @return Long    返回类型
	 * @throws
	 */
	@Query("SELECT sum (ai.num) from AchieveIncome ai where ai.achieveId = ?1 and ai.userId = ?2")
	Long countByAchieveIdAndUserId(Long achieveId, String userId);

	/**
	 *
	 * @Title: CountByAchieveId
	 * @Description: 根据
	 * @param @param achieveId
	 * @param @return    设定文件
	 * @return Long    返回类型
	 * @throws
	 */
	@Query("SELECT sum (ai.num) from AchieveIncome ai where ai.achieveId = ?1")
	Long countByAchieveId(Long achieveId);

}
