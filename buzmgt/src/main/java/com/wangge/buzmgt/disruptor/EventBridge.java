package com.wangge.buzmgt.disruptor;

import javax.persistence.EntityManager;

/**
 * Created by jiabin on 16-9-6.
 */
public  class EventBridge {

  /**
   * 生产者方法
   */
  public static void executeProducer(EntityManager em){
    EventProducer eventProducer=new EventProducer(null);
    eventProducer.getMallOrder(em);
  }
}
