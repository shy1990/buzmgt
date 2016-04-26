package com.wangge.buzmgt.sys.service;

import java.util.List;

import com.wangge.buzmgt.sys.entity.ChildAccount;


public interface ChildAccountService {
    
   void save(ChildAccount childAccount);
   
   int findChildCount(String id);
   
   List<ChildAccount> findChildCountByParentId(String id);
   
   ChildAccount findbyUserId(String id);
   
   void delete(ChildAccount childAccount);
}
