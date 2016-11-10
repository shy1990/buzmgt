package com.wangge.buzmgt.disruptor;

import com.lmax.disruptor.EventHandler;
import com.wangge.buzmgt.income.main.service.MainIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**消费者
 * Created by jiabin on 16-9-7.
 */
  @ComponentScan
  @SpringBootApplication
  @ServletComponentScan
public class EventCustomer implements EventHandler<MallOrder> {
  @Autowired
  private MainIncomeService mainIncomeService;

  private static ApplicationContext applicationContext = null;
  @Override
  public void onEvent(MallOrder mallOrder, long l, boolean b) throws Exception {
    System.out.println("ordernum:"+mallOrder.getOrderNum()+"*****memberid"+mallOrder.getMember_id());
    //mainIncomeService.caculateOutedOrder(mallOrder.getOrderNum());
    SpringUtil.getBean(MainIncomeService.class).caculateOutedOrder(mallOrder.getOrderNum());
  }


  /*@Bean
  public SpringUtil springUtil2(){return new SpringUtil();}

  public static void main(String[] args) {
    SpringApplication.run(MainIncomeService.class, args);
  }*/
}
