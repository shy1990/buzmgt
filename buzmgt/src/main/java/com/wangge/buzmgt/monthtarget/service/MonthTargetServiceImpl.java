package com.wangge.buzmgt.monthtarget.service;

import com.wangge.buzmgt.assess.service.RegistDataService;
import com.wangge.buzmgt.monthtarget.entity.MonthTarget;
import com.wangge.buzmgt.monthtarget.repository.MonthTargetRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.service.RegionService;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.Manager;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.teammember.service.SalesManService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.SQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private RegistDataService registDataService;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 根据当前登录管理员获取区域
     *
     * @return
     */
    @Override
    public Region getRegion() {
        // 获取user
        Manager manager = getManager();
        String regionId = manager.getRegion().getId();
        return regionService.getRegionById(regionId);
    }


    @Override
    public Map<String, Object> getOrderNum(String regionId) {

        String sql = "select 'one' ms,nvl(sum(m.NUMS),0) nums from\n" +
        "                    MOTHTARGETDATA m\n" +
        "                    where to_char(CREATETIME,'YYYY-MM') = (select to_char(last_day(add_months(sysdate,-2))+1,'YYYY-MM')  from dual)\n" +
        "                    and PARENTID = ?\n" +
        "                    union all\n" +
        "                    select 'three' ms,nvl(sum(m.NUMS)/3,0) nums from\n" +
        "                    MOTHTARGETDATA m\n" +
        "                    where to_char(CREATETIME,'YYYY-MM') between (select to_char(last_day(add_months(sysdate,-4))+1,'YYYY-MM')  from dual)\n" +
        "                    and (select to_char(last_day(add_months(sysdate,-2))+1,'YYYY-MM')  from dual) and PARENTID = ?";

        Query query = null;
        SQLQuery sqlQuery = null;
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        int a = 0;
        sqlQuery.setParameter(a, regionId);//业务参数
        sqlQuery.setParameter(1, regionId);
        List<Object[]> ret = sqlQuery.list();
        Map<String, Object> map = new HashMap<String, Object>();
        if (CollectionUtils.isNotEmpty(ret)) {
            ret.forEach(o -> {
                map.put((String)o[0], ((BigDecimal)o[1]).intValue());
            });
        }
        return map;
    }


    @Override
    public Map<String, Object> getSeller(String regionId) {
        String sql = "select 'one',count(1)\n" +
                "  from (select MT.MEMBERID, count(MT.ORDERID) total\n" +
                "          from MOTHTARGETDATA MT\n" +
                "         where MT.createtime between\n" +
                "               (select last_day(add_months(sysdate, -2)) + 1 from dual) and\n" +
                "               (select last_day(add_months(sysdate, -1)) from dual)\n" +
                "           and MT.parentId = ?\n" +
                "         Group by MT.MEMBERID)\n" +
                " where total >= ?\n" +
                "union all\n" +
                "select 'three',count(1)/3\n" +
                "  from (select MT.MEMBERID, count(MT.ORDERID) total\n" +
                "          from MOTHTARGETDATA MT\n" +
                "         where MT.createtime between\n" +
                "               (select last_day(add_months(sysdate, -4)) + 1 from dual) and\n" +
                "               (select last_day(add_months(sysdate, -1)) from dual)\n" +
                "           and MT.parentId = ?\n" +
                "         Group by MT.MEMBERID,to_char(MT.createtime,'yyyy-mm')) t\n" +
                " where t.total >= ?";

        Query query = null;
        SQLQuery sqlQuery = null;
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        int a = 0;
        sqlQuery.setParameter(a, regionId);//业务参数
        sqlQuery.setParameter(2, regionId);
        sqlQuery.setParameter(1, 1);
        sqlQuery.setParameter(3, 1);
        Map<String,Object> map = new HashMap<String,Object>();
        map = getMap(sqlQuery,map,1);
        return map;
    }

    public Map<String, Object> getMap(SQLQuery sqlQuery, Map map, int term) {
        List<Object[]> ret = sqlQuery.list();
        if (CollectionUtils.isNotEmpty(ret)) {
            if (term == 1) {
                ret.forEach(o -> {
                    map.put("mer" + (String)o[0], ((BigDecimal)o[1]).intValue());
                });
            }
            if (term == 2) {
                ret.forEach(o -> {
                    map.put("active" + (String)o[0], ((BigDecimal)o[1]).intValue());
                });
            }
            if (term == 5) {
                ret.forEach(o -> {
                    map.put("mature" + (String)o[0], ((BigDecimal)o[1]).intValue());
                });
            }
        }
        return map;
    }

    @Override
    public Map<String, Object> getMerchant(String regionId) {
        String sql = "SELECT 'one', COUNT(1) active_num1\n" +
                "  FROM (SELECT COUNT(t.MEMBERID), t.parentid\n" +
                "          FROM (SELECT m.MEMBERID,\n" +
                "                       m.parentid,\n" +
                "                       COUNT(TO_CHAR(m.CREATETIME, 'YYYY-MM-DD'))\n" +
                "                  FROM mothtargetdata m\n" +
                "                 WHERE TO_CHAR(m.CREATETIME, 'YYYY/MM/DD') between\n" +
                "                       (select to_char(add_months(last_day(sysdate), -2) + 1,\n" +
                "                                       'yyyy/mm/dd')\n" +
                "                          from dual) and\n" +
                "                       (select to_char(add_months(last_day(sysdate), -1),\n" +
                "                                       'yyyy/mm/dd')\n" +
                "                          from dual)\n" +
                "                 GROUP BY TO_CHAR(m.CREATETIME, 'YYYY-MM-DD'),\n" +
                "                          m.MEMBERID,\n" +
                "                          m.parentid\n" +
                "                ) t\n" +
                "         GROUP BY t.MEMBERID, t.parentid\n" +
                "        HAVING COUNT(t.MEMBERID) >= ?) active\n" +
                " where active.parentid = ?\n" +
                " union all\n" +
                "SELECT 'three', COUNT(1)/3 active_num1\n" +
                "  FROM (SELECT COUNT(t.MEMBERID), t.parentid\n" +
                "          FROM (SELECT m.MEMBERID,\n" +
                "                       m.parentid,\n" +
                "                       COUNT(TO_CHAR(m.CREATETIME, 'YYYY-MM-DD'))\n" +
                "                  FROM mothtargetdata m\n" +
                "                 WHERE TO_CHAR(m.CREATETIME, 'YYYY/MM/DD') between\n" +
                "                       (select to_char(add_months(last_day(sysdate), -4) + 1,\n" +
                "                                       'yyyy/mm/dd')\n" +
                "                          from dual) and\n" +
                "                       (select to_char(add_months(last_day(sysdate), -1),\n" +
                "                                       'yyyy/mm/dd')\n" +
                "                          from dual)\n" +
                "                 GROUP BY TO_CHAR(m.CREATETIME, 'YYYY-MM-DD'),\n" +
                "                          m.MEMBERID,\n" +
                "                          m.parentid\n" +
                "                ) t\n" +
                "         GROUP BY t.MEMBERID, t.parentid\n" +
                "        HAVING COUNT(t.MEMBERID) >= ?) active\n" +
                " where active.parentid = ?";

        Query query = null;
        SQLQuery sqlQuery = null;
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        int a = 0;
        sqlQuery.setParameter(1, regionId);//业务参数
        sqlQuery.setParameter(3, regionId);
        Map<String,Object> map = new HashMap<String,Object>();
        sqlQuery.setParameter(a, 2);
        sqlQuery.setParameter(2, 2);
        map = getMap(sqlQuery,map,2);
        sqlQuery.setParameter(a, 5);
        sqlQuery.setParameter(2, 5);
        map = getMap(sqlQuery,map,5);
        return map;
    }

    @Override
    public String save(MonthTarget mt, Region region) {
        Calendar cal = Calendar.getInstance();
        //下面的就是把当前日期加一个月
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String nextMonth = sdf.format(cal.getTime());
        MonthTarget m = mtr.findByRegionAndTargetCycle(region,nextMonth);
        SalesMan sm = smService.findByRegionAndStatus(region);
        if(ObjectUtils.isEmpty(m)){
            mt.setSalesman(sm);
            mt.setRegion(sm.getRegion());
            Manager manager = getManager();
            mt.setManagerRegion(manager.getRegion().getId());
            mt.setTargetCycle(nextMonth);
            mt = mtr.save(mt);
            return "ok";
        } else {
            return "exists";
        }
    }

    /*
     * 获取manager方法
     */
    public Manager getManager() {
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
                Predicate predicate = cb.equal(root.get("targetCycle").as(String.class), targetCycle);
//                Predicate predicate2 = cb.equal(root.get("managerRegion").as(String.class),getManager().getRegion().getId());
                if (StringUtils.isNotBlank(userName)) {
                    Join<MonthTarget, SalesMan> salesmanJoin = root.join(root.getModel()
                            .getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
                    Predicate predicate1 = (cb.like(salesmanJoin.get("truename").as(String.class),
                            "%" + userName + "%"));
                    predicate = cb.and(predicate, predicate1);
                }

                return predicate;
            }

        }, pageRequest);
        List<MonthTarget> list = page.getContent();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(m -> {
                int count = registDataService.findCountByRegionIdlike(m.getRegion().getId());
                m.setMatureAll(count);
                if (m.getPublishStatus() == 0) {
                    m.setView(true);
                } else {
                    m.setView(false);
                }
            });
        }
        return page;
    }

    @Override
    public String save(MonthTarget monthTarget) {
        if (!ObjectUtils.isEmpty(monthTarget)) {
            mtr.save(monthTarget);
            return "ok";
        } else {
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
        //根据管理员区域id查找未发布的指标
        List<MonthTarget> list = mtr.findByManagerRegionAndPublishStatus(manager.getRegion().getId(), 0);
        if (!ObjectUtils.isEmpty(list)) {
            list.forEach(mt -> {
                mt.setPublishStatus(1);
                mtr.save(mt);
            });
            return "ok";
        } else {
            return "non";
        }
    }

    /**
     * @param time：月份
     * @param pageable
     * @return
     */
    @Override
    public Page<MonthTarget> findByTargetCycleAndManagerId(String truename, String time, Pageable pageable) {
        Manager m = getManager();
        String managerRegion = m.getRegion().getId();
        Specification<MonthTarget> specification1 = null;
        logger.info("------- managerId ---------:" + m.getId().trim());
        //判断是不是root用户
        if ("1".equals(m.getId().trim())) {
            logger.info("--------------- go root ----------------");
            specification1 = new Specification<MonthTarget>() {
                @Override
                public Predicate toPredicate(Root<MonthTarget> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.like(root.get("targetCycle").as(String.class), "%" + time + "%");//插入查询时间
                    //根据姓名检索
                    if (truename != null && !"".equals(truename)) {
                        Join<MonthTarget, SalesMan> salesManJoin = root.join(root.getModel().getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
                        Predicate predicate2 = cb.like(salesManJoin.get("truename").as(String.class), "%" + truename + "%");
                        predicate = cb.and(predicate, predicate2);
                    }
                    return predicate;
                }
            };
        } else {
            specification1 = new Specification<MonthTarget>() {
                @Override
                public Predicate toPredicate(Root<MonthTarget> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    Predicate predicate = cb.like(root.get("targetCycle").as(String.class), "%" + time + "%");//插入查询时间
                    Predicate predicate1 = cb.like(root.get("managerRegion").as(String.class), "%" + managerRegion + "%");//插入当前的区域经理的id
                    Predicate p = cb.and(predicate, predicate1);
                    //根据姓名检索
                    if (truename != null && !"".equals(truename)) {
                        Join<MonthTarget, SalesMan> salesManJoin = root.join(root.getModel().getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
                        Predicate predicate2 = cb.like(salesManJoin.get("truename").as(String.class), "%" + truename + "%");
                        p = cb.and(predicate, predicate1, predicate2);
                    }
                    return p;
                }
            };
        }

        Page page = mtr.findAll(specification1, pageable);//查询出所有的目标设置信息（对应的是一条业务员的信息）
        logger.info(page);
        return findCount(time, page);
//        return page;
    }

    /**
     * 根据时间与日期查询业务员实际的提货量
     *
     * @param time
     * @return
     */
    @Override
    public Page<MonthTarget> findCount(String time, Page page) {
        //获取所有的指标信息
        List<MonthTarget> list = page.getContent();
        list.forEach(m -> {
            //获取业务员的区域id
            String parentId = m.getRegion().getId();
//            parentId = "370000";
            //根据业务员获取获取所有商家的提货量
            String sql = "select nvl(sum(m.NUMS),0) nums from " +
                    "MOTHTARGETDATA m " +
                    "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID = ?  ";

            //根据业务员获取获取活跃商家
            String sql1 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
                    "select m.shopname,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
                    "FROM mothtargetdata m " +
                    "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID = ? " +
                    "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname ) t " +
                    "group by t.shopname " +
                    "having count(t.shopname)>=2)";
            //根据业务员获取成熟商家
            String sql2 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
                    "select m.shopname,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
                    "FROM mothtargetdata m " +
                    "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID = ? " +
                    "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname ) t " +
                    "group by t.shopname " +
                    "having count(t.shopname)>=5)";

            //根据业务员获取提货商家
            String sql3 = "select nvl(count(1),0) from (select count(t.shopname) from (" +
                    "select m.shopname,m.createTime,count(to_char(CREATETIME,'YYYY-MM-DD')) " +
                    "FROM mothtargetdata m " +
                    "where to_char(CREATETIME,'YYYY-MM') like ? and PARENTID = ? " +
                    "group by to_char(CREATETIME,'YYYY-MM-DD'),m.shopname,m.createTime ) t " +
                    "group by t.shopname " +
                    ")";


            //根据业务员获取所有注册商家
            String sql4 = "select nvl(count(1),0) from(\n" +
                    "select r.parent_id pr_id\n" +
                    "from sys_registdata s left join sys_region r \n" +
                    "on s.region_id = r.region_id )\n" +
                    "\n" +
                    "where pr_id = ? ";

            Query query = entityManager.createNativeQuery(sql);
            int a = 1;
            query.setParameter(a, "%" + time + "%");
            int b = 2;
            query.setParameter(b, parentId.trim());
            Query query1 = entityManager.createNativeQuery(sql1);
            query1.setParameter(a, "%" + time + "%");
            query1.setParameter(b, parentId.trim());
            Query query2 = entityManager.createNativeQuery(sql2);
            query2.setParameter(a, "%" + time + "%");
            query2.setParameter(b, parentId.trim());
            Query query3 = entityManager.createNativeQuery(sql3);
            query3.setParameter(a, "%" + time + "%");
            query3.setParameter(b, parentId.trim());
            Query query4 = entityManager.createNativeQuery(sql4);
            query4.setParameter(a, parentId.trim());
            List<Object> list1 = query.getResultList();
            List<Object> list2 = query1.getResultList();
            List<Object> list3 = query2.getResultList();
            List<Object> list4 = query3.getResultList();
            List<Object> list5 = query4.getResultList();
            if (CollectionUtils.isNotEmpty(list1)) {
                logger.info(list1);
                m.setOrder(((BigDecimal) list1.get(0)).intValue());//插入实际的提货量
            }
            if (CollectionUtils.isNotEmpty(list2)) {
                m.setActive(((BigDecimal) list2.get(0)).intValue());//插入活跃商家
            }
            if (CollectionUtils.isNotEmpty(list3)) {
                m.setMature(((BigDecimal) list3.get(0)).intValue());//插入成熟商家数量
            }

            if (CollectionUtils.isNotEmpty(list4)) {
                m.setMerchant(((BigDecimal) list4.get(0)).intValue());//插入提货商家数量
            }
            if (CollectionUtils.isNotEmpty(list5)) {
                m.setMatureAll(((BigDecimal) list5.get(0)).intValue());//插入所有注册商家
            }

        });
        return page;
    }


    @Override
    public Page<MonthTarget> findActive(String time, Page page) {
        return null;
    }

    @Override
    public List<MonthTarget> exportExcel(String time) {
        Manager manager = getManager();
        String managerRegion = manager.getRegion().getId();//获取区域id

        String sql = "\n" +
                "SELECT tg.region_id,\n" +
                "  tg.manager_region,\n" +
                " s.truename ,\n" +
                "  tg.order_num,\n" +
                "  tg.merchant_num,\n" +
                "  tg.active_num,\n" +
                "  tg.mature_num,\n" +
                "  NVL(ab.order_num1,'0') order_num_a ,\n" +
                "  NVL(q.merchant_num1,'0') merchant_num_a,\n" +
                "  NVL(ac.active_num1,'0') active_num_a,\n" +
                "  NVL(cs.mature_num1,'0') mature_num_a,\n" +
                "  NVL(g.zhuce,'0') zhuce_a, \n" +
                "  tg.target_cycle\n" +
                "FROM sys_month_target tg\n" +
                "LEFT JOIN sys_salesman s\n" +
                "ON tg.user_id = s.user_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT m.parentid kk,\n" +
                "    SUM(m.NUMS) order_num1\n" +
                "  FROM MOTHTARGETDATA m\n" +
                "  WHERE TO_CHAR(CREATETIME,'YYYY-MM') LIKE ?\n" +
                "  GROUP BY m.parentid\n" +
                "  ) ab\n" +
                "ON ab.kk = tg.region_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT active.parentid,\n" +
                "    COUNT(1) active_num1\n" +
                "  FROM\n" +
                "    (SELECT COUNT(t.shopname),\n" +
                "      t.parentid\n" +
                "    FROM\n" +
                "      (SELECT m.shopname,\n" +
                "        m.parentid,\n" +
                "    \n" +
                "        COUNT(TO_CHAR(CREATETIME,'YYYY-MM-DD'))\n" +
                "      FROM mothtargetdata m\n" +
                "      WHERE TO_CHAR(CREATETIME,'YYYY-MM') LIKE ?\n" +
                " \n" +
                "      GROUP BY TO_CHAR(CREATETIME,'YYYY-MM-DD'),\n" +
                "        m.shopname,\n" +
                "        m.parentid\n" +
                "\n" +
                "      ) t\n" +
                "    GROUP BY t.shopname,\n" +
                "      t.parentid\n" +
                "    HAVING COUNT(t.shopname)>=2\n" +
                "    ) active\n" +
                "  GROUP BY active.parentid\n" +
                "  ) ac\n" +
                "ON ac.parentid= tg.region_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT active.parentid,\n" +
                "    COUNT(1) mature_num1\n" +
                "  FROM\n" +
                "    (SELECT COUNT(t.shopname),\n" +
                "      t.parentid\n" +
                "    FROM\n" +
                "      (SELECT m.shopname,\n" +
                "        m.parentid,\n" +
                "        COUNT(TO_CHAR(CREATETIME,'YYYY-MM-DD'))\n" +
                "      FROM mothtargetdata m\n" +
                "      WHERE TO_CHAR(CREATETIME,'YYYY-MM') LIKE ?\n" +
                "      GROUP BY TO_CHAR(CREATETIME,'YYYY-MM-DD'),\n" +
                "        m.shopname,\n" +
                "        m.parentid\n" +
                "      ) t\n" +
                "    GROUP BY t.shopname,\n" +
                "      t.parentid\n" +
                "    HAVING COUNT(t.shopname)>=5\n" +
                "    ) active\n" +
                "\n" +
                "  GROUP BY active.parentid\n" +
                "  ) cs\n" +
                "ON cs.parentid= tg.region_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT p.parentid,\n" +
                "    COUNT(1) merchant_num1\n" +
                "  FROM\n" +
                "    (SELECT m.parentid,\n" +
                "      m.shopname,\n" +
                "      m.phonenum\n" +
                "    FROM mothtargetdata m\n" +
                "    WHERE TO_CHAR(createtime,'yyyy-mm') LIKE ? \n" +
                "   \n" +
                "    GROUP BY m.parentid,\n" +
                "      m.shopname,\n" +
                "      m.phonenum\n" +
                "    ) p\n" +
                "   \n" +
                "  GROUP BY p.parentid\n" +
                "  ) q\n" +
                "ON q.parentid= tg.region_id\n" +
                "LEFT JOIN\n" +
                "  (SELECT pr_id,\n" +
                "    COUNT(1) zhuce\n" +
                "  FROM\n" +
                "    (SELECT r.parent_id pr_id\n" +
                "    FROM sys_registdata s\n" +
                "    LEFT JOIN sys_region r\n" +
                "    ON s.region_id = r.region_id\n" +
                "    )\n" +
                "  GROUP BY pr_id\n" +
                "  ) g ON g.pr_id = tg.region_id\n" +
                "WHERE tg.target_cycle LIKE  ? ";
        logger.info("managerRegion:  " + managerRegion);
        int a = 5;
        SQLQuery sqlQuery = null;
        if (!"0".equals(managerRegion)) {
            sql += " and tg.manager_region = ? ";
            Query query = entityManager.createNativeQuery(sql);
            sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
            sqlQuery.setParameter(a, managerRegion);
        }else {
            Query query = entityManager.createNativeQuery(sql);
            sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        }

        for (int i = 0; i <= 4; i++) {
            sqlQuery.setParameter(i, "%" + time + "%");
        }
        List<MonthTarget> list = new ArrayList<>();
        List<Object[]> ret = sqlQuery.list();
        logger.info(ret);
        if (CollectionUtils.isNotEmpty(ret)) {
            ret.forEach(o -> {
                MonthTarget monthTarget = new MonthTarget();
                Region region = regionService.getRegionById((String) o[0]);
                monthTarget.setRegion(region);//业务员区域
                monthTarget.setTrueName((String) o[2]);//姓名
                monthTarget.setOrderNum(((BigDecimal) o[3]).intValue());//指标提货量
                monthTarget.setMerchantNum(((BigDecimal) o[4]).intValue());//指标提货商家
                monthTarget.setActiveNum(((BigDecimal) o[5]).intValue());//指标活跃商家
                monthTarget.setMatureNum(((BigDecimal) o[6]).intValue());//指标成熟商家
                monthTarget.setOrder(((BigDecimal) o[7]).intValue());//实际提货量
                monthTarget.setMerchant(((BigDecimal) o[8]).intValue());//实际提货商家
                monthTarget.setActive(((BigDecimal) o[9]).intValue());//实际活跃商家
                monthTarget.setMature(((BigDecimal) o[10]).intValue());//实际成熟商家
                monthTarget.setMatureAll(((BigDecimal) o[11]).intValue()); //总注册商家
                monthTarget.setTargetCycle((String) o[12]);//指标时间
                list.add(monthTarget);
            });
        }

        return list;
    }

    @Override
    public Page<MonthTarget> findAllBySql(Integer page, Integer size, String truename, String time) {
        Manager m = getManager();
        String managerRegion = m.getRegion().getId();
        String sql = "\n" +
                "SELECT tg.region_id,\n" +
                "  tg.manager_region,\n" +
                " s.truename ,\n" +
                "  tg.order_num,\n" +
                "  tg.merchant_num,\n" +
                "  tg.active_num,\n" +
                "  tg.mature_num,\n" +
                "  NVL(ab.order_num1,'0') order_num_a ,\n" +
                "  NVL(q.merchant_num1,'0') merchant_num_a,\n" +
                "  NVL(ac.active_num1,'0') active_num_a,\n" +
                "  NVL(cs.mature_num1,'0') mature_num_a,\n" +
                "  NVL(g.zhuce,'0') zhuce_a, \n" +
                "  tg.target_cycle\n" +
                "FROM sys_month_target tg\n" +
                "LEFT JOIN sys_salesman s\n" +
                "ON tg.user_id = s.user_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT m.parentid kk,\n" +
                "    SUM(m.NUMS) order_num1\n" +
                "  FROM MOTHTARGETDATA m\n" +
                "  WHERE TO_CHAR(CREATETIME,'YYYY-MM') LIKE ?\n" +
                "  GROUP BY m.parentid\n" +
                "  ) ab\n" +
                "ON ab.kk = tg.region_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT active.parentid,\n" +
                "    COUNT(1) active_num1\n" +
                "  FROM\n" +
                "    (SELECT COUNT(t.shopname),\n" +
                "      t.parentid\n" +
                "    FROM\n" +
                "      (SELECT m.shopname,\n" +
                "        m.parentid,\n" +
                "    \n" +
                "        COUNT(TO_CHAR(CREATETIME,'YYYY-MM-DD'))\n" +
                "      FROM mothtargetdata m\n" +
                "      WHERE TO_CHAR(CREATETIME,'YYYY-MM') LIKE ?\n" +
                " \n" +
                "      GROUP BY TO_CHAR(CREATETIME,'YYYY-MM-DD'),\n" +
                "        m.shopname,\n" +
                "        m.parentid\n" +
                "\n" +
                "      ) t\n" +
                "    GROUP BY t.shopname,\n" +
                "      t.parentid\n" +
                "    HAVING COUNT(t.shopname)>=2\n" +
                "    ) active\n" +
                "  GROUP BY active.parentid\n" +
                "  ) ac\n" +
                "ON ac.parentid= tg.region_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT active.parentid,\n" +
                "    COUNT(1) mature_num1\n" +
                "  FROM\n" +
                "    (SELECT COUNT(t.shopname),\n" +
                "      t.parentid\n" +
                "    FROM\n" +
                "      (SELECT m.shopname,\n" +
                "        m.parentid,\n" +
                "        COUNT(TO_CHAR(CREATETIME,'YYYY-MM-DD'))\n" +
                "      FROM mothtargetdata m\n" +
                "      WHERE TO_CHAR(CREATETIME,'YYYY-MM') LIKE ?\n" +
                "      GROUP BY TO_CHAR(CREATETIME,'YYYY-MM-DD'),\n" +
                "        m.shopname,\n" +
                "        m.parentid\n" +
                "      ) t\n" +
                "    GROUP BY t.shopname,\n" +
                "      t.parentid\n" +
                "    HAVING COUNT(t.shopname)>=5\n" +
                "    ) active\n" +
                "\n" +
                "  GROUP BY active.parentid\n" +
                "  ) cs\n" +
                "ON cs.parentid= tg.region_id\n" +
                "LEFT JOIN \n" +
                "  (SELECT p.parentid,\n" +
                "    COUNT(1) merchant_num1\n" +
                "  FROM\n" +
                "    (SELECT m.parentid,\n" +
                "      m.shopname,\n" +
                "      m.phonenum\n" +
                "    FROM mothtargetdata m\n" +
                "    WHERE TO_CHAR(createtime,'yyyy-mm') LIKE ? \n" +
                "   \n" +
                "    GROUP BY m.parentid,\n" +
                "      m.shopname,\n" +
                "      m.phonenum\n" +
                "    ) p\n" +
                "   \n" +
                "  GROUP BY p.parentid\n" +
                "  ) q\n" +
                "ON q.parentid= tg.region_id\n" +
                "LEFT JOIN\n" +
                "  (SELECT pr_id,\n" +
                "    COUNT(1) zhuce\n" +
                "  FROM\n" +
                "    (SELECT r.parent_id pr_id\n" +
                "    FROM sys_registdata s\n" +
                "    LEFT JOIN sys_region r\n" +
                "    ON s.region_id = r.region_id\n" +
                "    )\n" +
                "  GROUP BY pr_id\n" +
                "  ) g ON g.pr_id = tg.region_id\n" +
                "WHERE tg.target_cycle LIKE ? \n" +
                " and s.truename like ? ";
        logger.info("managerRegion:  " + managerRegion);

//        if(truename != null && !"".equals(truename) ){
//            sql += " and tg.truename like ?  ";
//
//        }
        Page<MonthTarget> pageResult = null;

        SQLQuery sqlQuery = null;
        int a =5;
        if (!"0".equals(managerRegion)) {
            sql += " and tg.manager_region = ? ";
            Query query = entityManager.createNativeQuery(sql);
            sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
            sqlQuery.setParameter(a+1,managerRegion);
        }else{
            Query query = entityManager.createNativeQuery(sql);
            sqlQuery = query.unwrap(SQLQuery.class);//转换成sqlQuery
        }
        for (int i = 0; i <= 4; i++) {
            sqlQuery.setParameter(i, "%" + time + "%");
        }
        if (StringUtils.isNotEmpty(truename)) {
            sqlQuery.setParameter(a, "%" + truename + "%");
        } else{
            sqlQuery.setParameter(a, "%%");
        }

        int count = sqlQuery.list().size();//分页查询出总条数(不是分页之后的)
        sqlQuery.setFirstResult(page * size);//设置开始位置
        sqlQuery.setMaxResults(size);//每页显示条数
        List<MonthTarget> list = new ArrayList<>();
        List<Object[]> ret = sqlQuery.list();
        logger.info(ret);
        if (CollectionUtils.isNotEmpty(ret)) {
            ret.forEach(o -> {
                MonthTarget monthTarget = new MonthTarget();
                Region region = regionService.getRegionById((String) o[0]);
                monthTarget.setRegion(region);//业务员区域
                monthTarget.setTrueName((String) o[2]);//姓名
                monthTarget.setOrderNum(((BigDecimal) o[3]).intValue());//指标提货量
                monthTarget.setMerchantNum(((BigDecimal) o[4]).intValue());//指标提货商家
                monthTarget.setActiveNum(((BigDecimal) o[5]).intValue());//指标活跃商家
                monthTarget.setMatureNum(((BigDecimal) o[6]).intValue());//指标成熟商家
                monthTarget.setOrder(((BigDecimal) o[7]).intValue());//实际提货量
                monthTarget.setMerchant(((BigDecimal) o[8]).intValue());//实际提货商家
                monthTarget.setActive(((BigDecimal) o[9]).intValue());//实际活跃商家
                monthTarget.setMature(((BigDecimal) o[10]).intValue());//实际成熟商家
                monthTarget.setMatureAll(((BigDecimal) o[11]).intValue()); //总注册商家
                monthTarget.setTargetCycle((String) o[12]);//指标时间
                list.add(monthTarget);
            });
        }
        pageResult = new PageImpl<MonthTarget>(list, new PageRequest(page, size), count);

        return pageResult;
    }


}
