package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.areaattribute.entity.AreaAttribute;
import com.wangge.buzmgt.areaattribute.service.AreaAttributeService;
import com.wangge.buzmgt.income.main.vo.OrderGoods;
import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import com.wangge.buzmgt.section.entity.SectionRecord;
import com.wangge.buzmgt.section.repository.ProductionRepository;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-8-19.
 */
@Service
public class ProductionServiceImpl implements ProductionService {

    private static final Logger logger = Logger.getLogger(ProductionServiceImpl.class);

    @Autowired
    private ProductionRepository productionRepository;

    @Autowired
    private SectionRecordService sectionRecordService;

    @Autowired
    private LogService logService;

    @Autowired
    private AreaAttributeService attributeService;


    /**
     * 已支付计算
     *
     * @param orderNo:订单号
     * @param payTime1:支付时间("yyyy-MM-dd")
     * @param price:产品价格
     * @param userId:业务员id
     * @param goodsId:产品id
     * @param type:产品类型
     * @param planId:方案id
     * @param num:单品数量
     * @return
     */
    @Override
    public String compute(String orderNo, Date payTime1, Double price, String userId, String goodsId, String type, Long planId, Integer num, String regionId) {
        //1.需要的参数: 1.订单号 2.订单单品支付时间 3.商品的价格 4.用户id 5.区域id 6.产品类型 7.方案id(下面是模拟数据)
        String payTime = DateUtil.date2String(payTime1, "yyyy-MM-dd");
        try {
            //2.查询当前产品使用的价格区间
            Production p = findNow(type, payTime, planId);//订单日期正在使用的
            if (!ObjectUtils.notEqual(p, null)) {
                logger.info("没有合适的价格区间");
                return "没有合适的价格区间";
            }
            //判断price是属于那个区间的
            List<PriceRange> priceRanges = p.getPriceRanges();
            List<PriceRange> priceRanges1 = new ArrayList<PriceRange>();
            //查询出正在用的区间
            if (CollectionUtils.isNotEmpty(priceRanges)) {
                priceRanges.forEach(priceRange -> {
                    String impl = DateUtil.date2String(priceRange.getImplementationDate());
                    String endTime = DateUtil.date2String(priceRange.getEndTime());
                    try {
                        if (priceRange.getEndTime() != null) {//结束日期是不是空
                            if ("3".equals(priceRange.getPriceRangeStatus()) && "0".equals(priceRange.getStatus())) {//正在使用
                                if (DateUtil.compareDate(payTime, endTime) && DateUtil.compareDate(impl, payTime)) {// impl<=当前时间<=endTime
                                    priceRanges1.add(priceRange);//保留个区间
                                }
                            }
                        }
                        if (priceRange.getEndTime() == null) {//结束日期是空
                            if ("3".equals(priceRange.getPriceRangeStatus()) && "0".equals(priceRange.getStatus())) {//正在使用
                                if (DateUtil.compareDate(impl, payTime)) {// impl<=当前时间
                                    priceRanges1.add(priceRange);//保留这个区间
                                }
                            }
                        }
                    } catch (ParseException e) {
                        return;
                    }
                });
            }
            //判断价格属于哪个区间
            if (CollectionUtils.isNotEmpty(priceRanges1)) {
                priceRanges1.forEach(priceRange -> {

                    String[] prices = priceRange.getPriceRange().split("-");
                    if (Double.parseDouble(prices[0]) <= price && price < Double.parseDouble(prices[1])) {
                        AreaAttribute areaAttribute = attributeService.findByRegionIdAndRuleIdAndType(regionId, priceRange.getPriceRangeId(), AreaAttribute.PlanType.PRICERANGE);

                        SectionRecord sectionRecord = new SectionRecord();
                        if (ObjectUtils.notEqual(areaAttribute, null)) {
                            sectionRecord.setPercentage(priceRange.getPercentage() + areaAttribute.getCommissions());
                        } else {
                            sectionRecord.setPercentage(priceRange.getPercentage());
                        }
                        sectionRecord.setOrderNo(orderNo);//订单详情/订单
                        sectionRecord.setPayTime(DateUtil.string2Date(payTime));
                        sectionRecord.setPlanId(planId);
                        sectionRecord.setPriceRangeId(priceRange.getPriceRangeId());
                        sectionRecord.setSalesmanId(userId);
                        sectionRecord.setGoodsId(goodsId);
                        sectionRecord.setSectionId(priceRange.getProductionId());
                        sectionRecord.setNum(num);
                        sectionRecord.setOrderflag(1);//已付款
                        SectionRecord sectionRecord1 = sectionRecordService.save(sectionRecord);
                        logService.log(null, "区间方案单品已付款计算: " + sectionRecord1, Log.EventType.SAVE);
                        logger.info(sectionRecord1);
                        return;
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异常:计算失败");
        }
        return "";
    }

    /**
     * 出库计算
     *
     * @param orderNo:订单号
     * @param price:产品价格
     * @param userId:业务员id
     * @param goodsId:产品id
     * @param type:产品类型
     * @param planId:方案id
     * @param num:单品数量
     * @return
     */
    @Override
    public String compute(String orderNo, Double price, String userId, String goodsId, String type, Long planId, Integer num, String regionId) {
//1.需要的参数: 1.订单id 2.订单单品支付时间 3.商品的价格 4.用户id 5.区域id 6.产品类型 7.方案id(下面是模拟数据)
        String payTime = DateUtil.date2String(new Date(), "yyyy-MM-dd");
        try {
            //2.查询当前产品使用的价格区间
            Production p = findNow(type, payTime, planId);//订单日期正在使用的
            if (!ObjectUtils.notEqual(p, null)) {
                logger.info("没有合适的价格区间");
                return "没有合适的价格区间";
            }
            //判断price是属于那个区间的
            List<PriceRange> priceRanges = p.getPriceRanges();
            List<PriceRange> priceRanges1 = new ArrayList<PriceRange>();
            //查询出正在用的区间
            if (CollectionUtils.isNotEmpty(priceRanges)) {
                priceRanges.forEach(priceRange -> {
                    String impl = DateUtil.date2String(priceRange.getImplementationDate());
                    String endTime = DateUtil.date2String(priceRange.getEndTime());
                    try {
                        if (priceRange.getEndTime() != null) {//结束日期是不是空
                            if ("3".equals(priceRange.getPriceRangeStatus()) && "0".equals(priceRange.getStatus())) {//正在使用
                                if (DateUtil.compareDate(payTime, endTime) && DateUtil.compareDate(impl, payTime)) {// impl<=当前时间<=endTime
                                    priceRanges1.add(priceRange);//保留个区间
                                }
                            }
                        }
                        if (priceRange.getEndTime() == null) {//结束日期是空
                            if ("3".equals(priceRange.getPriceRangeStatus()) && "0".equals(priceRange.getStatus())) {//正在使用
                                if (DateUtil.compareDate(impl, payTime)) {// impl<=当前时间
                                    priceRanges1.add(priceRange);//保留这个区间
                                }
                            }
                        }
                    } catch (Exception e) {
                        return;
                    }
                });
            }
            //判断价格属于哪个区间
            if (CollectionUtils.isNotEmpty(priceRanges1)) {
                priceRanges1.forEach(priceRange -> {
                    String[] prices = priceRange.getPriceRange().split("-");
                    if (Double.parseDouble(prices[0]) <= price && price < Double.parseDouble(prices[1])) {
                        AreaAttribute areaAttribute = attributeService.findByRegionIdAndRuleIdAndType(regionId, priceRange.getPriceRangeId(), AreaAttribute.PlanType.PRICERANGE);
                        SectionRecord sectionRecord = new SectionRecord();
                        if (ObjectUtils.notEqual(areaAttribute, null)) {
                            sectionRecord.setPercentage(priceRange.getPercentage() + areaAttribute.getCommissions());
                        } else {
                            sectionRecord.setPercentage(priceRange.getPercentage());
                        }
                        sectionRecord.setOrderNo(orderNo);//订单号
                        sectionRecord.setPayTime(DateUtil.string2Date(payTime));
                        sectionRecord.setPlanId(planId);
                        sectionRecord.setPriceRangeId(priceRange.getPriceRangeId());
                        sectionRecord.setSalesmanId(userId);
                        sectionRecord.setGoodsId(goodsId);
                        sectionRecord.setSectionId(priceRange.getProductionId());
                        sectionRecord.setNum(num);
                        sectionRecord.setOrderflag(0);//出库计算
                        SectionRecord sectionRecord1 = sectionRecordService.save(sectionRecord);
//                        logService.log(null, "新增收益主方案: " + sectionRecord1, Log.EventType.SAVE);
                        logger.info(sectionRecord1);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("异常:计算失败");
        }
        return "";
    }


    /**
     * 出库计算(修改接口,传过订单详情集合.注意:优化的时候去做)
     *
     * @param orderNo:订单id/单品详情id
     * @param userId:业务员id
     * @param goodsId:产品id
     * @param orderGoodsList:单品详情
     * @param planId:方案id
     * @param regionId
     * @return
     */
    @Override
    public String compute(String orderNo, String userId, String goodsId, List<OrderGoods> orderGoodsList, Long planId, String regionId) {

        return null;
    }

    /**
     * 逻辑删除叠加方案
     *
     * @param production
     * @return
     */
    @Override
    public Production delete(Production production) {
        production.setStatus("1");//更改状态为1
        if (CollectionUtils.isNotEmpty(production.getPriceRanges())) {
            production.getPriceRanges().forEach(priceRange -> {
                priceRange.setStatus("1");//小区间方案也修改
            });
        }
        Production production1 = productionRepository.save(production);
        logService.log(null, "逻辑删除被驳回/创建中的区间方案: " + production1, Log.EventType.UPDATE);
        return production1;
    }

    /**
     * 初次添加区间方案
     *
     * @param priceRanges:区间值:["0-50","50-100"]
     * @param productType:产品类型
     * @param implementationDate:实施日期
     * @return:返回这个刚设置区间方案
     */
    @Override
    public Production addProduction(List<PriceRange> priceRanges, String productType, String implementationDate, Long planId) {
        //设置区间
        Date createTime = new Date();//初始化时间
        Date implDate = DateUtil.string2Date(implementationDate, "yyyy-MM-dd");
        //产品类的操作
        Production production = new Production();
        priceRanges.forEach(priceRange -> {
            priceRange.setImplementationDate(implDate);
            priceRange.setPriceRangeCreateDate(createTime);
        });
        production.setPlanId(planId);
        production.setImplDate(implDate);//实施日期
        production.setPriceRanges(priceRanges);//添加区间
        production.setProductionType(productType);//产品类型
        Production pc = productionRepository.save(production);
        logService.log(null, "创建区间方案: " + pc, Log.EventType.SAVE);
        return pc;

    }

    /**
     * 进入渠道审核(1--审核中),并且指定审核人
     *
     * @param id
     * @param status    状态是1
     * @param auditor
     */
    @Override
    public void toReview(Long id, String status, String auditor,String userId) {
        Production p = productionRepository.findByProductionId(id);//查出这套区间
        p.setProductStatus(status);
        p.setProductionAuditor(auditor);
        p.setUserId(userId);
        List<PriceRange> priceRanges = p.getPriceRanges();
        if (CollectionUtils.isNotEmpty(priceRanges)) {
            priceRanges.forEach(priceRange -> {
                priceRange.setPriceRangeStatus(status);
                priceRange.setPriceRangeAuditor(auditor);
                priceRange.setUserId(userId);
            });
        }
        p.setPriceRanges(priceRanges);
        productionRepository.save(p);
        logService.log(null, "区间方案进入审核状态: " + p, Log.EventType.UPDATE);

    }

    /**
     * 查询没有过期的数据:
     *
     * @param type:手机类型
     * @return
     */
    @Override
    public List<Production> findNotExpired(Long planId, String type) {
        String today = DateUtil.date2String(new Date());
        List<Production> ps = productionRepository.findStatus(planId, type, today);
        return ps;
    }

    @Override
    public List<Production> findNotExpiredQd(Long planId, String type) {
        String today = DateUtil.date2String(new Date());
        List<Production> ps = productionRepository.findStatus2Qd(planId, type, today);
        return ps;
    }


    /**
     * 查询已经过期的数据
     *
     * @param type
     * @param pageable
     * @return
     */
    @Override
    public Page<Production> findAll(Long planId, String type, Pageable pageable) {
        String date = DateUtil.currentDateToString();
        Date today = DateUtil.string2Date(date, "yyyy-MM-dd");
        Page<Production> page = productionRepository.findAll(new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                Predicate predicate = cb.lessThan(root.get("endTime").as(Date.class), today);
                Predicate predicate1 = cb.equal(root.get("productionType").as(String.class), type);
                Predicate predicate2 = cb.isNotNull(root.get("endTime").as(Date.class));
                Predicate predicate3 = cb.equal(root.get("productStatus").as(String.class), "3");
                Predicate predicate4 = cb.equal(root.get("status").as(String.class), "0");
                Predicate predicate5 = cb.equal(root.get("planId").as(Long.class), planId);

                Predicate p = cb.and(predicate2, predicate, predicate1, predicate3, predicate4, predicate5);
                return p;
            }
        }, pageable);
        return page;
    }


    /**
     * 审核区间是否通过
     * 判断status是"3"---通过:给上一套添加结束日期
     * 是"2"---驳回:上一套不做任何操作
     *
     * @param id
     * @param status
     */
    @Override
    public Production review(Long id, String status) {
        Production p = productionRepository.findByProductionId(id);//查出这套区间
        //TODO 若是下一次添加的需要改变上一套方案的结束时间
        if ("3".equals(status)) {//审核通过
            Production pc = productionRepository.findUseForEndTimeIsNull(p.getPlanId(), p.getProductionType());//结束时间没有的方案
            if (ObjectUtils.notEqual(null, pc)) {
                Date endTime = DateUtil.moveDate(p.getImplDate(), -1);//结束时间减去一天
                pc.setEndTime(endTime);
                List<PriceRange> priceRanges = pc.getPriceRanges();
                if (CollectionUtils.isNotEmpty(priceRanges)) {
                    priceRanges.forEach(priceRange -> {
                        if(priceRange.getEndTime() == null){
                            priceRange.setEndTime(endTime);
                        }

                    });
                }
            }
        }
        //改变这个区间方案的状态:审核通过/被驳回
        p.setProductStatus(status);
        List<PriceRange> priceRanges = p.getPriceRanges();
        if (CollectionUtils.isNotEmpty(priceRanges)) {
            priceRanges.forEach(priceRange -> {
                priceRange.setPriceRangeStatus(status);
            });
        }
        p.setPriceRanges(priceRanges);
        Production production = productionRepository.save(p);
        logService.log(null, "渠道审核区间方案", Log.EventType.UPDATE);
        return production;
    }

    /**
     * 计算正在使用的区间:用于列表显示
     *
     * @return
     */
    @Override
    public Production findNow(String type, String time, Long planId) {
        Production p = productionRepository.findNow(time, time, time, type, planId);
        return p;
    }


    /**
     * 查询正在使用的区间
     *
     * @return
     */
    @Override
    public Map<String, Object> findNowCW(String type, Long planId) {

        String time = DateUtil.date2String(new Date());//获取当前的时间

        Production p = productionRepository.findNow(time, time, time, type, planId);

        List<PriceRange> priceRanges = null;
        Long productionId = null;
        if (ObjectUtils.notEqual(p, null)) {
            priceRanges = p.getPriceRanges();
            productionId = p.getProductionId();
        }
        //判断price是属于那个区间的
        List<PriceRange> priceRanges1 = new ArrayList<PriceRange>();
        //查询出正在用的区间
        if (CollectionUtils.isNotEmpty(priceRanges)) {
            priceRanges.forEach(priceRange -> {
                String impl = DateUtil.date2String(priceRange.getImplementationDate());//实施日期
                String end = DateUtil.date2String(priceRange.getEndTime());//结束日期

                try {
                    //结束日期不是null,审核通过,并且当前时间比结束时间小
                    if (priceRange.getEndTime() != null && "3".equals(priceRange.getPriceRangeStatus()) && DateUtil.compareDate(time, end) && "0".equals(priceRange.getStatus())) {
                        priceRanges1.add(priceRange);//保留个区间
                    }
                    //结束时间是null,并且当前时间在开始时间之后
                    if (priceRange.getEndTime() == null && "3".equals(priceRange.getPriceRangeStatus()) && DateUtil.compareDate(impl, time) && "0".equals(priceRange.getStatus())) {
                        priceRanges1.add(priceRange);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
        Map<String, Object> map = new HashedMap();
        map.put("list", priceRanges1);
        map.put("productionId", productionId);
        return map;
    }

    @Override
    public Production save(Production production) {
        Production production1 = productionRepository.save(production);
        return production1;
    }

    /**
     * 查询出要审核的小区间
     *
     * @param type
     * @return
     */
    @Override
    public List<PriceRange> findReview(Long planId, String type) {
        String time = DateUtil.date2String(new Date());//获取当前的时间
        Production p = productionRepository.findNow(time, time, time, type, planId);//查询当前正在是用的
        List<PriceRange> priceRanges = null;
        if (ObjectUtils.notEqual(p, null)) {
            priceRanges = p.getPriceRanges();
        }

        List<PriceRange> list = new ArrayList<PriceRange>();

        if (CollectionUtils.isNotEmpty(priceRanges)) {
            priceRanges.forEach(priceRange -> {
                if ("1".equals(priceRange.getPriceRangeStatus())) {
                    list.add(priceRange);
                }
                if ("2".equals(priceRange.getPriceRangeStatus())) {
                    list.add(priceRange);
                }
            });
        }


        return list;
    }


    @Override
    public Production findById(Long id) {
        Production p = productionRepository.findByProductionId(id);
        return p;
    }


}
