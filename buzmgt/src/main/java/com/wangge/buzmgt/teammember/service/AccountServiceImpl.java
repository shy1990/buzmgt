package com.wangge.buzmgt.teammember.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.wangge.buzmgt.teammember.vo.AccountBean;

@Repository
public class AccountServiceImpl implements AccountService {

  private static final Logger LOG = Logger.getLogger(AccountServiceImpl.class);
  
  @PersistenceContext  
  private EntityManager em; 
  
  @Override
  public List<AccountBean> selectAccountByPositionAndStatus(String orgName,String status,String regionName,PageRequest page) {
//    String sql = "SELECT o.NAME as oname,u.USER_ID,m.TRUENAME,re.NAME as aname,sr.NAME as rname,u.STATUS FROM    SYS_USER u JOIN SYS_SALESMAN m on u.USER_ID = m.USER_ID JOIN SYS_USERS_ROLES r  ON m.USER_ID= r.USER_ID JOIN SYS_ROLE sr ON r.ROLE_ID = sr.ROLE_ID "+
//                  "JOIN SYS_ORGANIZATION o ON u.ORGANIZATION_ID = o.ORGANIZATION_ID  JOIN SYS_REGION re   ON m.REGION_ID = re.REGION_ID  where 1=1 ";
    StringBuffer sb = new StringBuffer();
    sb.append( "select v.oname, v.usedId, v.truename,v.aname,v.rname,v.status,v.rid from (");
     sb.append(" SELECT o.NAME as oname,u.USER_ID  usedId  ,m.TRUENAME   truename,re.NAME as aname,sr.NAME as rname,u.STATUS status,re.REGION_ID as rid FROM    SYS_USER u");
     sb.append(" JOIN SYS_SALESMAN m on u.USER_ID = m.USER_ID JOIN SYS_USERS_ROLES r  ON m.USER_ID= r.USER_ID JOIN SYS_ROLE sr ON r.ROLE_ID = sr.ROLE_ID");
     sb.append(" JOIN SYS_ORGANIZATION o ON u.ORGANIZATION_ID = o.ORGANIZATION_ID  JOIN SYS_REGION re   ON m.REGION_ID = re.REGION_ID    UNION ALL");
     sb.append(" SELECT o.NAME as oname,u.USER_ID  usedId  ,m.TRUENAME   truename,re.NAME as aname,sr.NAME as rname,u.STATUS status,re.REGION_ID as  rid FROM    SYS_USER u");
     sb.append(" JOIN SYS_MANAGER m on u.USER_ID = m.USER_ID JOIN SYS_USERS_ROLES r  ON m.USER_ID= r.USER_ID JOIN SYS_ROLE sr ON r.ROLE_ID = sr.ROLE_ID");
     sb.append(" JOIN SYS_ORGANIZATION o ON u.ORGANIZATION_ID = o.ORGANIZATION_ID  JOIN SYS_REGION re   ON m.REGION_ID = re.REGION_ID ) v where 1=1 and v.rid in  (SELECT region_id FROM SYS_REGION START WITH name='"+regionName+"' CONNECT BY PRIOR region_id=PARENT_ID)");
    if(orgName!=null && !"".equals(orgName)){
      if(!orgName.startsWith("al")){
        if("大区总监".equals(orgName.trim())){
         // sb.append( " and v.oname != '服务站经理' and v.truename!='root' and  v.status in ('0','1')");
          sb.append( " and v.oname = '大区总监' and v.truename!='root' and  v.status in ('0','1')");
        }else{
          sb.append( " and v.oname = '服务站经理' and  v.status in ('0','1')");
        }
       
      }else{
        if(!"used".equals(status)){
          sb.append( " and v.status='"+status+"'");
        }else{
          sb.append( " and v.status in ('0','1')");
        }
      }
    }
    Query query =  em.createNativeQuery(sb.toString());
    sb =null;
    int count = query.getResultList().size();
    query.setFirstResult(page.getPageNumber() * page.getPageSize());
    query.setMaxResults(page.getPageSize());
    List obj = query.getResultList();
    List<AccountBean> accList = new ArrayList<AccountBean>();
    if(obj!=null && obj.size()>0){
      Iterator it = obj.iterator();  
      while(it.hasNext()){
        Object[] o = (Object[])it.next(); 
        AccountBean ac = new AccountBean();
        ac.setAccountNum(o[1]+"");
        ac.setAreaName(o[3]+"");
        ac.setName(o[2]+"");
        ac.setPosition(o[0]+"");
        ac.setRoleName(o[4]+"");
        ac.setStatus(o[5]+"");
        ac.setTotalNum(count);
        accList.add(ac);
      }
    }
      return accList;
  }

  @Override
  @Transactional
  public boolean mofidyPwd(String id) {
    String sql = "update SYS_USER u set u.password='1234567' where u.user_id='"+id+"' ";
    Query query =  em.createNativeQuery(sql);
    return query.executeUpdate()>0?true:false;
  }

}
