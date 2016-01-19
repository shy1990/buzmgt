package com.wangge.buzmgt.saojie.service;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.saojie.entity.Saojie;

public interface SaojieService {

  void saveSaojie(Saojie saojie);

  Page<Saojie> getSaojieList(Saojie saojie,int pageNum);
}
