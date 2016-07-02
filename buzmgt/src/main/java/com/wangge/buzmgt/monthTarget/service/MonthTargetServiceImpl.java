package com.wangge.buzmgt.monthTarget.service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.wangge.buzmgt.monthTarget.entity.Count;
import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.monthTarget.repository.MonthTargetRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.service.ManagerService;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MonthTargetServiceImpl implements MonthTargetService {
  private static final Logger logger = Logger.getLogger(MonthTargetServiceImpl.class);

  @PersistenceContext
  private EntityManager entityManager;
  @Resource
  private MonthTargetRepository mtr;
  @Resource
  private ManagerService managerService;
  @Resource
  private RegionService regionService;


  @Override
  public Region getRegion() {
    // 获取user
    Subject subject = SecurityUtils.getSubject();
    User user = (User) subject.getPrincipal();
    Manager manager = managerService.getById(user.getId());
    String regionId = manager.getRegion().getId();
    return regionService.getRegionById(regionId);
  }

  /**
   *
   * @param time：月份
   * @param managerId:区域经理id
   * @param pageable
     * @return
     */
  @Override
  public Page<MonthTarget> findByTargetCycleAndManagerId(String time, String managerId, Pageable pageable) {
    logger.info("time:  "+time);
    Specification<MonthTarget> specification1 = new Specification<MonthTarget>() {
      @Override
      public Predicate toPredicate(Root<MonthTarget> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.like(root.get("targetCycle").as(String.class),"%"+time+"%");//插入查询时间
        Predicate predicate1 = cb.like(root.get("managerId").as(String.class),"%"+managerId+"%");//插入当前的区域经理的id
        Predicate p = cb.and(predicate,predicate1);
        return p;
      }
    };
    Page page = mtr.findAll(specification1,pageable);//查询出所有的目标设置信息（对应的是一条业务员的信息）
    logger.info(page);
//    findCount(time, page);
//    findActive(time, findCount(time, page));  //查询活跃商家
    return findCount(time, page);
//    List<MonthTarget> list = page.getContent();
//    return page;
  }

  /**
   * 根据时间与日期查询业务员实际的提货量
   * @param time
     * @return
     */
  @Override
  public Page<MonthTarget> findCount(String time, Page page) {
    //获取所有的指标信息
    List<MonthTarget> list = page.getContent();
    list.forEach(m->{
      //获取业务员的id
      String parentId = m.getRegion().getId();
      //根据业务员获取获取所有商家的提货量
      String sql = "select sum(m.NUMS) nums from " +
              "MOTHTARGETDATA m " +
              "where to_char(CREATETIME,'YYYY-MM') like '%2016-06%' and PARENTID like '%370000%' ";
      //根据业务员获取获取活跃商家
      String sql1 = "select count(1) from (select count(t.shopname) from (" +
              "select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
              "FROM mothtargetdata m " +
              "where to_char(CREATETIME,'YYYY-MM') like '%2016-06%' and PARENTID like '%370000%'" +
              "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
              "group by t.shopname " +
              "having count(t.shopname)>=2)";
      //根据业务员获取成熟商家
      String sql2 = "select count(1) from (select count(t.shopname) from (" +
              "select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
              "FROM mothtargetdata m " +
              "where to_char(CREATETIME,'YYYY-MM') like '%2016-06%' and PARENTID like '%370000%'" +
              "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
              "group by t.shopname " +
              "having count(t.shopname)>=5)";

      //根据业务员获取提货商家
      String sql3 = "select count(1) from (select count(t.shopname) from (" +
              "select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
              "FROM mothtargetdata m " +
              "where to_char(CREATETIME,'YYYY-MM') like '%2016-06%' and PARENTID like '%370000%'" +
              "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
              "group by t.shopname " +
              ")";

      //根据业务员获取所有注册商家
      String sql4 = "select count(1) from sys_registdata where user_id like '%A37170206040%' ";
      Query query = entityManager.createNativeQuery(sql);
//      Query query1 = entityManager.createNativeQuery(sql1);
//      Query query2 = entityManager.createNativeQuery(sql2);
//      Query query3 = entityManager.createNativeQuery(sql3);
//      Query query4 = entityManager.createNativeQuery(sql4);
      List<Object> list1 = query.getResultList();
//      List<Object[]> list2 = query1.getResultList();
//      List<Object[]> list3 = query2.getResultList();
//      List<Object[]> list4 = query3.getResultList();
//      List<Object[]> list5 = query4.getResultList();
      logger.info("----------------------------");
//      logger.info(list1);

      if(CollectionUtils.isNotEmpty(list1)){
        list1.get(0);
        logger.info("=====================");

          m.setOrder(((BigDecimal)list1.get(0)).intValue());//插入实际的提货量
      }
//      if(CollectionUtils.isNotEmpty(list2)){
//        list1.forEach(o->{
//          Integer c = (Integer) o[0];//获取活跃商家
//          m.setActive(c);//插入活跃商家
//        });
//      }
//      if(CollectionUtils.isNotEmpty(list3)){
//        list1.forEach(o->{
//          Integer c = (Integer) o[0];//获取成熟商家数量
//          m.setMature(c);//插入成熟商家数量
//        });
//      }
//
//      if(CollectionUtils.isNotEmpty(list4)){
//        list1.forEach(o->{
//          Integer c = (Integer) o[0];//获取提货商家数量
//          m.setMerchant(c);//插入提货商家数量
//        });
//      }
//      if(CollectionUtils.isNotEmpty(list5)){
//        list1.forEach(o->{
//          Integer c = (Integer) o[0];//获取所有注册商家
//          m.setMatureAll(c);//插入所有注册商家
//        });
//      }

    });
    return page;
  }

  @Override
  public Page<MonthTarget> findActive(String time, Page page) {
    return null;
  }


  public static Date getDate(String time) throws ParseException {

    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM");
    Date date = sf.parse(time);
    return date;
  }


}
