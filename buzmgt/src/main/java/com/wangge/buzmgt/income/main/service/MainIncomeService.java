package com.wangge.buzmgt.income.main.service;

/**
 * ClassName: MainIncomeService <br/>
 * Function: 提供各种计算功能. 计算订单, 计算业务员 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2016年8月22日 上午11:02:41 <br/>
 * 
 * @author yangqc
 * @version
 * @since JDK 1.8
 */
public interface MainIncomeService {
  /**
   * caculateOrder:计算已出库订单 <br/>
   * 
   * @author yangqc
   * @since JDK 1.8
   */
  void caculateOutedOrder();
  
  /**
   * caculatePayedOrder:计算已支付订单. <br/>
   * 
   * @author yangqc
   * @since JDK 1.8
   */
  void caculatePayedOrder();
  
  /**
   * caculateUser:计算每个业务员的薪资. <br/>
   * 
   * @author yangqc
   * @since JDK 1.8
   */
  void caculateSalesman();
}
