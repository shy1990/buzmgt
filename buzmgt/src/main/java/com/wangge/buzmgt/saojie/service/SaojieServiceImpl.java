package com.wangge.buzmgt.saojie.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.entity.Region.RegionType;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.Saojie.SaojieStatus;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.saojie.repository.SaojieRepository;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

@Service
public class SaojieServiceImpl implements SaojieService {
  @PersistenceContext  
  private EntityManager em; 
  @Resource
  private SaojieRepository saojieRepository;
  
  @Override
  public void saveSaojie(Saojie saojie) {
    saojieRepository.save(saojie);
  }

//  @Override
//  @Transactional
//  public Page<Saojie> getSaojieList(Saojie saojie, int pageNum) {
//    return saojieRepository.findAll(new Specification<Saojie>() {
//
//      public Predicate toPredicate(Root<Saojie> root, CriteriaQuery<?> query,
//          CriteriaBuilder cb) {
//        List<Predicate> predicates = new ArrayList<Predicate>();
//
//        if (saojie.getSalesman() != null) {
//          Join<Saojie, SalesMan> salesmanJoin = root.join(root.getModel()
//              .getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
//          Predicate p1 = cb.like(salesmanJoin.get("truename").as(String.class),
//              "%" + saojie.getSalesman().getTruename() + "%");
//          Predicate p2 = cb.equal(salesmanJoin.get("jobNum").as(String.class),
//              saojie.getSalesman().getJobNum());
//          predicates.add(cb.or(p1, p2));
//        }
//        if (saojie.getStatus() != null) {
//          predicates.add(cb.equal(root.get("status").as(SaojieStatus.class),
//              saojie.getStatus()));
//        }
//        /*
//         * else{
//         * predicates.add(cb.equal(root.get("status").as(SaojieStatus.class),
//         * SaojieStatus)); }
//         */
//
//        if (saojie.getRegion() != null) {
//          Join<Saojie, Region> regionJoin = root.join(root.getModel()
//              .getSingularAttribute("region", Region.class), JoinType.LEFT);
//          predicates.add(cb.equal(regionJoin.get("id").as(String.class), saojie
//              .getRegion().getId()));
//        }
//        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
//      }
//
//    }, new PageRequest(pageNum, 7));
//
//  }
  @Override
  @Transactional
  public Page<Saojie> getSaojieList(Saojie saojie, int pageNum,String regionName) {
        String hql = "select t.* from SYS_SAOJIE t ";
        if(saojie.getSalesman() != null){
         String serHql = "left join sys_salesman s on t.user_id = s.user_id where s.truename like '%"+saojie.getSalesman().getTruename()+"%' or s.job_num='"+saojie.getSalesman().getJobNum()+"'";
         hql += ""+serHql+" and t.region_id in"
             + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
        }else{
          hql += "where t.region_id in"
              + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";  
        }
        if(saojie.getStatus() != null){
          hql += " and t.saojie_status='"+saojie.getStatus().ordinal()+"'";
        }
        System.out.println("hql:"+hql);
        Query q = em.createNativeQuery(hql,Saojie.class);  
       int count=q.getResultList().size();
        q.setFirstResult(pageNum* 7);
        q.setMaxResults(7);
        
        Page<Saojie> page = new PageImpl<Saojie>(q.getResultList(),new PageRequest(pageNum,7),count);   
        return page;  
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

  @Override
  public int getRegionCount() {
    return saojieRepository.getRegionCount();
  }
  
  @Override
  @Transactional
  public SaojieDataVo getsaojieDataList(String userId,String regionId) {
    int a = 0;
    SaojieDataVo sdv = new SaojieDataVo();
     List<Saojie> list = saojieRepository.findAll(new Specification<Saojie>() {
      public Predicate toPredicate(Root<Saojie> root, CriteriaQuery<?> query,
          CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        
        if (userId != null && !"".equals(userId)) {
          Join<Saojie, SalesMan> salesManJoin = root.join(root.getModel()
              .getSingularAttribute("salesman", SalesMan.class), JoinType.LEFT);
          predicates.add(cb.equal(salesManJoin.get("id").as(String.class), userId));
        }

        if (regionId != null && !"".equals(regionId)) {
          Join<Saojie, Region> regionJoin = root.join(root.getModel()
              .getSingularAttribute("region", Region.class), JoinType.LEFT);
          predicates.add(cb.equal(regionJoin.get("id").as(String.class), regionId));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }

    });
   for(Saojie s : list){
     if(s.getSaojiedata() != null ){
        for(SaojieData data  : s.getSaojiedata()){
          sdv.getList().add(data);
        }
     }
     a += s.getMinValue();
   }
      sdv.addPercent(sdv.getList().size(),a);
    return sdv;
   
  }

  @Override
  public int getOrderNumById(String id) {
    int order = saojieRepository.getOrderNumById(id);
    return order;
  }

 /* @Override
  public List<SaojieData> getsaojieDataList(String userId) {
   Saojie s = saojieRepository.findBySalesmanUserId(userId);
    return null;
  }*/
}
  
  
