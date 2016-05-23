package com.wangge.buzmgt.saojie.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wangge.buzmgt.region.entity.Region;
import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.saojie.repository.SaojieDataRepository;

@Service
public class SaojieDataServiceImpl implements SaojieDataService {
  @Resource
  private SaojieDataRepository saojieDateRepository;
  @Override
  public List<SaojieData> findByReion(Region r) {
   
    return saojieDateRepository.findByRegion(r);
  }

}
