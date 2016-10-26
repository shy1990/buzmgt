package com.wangge.buzmgt.superposition.service;

import com.wangge.buzmgt.achieveaward.entity.Award;
import com.wangge.buzmgt.achieveaward.entity.AwardGood;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.superposition.entity.GoodsOrder;
import com.wangge.buzmgt.superposition.repository.GoodsOrderRepository;
import com.wangge.buzmgt.util.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by joe on 16-9-12.
 */
@Service
public class GoodsOrderServiceImpl implements GoodsOrderService {

    @Autowired
    private GoodsOrderRepository goodsOrderRepository;



    @Override
    public Page<GoodsOrder> findAll(Pageable pageable) {
        Page<GoodsOrder> page = goodsOrderRepository.findAll(new Specification<GoodsOrder>() {
            @Override
            public Predicate toPredicate(Root<GoodsOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return null;
            }
        },pageable);
        return page;
    }

    @Override
    public Integer countNums(String startTime, String endTime, String regionId) {

        Integer o = goodsOrderRepository.countNums(startTime,endTime,regionId);

        return o;
    }

    @Override
    public Page<GoodsOrder> findAll(HttpServletRequest request, Region region, Award award, Pageable pageable) {
        Sort s = new Sort(Sort.Direction.DESC, "payTime");
        pageable = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),s);
        Page<GoodsOrder> page = goodsOrderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = getPredicate(root,cb,request,region,award);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, pageable);
        return page;
    }

    @Override
    public List<GoodsOrder> findAll(HttpServletRequest request, Region region, Award award) {
        Sort s = new Sort(Sort.Direction.DESC, "payTime");
        List<GoodsOrder> list = goodsOrderRepository.findAll((root, query, cb) -> {
            List<Predicate> predicates = getPredicate(root,cb,request,region,award);
            return cb.and(predicates.toArray(new Predicate[predicates.size()]));
        }, s);
        return list;
    }

    /**
     * 获取Predicate条件
     * @param root
     * @param cb
     * @param request
     * @param region
     * @return
     */
    public List<Predicate> getPredicate(Root<GoodsOrder> root, CriteriaBuilder cb,HttpServletRequest request, Region region, Award award){
        Date startDate = DateUtil.string2Date(request.getParameter("startDate"));
        Date endDate = DateUtil.string2Date(request.getParameter("endDate"));
        List<Predicate> predicates = new ArrayList<Predicate>();
        Predicate predicate = cb.between(root.get("payTime").as(Date.class), startDate,endDate);
        Predicate predicate1;
        if ("镇".equals(region.getType().getName())){
            predicate1 = cb.equal(root.get("businessRegionId").as(String.class), region.getId());
        }else {
            predicate1 = cb.equal(root.get("regionId").as(String.class), region.getId());
        }
        Predicate predicate2;
        if (ObjectUtils.notEqual(award,null)){
            List<AwardGood> awardGoods = award.getAwardGoods();
            List<String> goodIds = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(awardGoods)){
                awardGoods.forEach(awardGood -> {
                    goodIds.add(awardGood.getGoodId());
                });
            }
            predicate2 = root.get("goodsId").in(goodIds);
        }else {
            String goodId = request.getParameter("goodId");
            predicate2 = cb.equal(root.get("goodsId").as(String.class),goodId);
        }
        predicates.add(cb.and(predicate, predicate1,predicate2));
        String terms = request.getParameter("terms");
        if (StringUtils.isNotBlank(terms)){
            Predicate predicate3 = cb.equal(root.get("orderNum").as(String.class),terms);
            Predicate predicate4 = cb.like(root.get("shopName").as(String.class),"%" + terms + "%");
            predicates.add(cb.or(predicate3, predicate4));
        }
        return predicates;
    }
}
