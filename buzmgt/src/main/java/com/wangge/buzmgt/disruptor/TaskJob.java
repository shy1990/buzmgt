package com.wangge.buzmgt.disruptor;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**定时任务类　每十分钟去执行查询出库方法
 * Created by jiabin on 16-9-6.
 */

@Component
@EnableScheduling
public class TaskJob {
  @PersistenceContext
  private EntityManager em;
  @Scheduled(fixedRate=1000*20)
  public void work(){
    //System.out.println("1秒执行了"+"hahhahahahah");
    EventBridge.executeProducer(em);
  }


}
