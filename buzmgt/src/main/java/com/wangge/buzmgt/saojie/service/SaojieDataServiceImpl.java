package com.wangge.buzmgt.saojie.service;

import java.util.List;

import javax.annotation.Resource;

import java.util.ArrayList;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.saojie.repository.SaojieDataRepository;
import com.wangge.buzmgt.saojie.repository.SaojieRepository;
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
  @Resource
  private SaojieDataRepository sdr;
  @Resource
  private SaojieRepository sr;

  @Override
  public Page<SaojieData> getsaojieDataList(String userId,String regionId,int pageNum,int limit) {
    Page<SaojieData> page = sdr.findAll((root, query, cb) -> {
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
   },new PageRequest(pageNum, limit));
    return page;
  }
  
  @Override
  public SaojieDataVo getsaojieDataList(String userId,String regionId) {
    SaojieDataVo sdv = new SaojieDataVo();
    List<SaojieData> list = sdr.findAll((Specification<SaojieData>) (root, query, cb) -> {
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
   });
    sdv.getList().addAll(list);
    return sdv;
  }

  @Override
  public SaojieData findById(Long id) {
    return saojieDateRepository.findOne(id);
  }

  @Override
  public void saveSaojieData(SaojieData saojieData) {

    saojieDateRepository.save(saojieData);
  }
  @Override
   public List<SaojieData> findByregionId(String regionId) {
    return sdr.findByregionId(regionId);
  }

  @Override
  public List<SaojieData> findBySalesman(SalesMan salesMan) {
    return sdr.findBySalesman(salesMan);
  }

  @Override
  public int getCountByUserId(String userId) {
    return sdr.getCountByUserId(userId);
  }
}
