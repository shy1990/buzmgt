package com.wangge.buzmgt.assess.service;

import com.wangge.buzmgt.assess.entity.RegistData;

import java.util.List;

public interface RegistDataService {

  int findCountByRegionIdlike(String regionId);

  List<RegistData> findByLoginAccount(String loginAccount);
}
