package com.wangge.buzmgt.monthTarget.service;

import com.wangge.buzmgt.monthTarget.entity.MothTargetData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by joe on 16-6-27.
 */
public interface MothTargetDataService {
    public Page<MothTargetData> getMothTargetDatas(String regionId,String name,String time, Integer page, Integer size);

//    public List<MothTargetData> findAll(String regionid,String time);


//    public Map<String,Integer> maps();

//    public Integer findCount();//查询当前业务员活跃商家

//    public Integer count_
}
