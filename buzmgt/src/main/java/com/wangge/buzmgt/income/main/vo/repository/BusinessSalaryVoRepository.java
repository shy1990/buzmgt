package com.wangge.buzmgt.income.main.vo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.income.main.vo.BusinessSalaryVo;

public interface BusinessSalaryVoRepository
    extends JpaRepository<BusinessSalaryVo, String>, JpaSpecificationExecutor<BusinessSalaryVo> {
  @Query(value = "select    g.NAME,inc.num,inc.incomes from \n"
      + "(select t.goods_id, t.num,t.percentage * t.num incomes  from SYS_SECTION_RECORD t\n"
      + " where t.orderflag = 1   and t.order_no = ?1 union all\n"
      + "select t.good_id good_id,t.sum num, INCOME incomes  from SYS_INCOME_TICHENG_BRAND t\n"
      + " where t.orderflag = 1   and t.ORDERNO = ?1) inc   left join sys_goods g on\n"
      + "   inc.goods_id = g.ID", nativeQuery = true)
  public List<Object> findByOrder(String orderno);
}
