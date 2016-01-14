package com.wangge.buzmgt.saojie.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.wangge.buzmgt.saojie.entity.Saojie;
import com.wangge.buzmgt.saojie.repository.SaojieRepository;

@Service
public class SaojieServiceImpl implements SaojieService {

	@Resource
	private SaojieRepository saojieRepository;

  @Override
  public void saveSaojie(Saojie saojie) {
    saojieRepository.save(saojie);
  }
	
}
