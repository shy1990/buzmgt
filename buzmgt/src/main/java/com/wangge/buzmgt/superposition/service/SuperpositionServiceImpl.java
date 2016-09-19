package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.income.main.service.MainPlanService;
import com.wangge.buzmgt.income.main.vo.PlanUserVo;
import com.wangge.buzmgt.superposition.entity.Group;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.entity.SuperpositionRule;
import com.wangge.buzmgt.superposition.pojo.SalesmanDetails;
import com.wangge.buzmgt.superposition.repository.SuperpositionRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.service.ManagerService;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-9-7.
 */
@Service

public class SuperpositionServiceImpl implements SuperpositonService {
    @Autowired
    private SuperpositionRepository repository;

    @Resource
    private ManagerService managerService;

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private MainPlanService mainPlanService;

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
     *根据id查询
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
     * @param pageable
     * @return
     */
    @Override
    public Page<Superposition> findAll(Pageable pageable,String type,String sign) {
        String statTime = "";//开始时间
        String endTime = "";//结束时间

        Page<Superposition> page = repository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();
                if("pass".equals(sign)){// 查询正在使用/审核通过/被驳回/正在审核
                    Predicate p1 = cb.equal(root.get("checkStatus").as(String.class),"2");
                    Predicate p2 = cb.equal(root.get("checkStatus").as(String.class),"1");
                    Predicate p3 = cb.lessThanOrEqualTo(root.get("implDate").as(Date.class),new Date());
                    Predicate p4 = cb.greaterThanOrEqualTo(root.get("endDate").as(Date.class),new Date());
                    Predicate p = cb.or(p1,p2,cb.and(p3,p4));
                    return  p;
                }

                if("expired".equals(sign)){//查询已经过期的
                    if(statTime != null && !"".equals(statTime) && endTime != null && !"".equals(endTime)){
                        list.add(cb.greaterThanOrEqualTo(root.get("implDate").as(Date.class), DateUtil.string2Date(statTime,"yyyy-MM-dd")));
                        list.add(cb.lessThanOrEqualTo(root.get("implDate").as(Date.class),DateUtil.string2Date(endTime,"yyyy-MM-dd")));
                    }
                    list.add(cb.lessThanOrEqualTo(root.get("endDate").as(Date.class),new Date()));//大于结束日期
                    list.add(cb.equal(root.get("checkStatus").as(String.class),"3"));//通过审核的
                    return cb.and(list.toArray(new Predicate[list.size()]));
                }
//                if("over".equals(sign)){ // 被驳回
//                    list.add(cb.equal(root.get("checkStatus").as(String.class),"2"));//被驳回
//                    return cb.and(list.toArray(new Predicate[list.size()]));
//                }

                return null;
            }
        },pageable);
        List<Superposition> superpositions = page.getContent();




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

        //1.先根据时间判断出使用的哪一个
//        Superposition superposition = ------
        Superposition superposition = repository.findOne(Long.parseLong("1"));//模拟查出的数据
        List<SuperpositionRule> ruleList = superposition.getRuleList();//获取公有的周期量任务
        int length = 0;
        if(superposition.getTaskThree() != null){
            length = 3;
        } else if (superposition.getTaskTwo() != null){
            length = 2;
        } else{
            length = 1;
        }


        /*
            只有一个指标
         */
        if (length == 1) {
            List<Group> groupList = superposition.getGroupList();//获取分组的
            groupList.forEach(group -> {
                group.getMembers().forEach(member -> {
                    if (memberId.equals(member.getUserId())) {
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
                    if (memberId.equals(member.getUserId())) {
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
                    if (memberId.equals(member.getUserId())) {
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


    @Override
    public void find1(Superposition superposition) {
        Integer taskOne = superposition.getTaskOne();
        Integer tasktTwo = superposition.getTaskTwo();
        Integer taskThree = superposition.getTaskThree();
        //模拟用户组的数据
        String[] userRegions = {"370126","370883","370829","370828","370827","370830"};
        SalesmanDetails salesmanDetails1 = new SalesmanDetails("123","lidong","370126");
        SalesmanDetails salesmanDetails2 = new SalesmanDetails("123","lidong","370883");
        SalesmanDetails salesmanDetails3 = new SalesmanDetails("123","lidong","370829");
        SalesmanDetails salesmanDetails4 = new SalesmanDetails("123","lidong","370827");
        List<SalesmanDetails> list = new ArrayList<SalesmanDetails>();
        list.add(salesmanDetails1);
        list.add(salesmanDetails2);
        list.add(salesmanDetails3);
        list.add(salesmanDetails4);

        list.forEach(salesmanDetails -> {
            //计算出每一个的提货量
            Integer o = goodsOrderService.countNums("2015-02-07","2015-02-24",salesmanDetails.getRegionId());
            System.out.println("-------:"+o);
        });



    }

    /**
     * 查询方案人员
     * @param pageReq
     * @param searchParams
     */
    @Override
    public Page<PlanUserVo> findMainPlanUsers(Pageable pageReq, Map<String, Object> searchParams) throws Exception {
        return mainPlanService.getUserpage(pageReq,searchParams);
    }


    /**
     * 获取用户的id
     * @return
     */
    public String getUserID(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return  user.getId();
    }

}
