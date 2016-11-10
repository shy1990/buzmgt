package com.wangge.buzmgt.disruptor;


import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

import javax.persistence.EntityManager;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by jiabin on 16-9-6.
 */
public  class EventBridge {

  /**
   * 执行多线程方法
   */
  public static void executeProducer(EntityManager em){


    Executor executor = Executors.newCachedThreadPool();
    MallOrderFactory factory = new MallOrderFactory();
    int bufferSize = 1024;
    Disruptor<MallOrder> disruptor = new Disruptor<MallOrder>(factory, bufferSize, executor);

    disruptor.handleEventsWith(new EventCustomer());

    disruptor.start();

    RingBuffer<MallOrder> ringBuffer = disruptor.getRingBuffer();

    EventProducer producer = new EventProducer(ringBuffer);

    producer.getMallOrder(em);

  }
}
