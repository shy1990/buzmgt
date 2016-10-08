package com.wangge.buzmgt.achieveset.vo.repository;

import com.wangge.buzmgt.achieveset.vo.AchieveIncomeVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * AchieveIncomeVo数据处理
 * Created by ChenGuop on 2016/10/8.
 */
public interface AchieveIncomeVoRepository extends JpaRepository<AchieveIncomeVo, Integer> ,
				JpaSpecificationExecutor<AchieveIncomeVo>{


}
