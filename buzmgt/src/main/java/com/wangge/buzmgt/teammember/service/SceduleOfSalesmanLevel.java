package com.wangge.buzmgt.teammember.service;

import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesmanLevel;
import com.wangge.buzmgt.teammember.repository.SalesmanLevelRepository;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.SQLQuery;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

/**
 * 执行统计每个县级区域的历史订单数量
 *
 * @author yangqc
 */
@Component
@EnableScheduling
public class SceduleOfSalesmanLevel {
  @PersistenceContext
  private EntityManager entityManager;
  @Resource
  private SalesmanLevelRepository salesmanLevelRepository;
  @Resource
  private SalesManService salesManService;

  /**
   * 定时任务设置根据上个月订单量判断业务所属等级(每个月1号晚上3点30触发)
   */
  // 每月1号3点30分 0 30 3 1 * ?
  @SuppressWarnings("unchecked")
  @Scheduled(cron = " 0 30 3 1 * ? ")
  public void handleMontholdData() {
    String sql = "select nvl(sum(m.NUMS),0) nums,m.parentid from\n" +
            "   MOTHTARGETDATA m right join sys_salesman s on m.parentid=s.region_id\n" +
            "   where to_char(CREATETIME,'YYYY-MM') = (select to_char(last_day(add_months(sysdate,-2))+1,'YYYY-MM')  from dual)\n" +
            "   group by m.parentid";

    Query query = null;
    SQLQuery sqlQuery = null;
    query = entityManager.createNativeQuery(sql);
    sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
    List<Object[]> ret = sqlQuery.list();
    if (CollectionUtils.isNotEmpty(ret)) {
      ret.forEach(o -> {
        List<SalesmanLevel> salesmanLevel = salesmanLevelRepository.findAll();
        if (CollectionUtils.isNotEmpty(salesmanLevel)) {
          salesmanLevel.forEach(sl -> {
            int num = ((BigDecimal) o[0]).intValue();
            String regionId = (String) o[1];
            int range = 0;
            SalesMan salesMan = null;
            if ("中学生".equals(sl.getLevelName())) {
              String[] array = sl.getSalesRange().split("-");
              int salesMin = Integer.parseInt(array[0]);
              int salesMax = Integer.parseInt(array[1]);
              if (num >= salesMin && num <= salesMax) {
                salesMan = getSalesMan(regionId);
                salesMan.setLevelName("中学生");
                salesManService.save(salesMan);
              }
            } else if ("小学生".equals(sl.getLevelName())) {
              range = Integer.parseInt(sl.getSalesRange());
              if (num >= 0 && num <= range) {
                salesMan = getSalesMan(regionId);
                salesMan.setLevelName("小学生");
                salesManService.save(salesMan);
              }
            } else {
              range = Integer.parseInt(sl.getSalesRange());
              if (num >= range) {
                salesMan = getSalesMan(regionId);
                salesMan.setLevelName("大学生");
                salesManService.save(salesMan);
              }
            }
          });
        }
      });
    }
  }

  /**
   * 根据regionId查询业务
   *
   * @param regionId
   * @return
   */
  public SalesMan getSalesMan(String regionId) {
    return salesManService.findSaleamanByRegionId(regionId);
  }

}
