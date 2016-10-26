package com.wangge.buzmgt.disruptor;

import com.lmax.disruptor.EventHandler;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import org.springframework.beans.factory.annotation.Autowired;

/**消费者
 * Created by jiabin on 16-9-7.
 */
  public class EventCustomer implements EventHandler<MallOrder>{
  @Autowired
  private MainIncomeService mainIncomeService;

  @Override
  public void onEvent(MallOrder mallOrder, long l, boolean b) throws Exception {

    System.out.println("ordernum:"+mallOrder.getOrderNum()+"*****memberid"+mallOrder.getMember_id());

    mainIncomeService.caculateOutedOrder(mallOrder.getOrderNum());
  }
}
