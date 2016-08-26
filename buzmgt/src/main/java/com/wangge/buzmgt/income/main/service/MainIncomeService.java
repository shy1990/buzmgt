package com.wangge.buzmgt.income.main.service;

import java.util.List;

import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.vo.BrandType;
import com.wangge.buzmgt.income.main.vo.MachineType;

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
  
  /** 
    * findIncomeMain:判断某月业务员的薪资记录是否存在,若不存在就新建一个返回. <br/> 
    * @author yangqc 
    * @param salesmanId
    * @param month
    * @return 
    * @since JDK 1.8 
    */  
  MainIncome findIncomeMain(String salesmanId,String month);

  /** 
    * findIncomeMain:判断本月业务员的薪资记录是否存在,若不存在就新建一个返回. <br/> 
    * @author yangqc 
    * @param salesmanId
    * @return 
    * @since JDK 1.8 
    */  
  MainIncome findIncomeMain(String salesmanId);
  /** 
    * getAllMachineType:获得所有的机型分类. <br/> 
    * @author yangqc 
    * @return 
    * @since JDK 1.8 
    */  
  List<MachineType> getAllMachineType();
  /** 
    * getAllBrandType:获得所有的品牌. <br/> 
    * @author yangqc 
    * @return 
    * @since JDK 1.8 
    */  
  List<BrandType> getAllBrandType();
}
