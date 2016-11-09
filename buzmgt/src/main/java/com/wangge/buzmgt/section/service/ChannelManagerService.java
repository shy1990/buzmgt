package com.wangge.buzmgt.section.service;

import com.wangge.buzmgt.section.pojo.ChannelManager;

import java.util.List;

/**
 * Created by joe on 16-11-8.
 */
public interface ChannelManagerService {

  public List<ChannelManager> findChannelManager(String name);
}
