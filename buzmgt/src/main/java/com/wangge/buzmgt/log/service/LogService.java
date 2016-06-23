package com.wangge.buzmgt.log.service;

import com.wangge.buzmgt.log.entity.Log;

/**
 * Created by barton on 16-6-7.
 */
public interface LogService {

    void log(Object origin, Object now, Log.EventType eventType);
}
