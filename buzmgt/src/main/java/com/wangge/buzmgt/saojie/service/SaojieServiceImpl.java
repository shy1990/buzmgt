package com.wangge.buzmgt.saojie.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.Saojie.SaojieStatus;
import com.wangge.buzmgt.saojie.repository.SaojieRepository;
import com.wangge.buzmgt.teammember.entity.SalesMan;

@Service
public class SaojieServiceImpl implements SaojieService {
  @PersistenceContext  
  private EntityManager em; 
  @Resource
  private SaojieRepository saojieRepository;
  
  @Override
  public Saojie saveSaojie(Saojie saojie) {
    return saojieRepository.save(saojie);
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
  public Page<Saojie> getSaojieList(Saojie saojie, int pageNum, int size ,String regionName) {
    String hql = " select sj.* from sys_saojie sj  left join sys_salesman s on s.user_id = sj.user_id  where ";
    if(saojie.getStatus() != null){
      int status = saojie.getStatus().ordinal();
      if(SaojieStatus.PENDING.equals(saojie.getStatus())){
        hql += " sj.saojie_status='"+status+"' and";
      }else if(SaojieStatus.AGREE.equals(saojie.getStatus())){
        hql += " sj.finish_status='1' and";//扫街全部完成的
      }
    }else{
      hql += " (sj.finish_status='1' or sj.saojie_status='1') and";
    }
   // hql +=" group by sj.saojie_id)) saojie right JOIN sys_salesman s ON s.user_id = saojie.user_id where ";
    if(saojie.getSalesman() != null){
      if((null!=saojie.getSalesman().getJobNum()&&!"".equals(saojie.getSalesman().getJobNum()))||(null!=saojie.getSalesman().getTruename()&&!"".equals(saojie.getSalesman().getTruename()))){
        String serHql = " (s.truename like '%"+saojie.getSalesman().getTruename()+"%' or s.job_num='"+saojie.getSalesman().getJobNum()+"')";
        hql += ""+serHql+" and s.region_id in"
            + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
      }else{
        hql += " s.region_id in"
            + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";  
      }
    }else{
      hql += " s.region_id in"
          + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";  
    }
    Query q = em.createNativeQuery(hql,Saojie.class);  
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* size);
    q.setMaxResults(size);
    System.out.println(q.getResultList());
    List<Saojie> list = new ArrayList<Saojie>();
    for(Object obj: q.getResultList()){
      Saojie sj = (Saojie)obj;
      if(sj != null){
        sj.addPercent(sj.getSaojiedata().size(), sj.getMinValue());
        Calendar cal = Calendar.getInstance();
        cal.setTime(sj.getBeginTime());
        long time1 = cal.getTimeInMillis();
        cal.setTime(sj.getExpiredTime());
        long time2 = cal.getTimeInMillis();
        long timing=(time2-time1)/(1000*3600*24);
        sj.setTiming(Integer.parseInt(String.valueOf(timing)));
        list.add(sj);
      }
      
    }
    
    Page<Saojie> page = new PageImpl<Saojie>(list,new PageRequest(pageNum,size),count);
    return page;  
}
  
  
  @Override
  public List<Saojie> findBysalesman(SalesMan salesman) {
    
    List<Saojie> listSaojie=new ArrayList<Saojie>();
    for(Saojie saojie:saojieRepository.findBysalesmanOrderByOrderAsc(salesman)){
      saojie.addPercent(saojie.getSaojiedata().size(), saojie.getMinValue());
      listSaojie.add(saojie);
   }
    return listSaojie;
    
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
  public int getOrderNumById(String id) {
    int order = saojieRepository.getOrderNumById(id);
    return order;
  }

 /* @Override
  public List<SaojieData> getsaojieDataList(String userId) {
   Saojie s = saojieRepository.findBySalesmanUserId(userId);
    return null;
  }*/
  
  @Override
  public List<Region> findRegionById(String id){
    return saojieRepository.findRegionById(id);
  }

  @Override
  public Saojie findByOrderAndSalesman(int ordernum, SalesMan salesman) {
    return saojieRepository.findByOrderAndSalesman(ordernum,salesman);
  }

  @Override
  public List<Saojie> findSaojie(SaojieStatus status, String userId) {
    return saojieRepository.findSaojie(status, userId);
  }
  @Override
  public Saojie findByOrderAndUserId(int order, String userId) {
    return saojieRepository.findByOrderAndUserId(order,userId);
  }

  @Override
  public Saojie findByregionId(String regionId) {
    return saojieRepository.findByregionId(regionId);
  }
  
}
  
  
