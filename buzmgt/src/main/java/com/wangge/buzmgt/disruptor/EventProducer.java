package com.wangge.buzmgt.disruptor;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;
import com.wangge.buzmgt.util.DateUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**生产者
 * Created by jiabin on 16-9-6.
 */
public class EventProducer {



  //一个translator可以看做一个事件初始化器，publicEvent方法会调用它
  //填充Event
  private static final EventTranslatorOneArg<MallOrder, MallOrder> TRANSLATOR =
      new EventTranslatorOneArg<MallOrder, MallOrder>() {
        public void translateTo(MallOrder mallOrder, long sequence, MallOrder order) {
          mallOrder.setOrderNum(order.getOrderNum());
          mallOrder.setMember_id(order.getMember_id());
        }
      };
  private final RingBuffer<MallOrder> ringBuffer;
  public EventProducer(RingBuffer<MallOrder> ringBuffer) {
    this.ringBuffer = ringBuffer;
  }





  private static final String ORDER_SQL="SELECT O.ID,O.MEMBER_ID,O.ORDER_NUM,O.SHIP_STATUS FROM SJZAIXIAN.SJ_TB_ORDER O";

  /**
   * 获取前十分钟订单
   */
  public  void getMallOrder(EntityManager em)  {
    String timeoftenMinutes= DateUtil.getTimeofTenMinutes();//十分钟之前的时间
   // String timeoftenMinutes="";
    String nowTime=DateUtil.currentDateTimeToString();
    String tenMinutesOrderSql=ORDER_SQL+" WHERE  O.SHIP_TIME BETWEEN to_date('"+timeoftenMinutes+"','yyyy-mm-dd hh24:mi:ss') AND to_date('"+nowTime+"','yyyy-mm-dd hh24:mi:ss')";

    List<Object[]> orderList = em.createNativeQuery(tenMinutesOrderSql).getResultList() ;

    for (Object[] order : orderList) {
//      //可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
//      long sequence = ringBuffer.next();
//      try {
//        //用上面的索引取出一个空的事件用于填充
//        MallOrder mallorder = ringBuffer.get(sequence);// for the sequence
//        mallorder.setMember_id(order[1]+"");
//        mallorder.setOrderNum(order[2]+"");
//      } finally {
//        //发布事件
//        ringBuffer.publish(sequence);
//      }


      /**
       * 另一种方式
       */
      MallOrder mallorder =new MallOrder();
      mallorder.setMember_id(order[1]+"");
      mallorder.setOrderNum(order[2]+"");
      ringBuffer.publishEvent(TRANSLATOR, mallorder);

      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

}
