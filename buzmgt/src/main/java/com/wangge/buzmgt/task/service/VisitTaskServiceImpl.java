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

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wangge.buzmgt.sys.vo.CustomerVo;
import com.wangge.buzmgt.task.entity.Visit;
import com.wangge.buzmgt.task.repository.VisitTaskRepository;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class VisitTaskServiceImpl implements VisitTaskService {
  @PersistenceContext
  private EntityManager em;
  @Resource
  private VisitTaskRepository vtr;
  
  @Override
  public Page<CustomerVo> getshopList(int pageNum,int limit, String regionName,int status,int condition) {
    String hql = "select p.registdata_id,s.user_id,p.username,p.days,p.COORDINATE,a.avgNum,p.total,p.address,p.lastvisit from sys_avg_ordernum a right outer join ";
    if(status == 1){
      hql += "SJ_BUZMGT.sys_assesslist_checkin p ";
    }
    if(status == 2){
      hql += "SJ_BUZMGT.sys_assesslist_passed p ";
    }
    hql += "on a.id = p.id left join SJ_BUZMGT.Sys_Salesman s on p.user_id = s.user_id where s.status = '"+status+"' ";
    if(condition == 2){
      hql += "and p.total >= "+condition+"";
    }
    if(condition == 1 || condition == 0){
      hql += "and p.total = "+condition+"";
    }
    if(regionName != null && !"".equals(regionName)){
      hql += " and s.region_id in (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
    }
    
    Query q = em.createNativeQuery(hql);
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* limit);
    q.setMaxResults(limit);
    List<CustomerVo> list = new ArrayList<CustomerVo>();
    List<Object> ls = q.getResultList();
    if(ls != null){
      for(int i = 0;i < ls.size();i++){
        Object[] o = (Object[])ls.get(i);
        CustomerVo cus = new CustomerVo();
        cus.setRegistId(((BigDecimal)o[0]).intValue());
        cus.setUserId(o[1]+"");
        cus.setShopName(o[2]+"");
        BigDecimal p = (BigDecimal)o[3];
        if(p != null && !"".equals(p)){
          cus.setPeriod(p.toString());
        }else{
          cus.setPeriod("无记录");
        }
        cus.setCoordinate(o[4]+"");
        p = (BigDecimal)o[5];
        if(p != null && !"".equals(p)){
          cus.setAvgOrderNum((p).intValue());
        }else{
          cus.setAvgOrderNum(0);
        }
        cus.setOrderTimes(((BigDecimal)o[6]));
        cus.setAddress(o[7]+"");
        Date date = (Date)o[8];
        if(date != null && !"".equals(date)){
          String d = DateUtil.date2String(date);
          cus.setLastVisit(d);
        }else{
          cus.setLastVisit("无记录");
        }
        list.add(cus);
      }
    }
    Page<CustomerVo> page = new PageImpl<CustomerVo>(list,new PageRequest(pageNum,limit),count);
    return page;
  }

  @Override
  public void addVisit(Visit visit) {
    vtr.save(visit);
  }

  @Override
  public String findMaxLastVisit(Long registId) {
      String sql = "select t.visit_status from (select * from sys_visit v where v.registdata_id='"+registId+"' and v.visit_status='0' order by v.expired_time desc)t where rownum =1";
      Query query =  em.createNativeQuery(sql);
      String str = null;
      List<Character>  resultList = query.getResultList();
      if(resultList != null && resultList.size() > 0){
          str = ((Character)resultList.get(0)).toString();
      }
      return str;
  }
  
  @Override
  public Page<Visit> getVisitData(int pageNum, int limit, String regionName) {
    String hql = "select v.visit_id,v.registdata_id,v.visit_status,v.begin_time,v.expired_time,v.task_name,s.truename,aa.name || bb.name || cc.name || dd.name as address from sys_visit v left join sys_salesman s on s.user_id=v.user_id "+
          "left join SJ_BUZMGT.SYS_REGISTDATA r on r.registdata_id=v.registdata_id left join SJZAIXIAN.SJ_TB_MEMBERS m on r.member_id=m.id "+
          "left join SJZAIXIAN.sj_tb_regions aa on m.province = aa.id "+
          "left join SJZAIXIAN.sj_tb_regions bb on m.city = bb.id "+
          "left join SJZAIXIAN.sj_tb_regions cc on m.area = cc.id "+
          "left join SJZAIXIAN.sj_tb_regions dd on m.town = dd.id  where s.region_id in "+
        "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID) order by v.visit_id";
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
      visit.setId(((BigDecimal)o[0]).longValue());
      String c = ((Character)o[2]).toString();
      visit.setVisitStatus(c);
      visit.setExpiredTime((Date)o[4]);
      visit.setTaskName(o[5]+"");
      visit.setExecutor(o[6]+"");
      visit.setShopAddress(o[7]+"");
      list.add(visit);
    }
  }
    Page<Visit> page = new PageImpl<Visit>(list,new PageRequest(pageNum,limit),count);
    return page;
  }
  
  @Override
  public List<CustomerVo> getshopMap(String regionName,int status,int condition) {
    String hql = "select t.registdata_id,s.user_id,p.username,p.days,t.COORDINATE,a.avgNum,p.total from sys_avg_ordernum a right outer join ";
    if(status == 1){
      hql += "SJ_BUZMGT.sys_assess_checkin p ";
    }
    if(status == 2){
      hql += "SJ_BUZMGT.sys_assess_passed p ";
    }
    hql += "on a.id = p.id left join SJ_BUZMGT.SYS_REGISTDATA t on t.member_id = p.id left join SJ_BUZMGT.Sys_Salesman s on t.user_id = s.user_id where s.status = '"+status+"' ";
    if(condition == 2){
      hql += "and p.total >= "+condition+"";
    }
    if(condition == 1 || condition == 0){
      hql += "and p.total = "+condition+"";
    }
    if(regionName != null && !"".equals(regionName)){
      hql += " and s.region_id in (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
    }
    
    Query q = em.createNativeQuery(hql);
    List customer = q.getResultList();
    int count=customer.size();
    System.out.println(customer);
    List<CustomerVo> list = new ArrayList<CustomerVo>();
    if(customer != null && count > 0){
      for(int i = 0;i < count;i++){
        Object[] o = (Object[])customer.get(i);
        CustomerVo cus = new CustomerVo();
        cus.setRegistId(((BigDecimal)o[0]).intValue());
        cus.setUserId(o[1]+"");
        cus.setShopName(o[2]+"");
        BigDecimal period = (BigDecimal)o[3];
        if(period != null && !"".equals(period)){
          cus.setPeriod(period.toString());
        }else{
          cus.setPeriod("");
        }
        cus.setCoordinate(o[4]+"");
        BigDecimal aon = (BigDecimal)o[5];
        if(aon != null && !"".equals(aon)){
          cus.setAvgOrderNum((aon).intValue());
        }
        cus.setOrderTimes(((BigDecimal)o[6]));
        list.add(cus);
      }
    }
    return list;
  }
}
