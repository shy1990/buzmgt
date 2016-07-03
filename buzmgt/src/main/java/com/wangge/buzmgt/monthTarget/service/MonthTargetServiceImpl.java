package com.wangge.buzmgt.monthTarget.service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;

import com.wangge.buzmgt.monthTarget.entity.Count;
import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.teammember.entity.SalesMan;
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
  public Page<MonthTarget> findByTargetCycleAndManagerId(String truename,String time, String managerId, Pageable pageable) {
    logger.info("time:  "+time);
    logger.info("managerId:  "+managerId);
    Specification<MonthTarget> specification1 = new Specification<MonthTarget>() {
      @Override
      public Predicate toPredicate(Root<MonthTarget> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate predicate = cb.like(root.get("targetCycle").as(String.class),"%"+time+"%");//插入查询时间
        Predicate predicate1 = cb.like(root.get("managerId").as(String.class),"%"+managerId+"%");//插入当前的区域经理的id
        Predicate p = cb.and(predicate,predicate1);
        //根据姓名检索
        if (truename != null && !"".equals(truename)){
          Join<MonthTarget,SalesMan> salesManJoin = root.join(root.getModel().getSingularAttribute("salesman",SalesMan.class),JoinType.LEFT);
          Predicate predicate2 = cb.like(salesManJoin.get("truename").as(String.class),"%拓展经理%");
          p = cb.and(predicate,predicate1,predicate2);
        }
        return p;
      }
    };
    Page page = mtr.findAll(specification1,pageable);//查询出所有的目标设置信息（对应的是一条业务员的信息）
    logger.info(page);
    return findCount(time, page);
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
      parentId = "370000";
      //根据业务员获取获取所有商家的提货量
      String sql = "select nvl(sum(m.NUMS),0) nums from " +
              "MOTHTARGETDATA m " +
              "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ?  ";
      //根据业务员获取获取活跃商家
      String sql1 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
              "select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
              "FROM mothtargetdata m " +
              "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ? " +
              "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
              "group by t.shopname " +
              "having count(t.shopname)>=2)";
      //根据业务员获取成熟商家
      String sql2 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
              "select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
              "FROM mothtargetdata m " +
              "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ? " +
              "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
              "group by t.shopname " +
              "having count(t.shopname)>=5)";

      //根据业务员获取提货商家
      String sql3 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
              "select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
              "FROM mothtargetdata m " +
              "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID like ? " +
              "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
              "group by t.shopname " +
              ")";

      //根据业务员获取所有注册商家
      String sql4 = "select nvl(count(1),0) from sys_registdata where user_id like ? ";

      Query query = entityManager.createNativeQuery(sql);
      int a = 1;
      query.setParameter(a,"%" + time + "%");
      int b = 2;
      query.setParameter(b,"%" + parentId + "%");
      Query query1 = entityManager.createNativeQuery(sql1);
      query1.setParameter(a,"%" + time + "%");
      query1.setParameter(b,"%" + parentId + "%");
      Query query2 = entityManager.createNativeQuery(sql2);
      query2.setParameter(a,"%" + time + "%");
      query2.setParameter(b,"%" + parentId + "%");
      Query query3 = entityManager.createNativeQuery(sql3);
      query3.setParameter(a,"%" + time + "%");
      query3.setParameter(b,"%" + parentId + "%");
      Query query4 = entityManager.createNativeQuery(sql4);
      query4.setParameter(a,"%" + parentId + "%");
      List<Object> list1 = query.getResultList();
      List<Object> list2 = query1.getResultList();
      List<Object> list3 = query2.getResultList();
      List<Object> list4 = query3.getResultList();
      List<Object> list5 = query4.getResultList();
      if(CollectionUtils.isNotEmpty(list1)){
        logger.info(list1);
          m.setOrder(((BigDecimal)list1.get(0)).intValue());//插入实际的提货量
      }
      if(CollectionUtils.isNotEmpty(list2)){
          m.setActive(((BigDecimal)list2.get(0)).intValue());//插入活跃商家
      }
      if(CollectionUtils.isNotEmpty(list3)){
          m.setMature(((BigDecimal)list3.get(0)).intValue());//插入成熟商家数量
      }

      if(CollectionUtils.isNotEmpty(list4)){
          m.setMerchant(((BigDecimal)list4.get(0)).intValue());;//插入提货商家数量
      }
      if(CollectionUtils.isNotEmpty(list5)){
          m.setMatureAll(((BigDecimal)list1.get(0)).intValue());;//插入所有注册商家
      }

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
