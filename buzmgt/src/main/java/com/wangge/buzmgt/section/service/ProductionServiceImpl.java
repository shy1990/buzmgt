package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.entity.PriceRange;
import com.wangge.buzmgt.section.entity.Production;
import com.wangge.buzmgt.section.repository.ProductionRepository;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductionRepository productionRepository;

    /**
     * 初次添加区间方案
     *
     * @param priceRanges:区间值:["0-50","50-100"]
     * @param productType:产品类型
     * @param implementationDate:实施日期
     * @return:返回这个刚设置区间方案
     */
    @Override
    public Production addProduction(List<PriceRange> priceRanges, String productType, String implementationDate, String status) {
        //设置区间
        Date createTime = new Date();//初始化时间
        Date implDate = DateUtil.string2Date(implementationDate, "yyyy-MM-dd");
        //产品类的操作
        Production production = new Production();
        priceRanges.forEach(priceRange -> {
            priceRange.setImplementationDate(implDate);
            priceRange.setPriceRangeCreateDate(createTime);
        });
        production.setProductStatus(status);//状态创建中
        production.setCreateTime(createTime);//产品设置时间
        production.setImplDate(implDate);//实施日期
        production.setPriceRanges(priceRanges);//添加区间
        production.setProductionType(productType);//产品类型
        Production pc = productionRepository.save(production);
        return pc;

    }

    /**
     * 进入渠道审核(1--审核中),并且指定审核人
     *
     * @param id
     * @param status    状态是1
     * @param auditorId
     */
    @Override
    public void toReview(Long id, String status, String auditorId) {
        Production p = productionRepository.findByProductionId(id);//查出这套区间
        p.setProductStatus(status);
        p.setProductionAuditor(auditorId);
        List<PriceRange> priceRanges = p.getPriceRanges();
        if (CollectionUtils.isNotEmpty(priceRanges)) {
            priceRanges.forEach(priceRange -> {
                priceRange.setPriceRangeStatus(status);
                priceRange.setPriceRangeAuditor(auditorId);
            });
        }
        p.setPriceRanges(priceRanges);
        productionRepository.save(p);


    }

    /**
     * 查询没有过期的数据:
     *
     * @param type:手机类型
     * @return
     */
    @Override
    public List<Production> findNotExpired(String type) {
        String today = DateUtil.date2String(new Date());
//        List<Production> ps = productionRepository.findNotExpired(today, type);

        List<Production> ps = productionRepository.findStatus(type,today);
        return ps;
    }

    /**
     * 查询过期的
     *
     * @param type
     * @param status
     * @return
     */
    @Override
    public Page<Production> findExpired(String type, String status, Integer page, Integer size) {

        String sql = "select * from SYS_PRODUCTION where PRODUCT_STATUS ='3' and END_TIME < to_date (?,'yyyy-MM-dd') and PRODUCTION_TYPE = ?";

        Query query = entityManager.createNativeQuery(sql);

        SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
        int a = 0;
        sqlQuery.setParameter(a, "2016-10-25");

        int b = 1;
        sqlQuery.setParameter(b, type);


        int count = sqlQuery.list().size();//分页查询出总条数(不是分页之后的)
        sqlQuery.setFirstResult(page * size);//设置开始位置
        sqlQuery.setMaxResults(size);//每页显示条数

        List<Production> ps = new ArrayList<Production>();

        List<Object[]> rst = sqlQuery.list();
        if (CollectionUtils.isNotEmpty(rst)) {
            rst.forEach(p -> {
                List<PriceRange> pp = new ArrayList<PriceRange>();
                PriceRange priceRange = new PriceRange();
                priceRange.setStatus("5645454");
                pp.add(priceRange);
                Production pr = new Production();
                pr.setProductionId(((BigDecimal) p[0]).longValue());
                pr.setCreateTime((Date) p[1]);
                pr.setEndTime((Date) p[2]);
                pr.setImplDate((Date) p[3]);
                pr.setProductStatus((String) p[4]);
                pr.setProductionAuditor((String) p[5]);
                pr.setProductionType((String) p[6]);
                pr.setStatus((String) p[7]);
                pr.setPriceRanges(pp);
                ps.add(pr);
            });
        }
        Page pageRequest = new PageImpl<Production>(ps, new PageRequest(page, size), count);
        return pageRequest;
    }

    /**
     * 查询已经过期的数据
     *
     * @param type
     * @param pageable
     * @return
     */
    @Override
    public Page<Production> findAll(String type, Pageable pageable) {
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

                Predicate p = cb.and(predicate2, predicate, predicate1, predicate3, predicate4);
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
    public void review(Long id, String status) {
        Production p = productionRepository.findByProductionId(id);//查出这套区间

        //TODO 若是下一次添加的需要改变上一套方案的结束时间
        if ("3".equals(status)) {//审核通过
            Production pc = productionRepository.findByEndTimeIsNullAndProductStatusAndProductionType("3",p.getProductionType());
            if (ObjectUtils.notEqual(null, pc)) {
                Date endTime = DateUtil.moveDate(p.getImplDate(), -1);//结束时间减去一天
                pc.setEndTime(endTime);
                List<PriceRange> priceRanges = pc.getPriceRanges();
                if (CollectionUtils.isNotEmpty(priceRanges)) {
                    priceRanges.forEach(priceRange -> {
                        if (priceRange.getEndTime() == null) {
                            priceRange.setEndTime(endTime);
                        }
                    });
                }
                productionRepository.save(pc);
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
        productionRepository.save(p);
    }


    /**
     * 查询没有结束时间的
     * @param productStatus
     * @return
     */
    @Override
    public Production findByEndTimeIsNullAndProductStatus(String productStatus,String type) {

        Production p = productionRepository.findByEndTimeIsNullAndProductStatusAndProductionType(productStatus,type);
        return p;
    }


    /**
     * 计算正在使用的区间:用于列表显示
     *
     * @return
     */
    @Override
    public Production findNow(String type) {

        String time = DateUtil.date2String(new Date());//获取当前的时间

        Production p = productionRepository.findNow(time, time, time, type);

        List<PriceRange> prs = null;
        if (ObjectUtils.notEqual(p, null)) {
            prs = p.getPriceRanges();
        }

        logger.info(prs);

        return p;
    }

    /**
     * 计算正在使用的区间:用于列表显示
     *
     * @return
     */
    @Override
    public Production findNow(String type,String time) {
        Production p = productionRepository.findNow(time, time, time, type);
        List<PriceRange> prs = null;
        if (ObjectUtils.notEqual(p, null)) {
            prs = p.getPriceRanges();
        }

        logger.info(prs);

        return p;
    }





    /**
     * 查询正在使用的区间
     *
     * @return
     */
    @Override
    public Map<String, Object> findNowCW(String type) {

        String time = DateUtil.date2String(new Date());//获取当前的时间

        Production p = productionRepository.findNow(time, time, time, type);

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
                    if (priceRange.getEndTime() != null && "3".equals(priceRange.getPriceRangeStatus()) && !DateUtil.compareDate(end, time)) {
                        priceRanges1.add(priceRange);//保留个区间
                    }

                    //结束时间是null,并且当前时间在开始时间之后
                    if (priceRange.getEndTime() == null && "3".equals(priceRange.getPriceRangeStatus()) && DateUtil.compareDate(impl, time)) {
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
    public List<PriceRange> findReview(String type) {
        String time = DateUtil.date2String(new Date());//获取当前的时间

        Production p = productionRepository.findNow(time, time, time, type);//查询当前正在是用的
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
