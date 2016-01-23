package com.wangge.buzmgt.saojie.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;
import com.wangge.buzmgt.teammember.entity.SalesMan;

public interface SaojieService {

  void saveSaojie(Saojie saojie);

  Page<Saojie> getSaojieList(Saojie saojie,int pageNum);

  SaojieDataVo getsaojieDataList(String userId, String regionId);

}
