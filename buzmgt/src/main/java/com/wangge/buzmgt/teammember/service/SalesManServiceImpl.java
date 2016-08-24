package com.wangge.buzmgt.teammember.service;

import com.wangge.buzmgt.log.entity.Log.EventType;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.repository.SaojieRepository;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesmanLevel;
import com.wangge.buzmgt.teammember.entity.SalesmanStatus;
import com.wangge.buzmgt.teammember.repository.SalesManRepository;
import com.wangge.buzmgt.teammember.repository.SalesmanLevelRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public  class SalesManServiceImpl implements SalesManService {

    @Resource
    private SalesManRepository salesManRepository;
    @Resource
    private SaojieRepository SaojieRepository;
    @Resource
    private LogService logService;
    @Resource
    private SalesmanLevelRepository salesmanLevelRepository;

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

        salesman = salesManRepository.save(salesman);
        logService.log(null, salesman, EventType.UPDATE);
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
                if( salesMan.getStatus()!= null){
                    predicates.add(cb.equal(root.get("salesmanStatus").as(SalesmanStatus.class), salesMan.getStatus()));
                }else{
                    predicates.add(cb.equal(root.get("salesmanStatus").as(SalesmanStatus.class), SalesmanStatus.saojie));
                }

                if(salesMan.getRegion() != null){
                    Join<SalesMan, Region> regionJoin =   root.join(root.getModel().getSingularAttribute("region",Region.class) , JoinType.LEFT);
                    predicates.add(cb.equal(regionJoin.get("id").as(String.class), salesMan.getRegion().getId()));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

        }, new PageRequest(pageNum, 20,new Sort(Sort.Direction.DESC)));

    }

    public Page<SalesMan> getSalesmanList(SalesMan salesMan,String salesmanStatus, int pageNum, String regionName,String stage){
        String whereql = stage!=null && !"".equals(stage.trim()) ?  stage : "1 = 1" ;
        String hql = "select t.* from SYS_SALESMAN t where  "+whereql+" and  t.region_id in "
                + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";

        if(null!=salesmanStatus && !"全部".equals(salesmanStatus)){
            hql+= " and t.status='"+salesMan.getStatus().ordinal()+"'";
        }
        Query q = em.createNativeQuery(hql,SalesMan.class);
        if(salesMan.getTruename()!= null && !"".equals(salesMan.getTruename())){
            q.setParameter(1, "%"+salesMan.getTruename()+"%");
        }
        if(salesMan.getJobNum() != null && !"".equals(salesMan.getJobNum())){
            q.setParameter(2, "%"+salesMan.getJobNum()+"%");
        }
        int count=q.getResultList().size();
        q.setFirstResult(pageNum* 20);
        q.setMaxResults(20);
        Page<SalesMan> page = new PageImpl<SalesMan>(q.getResultList(),new PageRequest(pageNum,20),count);
        return page;
    }


    @Override
    public SalesMan getSalesmanByUserId(String userId) {

        return salesManRepository.findById(userId);
    }

    //获取添加扫街业务
    public List<Object> gainSaojieMan(SalesmanStatus status) {
        return salesManRepository.getSaojieMan(status);
    }

    public SalesMan findById(String id) {
        return salesManRepository.findById(id);
    }

    @Override
    public SalesMan findByUserId(String userId) {
        return salesManRepository.findById(userId);
    }

    @Override
    public String getRegionIdByUserId(String userId) {
        return salesManRepository.getRegionIdByUserId(userId);
    }

    @Override
    public List<String> findByTruename(String truename) {
      return salesManRepository.getIdByTurename(truename);
    }

    @Override
    public SalesMan findSaleamanByRegionId(String regionId) {
        return salesManRepository.findSaleamanByRegionId(regionId);
    }


    @Override
    public Set<SalesMan> findForTargetByReginId(String regionId) {
        return salesManRepository.findForTargetByReginId(regionId);
    }

  /*@Override
  public int deleteRegionById(String id) {
    return salesManRepository.deleteRegionById(id);
  }*/

    @Override
    public SalesMan findByRegionAndisPrimaryAccount(Region region) {
        if (!ObjectUtils.isEmpty(region)){
            return salesManRepository.findByRegionAndIsPrimaryAccount(region,1);
        }else{
            return null;
        }
    }

    @Override
    public SalesmanLevel addSalesmanLevel(SalesmanLevel salesmanLevels) {
       return salesmanLevelRepository.save(salesmanLevels);
    }
}
