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

import com.wangge.buzmgt.sys.vo.CustomerVo;
import com.wangge.buzmgt.sys.vo.OrderVo;
import com.wangge.buzmgt.task.entity.Visit;
import com.wangge.buzmgt.task.entity.Visit.VisitStatus;
import com.wangge.buzmgt.task.repository.VisitTaskRepository;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.util.DateUtil;

@Service
public class VisitTaskServiceImpl implements VisitTaskService {
  @PersistenceContext
  private EntityManager em;
  @Resource
  private VisitTaskRepository vtr;
  
  @Override
  public Page<CustomerVo> getshopList(int pageNum,int limit, String regionName,int status,int condition) {
    String hql = "select m.id,m.username,aa.name || bb.name || cc.name || dd.name as address,count(o.id),trunc(sysdate)-trunc(max(o.createtime)),max(v.expired_time),t.registdata_id,s.user_id"+
    " from SJ_BUZMGT.SYS_REGISTDATA t left join SJ_BUZMGT.Sys_Salesman s on t.user_id=s.user_id left join SJ_BUZMGT.Sys_Visit v on t.registdata_id=v.registdata_id"+
    " left join SJZAIXIAN.SJ_TB_MEMBERS m on t.member_id=m.id  left join SJZAIXIAN.sj_tb_regions aa on m.province = aa.id"+
    " left join SJZAIXIAN.sj_tb_regions bb on m.city = bb.id"+
    " left join SJZAIXIAN.sj_tb_regions cc on m.area = cc.id"+
    " left join SJZAIXIAN.sj_tb_regions dd on m.town = dd.id left join ";
    if(status == 1){
      hql += "SJ_BUZMGT.sys_check ";
    }
    if(status == 2){
      hql += "SJ_BUZMGT.sys_passed ";
    }
    hql += "oo on m.id=oo.id left join SJZAIXIAN.SJ_TB_ORDER o on o.member_id=oo.id where t.member_id=m.id and s.salesman_status="+status+" ";
    if(status == 2){
      hql += "and (o.createtime >= (  select to_date(to_char(sysdate,'yyyy/mm'),'yyyy/mm') from dual)   and  o.createtime <= (  select to_date(to_char(sysdate+1,'yyyy/mm/dd'),'yyyy/mm/dd') from dual) ) ";
    }
    if(condition == 2){
      hql += "and total >= "+condition+"";
    }
    if(condition == 1 || condition == 0){
      hql += "and total = "+condition+"";
    }
    if(regionName != null && !"".equals(regionName)){
      hql += " and s.region_id in (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
    }
    hql += " Group by m.id,m.username,aa.name || bb.name || cc.name || dd.name,t.registdata_id,s.user_id";
    
    Query q = em.createNativeQuery(hql);
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* limit);
    q.setMaxResults(limit);
    System.out.println(q.getResultList());
    List<CustomerVo> list = new ArrayList<CustomerVo>();
    if(q.getResultList() != null && count > 0){
    Iterator it = q.getResultList().iterator();  
    while(it.hasNext()){
      Object[] o = (Object[])it.next();
      CustomerVo cus = new CustomerVo();
      cus.setShopName(o[1]+"");
      cus.setAddress(o[2]+"");
      cus.setOrderTimes((BigDecimal)o[3]);
      BigDecimal p = (BigDecimal)o[4];
      if(p != null && !"".equals(p)){
        cus.setPeriod(p.toString());
      }else{
        cus.setPeriod("无记录");
      }
      Date date = (Date)o[5];
      if(date != null && !"".equals(date)){
        String d = DateUtil.date2String(date);
        cus.setLastVisit(d);
      }else{
        cus.setLastVisit("无记录");
      }
      cus.setRegistId(((BigDecimal) o[6]).intValue());
      cus.setUserId(o[7]+"");
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
    String hql = "select t.registdata_id,s.user_id,m.username,count(o.id),t.COORDINATE,trunc(count(o.id)/3)"+
    " from SJ_BUZMGT.SYS_REGISTDATA t left join SJ_BUZMGT.Sys_Salesman s on t.user_id=s.user_id left join SJ_BUZMGT.Sys_Visit v on t.registdata_id=v.registdata_id"+
    " left join SJZAIXIAN.SJ_TB_MEMBERS m on t.member_id=m.id left join ";
    if(status == 1){
      hql += "SJ_BUZMGT.sys_check ";
    }
    if(status == 2){
      hql += "SJ_BUZMGT.sys_avg_passed ";
    }
    hql += "oo on m.id=oo.id left join SJZAIXIAN.SJ_TB_ORDER o on o.member_id=oo.id where t.member_id=m.id and s.salesman_status="+status+" ";
    if(status == 2){
      hql += "and (o.createtime >= ( select add_months(trunc(sysdate, 'mm' )-1,-3) from dual) and o.createtime <= ( select trunc(sysdate, 'mm' )-1 from dual )) ";
    }
    if(condition == 2){
      hql += "and total >= "+condition+"";
    }
    if(condition == 1 || condition == 0){
      hql += "and total = "+condition+"";
    }
    if(regionName != null && !"".equals(regionName)){
      hql += " and s.region_id in (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
    }
    hql += " Group by t.registdata_id,s.user_id,m.username,t.COORDINATE";
    
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
        cus.setOrderNum((BigDecimal)o[3]);
        cus.setCoordinate(o[4]+"");
        cus.setAvgOrderNum(((BigDecimal)o[5]).intValue());
        list.add(cus);
      }
    }
    return list;
  }
}
