package com.wangge.buzmgt.monthTask.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.assess.repository.RegistDataRepository;
import com.wangge.buzmgt.monthTask.entity.MonthOdersData;
import com.wangge.buzmgt.monthTask.entity.MonthshopBasData;
import com.wangge.buzmgt.monthTask.repository.MonthOrdersDataRepository;
import com.wangge.buzmgt.monthTask.repository.MonthshopBasDataRepository;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.util.DateUtil;

/**
 * 执行统计每个县级区域的历史订单数量
 * 
 * @author yangqc
 *
 */
@Component
@EnableScheduling
public class SceduleOfMonthOrders {
  @PersistenceContext
  private EntityManager em;
  @Autowired
  private MonthOrdersDataRepository monthRep;
  @Autowired
  private SalesManRepository salesRep;
  @Autowired
  private RegistDataRepository regRep;
  
  @Autowired
  private MonthshopBasDataRepository shopRep;
  
  // 订单支付状态为1,计算上个月的订单情况
  public static final String lsdatasql =
      
      "select r.registdata_id, r.region_id, o3.days, r.shop_name, '1' month\n" + "  from sys_registdata r\n"
          + "  left join SJZAIXIAN.SJ_TB_members m on r.member_id = m.id\n"
          + "  left join (select count(1) days, o2.member_id\n" + "               from (select o1.member_id, o1.day\n"
          + "                       from (select t.member_id,\n" + "                                    t.CREATETIME,\n"
          + "                                    to_char(t.CREATETIME, 'yyyy-mm-dd') day,\n"
          + "                                    t.ship_Name\n"
          + "                               from SJZAIXIAN.SJ_TB_ORDER t\n"
          + "                              where to_char(t.createTime, 'yyyy-mm') =\n"
          + "                                    to_char(sysdate - interval '1' month,\n"
          + "                                            'yyyy-mm')\n"
          + "                                and t.pay_status = '1') o1\n"
          + "                      group by o1.member_id, o1.day) o2\n" + "              group by o2.member_id\n"
          + "              order by days desc) o3 on o3.member_id = m.id\n" + " where exists (select 1\n"
          + "          from (select *\n" + "                  from sys_region r\n"
          + "                 start with r.region_id = $town \n"
          + "                connect by prior r.region_id = r.parent_id) tmp\n"
          + "         where tmp.region_id = r.region_id)";
  // 上月拜访访情况
  public static final String lsVisitSql = "select count(1), tmp.month, tmp.registdata_id, tmp.region_id\n"
      + "  from (select substr(g.finish_time, 0, 7) month,\n" + "               g.registdata_id,\n"
      + "               g.region_id\n" + "          from (select to_char(v.time, 'yyyy-mm-dd') finish_time,\n"
      + "                       r.registdata_id,\n" + "                       r.region_id\n"
      + "                  from sys_monthtask_execution v\n"
      + "                  left join sys_registdata r on v.memberid = r.registdata_id\n"
      + "                 where (to_char(v.time, 'yyyy-mm') =\n"
      + "                       to_char(sysdate - interval '1' month, 'yyyy-mm'))\n" + "                   and exists\n"
      + "                 (select 1\n" + "                          from (select \n"
      + "                                  from sys_region r\n"
      + "                                 start with r.region_id = $town \n"
      + "                                connect by prior r.region_id = r.parent_id) tmp\n"
      + "                         where tmp.region_id = r.region_id)) g\n"
      + "         group by g.finish_time, g.registdata_id, g.region_id) tmp\n"
      + " group by tmp.month, tmp.registdata_id, tmp.region_id";
  // 查询所有业务和区域
  private static final String townSql = "select s.region_id, s.user_id, s.truename  from sys_salesman s \n"
      + "  left join sys_user u on s.user_id = u.user_id  where u.status in (0, 1)";
  
  // 每月15号1点时分 0 30 1 15 * ?
  @SuppressWarnings("unchecked")
  @Scheduled(cron = " 0 30 1 15 * ? ")
  public void handleMontholdData() {
    List<Object[]> townList = em.createNativeQuery(townSql).getResultList();
    for (Object[] towns : townList) {
      handleOneTownOrders(towns[0] + "", towns[1] + "", towns[2] + "");
    }
    // handleOneTownOrders("371502","A3715021250",null);
  }
  
  /**
   * 计算出每个区域的历史订单值;三月内平均,上月的;
   * 
   * @param town
   * @param salemanid
   *          目前通过业务员作为查询条件
   * @param salemanName
   * @throws NumberFormatException
   */
  private void handleOneTownOrders(String town, String salemanid, String salemanName) throws NumberFormatException {
    String sql = lsdatasql.replace("$town", town);
    // 得出三个月的数据sql
    sql += "union " + sql.replace("'1' month", "'2' month") + " union " + sql.replace("'1' month", "'3' month");
    Query q = em.createNativeQuery(sql);
    @SuppressWarnings("unchecked")
    List<Object[]> datalist = q.getResultList();
    Map<String, Object> lastMonth = new HashMap<String, Object>();
    lastMonth.put("town", town);
    // 上月的数据
    Integer[] sum1 = new Integer[] { 0, 0, 0, 0, 0 };
    Integer[] sum2 = new Integer[] { 0, 0, 0, 0, 0 };
    Integer[] sum3 = new Integer[] { 0, 0, 0, 0, 0 };
    // 平均三月内的数据
    Integer[] sum4 = new Integer[] { 0, 0, 0, 0, 0 };
    // 上月拜访次数
    Integer[] sum5 = new Integer[] { 0, 0, 0, 0, 0 };
    // 系统建议次数
    Integer[] sum6 = new Integer[] { 14, 14, 14, 14, 14 };
    Map<String, Map<String, Object>> shipAllMap = new HashMap<String, Map<String, Object>>();
    for (Object[] arr1 : datalist) {
      int tal = null == arr1[2] ? 0 : Integer.parseInt(arr1[2] + "");
      Map<String, Object> shopMap = new HashMap<String, Object>();
      int month = Integer.parseInt(arr1[4] + "");
      String shopId = arr1[0] + "";
      shopMap.put(month + "", tal + "");
      String shopReg = arr1[1] + "";
      shopMap.put("shopReg", shopReg);
      shopMap.put("shopId", shopId);
      shopMap.put("shopName", arr1[3] + "");
      // 存储单个店铺的信息
      putShopMap(shipAllMap, shopMap, shopId);
      if (month == 1) {
        handleSum(tal, sum1);
        
      } else if (month == 2) {
        handleSum(tal, sum2);
      } else {
        handleSum(tal, sum3);
      }
    }
    // 计算三月的平均值
    for (int i = 0; i < 5; i++) {
      int sum = sum1[i] + sum2[i] + sum3[i];
      sum4[i] = sum % 3 == 0 ? sum / 3 : (sum / 3 + 1);
    }
    String vsSql = lsVisitSql.replace("$town", town);
    @SuppressWarnings("unchecked")
    List<Object[]> visList = em.createNativeQuery(vsSql).getResultList();
    for (Object[] vist : visList) {
      int tal = Integer.parseInt(vist[0] + "");
      Map<String, Object> shopMap = new HashMap<String, Object>();
      String shopId = vist[2] + "";
      String shopReg = vist[3] + "";
      shopMap.put("shopReg", shopReg);
      shopMap.put("visitcount", tal);
      shopMap.put("shopId", shopId);
      putShopMap(shipAllMap, shopMap, shopId);
      handleSum(tal, sum5);
    }
    for (int i = 0; i < 5; i++) {
      int sum = sum1[i] + sum4[i] + sum5[i];
      sum6[i] = sum % 3 == 0 ? sum / 3 : (sum / 3 + 1);
    }
    String month = DateUtil.getPreMonth(new Date(), 1);
    // 开始计算保存每个店铺的数据
    Collection<Map<String, Object>> shopIts = shipAllMap.values();
    Set<MonthshopBasData> datlist = new HashSet<MonthshopBasData>();
    
    SalesMan salesman = new SalesMan();
    // salemanid
    if (!salemanid.isEmpty()) {
      salesman = salesRep.findById(salemanid);
    }
    for (Map<String, Object> shopMap : shopIts) {
      int sum = getIntValfromMap(shopMap, "1") + getIntValfromMap(shopMap, "2") + getIntValfromMap(shopMap, "3");
      int avg = sum % 3 == 0 ? sum / 3 : (sum / 3 + 1);
      int viscount = getIntValfromMap(shopMap, "visitcount");
      RegistData regsData = new RegistData();
      if (null != shopMap.get("shopId")) {
        regsData = regRep.findOne(getLongValfromMap(shopMap, "shopId"));
      }
      MonthshopBasData shopdata = new MonthshopBasData(shopMap.get("shopReg").toString(),
          getIntValfromMap(shopMap, "1"), avg, month, viscount, regsData, salesman);
      datlist.add(shopdata);
    }
    
    try {
      shopRep.save(datlist);
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(town + salesman.getTruename());
    }
    
    // 结束计算保存每个店铺的数据
    // 每个地区的数据的保存
    MonthOdersData monthda = new MonthOdersData(town, month, sum1[0], sum4[0], sum1[1], sum4[1], sum1[2], sum4[2],
        sum1[3], sum4[3], sum1[4], sum4[4], sum5[0], sum5[1], sum5[2], sum5[3], sum5[4], sum6[0], sum6[1], sum6[2],
        sum6[3], sum6[4], salesman);
    try {
      monthRep.save(monthda);
    } catch (Exception e) {
      System.out.println(monthda);
      e.printStackTrace();
    }
  }
  
  /**
   * @param shopMap
   * @return
   * @throws NumberFormatException
   */
  private int getIntValfromMap(Map<String, Object> shopMap, String key) throws NumberFormatException {
    int viscount = 0;
    try {
      viscount = null == shopMap.get(key) ? 0 : Integer.parseInt(shopMap.get(key) + "");
      
    } catch (Exception e) {
      System.out.println(shopMap.get(key));
    }
    return viscount;
  }
  
  /**
   * @param shopMap
   * @return
   * @throws NumberFormatException
   */
  private long getLongValfromMap(Map<String, Object> shopMap, String key) throws NumberFormatException {
    long viscount = 0;
    try {
      viscount = null == shopMap.get(key) ? 0 : Long.parseLong(shopMap.get(key) + "");
      
    } catch (Exception e) {
      System.out.println(shopMap.get(key));
    }
    return viscount;
  }
  
  /**
   * @param shipAllMap
   * @param shopMap
   * @param shopId
   */
  private void putShopMap(Map<String, Map<String, Object>> shipAllMap, Map<String, Object> shopMap, String shopId) {
    if (null == shipAllMap.get(shopId)) {
      shipAllMap.put(shopId, shopMap);
    } else {
      shipAllMap.get(shopId).putAll(shopMap);
    }
  }
  
  /**
   * 给每月的访问情况计数
   * 
   * @param tal
   * @param arr
   */
  private void handleSum(int tal, Integer[] arr) {
    if (tal >= 20) {
      arr[4]++;
    } else if (tal >= 15 && tal < 20) {
      arr[0]++;
    } else if (10 <= tal && tal < 15) {
      arr[1]++;
    } else if (7 <= tal && tal < 10) {
      arr[2]++;
    } else if (4 <= tal && tal < 7) {
      arr[3]++;
    }
  }
}
