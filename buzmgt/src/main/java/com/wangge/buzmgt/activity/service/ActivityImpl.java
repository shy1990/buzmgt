package com.wangge.buzmgt.activity.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
@Repository
public class ActivityImpl implements ActivityDao{
  
  @PersistenceContext  
  private EntityManager em; 
  @Override
  public String selSalesmanByRegion(String regionId) {
    
    String[] sl = null;
    if(regionId!=null && regionId.contains(" ")){
      sl = regionId.split(" ");
    }else{
      sl[0] = regionId;
    }
    StringBuffer sb = new StringBuffer();
    for(String s:sl){
      String sql = "select PHONE from SJ_DB.SYS_USER WHERE USER_ID in(select USER_ID from SJ_YEWU.BIZ_SALESMAN where REGION_ID in(select REGION_ID from SJ_DB.sys_region WHERE REGION_ID='"+s+"' OR PARENT_ID='"+s+"'))";
      Query query =  em.createNativeQuery(sql);
      List obj = query.getResultList();
      if(obj!=null &&  obj.size()>0){
        Iterator it = obj.iterator(); 
        while(it.hasNext()){
          sb.append((it.next()+",").trim()); 
        }
      }
    }
    return sb == null ? null : sb.substring(0,sb.length()-1);
  }

}
