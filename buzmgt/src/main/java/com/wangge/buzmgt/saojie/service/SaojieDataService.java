package com.wangge.buzmgt.saojie.service;

import java.util.List;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.SaojieData;

public interface SaojieDataService {
  List<SaojieData> findByReion(Region r);
}
