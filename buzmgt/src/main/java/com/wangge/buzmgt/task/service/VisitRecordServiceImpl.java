package com.wangge.buzmgt.task.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.wangge.buzmgt.sys.vo.VisitVo;
import com.wangge.buzmgt.task.entity.Visit;
import com.wangge.buzmgt.task.repository.VisitTaskRepository;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class VisitRecordServiceImpl implements VisitRecordService {
  @PersistenceContext
  private EntityManager em;
  @Resource
  private VisitTaskRepository vtr;
  
  @Override
  public Page<VisitVo> getVisitData(int pageNum, int limit, String regionName,String begin,String end) {
    String hql = "";
    if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
      hql = "select s.user_id,a.name area,s.truename,sr.name,max(v.finish_time),t.total,tt.overtime from sys_salesman s left join sys_visit v on s.user_id=v.user_id "+
          "left join (select v.user_id,count(v.visit_id) total from sys_visit v where v.visit_status='1' and v.finish_time between "+
          "to_date('"+begin+"','yyyy/mm/dd') and to_date('"+end+"','yyyy/mm/dd') group by v.user_id) t on v.user_id=t.user_id left join "+
          "(select v.user_id,count(v.user_id) overtime from sys_visit v where v.finish_time-v.expired_time > 0 and v.finish_time between to_date('"+begin+"','yyyy/mm/dd') "+
          "and to_date('"+end+"','yyyy/mm/dd') group by v.user_id) tt on v.user_id=tt.user_id left join sys_users_roles sur on v.user_id=sur.user_id left join sys_role sr "+ 
          "on sur.role_id=sr.role_id left join sys_region a on s.region_id=a.region_id where v.finish_time between to_date('"+begin+"','yyyy/mm/dd') and to_date('"+end+"','yyyy/mm/dd') "+ 
          "and s.region_id in (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID) "+
          "group by s.user_id,a.name,s.truename,t.total,tt.overtime,sr.name";
    }else{
      hql = "select s.user_id,a.name area,s.truename,sr.name,max(v.finish_time),t.total,tt.overtime from sys_salesman s left join sys_visit v on s.user_id=v.user_id "+
          "left join (select v.user_id,count(v.visit_id) total from sys_visit v where v.visit_status='1' group by v.user_id) t on v.user_id=t.user_id left join "+ 
          "(select v.user_id,count(v.user_id) overtime from sys_visit v where v.finish_time-v.expired_time > 0 group by v.user_id) tt on v.user_id=tt.user_id left join sys_users_roles sur on s.user_id=sur.user_id left join sys_role sr "+ 
          "on sur.role_id=sr.role_id left join sys_region a on s.region_id=a.region_id where "+
          "s.region_id in (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID) "+
          "group by s.user_id,a.name,s.truename,t.total,tt.overtime,sr.name";
    }
    Query q = em.createNativeQuery(hql);
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* limit);
    q.setMaxResults(limit);
    List<VisitVo> list = new ArrayList<VisitVo>();
    if(q.getResultList() != null && count > 0){
    Iterator it = q.getResultList().iterator();
    while(it.hasNext()){
      Object[] o = (Object[])it.next();
      VisitVo vv = new VisitVo();
      vv.setUserId(o[0]+"");
      vv.setArea(o[1]+"");
      vv.setName(o[2]+"");
      vv.setRoleName(o[3]+"");
      Date last = (Date)o[4];
      if(last != null && !"".equals(last)){
        vv.setLastVisit(DateUtil.date2String(last));
      }else{
        vv.setLastVisit("无记录");
      }
      BigDecimal vt = (BigDecimal)o[5];
      if(vt != null && !"".equals(vt)){
        vv.setVisitTimes(vt.intValue());
      }
      vt = (BigDecimal)o[6];
      if(vt != null && !"".equals(vt)){
        vv.setOverTimes(vt.intValue());
      }
      list.add(vv);
    }
  }
    Page<VisitVo> page = new PageImpl<VisitVo>(list,new PageRequest(pageNum,limit),count);
    return page;
  }

  @Override
  public int getTotalVisit(String regionName, String begin, String end) {
        String sql = "select sum(total) from (select v.user_id,count(v.visit_id) total from sys_visit v left join sys_salesman s on v.user_id=s.user_id where v.visit_status='1' and "+
            "s.region_id in (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID) ";
        if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
            sql += "and v.finish_time between to_date('"+begin+"','yyyy/mm/dd') and to_date('"+end+"','yyyy/mm/dd') ";
        }
        sql += "group by v.user_id)";
        Query query =  em.createNativeQuery(sql);
        int total = 0;
        List<BigDecimal>  resultList = query.getResultList();
        if(resultList != null && resultList.size() > 0){
            total = ((BigDecimal)resultList.get(0)).intValue();
        }
        return total;
    }
  
  @Override
  public Page<Visit> getVisitYWData(int pageNum, int limit, String userId,String begin,String end) {
    String hql = "select v.user_id,v.visit_id,r.shop_name,v.finish_time,v.address,trunc(sysdate)-trunc(max(v.finish_time)) from sys_visit v "+
        "left join sys_registdata r on v.registdata_id = r.registdata_id where v.finish_time in (select max(v.finish_time) "+ 
        "from sys_visit v where v.user_id='"+userId+"' ";
    if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
        hql += "and v.finish_time between to_date('"+begin+"','yyyy/mm/dd') and to_date('"+end+"','yyyy/mm/dd') ";
    }
    hql += ")and v.visit_status='1' group by v.user_id,v.visit_id,r.shop_name,v.finish_time,v.address";
    Query q = em.createNativeQuery(hql);
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* limit);
    q.setMaxResults(limit);
    List<Visit> list = new ArrayList<Visit>();
    if(q.getResultList() != null && count > 0){
    Iterator it = q.getResultList().iterator();
    while(it.hasNext()){
      Object[] o = (Object[])it.next();
      Visit visit = new Visit();
      visit.setId(((BigDecimal)o[1]).longValue());
      visit.setTaskName(o[2]+"");
      visit.setExpiredTime((Date)o[3]);
      visit.setAddress(o[4]+"");
      visit.setPeriod(((BigDecimal)o[5]).intValue());
      list.add(visit);
    }
  }
    Page<Visit> page = new PageImpl<Visit>(list,new PageRequest(pageNum,limit),count);
    return page;
  }

  @Override
  public Visit findById(Long visitId) {
    return vtr.findOne(visitId);
  }
  
  
}
