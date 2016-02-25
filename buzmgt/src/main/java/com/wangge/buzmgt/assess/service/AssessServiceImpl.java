package com.wangge.buzmgt.assess.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.assess.entity.Assess;
import com.wangge.buzmgt.assess.repository.AssessRepository;
import com.wangge.buzmgt.saojie.entity.Saojie;

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
	
}
