package com.wangge.buzmgt.saojie.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.Saojie.SaojieStatus;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface SaojieService {

  void saveSaojie(Saojie saojie);

  Page<Saojie> getSaojieList(Saojie saojie,int pageNum,String regionName);
  
  List<Saojie> findBysalesman(SalesMan salesman);
  
  Saojie findByregion(Region region);
  
  Saojie findById(String id);
  
  Saojie changeOrder(int ordernum,String userId);
  
  int getRegionCount();
  SaojieDataVo getsaojieDataList(String userId, String regionId,int pageNum,int limit);
  
  int getOrderNumById(String id);
  
  List<Region> findRegionById(String id);
  
  Saojie findByOrderAndSalesman(int ordernum,SalesMan salesman);

  SaojieDataVo getsaojieDataList(String userId, String regionId);
  
  
  List<Saojie> findSaojie(SaojieStatus  status,String userId);
  Saojie findByOrderAndUserId(int order,String userId);
}
