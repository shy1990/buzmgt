package com.wangge.buzmgt.assess.service;

import com.wangge.buzmgt.assess.entity.RegistData;
import com.wangge.buzmgt.assess.repository.RegistDataRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/7/16.
 */
@Service
public class RegistDataServiceImpl implements RegistDataService{
    @Resource
    private RegistDataRepository registDataRepository;

    @Override
    public int findCountByRegionIdlike(String regionId) {
        return registDataRepository.findCountByRegionIdlike(regionId);
    }

    @Override
    public List<RegistData> findByLoginAccount(String loginAccount) {
        return registDataRepository.findByLoginAccount(loginAccount);
    }
}
