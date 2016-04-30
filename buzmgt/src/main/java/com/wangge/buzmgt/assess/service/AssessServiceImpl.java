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
import com.wangge.buzmgt.assess.entity.Assess.AssessStatus;
import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.assess.repository.AssessRepository;
import com.wangge.buzmgt.assess.repository.RegistDataRepository;
import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.region.repository.RegionRepository;
import com.wangge.buzmgt.sys.vo.OrderVo;
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
  @Resource
  private RegistDataRepository rdr;
  
  @Override
  public void saveAssess(Assess assess) {
    assessRepository.save(assess);
  }

  @Override
  public Page<Assess> getAssessList(Assess assess, int pageNum, String regionName) {
    String hql = "select t.* from SYS_ASSESS t,(select user_id,max(to_number(assess_stage)) stage from SYS_Assess group by user_id) b left join sys_salesman s on b.user_id = s.user_id where t.user_id=b.user_id and to_number(t.assess_stage)=b.stage ";
    if(assess.getSalesman() != null){
      if((null!=assess.getSalesman().getJobNum()&&!"".equals(assess.getSalesman().getJobNum()))||(null!=assess.getSalesman().getTruename()&&!"".equals(assess.getSalesman().getTruename()))){
        String serHql = "and s.truename like '%"+assess.getSalesman().getTruename()+"%' or s.job_num='"+assess.getSalesman().getJobNum()+"'";
        hql += ""+serHql+" and s.region_id in"
            + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";
      }else{
        hql += "and s.region_id in"
            + "(SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)";  
      }
    }else{
      hql += "and s.region_id in"
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
    List<Assess> list = new ArrayList<Assess>();
    for(Object obj: q.getResultList()){
      Assess ass = (Assess)obj;
      String[] assStr=null;
      if(null !=ass.getDefineArea()){
        assStr = ass.getDefineArea().split(",");
      }
      double orderNum = 0;//提货量
      double active = 0;//活跃家数
      if("3".equals(ass.getAssessStage()) && ass.getStatus().equals(AssessStatus.AGREE)){
        String sql = "select count(o.id) from SYS_REGISTDATA t left join SYS_Assess a on t.user_id=a.user_id left join SJZAIXIAN.SJ_TB_MEMBERS m "+
            "on t.member_id=m.id left join SJZAIXIAN.SJ_TB_ORDER o on m.id=o.member_id "+
            "where t.member_id=m.id and o.pay_status='1' and o.createtime >=TRUNC(SYSDATE, 'MM') and o.createtime<=last_day(SYSDATE)";
        Query query =  em.createNativeQuery(sql);
        BigDecimal str = null;
        List<BigDecimal>  resultList = query.getResultList();
        if(resultList != null && resultList.size() > 0){
            str = (BigDecimal) resultList.get(0);
            orderNum = str.doubleValue();
        }
        String sql1 = "select count(*) from (select m.id,count(o.id) total from SYS_REGISTDATA t "+
            "left join SYS_Assess a on t.user_id=a.user_id left join SJZAIXIAN.SJ_TB_MEMBERS m "+
            "on t.member_id=m.id left join SJZAIXIAN.SJ_TB_ORDER o on m.id=o.member_id "+
            "where t.member_id=m.id and o.pay_status='1' and o.createtime >=TRUNC(SYSDATE, 'MM') and o.createtime<=last_day(SYSDATE) "+
            "Group by m.id) where total >= 2";
        query =  em.createNativeQuery(sql1);
        BigDecimal big = null;
        resultList = query.getResultList();
        if(resultList != null && resultList.size() > 0){
            big = (BigDecimal) resultList.get(0);
            active = big.doubleValue();
        }
      }else{
        if(null!=assStr){
        Assess a = assessRepository.findByStageAndSalesman("1",ass.getSalesman().getId());//看是否有第一阶段
        for(int i=0; i<assStr.length; i++){
          String sql = "select count(o.id) from SYS_REGISTDATA t left join SYS_Assess a on t.user_id=a.user_id left join SJZAIXIAN.SJ_TB_MEMBERS m "+
              "on t.member_id=m.id left join SJZAIXIAN.SJ_TB_ORDER o on m.id=o.member_id "+
              "where t.member_id=m.id and o.pay_status='1' and t.REGION_ID='"+assStr[i]+"' ";
          if(a != null && !"".equals(a)){//如果有就从第一阶段开始时间到第二阶段结束时间统计
            sql += "and to_char(o.createtime,'yyyy-mm-dd hh24:mi:ss') BETWEEN '"+a.getAssessTime()+"' AND '"+ass.getAssessEndTime()+"'";
          }else{//没有说明是第一阶段
            sql += "and to_char(o.createtime,'yyyy-mm-dd hh24:mi:ss') BETWEEN '"+ass.getAssessTime()+"' AND '"+ass.getAssessEndTime()+"'";
          }
          Query query =  em.createNativeQuery(sql);
          BigDecimal str = null;
          List<BigDecimal>  resultList = query.getResultList();
          if(resultList != null && resultList.size() > 0){
              str = (BigDecimal) resultList.get(0);
              double num = str.doubleValue();
              orderNum += num;
          }
          
          String sql1 = "select count(*) from (select m.id,count(o.id) total from SYS_REGISTDATA t "+
              "left join SYS_Assess a on t.user_id=a.user_id left join SJZAIXIAN.SJ_TB_MEMBERS m "+
              "on t.member_id=m.id left join SJZAIXIAN.SJ_TB_ORDER o on m.id=o.member_id "+
              "where t.member_id=m.id and o.pay_status='1' and t.REGION_ID='"+assStr[i]+"' ";
              if(a != null && !"".equals(a)){//如果有就从第一阶段开始时间到第二阶段结束时间统计
                sql1 += "and to_char(o.createtime,'yyyy-mm-dd hh24:mi:ss') BETWEEN '"+a.getAssessTime()+"' AND '"+ass.getAssessEndTime()+"'";
              }else{//没有说明是第一阶段
                sql1 += "and to_char(o.createtime,'yyyy-mm-dd hh24:mi:ss') BETWEEN '"+ass.getAssessTime()+"' AND '"+ass.getAssessEndTime()+"'";
              }
              sql1 += "Group by m.id) where total >= 2";
          query =  em.createNativeQuery(sql1);
          BigDecimal big = null;
          resultList = query.getResultList();
          if(resultList != null && resultList.size() > 0){
              big = (BigDecimal) resultList.get(0);
              double num = big.doubleValue();
              active += num;
          }
        }
        }
      }
      double activeNum = active / Integer.parseInt(ass.getAssessActivenum());//活跃家数占的比例
      double orderCount = orderNum / Integer.parseInt(ass.getAssessOrdernum());//提货量占的比例
      double num = activeNum + orderCount;
      ass.addPercent(num);
      Calendar cal = Calendar.getInstance();
      cal.setTime(ass.getAssessTime());
      long time1 = cal.getTimeInMillis();
      cal.setTime(ass.getAssessEndTime());
      long time2 = cal.getTimeInMillis();
      long timing=(time2-time1)/(1000*3600*24);
      ass.setTiming(Integer.parseInt(String.valueOf(timing)));
      ass.setActiveNum((int)active);
      ass.setOrderNum((int)orderNum);
      list.add(ass);
    }
    
    Page<Assess> page = new PageImpl<Assess>(list,new PageRequest(pageNum,7),count);   
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
    for(Assess ass: result){
      String[] assStr = ass.getDefineArea().split(",");
      double orderNum = 0;//提货量
      double active = 0;//活跃家数
      for(int i=0; i<assStr.length; i++){
        String sql = "select count(o.id) from SYS_REGISTDATA t left join SYS_Assess a on t.user_id=a.user_id left join SJZAIXIAN.SJ_TB_MEMBERS m "+
            "on t.member_id=m.id left join SJZAIXIAN.SJ_TB_ORDER o on m.id=o.member_id "+
            "where t.member_id=m.id and o.pay_status='1' and t.REGION_ID='"+assStr[i]+"' and to_char(o.createtime,'yyyy-mm-dd hh24:mi:ss') BETWEEN '"+ass.getAssessTime()+"' AND '"+ass.getAssessEndTime()+"'";
        Query query =  em.createNativeQuery(sql);
        BigDecimal str = null;
        List<BigDecimal>  resultList = query.getResultList();
        if(resultList != null && resultList.size() > 0){
            str = (BigDecimal) resultList.get(0);
            double num = str.doubleValue();
            orderNum += num;
        }
        
        String sql1 = "select count(*) from (select m.id,count(o.id) total from SYS_REGISTDATA t "+
            "left join SYS_Assess a on t.user_id=a.user_id left join SJZAIXIAN.SJ_TB_MEMBERS m "+
            "on t.member_id=m.id left join SJZAIXIAN.SJ_TB_ORDER o on m.id=o.member_id "+
            "where t.member_id=m.id and o.pay_status='1' and t.REGION_ID='"+assStr[i]+"' and to_char(o.createtime,'yyyy-mm-dd hh24:mi:ss') BETWEEN '"+ass.getAssessTime()+"' AND '"+ass.getAssessEndTime()+"' "+
            "Group by m.id) where total >= 2";
        query =  em.createNativeQuery(sql1);
        BigDecimal big = null;
        resultList = query.getResultList();
        if(resultList != null && resultList.size() > 0){
            big = (BigDecimal) resultList.get(0);
            double num = big.doubleValue();
            active += num;
        }
      }
      String regionName = "";
      String [] stringArr= ass.getDefineArea().split(",");
      for(int i=0;i<stringArr.length;i++){
        Region region = regionRepository.findById(stringArr[i]);
        if(region != null && !"".equals(region)){
          regionName += region.getName() + " ";
        }
      }
      ass.setRegionName(regionName);
      ass.setActiveNum((int)active);
      ass.setOrderNum((int)orderNum);
      list.add(ass);
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

  @Override
  public Page<OrderVo> getOrderStatistics(String salesmanId,String regionid,int pageNum,String begin,String end,int limit) {
    String hql = "select m.username,count(o.id),sum(oi.nums),sum(o.total_cost) from SJ_BUZMGT.SYS_REGISTDATA t "+
"left join SJ_BUZMGT.SYS_Assess a on t.user_id=a.user_id left join SJZAIXIAN.SJ_TB_MEMBERS m "+
"on t.member_id=m.id left join SJZAIXIAN.SJ_TB_ORDER o on m.id=o.member_id left join SJZAIXIAN.SJ_TB_ORDER_ITEMS oi on o.id=oi.order_id "+
"where t.member_id=m.id and oi.target_type='sku' and t.user_id='"+salesmanId+"'";
    if(regionid != null && !"".equals(regionid)){
      hql += " and t.REGION_ID='"+regionid+"'";
    }
    if(begin != null && !"".equals(begin) && end != null && !"".equals(end)){
      hql += " and to_char(o.createtime,'yyyy-mm-dd') BETWEEN '"+begin+"' AND '"+end+"'";
    }
    hql += " Group by m.username";
    
    Query q = em.createNativeQuery(hql);
    int count=q.getResultList().size();
    q.setFirstResult(pageNum* limit);
    q.setMaxResults(limit);
    List<OrderVo> list = new ArrayList<OrderVo>();
    if(q.getResultList() != null && count > 0){
    Iterator it = q.getResultList().iterator();  
    while(it.hasNext()){
      Object[] o = (Object[])it.next();
      OrderVo order = new OrderVo();
      order.setShopName(o[0]+"");
      order.setOrderTimes((BigDecimal)o[1]);
      order.setOrderNum((BigDecimal)o[2]);
      order.setOrderTotalCost((BigDecimal)o[3]);
      list.add(order);
    }
  }
    
    Page<OrderVo> page = new PageImpl<OrderVo>(list,new PageRequest(pageNum,limit),count);
    return page;
  }

  @Override
  public RegistData findRegistData(Long registId) {
    return rdr.findRegistDataById(registId);
  }

  @Override
  public Assess findByStageAndSalesman(String stage,String userId) {
    return assessRepository.findByStageAndSalesman(stage,userId);
  }
	
}
