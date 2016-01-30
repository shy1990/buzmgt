package com.wangge.buzmgt.teammember.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.repository.SaojieRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesMan.SalesmanStatus;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;

@Service
public  class SalesManServiceImpl implements SalesManService {

	@Resource
	private SalesManRepository salesManRepository;
	@Resource
	private SaojieRepository SaojieRepository;
	
	 @PersistenceContext  
	  private EntityManager em; 
	/*
	* <p>Title: addSalesman</p> 
	* <p>Description: 添加一条业务员数据</p> 
	* @param salesman 
	* @see com.wangge.buzmgt.salesman.service.salesManService#addSalesman(com.wangge.buzmgt.salesman.entity.salesMan)
	 */
	@Override
	public void addSalesman(SalesMan salesman) {
		
		salesManRepository.save(salesman);
	}
	
  /*
  * <p>Title: findByReginId</p> 
  * <p>Description:根据id查询区域 </p> 
  * @param regionId
  * @return 
  * @see com.wangge.buzmgt.salesman.service.salesManService#findByReginId(java.lang.String)
   */
	@Override
	@Transactional
	public List<User> findByReginId(String regionId) {
		
		return salesManRepository.findByRegionId(regionId);
	}

  /*
  * <p>Title: getSalesmanList</p> 
  * <p>Description: 获取业务员列表</p> 
  * @return 
  * @see com.wangge.buzmgt.salesman.service.salesManService#getSalesmanList()
   */
  @Override
  @Transactional
  public Page<SalesMan> getSalesmanList(SalesMan salesMan, int pageNum) {
    
         // salesManRepository.findAll(new PageRequest(0, 20,new Sort(Sort.Direction.DESC)));
    return   salesManRepository.findAll(new Specification<SalesMan> () {  
      
      public Predicate toPredicate(Root<SalesMan> root,  
        CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        /*************第一种写法*************/
//   Path<String> namePath = root.get("name");  
//   Path<String> truenamePath = root.get("truename");
//   Path<Enum<SalesmanStatus>> statusPath = root.get("salesmanStatus");
//   Path<Region> regionPath = root.get("region");  
//   query.where(/*cb.like(namePath, "%"+salesMan.getTruename()+"%"),*/ cb.like(truenamePath, "%"+salesMan.getTruename()+"%"), cb.equal(statusPath,salesMan.getSalesmanStatus()), cb.equal(regionPath, salesMan.getRegion())); //这里可以设置任意条查询条件  
        /*************第二种写法*************/
       
       if((salesMan.getTruename() != null && salesMan.getTruename().length() > 0) || (salesMan.getJobNum() != null && salesMan.getJobNum().length() > 0)){
         Predicate p1=cb.like(root.get("truename").as(String.class), "%"+salesMan.getTruename()+"%");
         Predicate p2=cb.equal(root.get("jobNum").as(String.class), salesMan.getJobNum());        
         predicates.add(cb.or(p1,p2));
       }
       if( salesMan.getSalesmanStatus() != null){
         predicates.add(cb.equal(root.get("salesmanStatus").as(SalesmanStatus.class), salesMan.getSalesmanStatus()));
         }else{
           predicates.add(cb.equal(root.get("salesmanStatus").as(SalesmanStatus.class), SalesmanStatus.SAOJIE));
         }
       
        if(salesMan.getRegion() != null){
          Join<SalesMan, Region> regionJoin =   root.join(root.getModel().getSingularAttribute("region",Region.class) , JoinType.LEFT);
          predicates.add(cb.equal(regionJoin.get("id").as(String.class), salesMan.getRegion().getId()));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }  
        
     }, new PageRequest(pageNum, 10,new Sort(Sort.Direction.DESC)));
  
  }
  
  public Page<SalesMan> getSalesmanList(SalesMan salesMan, int pageNum, String regionName,String where){
   // String and = "";
    String whereql = where!=null && !"".equals(where.trim()) ? " and "+ where : "" ;
    String hql = "select t.* from SYS_SALESMAN t where  t.salesman_status = '"+salesMan.getSalesmanStatus().ordinal()+"' "+whereql+" and  t.region_id in "
        + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";  
    Query q = em.createNativeQuery(hql,SalesMan.class); 
    if(salesMan.getTruename()!= null && !"".equals(salesMan.getTruename())){
      q.setParameter(1, "%"+salesMan.getTruename()+"%");
    }
    if(salesMan.getJobNum() != null && !"".equals(salesMan.getJobNum())){
      q.setParameter(2, "%"+salesMan.getJobNum()+"%");
    }
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* 7);
    q.setMaxResults(7);
    Page<SalesMan> page = new PageImpl<SalesMan>(q.getResultList(),new PageRequest(pageNum,7),count);   
    return page;  
  }
  

  @Override
  public SalesMan getSalesmanByUserId(String userId) {
       
    return salesManRepository.findById(userId);
  }
  
  public List<SalesMan> gainSaojieMan() {
    return salesManRepository.gainSaojieMan();
  }

  public SalesMan findById(String id) {
    return salesManRepository.findById(id);
  }
  
  @Override
  public SalesMan findByUserId(String userId) {
    return salesManRepository.findById(userId);
  }


  
}
