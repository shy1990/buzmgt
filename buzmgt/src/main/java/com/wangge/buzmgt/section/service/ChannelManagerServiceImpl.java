package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.pojo.ChannelManager;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joe on 16-11-8.
 */
@Service
public class ChannelManagerServiceImpl implements ChannelManagerService {
  @PersistenceContext
  private EntityManager entityManager;
  /**
   *查找渠道人员
   * @param name
   * @return
   */
  @Override
  public List<ChannelManager> findChannelManager(String name) {
    String sql = "select usr.USER_ID,manger.TRUENAME from sys_user usr\n" +
            "left join SYS_USERS_ROLES sur\n" +
            "on usr.USER_ID = sur.USER_ID\n" +
            "left join SYS_ROLE rol\n" +
            "on rol.ROLE_ID = sur.ROLE_ID\n" +
            "left join sys_manager manger\n" +
            "on manger.USER_ID = usr.USER_ID\n" +
            "where rol.NAME like ?\n";
    Query query = entityManager.createNativeQuery(sql);
    SQLQuery sqlQuery = query.unwrap(SQLQuery.class);
    int a =0;
    sqlQuery.setParameter(a,"渠道总监");
    List<Object[]> list = sqlQuery.list();
    List<ChannelManager> channelManagers = new ArrayList<>();
    list.forEach(object -> {
      ChannelManager channelManager = new ChannelManager();
      channelManager.setUserId((String) object[0]);
      channelManager.setName((String) object[1]);
      channelManagers.add(channelManager);
    });

    return channelManagers;
  }
}
