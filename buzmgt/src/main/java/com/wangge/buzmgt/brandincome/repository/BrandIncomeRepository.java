package com.wangge.buzmgt.brandincome.repository;

import com.wangge.buzmgt.brandincome.entity.BrandIncome;
import com.wangge.buzmgt.brandincome.entity.BrandIncome.BrandIncomeStatus;
import com.wangge.buzmgt.common.FlagEnum;
import com.wangge.buzmgt.goods.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandIncomeRepository extends JpaRepository<BrandIncome, Long>,JpaSpecificationExecutor<BrandIncome> {

  @Override
  Page<BrandIncome> findAll(Specification<BrandIncome> spec, Pageable pageable);

  @Override
  List<BrandIncome> findAll(Specification<BrandIncome> spec, Sort sort);

  @Query(value = "select nvl(sum(g.nums), 0) AS nums\n" +
                  "  from sys_goods_order g\n" +
                  " inner join sys_brand_income b\n" +
                  "    on g.goods_id = b.good_id\n" +
                  " where to_char(g.PAY_TIME, 'yyyy-mm-dd') between\n" +
                  "       to_char(b.start_date, 'yyyy-mm-dd') and\n" +
                  "       to_char(b.end_date, 'yyyy-mm-dd')\n" +
                  "   and g.goods_id = ?1 and b.id = ?2", nativeQuery = true)
  int findCycleSales(String goodId,long id);

  BrandIncome findByGoodIdAndPlanIdAndStatus(String goodId,Long planId,BrandIncomeStatus status);

  @Query("select b from BrandIncome b where b.id = (select max(t.id) from BrandIncome t where t.goodId = ?1 and t.planId = ?2 and t.flag = ?3)")
  BrandIncome findByGoodIdAndPlanIdAndStatus(String goodId, long planId, FlagEnum flagEnum);
}
