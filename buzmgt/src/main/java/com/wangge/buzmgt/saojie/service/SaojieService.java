package com.wangge.buzmgt.saojie.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.Saojie.SaojieStatus;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface SaojieService {

  Saojie saveSaojie(Saojie saojie);

  Page<Saojie> getSaojieList(Saojie saojie,int pageNum,int size, String regionName);
  
  List<Saojie> findBysalesman(SalesMan salesman);
  
  Saojie findByregion(Region region);
  
  Saojie findById(String id);
  
  Saojie changeOrder(int ordernum,String userId);
  
  int getRegionCount();
  
  int getOrderNumById(String id);
  
  List<Region> findRegionById(String id);
  
  Saojie findByOrderAndSalesman(int ordernum,SalesMan salesman);

  List<Saojie> findSaojie(SaojieStatus  status,String userId);
  Saojie findByOrderAndUserId(int order,String userId);
  
  Saojie findByregionId(String regionId);
}
