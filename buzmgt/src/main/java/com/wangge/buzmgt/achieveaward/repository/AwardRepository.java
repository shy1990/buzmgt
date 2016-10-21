package com.wangge.buzmgt.achieveaward.repository;

import com.wangge.buzmgt.achieveaward.entity.Award;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 
* @ClassName: AwardRepository
* @Description: 达量奖励设置的持久化层
* @author ChenGuop
* @date 2016年9月14日 上午11:17:33
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

  @Query(value = "select nvl(sum(g.nums), 0) AS nums\n" +
          "  from sys_goods_order g\n" +
          " inner join SYS_AWARD_SET_GOODS asg\n" +
          "    on g.goods_id = asg.good_id\n" +
          " inner join SYS_ACHIEVE_AWARD_SET aas\n" +
          "    on asg.AWARD_ID = aas.AWARD_ID\n" +
          " where to_char(g.PAY_TIME, 'yyyy-mm-dd') between\n" +
          "       to_char(aas.start_date, 'yyyy-mm-dd') and\n" +
          "       to_char(aas.end_date, 'yyyy-mm-dd')\n" +
          "   and g.goods_id in (?1)", nativeQuery = true)
  int findCycleSales(List<String> goodIds);
}
