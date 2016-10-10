package com.wangge.buzmgt.income.main.repository;

import com.wangge.buzmgt.income.main.entity.Hedge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

public interface HedgeRepository extends JpaRepository<Hedge, Long>{

  @Query(value = "select nvl(sum(hedge.sum), 0) AS nums\n" +
                  "  from SYS_INCOME_SHOUHOU_HEDGE hedge\n" +
                  " inner join sys_brand_income b\n" +
                  "    on hedge.sku = b.good_id\n" +
                  " where to_char(hedge.shdate, 'yyyy-mm-dd') between\n" +
                  "       to_char(b.start_date, 'yyyy-mm-dd') and\n" +
                  "       to_char(b.end_date, 'yyyy-mm-dd')\n" +
                  "   and b.good_id = ?1", nativeQuery = true)
  int countByGoodId(String goodId);
  
  /** 
    * 计算上月价格区间和品牌的冲减 <br/> 
    */  
  @Procedure("income_shouhou_good_cal")
  void calSectionAndBrandGood();
  /**
   * 计算总的售后冲减
   * */
  @Procedure("income_shouhou_hedge_cal")
  void calShouhouHege();
}
