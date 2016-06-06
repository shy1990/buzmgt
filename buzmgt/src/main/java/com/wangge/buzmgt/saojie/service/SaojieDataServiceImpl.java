package com.wangge.buzmgt.saojie.service;

import java.util.List;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.saojie.repository.SaojieDataRepository;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.saojie.repository.SaojieDataRepository;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

@Service
public class SaojieDataServiceImpl implements SaojieDataService {
  @Resource
  private SaojieDataRepository saojieDateRepository;
  @Override
  public List<SaojieData> findByReion(Region r) {
   
    return saojieDateRepository.findByRegion(r);
  }
  private SaojieDataRepository sdr;

  @Override
  public Page<SaojieData> getsaojieDataList(String userId,String regionId,int pageNum,int limit) {
    return sdr.findAll(new Specification<SaojieData>() {
      public Predicate toPredicate(Root<SaojieData> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if (userId != null && !"".equals(userId)) {
          Join<SaojieData, SalesMan> salesManJoin = root.join(root.getModel()
              .getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
          predicates.add(cb.equal(salesManJoin.get("id").as(String.class), userId));
        }

        if (regionId != null && !"".equals(regionId)) {
          Join<SaojieData, Region> regionJoin = root.join(root.getModel()
              .getSingularAttribute("region", Region.class), JoinType.LEFT);
          predicates.add(cb.equal(regionJoin.get("id").as(String.class), regionId));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }

    },new PageRequest(pageNum, limit));
  }
  
  @Override
  public SaojieDataVo getsaojieDataList(String userId,String regionId) {
    SaojieDataVo sdv = new SaojieDataVo();
    List<SaojieData> list = sdr.findAll(new Specification<SaojieData>() {
      public Predicate toPredicate(Root<SaojieData> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if (userId != null && !"".equals(userId)) {
          Join<SaojieData, SalesMan> salesManJoin = root.join(root.getModel()
              .getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
          predicates.add(cb.equal(salesManJoin.get("id").as(String.class), userId));
        }

        if (regionId != null && !"".equals(regionId)) {
          Join<SaojieData, Region> regionJoin = root.join(root.getModel()
              .getSingularAttribute("region", Region.class), JoinType.LEFT);
          predicates.add(cb.equal(regionJoin.get("id").as(String.class), regionId));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }

    });
    sdv.getList().addAll(list);
    return sdv;
  }
}
