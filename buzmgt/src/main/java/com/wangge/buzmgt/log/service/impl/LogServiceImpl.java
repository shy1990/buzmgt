package com.wangge.buzmgt.log.service.impl;

import com.wangge.buzmgt.log.entity.Log;
import com.wangge.buzmgt.log.repository.LogRepository;
import com.wangge.buzmgt.log.service.LogService;
import com.wangge.buzmgt.log.util.LogUtil;
import com.wangge.buzmgt.util.EnvironmentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

/**
 * Created by barton on 16-6-7.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogRepository logRepository;

    //@Async
    @Override
    public void log(Object origin, Object now, Log.EventType eventType) {

        Log log = new Log();

        log.setEventUsername(EnvironmentUtils.getUser().getUsername());

        log.setEventDate(new Date());

        log.setEvent(eventType);

        log.setAddress(EnvironmentUtils.getIp() + ":" + EnvironmentUtils.getPort());

        log.setOrigin(ObjectUtils.isEmpty(origin) ? "" : origin.toString()); // 先用 entity.toString方法来作为参数,现在阶段的entity关系太复杂,很有可能出现栈溢出

        log.setNow(ObjectUtils.isEmpty(now) ? "" : now.toString()); // 先用 entity.toString方法来作为参数,现在阶段的entity关系太复杂,很有可能出现栈溢出

        this.logRepository.save(log);

        LogUtil.info("日志记录成功");
    }

}
