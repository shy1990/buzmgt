package com.wangge.buzmgt.salesman.service;

import com.wangge.buzmgt.salesman.entity.MonthPunishUp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by 神盾局 on 2016/5/21.
 */
public interface MonthPunishUpService {

    public Page<MonthPunishUp> findByPage(Pageable pageable);
    public Page<MonthPunishUp> findByPage(String timeStrat, String timeEnd, Pageable pageable);
    public List<MonthPunishUp> findAllExport();
}
