package com.wangge.buzmgt.monthTarget.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import com.wangge.buzmgt.saojie.service.SaojieDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.SQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import com.wangge.buzmgt.monthTarget.entity.MonthTarget;
import com.wangge.buzmgt.monthTarget.repository.MonthTargetRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;

@Service
public class MonthTargetServiceImpl implements MonthTargetService {
    private static final Logger logger = Logger.getLogger(MonthTargetServiceImpl.class);
    @Resource
    private MonthTargetRepository mtr;
    @Resource
    private ManagerService managerService;
    @Resource
    private RegionService regionService;
    @Resource
    private SalesManService smService;
    @Resource
    private SaojieDataService saojieDataService;
    @PersistenceContext
    private EntityManager entityManager;

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
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        int a = 0;
        sqlQuery.setParameter(a, userId);//业务参数
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
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        int a = 0;
        sqlQuery.setParameter(a, userId);//业务参数
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
            mt.setManagerRegion(manager.getRegion().getId());
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

    @Override
    public Page<MonthTarget> findAll(String targetCycle, String userName, Pageable pageRequest) {
        Page<MonthTarget> page = mtr.findAll(new Specification<MonthTarget>() {

            public Predicate toPredicate(Root<MonthTarget> root, CriteriaQuery<?> query,
                                         CriteriaBuilder cb) {
//                List<Predicate> predicates = new ArrayList<Predicate>();
                Predicate predicate = cb.equal(root.get("targetCycle").as(String.class), targetCycle);
//                predicates.add(cb.equal(root.get("targetCycle").as(String.class), targetCycle));
                if (StringUtils.isNotBlank(userName)) {
                    Join<MonthTarget, SalesMan> salesmanJoin = root.join(root.getModel()
                            .getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
                    Predicate predicate1= (cb.like(salesmanJoin.get("truename").as(String.class),
                            "%" + userName + "%"));
                    predicate = cb.and(predicate,predicate1);
                }

                return predicate;
            }

        }, pageRequest);
        List<MonthTarget> list = page.getContent();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(m -> {
                int count = saojieDataService.getCountByUserId(m.getSalesman().getId());
                m.setMatureAll(count);
                if (m.getPublishStatus() == 0){
                    m.setView(true);
                }else{
                    m.setView(false);
                }
            });
        }
        return page;
    }

    @Override
    public String save(MonthTarget monthTarget) {
        if(!ObjectUtils.isEmpty(monthTarget)){
            mtr.save(monthTarget);
            return "ok";
        }else{
            return "error";
        }
    }

    @Override
    public String publish(MonthTarget monthTarget) {
        monthTarget.setPublishStatus(1);//设置为已发布
        mtr.save(monthTarget);
        return "ok";
    }

    @Override
    public String publishAll() {
        Manager manager = getManager();
        //根据管理员id查找未发布的指标
        List<MonthTarget> list = mtr.findByManagerIdAndPublishStatus(manager.getId(),0);
        if (!ObjectUtils.isEmpty(list)){
            list.forEach(mt -> {
                mt.setPublishStatus(1);
                mtr.save(mt);
            });
            return "ok";
        }else {
            return "non";
        }
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
        Specification<MonthTarget> specification1 = null;
        //判断是不是root用户
        if("0".equals(managerId.trim())){
            specification1 = new Specification<MonthTarget>() {
                @Override
                public Predicate toPredicate(Root<MonthTarget> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.like(root.get("targetCycle").as(String.class),"%"+time+"%");//插入查询时间
                    //根据姓名检索
                    if (truename != null && !"".equals(truename)){
                        Join<MonthTarget,SalesMan> salesManJoin = root.join(root.getModel().getSingularAttribute("salesman",SalesMan.class),JoinType.LEFT);
                        Predicate predicate2 = cb.like(salesManJoin.get("truename").as(String.class),"%"+truename+"%");
                        predicate = cb.and(predicate,predicate2);
                    }
                    return predicate;
                }
            };
        }else{
            specification1 = new Specification<MonthTarget>() {
                @Override
                public Predicate toPredicate(Root<MonthTarget> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.like(root.get("targetCycle").as(String.class),"%"+time+"%");//插入查询时间
                    Predicate predicate1 = cb.like(root.get("managerId").as(String.class),"%"+managerId+"%");//插入当前的区域经理的id
                    Predicate p = cb.and(predicate,predicate1);
                    //根据姓名检索
                    if (truename != null && !"".equals(truename)){
                        Join<MonthTarget,SalesMan> salesManJoin = root.join(root.getModel().getSingularAttribute("salesman",SalesMan.class),JoinType.LEFT);
                        Predicate predicate2 = cb.like(salesManJoin.get("truename").as(String.class),"%"+truename+"%");
                        p = cb.and(predicate,predicate1,predicate2);
                    }
                    return p;
                }
            };
        }

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
//            parentId = "370000";
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
                m.setMerchant(((BigDecimal)list4.get(0)).intValue());//插入提货商家数量
            }
            if(CollectionUtils.isNotEmpty(list5)){
                m.setMatureAll(((BigDecimal)list1.get(0)).intValue());//插入所有注册商家
            }

        });
        return page;
    }





    @Override
    public Page<MonthTarget> findActive(String time, Page page) {
        return null;
    }




}
