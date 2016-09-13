package com.wangge.buzmgt.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Created by jiabin on 16-9-8.
 */
public class MallOrderFactory implements EventFactory{
  @Override
  public Object newInstance() {
    return new MallOrder();
  }
}
