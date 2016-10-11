package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.goods.entity.Goods;
import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.superposition.entity.*;
import com.wangge.buzmgt.superposition.pojo.SuperpositionProgress;
import com.wangge.buzmgt.superposition.repository.SuperpositionRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
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
import java.util.*;

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

    @Autowired
    private SuperpositionRecordService recordService;

    @Autowired
    private LogService logService;

    /**
     * 逻辑删除
     *
     * @param superposition
     */
    @Override
    public void stop(Superposition superposition, String checkStatus) {
        superposition.setCheckStatus(checkStatus);
        repository.save(superposition);
        logService.log(null, "修改叠加方案状态: " + checkStatus + "(注:4-终止/逻辑删除,2-驳回,3-审核通过)", Log.EventType.UPDATE);
    }

    /**
     * 计算叠加收益
     *
     * @param planId
     * @return
     */
    @Override
    public List<SuperpositionProgress> compute(Long planId, Superposition superposition) {

        List<SuperpositionProgress> progressList1 = findRole(superposition, planId);//获取提货量以及所对应的规则
        //判断属于哪个区间,去掉不符合的记录
        if (CollectionUtils.isNotEmpty(progressList1)) {
            Iterator<SuperpositionProgress> progressListIterator = progressList1.iterator();
            while (progressListIterator.hasNext()) {
                SuperpositionProgress su = progressListIterator.next();
                if (!(Integer.parseInt(su.getNums()) >= su.getMin() && Integer.parseInt(su.getNums()) < su.getMax())) {
                    progressListIterator.remove();
                }
            }
            //计算收益
            progressList1.forEach(su -> {
                SuperpositionRecord superpositionRecord = new SuperpositionRecord();
                superpositionRecord.setSalesmanId(su.getUserId());
                superpositionRecord.setPlanId(planId);
                superpositionRecord.setSuperId(superposition.getId());
                superpositionRecord.setAmount(((Integer.parseInt(su.getNums())) * (su.getPercentage())));
                superpositionRecord.setRecord(Integer.parseInt(su.getNums()));
                recordService.save(superpositionRecord);
                logService.log(null, "叠加收益计算保存: " + superpositionRecord, Log.EventType.SAVE);
            });
        }

        return progressList1;
    }

    /*
        获取规则
     */
    public List<SuperpositionProgress> findRole(Superposition superposition, Long planId) {
        List<SuperpositionProgress> progressList = getAll(planId, superposition);
        logger.info("progressList:  " + progressList);//获取规则
        List<SuperpositionProgress> progressList1 = new ArrayList<SuperpositionProgress>();
        if (CollectionUtils.isNotEmpty(progressList)) {
            if (superposition.getTaskThree() != null) {//3个指标
                progressList.forEach(progress -> {
                    if (progress.getOneAdd() != null) {
                        if ("1".equals(progress.getSign())) {
                            progress.setMax(Integer.parseInt(progress.getOneAdd()));
                        } else if ("2".equals(progress.getSign())) {
                            progress.setMin(Integer.parseInt(progress.getOneAdd()));
                            progress.setMax(Integer.parseInt(progress.getTwoAdd()));
                        } else if ("3".equals(progress.getSign())) {
                            progress.setMin(Integer.parseInt(progress.getTwoAdd()));
                            progress.setMax(Integer.parseInt(progress.getTaskThree()));

                        } else if ("4".equals(progress.getSign())) {
                            progress.setMin(Integer.parseInt(progress.getThreeAdd()));
                        }
                        progressList1.add(progress);
                    } else {
                        progressList1.add(progress);
                    }
                });
            } else if (superposition.getTaskTwo() != null) {//两个指标
                progressList.forEach(progress -> {
                    if (progress.getOneAdd() != null) {
                        if ("1".equals(progress.getSign())) {
                            progress.setMax(Integer.parseInt(progress.getOneAdd()));
                        } else if ("2".equals(progress.getSign())) {
                            progress.setMin(Integer.parseInt(progress.getOneAdd()));
                            progress.setMax(Integer.parseInt(progress.getTwoAdd()));
                        } else if ("3".equals(progress.getSign())) {
                            progress.setMin(Integer.parseInt(progress.getTwoAdd()));
                        }
                        progressList1.add(progress);
                    } else {
                        progressList1.add(progress);
                    }

                });
            } else if (superposition.getTaskOne() != null) {//一个指标
                progressList.forEach(progress -> {
                    if (progress.getOneAdd() != null) {
                        if ("1".equals(progress.getSign())) {
                            progress.setMax(Integer.parseInt(progress.getOneAdd()));
                        } else if ("2".equals(progress.getSign())) {
                            progress.setMin(Integer.parseInt(progress.getOneAdd()));
                        }
                        progressList1.add(progress);
                    } else {
                        progressList1.add(progress);
                    }

                });
            }
        }

        logger.info("progressList1:  " + progressList1);
        return progressList1;
    }

    /**
     * 用于退货冲减计算
     *
     * @param userId  业务员id
     * @param goodsId 产品id
     * @param payTime 支付时间
     * @param num     数量
     */
    @Override
    public Superposition computeAfterReturnGoods(String userId, String goodsId, String payTime, Integer num, Long planId) {
        Superposition superposition = repository.findUseByTime(payTime, payTime, planId);//获取此商品使用的方案
        List<GoodsType> goodsTypeList = superposition.getGoodsTypeList();//获取所有叠加的商品
        if (containsGoods(goodsTypeList, goodsId)) {//判断是否有这个商品
            //获取规则
            List<SuperpositionRule> ruleList = superposition.getRuleList();
            //获取用户组
            Map<String, Object> map = getUse(superposition, userId);
            if ((Boolean) map.get("flag")) { //判断是否是特殊用户组
                Group group = (Group) map.get("group");
                if (superposition.getTaskThree() != null) {
                    Integer oneAdd = group.getOneAdd();
                    Integer twoAdd = group.getTwoAdd();
                    Integer threeAdd = group.getThreeAdd();
                    ruleList.get(0).setMax(oneAdd);
                    ruleList.get(1).setMin(oneAdd);
                    ruleList.get(1).setMax(twoAdd);
                    ruleList.get(2).setMin(twoAdd);
                    ruleList.get(2).setMax(threeAdd);
                    ruleList.get(3).setMin(threeAdd);
                } else if (superposition.getTaskTwo() != null) {
                    Integer oneAdd = group.getOneAdd();
                    Integer twoAdd = group.getTwoAdd();
                    ruleList.get(0).setMax(oneAdd);
                    ruleList.get(1).setMin(oneAdd);
                    ruleList.get(1).setMax(twoAdd);
                    ruleList.get(2).setMin(twoAdd);
                } else {
                    Integer oneAdd = group.getOneAdd();
                    ruleList.get(0).setMax(oneAdd);
                    ruleList.get(1).setMin(oneAdd);
                }

            }
            //获取已经保存的叠加提成
            SuperpositionRecord superpositionRecord = recordService.findBySalesmanIdAndPlanIdAndSuperId(userId, planId, superposition.getId());
            Integer newNum = superpositionRecord.getRecord() - num;//计算出冲减之后的数量
            ruleList.forEach(superpositionRule -> {
                if (newNum >= superpositionRule.getMin() && newNum <= superpositionRule.getMax()) {
                    logger.info("superpositionRule:------     " + superpositionRule);//保存冲减之后的记录
                    logger.info("--------:  " + (newNum * superpositionRule.getPercentage()));
                }
            });

        }
        return superposition;
    }


    /*
        判断是否是特殊组,是返回这个组
     */
    public Map<String, Object> getUse(Superposition superposition, String userId) {
        Group groupUse = null;
        //判断是特殊用户组
        List<Group> groupList = superposition.getGroupList();
        flag:
        for (Group group : groupList) {
            for (Member member : group.getMembers()) {
                if (userId.equals(member.getSalesmanId())) {
                    groupUse = group;
                    break flag;
                }
            }
        }
        Map<String, Object> map = new HashedMap();
        map.put("group", groupUse);
        if (groupUse == null)
            map.put("flag", false);
        else
            map.put("flag", true);

        return map;
    }


    /*
     * 判断规则中是否有这个商品
     */
    public Boolean containsGoods(List<GoodsType> goodsTypeList, String goodsId) {
        Boolean flag = false;
        //判断是否包含这个产品
        List<String> goodsIds = new ArrayList<String>();

        if (CollectionUtils.isNotEmpty(goodsTypeList)) {
            goodsTypeList.forEach(goodsType -> {
                goodsIds.add(goodsType.getGoodId());
            });
        }
        if (goodsIds.size() > 0 && goodsIds.contains(goodsId)) {
            flag = true;
        }
        return flag;
    }


    /**
     * 获取所有的方案中的人员提货量
     *
     * @param planId
     * @param superposition
     * @return
     */
    public List<SuperpositionProgress> getAll(Long planId, Superposition superposition) {
        String sql = "SELECT NVL(SUM(progress.NUMS),0) AS NUMS,\n" +
                "  progress.USER_ID,\n" +
                "  progress.TRUENAME,\n" +
                "  progress.REGION_ID,\n" +
                "  progress.TASK_ONE,\n" +
                "  progress.TASK_TWO,\n" +
                "  progress.TASK_THREE,\n" +
                "  progress.IMPL_DATE,\n" +
                "  progress.END_DATE,\n" +
                "  progress.ONE_ADD,\n" +
                "  progress.TWO_ADD,\n" +
                "  progress.THREE_ADD,\n" +
                "  progress.NAMEPATH,\n" +
                "  progress.NAME,\n" +
                "  progress.min,\n" +
                "  progress.max,\n" +
                "  progress.percentage,\n " +
                "  progress.sign \n" +
                "FROM\n" +
                "  (SELECT usr.TRUENAME,\n" +
                "    usr.REGION_ID,\n" +
                "    usr.USER_ID,\n" +
                "    usr.NAMEPATH,\n" +
                "    usr.PLAN_ID,\n" +
                "    oder.SHOP_NAME,\n" +
                "    oder.PAY_TIME,\n" +
                "    oder.NUMS,\n" +
                "    oder.NAMEPATH AS SHOP_ADDRESS,\n" +
                "    super.SU_PO_ID,\n" +
                "    super.TASK_ONE,\n" +
                "    super.TASK_TWO,\n" +
                "    super.TASK_THREE,\n" +
                "    super.IMPL_DATE,\n" +
                "    super.END_DATE,\n" +
                "    t.ONE_ADD,\n" +
                "    t.TWO_ADD,\n" +
                "    t.THREE_ADD,\n" +
                "    t.NAME,\n" +
                "    superrule.min,\n" +
                "    superrule.max,\n" +
                "    superrule.percentage, \n " +
                "    superrule.serial_number sign \n" +
                "  FROM VIEW_INCOME_MAIN_PLAN_USER usr\n" +
                "  LEFT JOIN SYS_SUPERPOSITION super\n" +
                "  ON super.PLAN_ID = usr.PLAN_ID\n" +
                "  LEFT JOIN SYS_SUPERPOSITION_RULE superrule\n" +
                "  ON superrule.SU_ID = super.SU_PO_ID\n" +
                "  LEFT JOIN SYS_SUPER_GOODS_TYPE goods\n" +
                "  ON goods.SU_ID = super.SU_PO_ID\n" +
                "  LEFT JOIN SYS_GOODS_ORDER oder\n" +
                "  ON oder.REGION_ID                  = usr.REGION_ID\n" +
                "  AND oder.GOODS_ID                  = goods.GOOD_ID\n" +
                "  AND REPLACE(oder.machine_type,',') = goods.MACHINE_TYPE\n" +
                "  LEFT JOIN\n" +
                "    (SELECT grop.GROUP_ID,\n" +
                "      grop.NAME,\n" +
                "      grop.ONE_ADD,\n" +
                "      grop.TWO_ADD,\n" +
                "      grop.THREE_ADD,\n" +
                "      grop.SU_ID,\n" +
                "      meber.USER_ID\n" +
                "    FROM SYS_SUPER_GROUP grop\n" +
                "    LEFT JOIN sys_super_member meber\n" +
                "    ON meber.group_id    = grop.group_id\n" +
                "    ) t ON t.SU_ID       = super.SU_PO_ID\n" +
                "  AND t.USER_ID          = usr.USER_ID\n" +
                "  WHERE usr.PLAN_ID      = ?\n" +
                "  AND super.SU_PO_ID     = ?\n" +
                "  AND super.CHECK_STATUS = '3'\n" +
                "  AND (TO_CHAR(oder.pay_time,'yyyy-mm-dd') BETWEEN ? AND ? \n" +
                "  OR oder.pay_time IS NULL)\n" +
                "  ) progress\n" +
                "GROUP BY progress.REGION_ID,\n" +
                "  progress.TRUENAME,\n" +
                "  progress.USER_ID,\n" +
                "  progress.TRUENAME,\n" +
                "  progress.REGION_ID,\n" +
                "  progress.TASK_ONE,\n" +
                "  progress.TASK_TWO,\n" +
                "  progress.TASK_THREE,\n" +
                "  progress.IMPL_DATE,\n" +
                "  progress.END_DATE,\n" +
                "  progress.ONE_ADD,\n" +
                "  progress.TWO_ADD,\n" +
                "  progress.THREE_ADD,\n" +
                "  progress.NAMEPATH,\n" +
                "  progress.NAME,\n" +
                "  progress.min,\n" +
                "  progress.max,\n" +
                "  progress.percentage,\n " +
                "  progress.sign ";
        Query query = null;
        SQLQuery sqlQuery = null;
        int a = 0;
        int b = 1;
        int c = 2;
        int d = 3;
        query = entityManager.createNativeQuery(sql);
        sqlQuery = query.unwrap(SQLQuery.class);
        sqlQuery.setParameter(a, planId);//方案id
        sqlQuery.setParameter(b, superposition.getId());//叠加方案id
        sqlQuery.setParameter(c, DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"));//开始日期
        sqlQuery.setParameter(d, DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"));//结束日期
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
                progress.setImplDate(DateUtil.date2String(superposition.getImplDate(), "yyyy-MM-dd"));
                progress.setEndDate(DateUtil.date2String(superposition.getEndDate(), "yyyy-MM-dd"));
                progress.setOneAdd((String) r[9]);
                progress.setTwoAdd((String) r[10]);
                progress.setThreeAdd((String) r[11]);
                progress.setNamePath((String) r[12]);
                progress.setName((String) r[13]);
                progress.setMin(Integer.parseInt(((String) (r[14]))));
                progress.setMax(Integer.parseInt(((String) (r[15]))));
                if (r[16] != null) {
                    progress.setPercentage(((BigDecimal) r[16]).floatValue());
                }
                progress.setSign((String) r[17]);
                progressList.add(progress);
            });
        }
        return progressList;
    }


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
    public Page<SuperpositionProgress> searchDetail(Long planId, Long superId, String userId, String startDate, String endDate, String name, Integer page, Integer size) {
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
    public Page<SuperpositionProgress> findAll(Long planId, Long superId, String startDate, String endDate, String name, Integer page, Integer size) {
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
                "  progress.NAMEPATH,\n" +
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
                "        super.SU_PO_ID = ? \n " +
                "    and super.CHECK_STATUS = '3' \n" +
                "    and \n" +
                "      (to_char(oder.pay_time,'yyyy-mm-dd') between ? and ? or oder.pay_time is null)) progress\n" +
                "     \n" +
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
    public Page<Superposition> findAll(Pageable pageable, String type, String sign, Long planId) {
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
                    Predicate p5 = cb.equal(root.get("planId").as(Long.class), planId);
                    Predicate p6 = cb.equal(root.get("checkStatus").as(String.class), "3");
                    Predicate p = cb.and(p5, cb.or(cb.and(cb.or(p1, p2)), cb.and(p3, p4, p6)));
                    return p;
                } else if ("expired".equals(sign)) {//查询已经过期的
                    if (statTime != null && !"".equals(statTime) && endTime != null && !"".equals(endTime)) {
                        list.add(cb.greaterThanOrEqualTo(root.get("implDate").as(Date.class), DateUtil.string2Date(statTime, "yyyy-MM-dd")));
                        list.add(cb.lessThanOrEqualTo(root.get("implDate").as(Date.class), DateUtil.string2Date(endTime, "yyyy-MM-dd")));
                    }
                    list.add(cb.lessThanOrEqualTo(root.get("endDate").as(Date.class), new Date()));//大于结束日期
                    list.add(cb.equal(root.get("checkStatus").as(String.class), "3"));//通过审核的
                    list.add(cb.equal(root.get("planId").as(Long.class), planId));//方案id
                    return cb.and(list.toArray(new Predicate[list.size()]));
                }
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
    public Superposition checkMember(Superposition superposition, String memberId) {
//
        //1.先根据时间判断出使用的哪一个
//        Superposition superposition = ------
//        Superposition superposition = repository.findOne(Long.parseLong("1"));//模拟查出的数据
        List<SuperpositionRule> ruleList = superposition.getRuleList();//获取公有的周期量任务
        int length = 0;
        if (superposition.getTaskThree() != null) {
            length = 3;
        } else if (superposition.getTaskTwo() != null) {
            length = 2;
        } else {
            length = 1;
        }

        /*
            只有一个指标
         */
        if (length == 1) {
            List<Group> groupList = superposition.getGroupList();//获取分组的
            groupList.forEach(group -> {
                group.getMembers().forEach(member -> {
                    if (memberId.equals(member.getSalesmanId())) {
                        Integer oneAdd = group.getOneAdd();
                        superposition.setTaskOne(oneAdd);
                        ruleList.get(0).setMax(oneAdd);
                        ruleList.get(1).setMin(oneAdd);
                    }
                });

            });

            return superposition;
        }

        /*
            有两个指标
         */
        if (length == 2) {
            List<Group> groupList = superposition.getGroupList();//获取分组的
            groupList.forEach(group -> {
                group.getMembers().forEach(member -> {
                    if (memberId.equals(member.getSalesmanId())) {
                        Integer oneAdd = group.getOneAdd();
                        Integer twoAdd = group.getTwoAdd();
                        superposition.setTaskOne(oneAdd);
                        superposition.setTaskTwo(twoAdd);
                        ruleList.get(0).setMax(oneAdd);
                        ruleList.get(1).setMin(oneAdd);
                        ruleList.get(1).setMax(twoAdd);
                        ruleList.get(2).setMin(twoAdd);
                    }
                });

            });
            return superposition;

        }
        /*
            有三个指标
         */
        if (length == 3) {
            List<Group> groupList = superposition.getGroupList();//获取分组的
            groupList.forEach(group -> {
                group.getMembers().forEach(member -> {
                    if (memberId.equals(member.getSalesmanId())) {
                        Integer oneAdd = group.getOneAdd();
                        Integer twoAdd = group.getTwoAdd();
                        Integer threeAdd = group.getThreeAdd();
                        superposition.setTaskOne(oneAdd);
                        superposition.setTaskTwo(twoAdd);
                        superposition.setTaskThree(threeAdd);
                        ruleList.get(0).setMax(oneAdd);
                        ruleList.get(1).setMin(oneAdd);
                        ruleList.get(1).setMax(twoAdd);
                        ruleList.get(2).setMin(twoAdd);
                        ruleList.get(2).setMax(threeAdd);
                        ruleList.get(3).setMin(threeAdd);
                    }
                });

            });

            return superposition;
        }

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
        //1.方案中的


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
