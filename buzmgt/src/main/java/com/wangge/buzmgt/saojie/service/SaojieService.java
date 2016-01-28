package com.wangge.buzmgt.saojie.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface SaojieService {

  void saveSaojie(Saojie saojie);

  Page<Saojie> getSaojieList(Saojie saojie,int pageNum);
  
  List<Saojie> findBysalesman(SalesMan salesman);
  
  Saojie findByregion(Region region);
  
  Saojie findById(String id);
  
  Saojie changeOrder(int ordernum,String userId);
  
  int getRegionCount();
  SaojieDataVo getsaojieDataList(String userId, String regionId);
  
  int getOrderNumById(String id);
}
