package com.wangge.buzmgt.teammember.service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesmanLevel;
import com.wangge.buzmgt.teammember.entity.SalesmanStatus;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface SalesManService {

    void addSalesman(SalesMan salesman);

    List<User> findByReginId(String regionId);

    Page<SalesMan> getSalesmanList(SalesMan salesman,String salesmanStatus, int pageNum, String regionName, String where);

    Page<SalesMan> getSalesmanList(SalesMan salesman, int pageNum);

    SalesMan getSalesmanByUserId(String userId);

    SalesMan findById(String id);

    List<Object> gainSaojieMan(SalesmanStatus status);
    SalesMan findByUserId(String userId);

    List<String> findByTruename(String truename);

    String getRegionIdByUserId(String userId);

    SalesMan findSaleamanByRegionId(String regionId);


    Set<SalesMan> findForTargetByReginId(String regionId);

    SalesMan findByRegionAndisPrimaryAccount(Region region);

    SalesmanLevel addSalesmanLevel(SalesmanLevel salesmanLevels);
}
