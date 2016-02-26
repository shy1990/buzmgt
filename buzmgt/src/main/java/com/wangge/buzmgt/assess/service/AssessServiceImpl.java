package com.wangge.buzmgt.assess.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.repository.AssessRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.teammember.entity.SalesMan;

/**
 * 
  * ClassName: RoleServiceImpl <br/> 
  * Function: TODO ADD FUNCTION. <br/> 
  * Reason: TODO ADD REASON(可选). <br/> 
  * date: 2016年2月19日 上午10:58:40 <br/> 
  * 
  * @author peter 
  * @version  1.1
  * @since JDK 1.8
 */
@Service
public class AssessServiceImpl implements AssessService {
  @PersistenceContext  
  private EntityManager em; 
  @Resource
  private AssessRepository assessRepository;
  @Autowired
  private RegionRepository regionRepository;
  
  @Override
  public void saveAssess(Assess assess) {
    assessRepository.save(assess);
  }

  @Override
  @Transactional
  public Page<Assess> getAssessList(Assess assess, int pageNum, String regionName) {
    String hql = "select t.* from SYS_ASSESS t left join sys_salesman s on t.user_id = s.user_id ";
    if(assess.getSalesman() != null){
      if((null!=assess.getSalesman().getJobNum()&&!"".equals(assess.getSalesman().getJobNum()))||(null!=assess.getSalesman().getTruename()&&!"".equals(assess.getSalesman().getTruename()))){
        String serHql = "where s.truename like '%"+assess.getSalesman().getTruename()+"%' or s.job_num='"+assess.getSalesman().getJobNum()+"'";
        hql += ""+serHql+" and s.region_id in"
            + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
      }else{
        hql += "where s.region_id in"
            + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";  
      }
    }else{
      hql += "where s.region_id in"
          + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";  
    }
    if(assess.getStatus() != null){
      hql += " and t.assess_status='"+assess.getStatus().ordinal()+"'";
    }
    Query q = em.createNativeQuery(hql,Assess.class);  
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* 7);
    q.setMaxResults(7);
    System.out.println(q.getResultList());
    /*List<Assess> list = new ArrayList<Assess>();
    for(Object obj: q.getResultList()){
      Assess sj = (Assess)obj;
      sj.addPercent(sj.getSaojiedata().size(), sj.getMinValue());
      Calendar cal = Calendar.getInstance();
      cal.setTime(sj.getBeginTime());
      long time1 = cal.getTimeInMillis();
      cal.setTime(sj.getExpiredTime());
      long time2 = cal.getTimeInMillis();
      long timing=(time2-time1)/(1000*3600*24);
      sj.setTiming(Integer.parseInt(String.valueOf(timing)));
      list.add(sj);
    }*/
    
    Page<Assess> page = new PageImpl<Assess>(q.getResultList(),new PageRequest(pageNum,7),count);   
    return page;  
}

  @Override
  public Assess findAssess(long id) {
    return assessRepository.findOne(id);
  }

  @Override
  public List<Assess> findBysalesman(SalesMan salesman) {
    List<Assess> result = assessRepository.findBysalesman(salesman);
    List<Assess> list = new ArrayList<Assess>();
    for(Assess obj: result){
      String regionName = "";
      String [] stringArr= obj.getAssessArea().split(",");
      for(int i=0;i<stringArr.length;i++){
        Region region = regionRepository.findById(stringArr[i]);
        if(region != null && !"".equals(region)){
          regionName += region.getName() + " ";
        }
      }
      obj.setRegionName(regionName);
      list.add(obj);
    }
    return list;
  }

  @Override
  public int gainMaxStage(String salesmanId) {
    String sql = "select max(to_number(a.assess_stage)) from SYS_ASSESS a where a.user_id='"+salesmanId+"'";
    Query query =  em.createNativeQuery(sql);
    BigDecimal str = null;
    List<BigDecimal>  resultList = query.getResultList();
    if(resultList != null && resultList.size() > 0){
        str = (BigDecimal) resultList.get(0);
    }
    int stage = 0;
    if(str != null && !"".equals(str)){
      stage = str.intValue();
    }
    return stage;
  }
	
}
