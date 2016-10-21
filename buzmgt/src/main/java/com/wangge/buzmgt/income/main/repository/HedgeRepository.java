package com.wangge.buzmgt.income.main.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;

import com.wangge.buzmgt.income.main.entity.Hedge;

public interface HedgeRepository extends JpaRepository<Hedge, Long> {
  
  @Query(value = "select nvl(sum(hedge.sum), 0) AS nums\n" + "  from SYS_INCOME_SHOUHOU_HEDGE hedge\n"
      + " inner join sys_brand_income b\n" + "    on hedge.sku = b.good_id\n"
      + " where to_char(hedge.shdate, 'yyyy-mm-dd') between\n" + "       to_char(b.start_date, 'yyyy-mm-dd') and\n"
      + "       to_char(b.end_date, 'yyyy-mm-dd')\n" + "   and b.good_id = ?1", nativeQuery = true)
  int countByGoodId(String goodId);
  
  /**
   * 计算上月价格区间和品牌的冲减 <br/>
   */
  @Procedure("income_shouhou_good_cal")
  void calSectionAndBrandGood();
  
  /**
   * 计算总的售后冲减
   */
  @Procedure("income_shouhou_hedge_cal")
  void calShouhouHege();
  
  /**
   * 查找一段时间内导入的售后冲销每个业务员每一单品的数量及其 支付时间<br/>
   * 
   */
  @Query(value = "select h.orderno, sk.goods_id,h.sum,\n"
      + "to_date( to_char(oder.pay_time,'yyyy-mm-dd'),'yyyy-mm-dd') pay_time, o.user_id,h.id,h.shdate\n"
      + "     from sys_income_shouhou_hedge h   left JOIN sjzaixian.sj_tb_goods_sku sk ON sk.id = h.sku\n"
      + "      left join biz_order_signfor o on h.orderno = o.order_no\n"
      + "      left join sjzaixian.sj_tb_order oder on h.orderno = oder.order_num\n"
      + "      where  h.inserttime = ?1", nativeQuery = true)
  List<Object> findByDate(Date insertDate);

  @Query(value = "select nvl(sum(hedge.sum), 0) AS nums\n"
          + "  from SYS_INCOME_SHOUHOU_HEDGE hedge\n" +
          " inner join SYS_AWARD_SET_GOODS asg\n" +
          "    on hedge.sku = asg.good_id\n" +
          " inner join SYS_ACHIEVE_AWARD_SET aas\n" +
          "    on asg.AWARD_ID = aas.AWARD_ID\n"
          + " where to_char(hedge.shdate, 'yyyy-mm-dd') between\n" + "       to_char(aas.start_date, 'yyyy-mm-dd') and\n"
          + "       to_char(aas.end_date, 'yyyy-mm-dd')\n" + "   and asg.good_id in (?1)", nativeQuery = true)
  int countByGoodId(List<String> goodIds);
}
