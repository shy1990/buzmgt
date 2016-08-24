package com.wangge.buzmgt.monthtarget.service;

import org.springframework.data.domain.Page;

import com.wangge.buzmgt.monthtarget.entity.MothTargetData;

import java.util.List;

/**
 * Created by joe on 16-6-27.
 */
public interface MothTargetDataService {
    public Page<MothTargetData> getMothTargetDatas(String regionId,String name,String time, Integer page, Integer size);

    public List<MothTargetData> findAll(String regionid,String time);


//    public Map<String,Integer> maps();

//    public Integer findCount();//查询当前业务员活跃商家

}
