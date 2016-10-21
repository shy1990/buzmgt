package com.wangge.buzmgt.achieveset.vo.service;

import com.wangge.buzmgt.achieveset.entity.AchieveIncome;
import com.wangge.buzmgt.achieveset.vo.AchieveIncomeVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

/**
 * 达量收益统计
 * Created by ChenGuop on 2016/10/8.
 */
public interface AchieveIncomeVoService {
	/**
	 * @Title: findAll
	 * @Description: 条件查询列表
	 * @param @param spec
	 * @param @param sort
	 * @param @return    设定文件
	 * @return List<AchieveIncome>    返回类型
	 * @throws
	 */
	List<AchieveIncomeVo> findAll(Map<String, Object> spec);

	/**
	 * @Title: findAll
	 * @Description: 条件查询列表
	 * @param @param spec
	 * @param @param sort
	 * @param @return    设定文件
	 * @return List<AchieveIncomeVo>    返回类型
	 */
	Page<AchieveIncomeVo> findAll(Map<String, Object> spec, Pageable pageable);

	/**
	 * @Title: findByAchieveIdAndStatus
	 * @Description
	 * @param achieveId
	 * @param pay
	 * @return
	 */
	List<AchieveIncomeVo> findByAchieveIdAndStatus(Long achieveId, AchieveIncome.PayStatusEnum pay);
}
