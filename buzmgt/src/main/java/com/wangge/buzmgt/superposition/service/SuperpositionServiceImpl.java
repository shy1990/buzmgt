package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.superposition.entity.Group;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.entity.SuperpositionRule;
import com.wangge.buzmgt.superposition.pojo.SuperpositionProgress;
import com.wangge.buzmgt.superposition.repository.SuperpositionRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-9-7.
 */
@Service

public class SuperpositionServiceImpl implements SuperpositonService {

    private static final Logger logger = Logger.getLogger(SuperpositionServiceImpl.class);
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private SuperpositionRepository repository;

    @Resource
    private ManagerService managerService;

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private MainPlanService mainPlanService;

    /**
     * 查看详情
     *
     * @param planId
     * @param superId
     * @param userId
     * @param startDate
     * @param endDate
     * @param name
     * @param page
     * @param size
     * @return
     */
    @Override
    public Page<SuperpositionProgress> searchDetail(String planId, Long superId, String userId, String startDate, String endDate, String name, Integer page, Integer size) {
        String sql = " select \n" +
                "    usr.PLAN_ID,\n" +
                "    oder.SHOP_NAME,\n" +
                "    oder.PAY_TIME,\n" +
                "    oder.ORDER_NUM,\n" +
                "    sum(oder.NUMS) as nums,\n" +
                "    oder.goods_name,\n" +
                "    oder.NAMEPATH as SHOP_ADDRESS,\n" +
                "    super.SU_PO_ID,\n" +
                "    t.NAME,\n" +
                "    oder.BUSINESS_REGION_ID\n" +
                "  from \n" +
                "    VIEW_INCOME_MAIN_PLAN_USER usr\n" +
                "  left join\n" +
                "    SYS_SUPERPOSITION super\n" +
                "  on\n" +
                "    super.PLAN_ID = usr.PLAN_ID \n" +
                "  left join\n" +
                "    SYS_SUPER_GOODS_TYPE goods\n" +
                "  on\n" +
                "   goods.SU_ID = super.SU_PO_ID\n" +
                "  left join \n" +
                "    SYS_GOODS_ORDER oder\n" +
                "  on\n" +
                "    oder.REGION_ID = usr.REGION_ID and oder.GOODS_ID = goods.GOOD_ID and replace(oder.machine_type,',') = goods.MACHINE_TYPE\n" +
                "  left join\n" +
                "   -- SYS_SUPER_GROUP grop\n" +
                "   (select grop.GROUP_ID,grop.NAME,grop.ONE_ADD,grop.TWO_ADD,grop.THREE_ADD,grop.SU_ID, meber.USER_ID from \n" +
                "    SYS_SUPER_GROUP grop\n" +
                "  left join \n" +
                "    sys_super_member meber\n" +
                "  on\n" +
                "    meber.group_id = grop.group_id) t\n" +
                "  on\n" +
                "    t.SU_ID = super.SU_PO_ID and t.USER_ID = usr.USER_ID\n" +
                "  where \n" +
                "        usr.PLAN_ID = ?   \n" +
                "    and \n" +
                "        super.SU_PO_ID = ?\n" +
                "    and\n" +
                "        usr.user_id = ?\n" +
                "    and \n" +
                "      (to_char(oder.pay_time,'yyyy-mm-dd') between ? and ? or oder.pay_time is null)\n" +
                " group by\n" +
                "    oder.BUSINESS_REGION_ID,\n" +
                "    usr.PLAN_ID,\n" +
                "    oder.SHOP_NAME,\n" +
                "    oder.PAY_TIME,\n" +
                "    oder.NAMEPATH,\n" +
                "    super.SU_PO_ID,\n" +
                "    t.NAME,\n" +
                "    oder.ORDER_NUM,\n" +
                "    oder.goods_name ";

        Query query = null;
        SQLQuery sqlQuery = null;
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        int e = 4;
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setParameter(a, planId);//方案id
        sqlQuery.setParameter(b, superId);//叠加方案id
        sqlQuery.setParameter(c, userId);//业务员id
        sqlQuery.setParameter(d, startDate);//开始日期
        sqlQuery.setParameter(e, endDate);//结束日期
        int count = sqlQuery.list().size();//分页查询出总条数(不是分页之后的)
        sqlQuery.setFirstResult(page * size);//设置开始位置
        sqlQuery.setMaxResults(size);//每页显示条数
        List<SuperpositionProgress> progressList = new ArrayList<SuperpositionProgress>();
        List<Object[]> ret = sqlQuery.list();
        if (CollectionUtils.isNotEmpty(ret)) {
            ret.forEach(r -> {
                SuperpositionProgress progress = new SuperpositionProgress();
                progress.setShopName((String) r[1]);
                progress.setPayTime(DateUtil.date2String((Date) r[2]));
                progress.setOrderNum((String) r[3]);
                if (r[4] != null) {
                    progress.setNums(((BigDecimal) r[4]).toString());
                }
                progress.setGoodsName((String) r[5]);
                progress.setShopAddress((String) r[6]);
                progressList.add(progress);
            });

        }
        Page<SuperpositionProgress> pageResult = new PageImpl<SuperpositionProgress>(progressList, new PageRequest(page, size), count);
        return pageResult;
    }

    /**
     * 查看进程
     *
     * @param planId
     * @param superId
     * @return
     */
    @Override
    public Page<SuperpositionProgress> findAll(String planId, Long superId, String startDate, String endDate, String name, Integer page, Integer size) {
        logger.info(startDate + "  : " + endDate);
        String sql = "\n" +
                "select \n" +
                "  nvl(sum(progress.NUMS),0) as NUMS,\n" +
                "  progress.USER_ID,\n" +
                "  progress.TRUENAME,\n" +
                "  progress.REGION_ID, \n" +
                "  progress.TASK_ONE,\n" +
                "  progress.TASK_TWO,\n" +
                "  progress.TASK_THREE,\n" +
                "  progress.IMPL_DATE,\n" +
                "  progress.END_DATE,\n" +
                "  progress.ONE_ADD,\n" +
                "  progress.TWO_ADD,\n" +
                "  progress.THREE_ADD,\n " +
                "  progress.NAMEPATH\n," +
                "  progress.NAME " +
                "from \n" +
                "  (select \n" +
                "    usr.TRUENAME,\n" +
                "    usr.REGION_ID,\n" +
                "    usr.USER_ID,\n" +
                "    usr.NAMEPATH,\n" +
                "    usr.PLAN_ID,\n" +
                "    oder.SHOP_NAME,\n" +
                "    oder.PAY_TIME,\n" +
                "    oder.NUMS,\n" +
                "    oder.NAMEPATH as SHOP_ADDRESS,\n" +
                "    super.SU_PO_ID,\n" +
                "    super.TASK_ONE,\n" +
                "    super.TASK_TWO,\n" +
                "    super.TASK_THREE,\n" +
                "    super.IMPL_DATE,\n" +
                "    super.END_DATE,\n" +
                "    t.ONE_ADD,\n" +
                "    t.TWO_ADD,\n" +
                "    t.THREE_ADD,\n" +
                "    t.NAME \n" +
                "  from \n" +
                "    VIEW_INCOME_MAIN_PLAN_USER usr\n" +
                "  left join\n" +
                "    SYS_SUPERPOSITION super\n" +
                "  on\n" +
                "    super.PLAN_ID = usr.PLAN_ID \n" +
                "  left join\n" +
                "    SYS_SUPER_GOODS_TYPE goods\n" +
                "  on\n" +
                "   goods.SU_ID = super.SU_PO_ID\n" +
                "  left join \n" +
                "    SYS_GOODS_ORDER oder\n" +
                "  on\n" +
                "    oder.REGION_ID = usr.REGION_ID and oder.GOODS_ID = goods.GOOD_ID and replace(oder.machine_type,',') = goods.MACHINE_TYPE\n" +
                "  left join\n" +
                "   (select grop.GROUP_ID,grop.NAME,grop.ONE_ADD,grop.TWO_ADD,grop.THREE_ADD,grop.SU_ID, meber.USER_ID from \n" +
                "    SYS_SUPER_GROUP grop\n" +
                "  left join \n" +
                "    sys_super_member meber\n" +
                "  on\n" +
                "    meber.group_id = grop.group_id) t\n" +
                "  on\n" +
                "    t.SU_ID = super.SU_PO_ID and t.USER_ID = usr.USER_ID\n" +
                "  where \n" +
                "        usr.PLAN_ID = ?   \n" +
                "    and \n" +
                "        super.SU_PO_ID = ? \n" +
                "    and \n" +
                "      (to_char(oder.pay_time,'yyyy-mm-dd') between ? and ? or oder.pay_time is null)) progress\n" +
                "      \n" +
                "GROUP by  \n" +
                "    progress.REGION_ID,\n" +
                "    progress.TRUENAME,\n" +
                "    progress.USER_ID,\n" +
                "    progress.TRUENAME,\n" +
                "    progress.REGION_ID, \n" +
                "    progress.TASK_ONE,\n" +
                "    progress.TASK_TWO,\n" +
                "    progress.TASK_THREE,\n" +
                "    progress.IMPL_DATE,\n" +
                "    progress.END_DATE,\n" +
                "    progress.ONE_ADD,\n" +
                "    progress.TWO_ADD,\n" +
                "    progress.THREE_ADD,\n " +
                "    progress.NAMEPATH,\n" +
                "    progress.NAME";
        Query query = null;
        SQLQuery sqlQuery = null;
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setParameter(a, planId);//方案id
        sqlQuery.setParameter(b, superId);//叠加方案id
        sqlQuery.setParameter(c, startDate);//开始日期
        sqlQuery.setParameter(d, endDate);//结束日期
        int count = sqlQuery.list().size();//分页查询出总条数(不是分页之后的)
        sqlQuery.setFirstResult(page * size);//设置开始位置
        sqlQuery.setMaxResults(size);//每页显示条数
        List<SuperpositionProgress> progressList = new ArrayList<SuperpositionProgress>();
        List<Object[]> ret = sqlQuery.list();
        if (CollectionUtils.isNotEmpty(ret)) {
            ret.forEach(r -> {
                SuperpositionProgress progress = new SuperpositionProgress();
                if (r[0] != null) {
                    progress.setNums(((BigDecimal) r[0]).toString());
                }
                progress.setUserId((String) r[1]);
                progress.setTrueName((String) r[2]);
                progress.setRegionId((String) r[3]);
                progress.setTaskOne((String) r[4]);
                progress.setTaskTwo((String) r[5]);
                progress.setTaskThree((String) r[6]);
                progress.setImplDate(startDate);
                progress.setEndDate(endDate);
                progress.setOneAdd((String) r[9]);
                progress.setTwoAdd((String) r[10]);
                progress.setThreeAdd((String) r[11]);
                progress.setNamePath((String) r[12]);
                progress.setName((String) r[13]);
                progressList.add(progress);
            });
        }
        logger.info(progressList);
        Page<SuperpositionProgress> pageResult = new PageImpl<SuperpositionProgress>(progressList, new PageRequest(page, size), count);
        return pageResult;
    }

    /**
     * 添加叠加任务设置
     *
     * @param superposition
     * @return
     */
    @Override
    public Superposition save(Superposition superposition) {
        List<Group> groupList = superposition.getGroupList();
        List<SuperpositionRule> ruleList = superposition.getRuleList();
//        Integer.parseInt("ss");
        Superposition superposition1 = repository.save(superposition);
        return superposition1;
    }

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    @Override
    public Superposition findById(Long id) {
        Superposition superposition = repository.findOne(id);
        return superposition;
    }

    /**
     * 查询全部的
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Superposition> findAll(Pageable pageable, String type, String sign, String planId) {
        String statTime = "";//开始时间
        String endTime = "";//结束时间

        Page<Superposition> page = repository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if ("pass".equals(sign)) {// 查询正在使用/审核通过/被驳回/正在审核
                    Predicate p1 = cb.equal(root.get("checkStatus").as(String.class), "2");
                    Predicate p2 = cb.equal(root.get("checkStatus").as(String.class), "1");
                    Predicate p3 = cb.lessThanOrEqualTo(root.get("implDate").as(Date.class), new Date());
                    Predicate p4 = cb.greaterThanOrEqualTo(root.get("endDate").as(Date.class), new Date());
                    Predicate p5 = cb.greaterThanOrEqualTo(root.get("planId").as(String.class), planId);
                    Predicate p6 = cb.or(p1, p2, cb.and(p3, p4));
                    Predicate p = cb.and(p6, p5);
                    return p;
                }

                if ("expired".equals(sign)) {//查询已经过期的
                    if (statTime != null && !"".equals(statTime) && endTime != null && !"".equals(endTime)) {
                        list.add(cb.greaterThanOrEqualTo(root.get("implDate").as(Date.class), DateUtil.string2Date(statTime, "yyyy-MM-dd")));
                        list.add(cb.lessThanOrEqualTo(root.get("implDate").as(Date.class), DateUtil.string2Date(endTime, "yyyy-MM-dd")));
                    }
                    list.add(cb.lessThanOrEqualTo(root.get("endDate").as(Date.class), new Date()));//大于结束日期
                    list.add(cb.equal(root.get("checkStatus").as(String.class), "3"));//通过审核的
                    list.add(cb.equal(root.get("planId").as(String.class), planId));//通过审核的
                    return cb.and(list.toArray(new Predicate[list.size()]));
                }
//                if("over".equals(sign)){ // 被驳回
//                    list.add(cb.equal(root.get("checkStatus").as(String.class),"2"));//被驳回
//                    return cb.and(list.toArray(new Predicate[list.size()]));
//                }

                return null;
            }
        }, pageable);
        return page;
    }


    /**
     * 根据id删除
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        repository.delete(id);
    }


    /**
     * 判断使用哪个叠加任务周期量
     *
     * @param memberId
     */
    @Override
    public Superposition checkMember(String memberId) {
//
//        //1.先根据时间判断出使用的哪一个
////        Superposition superposition = ------
//        Superposition superposition = repository.findOne(Long.parseLong("1"));//模拟查出的数据
//        List<SuperpositionRule> ruleList = superposition.getRuleList();//获取公有的周期量任务
//        int length = 0;
//        if(superposition.getTaskThree() != null){
//            length = 3;
//        } else if (superposition.getTaskTwo() != null){
//            length = 2;
//        } else{
//            length = 1;
//        }
//
//
//        /*
//            只有一个指标
//         */
//        if (length == 1) {
//            List<Group> groupList = superposition.getGroupList();//获取分组的
//            groupList.forEach(group -> {
//                group.getMembers().forEach(member -> {
//                    if (memberId.equals(member.getUserId())) {
//                        Integer oneAdd = group.getOneAdd();
//                        superposition.setTaskOne(oneAdd);
//                        ruleList.get(0).setMax(oneAdd);
//                        ruleList.get(1).setMin(oneAdd);
//                    }
//                });
//
//            });
//
//            return superposition;
//        }
//
//        /*
//            有两个指标
//         */
//        if (length == 2) {
//            List<Group> groupList = superposition.getGroupList();//获取分组的
//            groupList.forEach(group -> {
//                group.getMembers().forEach(member -> {
//                    if (memberId.equals(member.getUserId())) {
//                        Integer oneAdd = group.getOneAdd();
//                        Integer twoAdd = group.getTwoAdd();
//                        superposition.setTaskOne(oneAdd);
//                        superposition.setTaskTwo(twoAdd);
//                        ruleList.get(0).setMax(oneAdd);
//                        ruleList.get(1).setMin(oneAdd);
//                        ruleList.get(1).setMax(twoAdd);
//                        ruleList.get(2).setMin(twoAdd);
//                    }
//                });
//
//            });
//
//            return superposition;
//
//        }
//        /*
//            有三个指标
//         */
//        if (length == 3) {
//            List<Group> groupList = superposition.getGroupList();//获取分组的
//            groupList.forEach(group -> {
//                group.getMembers().forEach(member -> {
//                    if (memberId.equals(member.getUserId())) {
//                        Integer oneAdd = group.getOneAdd();
//                        Integer twoAdd = group.getTwoAdd();
//                        Integer threeAdd = group.getThreeAdd();
//                        superposition.setTaskOne(oneAdd);
//                        superposition.setTaskTwo(twoAdd);
//                        superposition.setTaskThree(threeAdd);
//                        ruleList.get(0).setMax(oneAdd);
//                        ruleList.get(1).setMin(oneAdd);
//                        ruleList.get(1).setMax(twoAdd);
//                        ruleList.get(2).setMin(twoAdd);
//                        ruleList.get(2).setMax(threeAdd);
//                        ruleList.get(3).setMin(threeAdd);
//                    }
//                });
//
//            });
//
//            return superposition;
//        }

        return null;
    }

    /**
     * 查询方案人员
     *
     * @param pageReq
     * @param searchParams
     */
    @Override
    public Page<PlanUserVo> findMainPlanUsers(Pageable pageReq, Map<String, Object> searchParams) throws Exception {
        return mainPlanService.getUserpage(pageReq, searchParams);
    }

    /**
     * 计算收益
     *
     * @param superposition
     * @return
     */
    @Override
    public String compute(Superposition superposition) {

//        List<Group> groups = superposition.getGroupList();//获取分组
//        groups.forEach(group -> {
//
//
//        });


        return null;
    }


    /**
     * 获取用户的id
     *
     * @return
     */
    public String getUserID() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user.getId();
    }

}
