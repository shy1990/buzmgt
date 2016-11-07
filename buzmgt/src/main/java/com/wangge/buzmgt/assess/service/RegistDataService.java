package com.wangge.buzmgt.assess.service;

public interface RegistDataService {

  int findCountByRegionIdlike(String regionId);

  String findLoginAccountByLoginAccount(String loginAccount);
}
