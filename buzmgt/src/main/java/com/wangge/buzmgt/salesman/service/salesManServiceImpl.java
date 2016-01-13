package com.wangge.buzmgt.salesman.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.salesman.entity.salesMan;
import com.wangge.buzmgt.salesman.entity.salesMan.SalesmanStatus;
import com.wangge.buzmgt.salesman.repository.salesManRepository;
import com.wangge.buzmgt.sys.entity.User;

@Service
public class salesManServiceImpl implements salesManService {

	@Resource
	private salesManRepository salesManRepository;
	/*
	* <p>Title: addSalesman</p> 
	* <p>Description: 添加一条业务员数据</p> 
	* @param salesman 
	* @see com.wangge.buzmgt.salesman.service.salesManService#addSalesman(com.wangge.buzmgt.salesman.entity.salesMan)
	 */
	@Override
	public void addSalesman(salesMan salesman) {
		
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
  public Page<salesMan> getSalesmanList(salesMan salesMan, int pageNum) {
    
         // salesManRepository.findAll(new PageRequest(0, 20,new Sort(Sort.Direction.DESC)));
    return   salesManRepository.findAll(new Specification<salesMan> () {  
      
      public Predicate toPredicate(Root<salesMan> root,  
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
          Join<salesMan, Region> regionJoin =   root.join(root.getModel().getSingularAttribute("region",Region.class) , JoinType.LEFT);
          predicates.add(cb.equal(regionJoin.get("id").as(String.class), salesMan.getRegion().getId()));
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
      }  
        
     }, new PageRequest(pageNum, 1));
  
  }}
