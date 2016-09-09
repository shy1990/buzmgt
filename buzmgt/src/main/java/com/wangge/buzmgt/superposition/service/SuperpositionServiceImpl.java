package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.superposition.entity.Group;
import com.wangge.buzmgt.superposition.entity.Superposition;
import com.wangge.buzmgt.superposition.entity.SuperpositionRule;
import com.wangge.buzmgt.superposition.repository.SuperpositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by joe on 16-9-7.
 */
@Service
public class SuperpositionServiceImpl implements SuperpositonService {
    @Autowired
    private SuperpositionRepository repository;

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



        Superposition superposition1 = repository.save(superposition);
        return superposition1;
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

        //2.判断人员属于哪一个
//
//        List<Group> groupList = superposition.getGroupList();//获取分组的
//        groupList.forEach(group -> {
//            group.getMembers().forEach(member -> {
//                if (memberId.equals(member.getUserId())) {
//                    Integer oneAdd = group.getOneAdd();
//                    Integer twoAdd = group.getTwoAdd();
//                    Integer threeAdd = group.getThreeAdd();
//                    superposition.setTaskOne(oneAdd);
//                    superposition.setTaskTwo(twoAdd);
//                    superposition.setTaskThree(threeAdd);
//                    ruleList.get(0).setMax(oneAdd);
//                    ruleList.get(1).setMin(oneAdd);
//                    ruleList.get(1).setMax(twoAdd);
//                    ruleList.get(2).setMin(twoAdd);
//                    ruleList.get(2).setMax(threeAdd);
//                    ruleList.get(3).setMin(threeAdd);
//                }
//            });
//
//        });

        return null;
    }
}
