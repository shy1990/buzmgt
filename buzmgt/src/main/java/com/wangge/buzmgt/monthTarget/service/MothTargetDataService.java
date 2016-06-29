package com.wangge.buzmgt.monthTarget.service;

import com.wangge.buzmgt.monthTarget.entity.MothTargetData;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by joe on 16-6-27.
 */
public interface MothTargetDataService {
    public Page<MothTargetData> getMothTargetDatas(String name,String time, Integer page, Integer size);

    public List<MothTargetData> findAll(String time);
}
