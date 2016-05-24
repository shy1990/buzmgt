package com.wangge.buzmgt.saojie.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.saojie.entity.SaojieData;
import com.wangge.buzmgt.sys.vo.SaojieDataVo;

public interface SaojieDataService {
  
  SaojieDataVo getsaojieDataList(String userId, String regionId);
  
  Page<SaojieData> getsaojieDataList(String userId, String regionId,int pageNum,int limit);
}
