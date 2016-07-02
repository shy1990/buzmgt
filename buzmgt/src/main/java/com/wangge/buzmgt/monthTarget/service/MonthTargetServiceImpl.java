package com.wangge.buzmgt.monthTarget.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.monthTarget.repository.MonthTargetRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import com.wangge.buzmgt.util.DateUtil;
import com.wangge.buzmgt.util.JsonResponse;

@Service
public class MonthTargetServiceImpl implements MonthTargetService {
  @Resource
  private MonthTargetRepository mtr;
  @Resource
  private ManagerService managerService;
  @Resource
  private RegionService regionService;
  @Resource
  private SalesManService smService;
  @PersistenceContext
  private EntityManager em;
  
  @Override
  public Region getRegion() {
    // 获取user
    Manager manager = getManager();
    String regionId = manager.getRegion().getId();
    return regionService.getRegionById(regionId);
  }


  @Override
  public Map<String,Object> getOrderNum(String userId) {

      String sql ="select 'one',count(oi.id) ms from sys_registdata r left join sjzaixian.sj_tb_order o on r.member_id=o.member_id\n" +
      "left join sjzaixian.sj_tb_order_items oi on o.id=oi.order_id where o.pay_status='1' and oi.target_type='sku'\n" + 
      "and o.createtime between (select last_day(add_months(sysdate,-2))+1  from dual)\n" + 
      "and (select last_day(add_months(sysdate,-1))  from dual) and r.user_id=?\n" + 
      "union all\n" + 
      "select 'three',count(oi.id) ms from sys_registdata r left join sjzaixian.sj_tb_order o on r.member_id=o.member_id\n" + 
      "left join sjzaixian.sj_tb_order_items oi on o.id=oi.order_id where o.pay_status='1' and oi.target_type='sku'\n" + 
      "and o.createtime between (select last_day(add_months(sysdate,-4))+1  from dual)\n" + 
      "and (select last_day(add_months(sysdate,-1))  from dual) and r.user_id=?";

      Query query = null;
      SQLQuery sqlQuery = null;
      query = em.createNativeQuery(sql);
      sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
      sqlQuery.setParameter(0, userId);//业务参数
      sqlQuery.setParameter(1, userId);
      List<Object[]> ret = sqlQuery.list();
      Map<String,Object> map = new HashMap<String,Object>();
      if (CollectionUtils.isNotEmpty(ret)) {
          ret.forEach(o -> {
              map.put((String)o[0], (BigDecimal)o[1]);
          });
      }
    return map;
  }


  @Override
  public Map<String, Object> getSeller(String userId) {
    String sql = "select 'one',count(*)\n" +
                  "  from (select MT.MEMBERID, count(MT.ORDERID) total\n" + 
                  "          from MOTHTARGETDATA MT\n" + 
                  "         where MT.createtime between\n" + 
                  "               (select last_day(add_months(sysdate, -2)) + 1 from dual) and\n" + 
                  "               (select last_day(add_months(sysdate, -1)) from dual)\n" + 
                  "           and MT.userid = ?\n" + 
                  "         Group by MT.MEMBERID)\n" + 
                  " where total >= ?\n" + 
                  "union all\n" + 
                  "select 'three',count(*)\n" + 
                  "  from (select MT.MEMBERID, count(MT.ORDERID) total\n" + 
                  "          from MOTHTARGETDATA MT\n" + 
                  "         where MT.createtime between\n" + 
                  "               (select last_day(add_months(sysdate, -4)) + 1 from dual) and\n" + 
                  "               (select last_day(add_months(sysdate, -1)) from dual)\n" + 
                  "           and MT.userid = ?\n" + 
                  "         Group by MT.MEMBERID)\n" + 
                  " where total >= ?";

        Query query = null;
        SQLQuery sqlQuery = null;
        query = em.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        sqlQuery.setParameter(0, userId);//业务参数
        sqlQuery.setParameter(2, userId);
        sqlQuery.setParameter(1, 1);
        sqlQuery.setParameter(3, 1);
        Map<String,Object> map = new HashMap<String,Object>();
        map = getMap(sqlQuery,map,1);
        sqlQuery.setParameter(1, 2);
        sqlQuery.setParameter(3, 2);
        map = getMap(sqlQuery,map,2);
        sqlQuery.setParameter(1, 5);
        sqlQuery.setParameter(3, 5);
        map = getMap(sqlQuery,map,5);
      return map;
  }
  
  public Map<String,Object> getMap(SQLQuery sqlQuery,Map map,int term){
    List<Object[]> ret = sqlQuery.list();
    if (CollectionUtils.isNotEmpty(ret)) {
      if(term == 1){
        ret.forEach(o -> {
          map.put("mer" + (String)o[0], (BigDecimal)o[1]);
        });
      }
      if(term == 2){
        ret.forEach(o -> {
          map.put("active" + (String)o[0], (BigDecimal)o[1]);
        });
      }
      if(term == 5){
        ret.forEach(o -> {
          map.put("mature" + (String)o[0], (BigDecimal)o[1]);
        });
      }
    }
    return map;
  }


  @Override
  public String save(MonthTarget mt,SalesMan sm){
    MonthTarget m = mtr.findBySalesman(sm);
    if(ObjectUtils.isEmpty(m)){
      mt.setSalesman(sm);
      mt.setRegion(sm.getRegion());
      Manager manager = getManager();
      mt.setManagerId(manager.getId());
      Calendar cal = Calendar.getInstance();
      //下面的就是把当前日期加一个月
      cal.add(Calendar.MONTH, 1);
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
      mt.setTargetCycle(sdf.format(cal.getTime()));
      System.out.println(mt.getTargetCycle());
      mt = mtr.save(mt);
      return "ok";
    }else{
      return "exists";
    }
  }
  
  /*
   * 获取manager方法
   */
  public Manager getManager(){
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    return manager;
  }
  
}
