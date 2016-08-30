package com.wangge.buzmgt.plan.server;

import java.util.List;

import com.wangge.buzmgt.plan.entity.GroupNumber;

public interface GroupNumberServer {

  List<GroupNumber> findAll();
  
  List<GroupNumber> findByGroupName(String groupName);
  
}
