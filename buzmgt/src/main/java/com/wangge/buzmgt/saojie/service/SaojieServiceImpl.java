package com.wangge.buzmgt.saojie.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.salesman.entity.SalesMan;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.Saojie.SaojieStatus;
import com.wangge.buzmgt.saojie.repository.SaojieRepository;

@Service
public class SaojieServiceImpl implements SaojieService {

  @Resource
  private SaojieRepository saojieRepository;

  @Override
  public void saveSaojie(Saojie saojie) {
    saojieRepository.save(saojie);
  }

  @Override
  @Transactional
  public Page<Saojie> getSaojieList(Saojie saojie, int pageNum) {
    return saojieRepository.findAll(new Specification<Saojie>() {

      public Predicate toPredicate(Root<Saojie> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();

        if (saojie.getSalesman() != null) {
          Join<Saojie, SalesMan> salesmanJoin = root.join(root.getModel()
              .getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
          Predicate p1 = cb.like(salesmanJoin.get("truename").as(String.class),
              "%" + saojie.getSalesman().getTruename() + "%");
          Predicate p2 = cb.equal(salesmanJoin.get("jobNum").as(String.class),
              saojie.getSalesman().getJobNum());
          predicates.add(cb.or(p1, p2));
        }
        if (saojie.getStatus() != null) {
          predicates.add(cb.equal(root.get("status").as(SaojieStatus.class),
              saojie.getStatus()));
        }
        /*
         * else{
         * predicates.add(cb.equal(root.get("status").as(SaojieStatus.class),
         * SaojieStatus)); }
         */

        if (saojie.getRegion() != null) {
          Join<Saojie, Region> regionJoin = root.join(root.getModel()
              .getSingularAttribute("region", Region.class), JoinType.LEFT);
          predicates.add(cb.equal(regionJoin.get("id").as(String.class), saojie
              .getRegion().getId()));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }

    }, new PageRequest(pageNum, 2));

  }

  @Override
  public List<Saojie> findBysalesman(SalesMan salesman) {
    return saojieRepository.findBysalesmanOrderByOrderAsc(salesman);
  }

  @Override
  public Saojie findByregion(Region region) {
    return saojieRepository.findByregion(region);
  }

  @Override
  public Saojie findById(String id) {
    return saojieRepository.findOne(Long.parseLong(id));
  }

  @Override
  public Saojie changeOrder(int ordernum, String userId) {
    return saojieRepository.changeOrder(ordernum,userId);
  }
  
}
