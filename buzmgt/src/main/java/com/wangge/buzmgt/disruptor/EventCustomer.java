package com.wangge.buzmgt.disruptor;

import com.lmax.disruptor.EventHandler;

/**消费者
 * Created by jiabin on 16-9-7.
 */
  public class EventCustomer implements EventHandler<MallOrder>{


  @Override
  public void onEvent(MallOrder mallOrder, long l, boolean b) throws Exception {

    System.out.println("ordernum:"+mallOrder.getOrderNum()+"*****memberid"+mallOrder.getMember_id());
  }
}
