package com.wangge.buzmgt.income.main.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wangge.buzmgt.income.main.entity.MainIncome;
import com.wangge.buzmgt.income.main.vo.MainIncomeVo;
import com.wangge.buzmgt.income.main.vo.OrderGoods;

/**
 * ClassName: MainIncomeService <br/>
 * Function: 提供各种计算功能. 计算订单, 计算业务员 <br/>
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
  void caculateOutedOrder(String orderNo, String memberId, String payStatus, Date paytime);
  
  /**
   * caculatePayedOrder:计算线下支付订单. <br/>
   * 
   * 该功能可以app-interface里完成,通过订单接口调用;
   * 
   * @author yangqc
   * @since JDK 1.8
   */
  void caculatePayedOrder(String orderNo, String userId,Date payDate, String regionId);
  
  /**
   * 计算油补
   */
  void calculateOil();
  
  /**
   * findIncomeMain:判断某月业务员的薪资记录是否存在,若不存在就新建一个返回. <br/>
   * 
   * @author yangqc
   * @param salesmanId
   * @param month
   * @return
   * @since JDK 1.8
   */
  MainIncome findIncomeMain(String salesmanId, String month);
  
  /**
   * findIncomeMain:判断本月业务员的薪资记录是否存在,若不存在就新建一个返回. <br/>
   * 
   * @author yangqc
   * @param salesmanId
   * @return
   * @since JDK 1.8
   */
  MainIncome findIncomeMain(String salesmanId);
  
  /**
   * 查询视图页面
   */
  Page<MainIncomeVo> getVopage(Pageable pageReq, Map<String, Object> searchParams) throws Exception;
  
  /**
   * 导出用查询 <br/>
   */
  List<MainIncomeVo> findAll(Map<String, Object> searchParams);
  
  /**
   * 计算已付款的订单的单品方法 <br/>
   * 
   * @param achieveFlag
   *          是否计算达量 0不计算,1计算
   */
  void caculatePayedOrder(String userId, Long planId, Date payDate, List<OrderGoods> goodList, String regionId,
      int achieveFlag);
  
  /**
   * 删除分支订单收益 <br/>
   * 
   * @throws Exception
   */
  void deleteSubIncome(Long planId, String userId, Date startDate) throws Exception;
  
  /**
   * 每月一算,总结计算
   */
  void calIncomePerMonth();
  
  /**
   * 计算收益修改有效开始时间
   * 
   * @return
   * @since JDK 1.8
   */
  Date getEffectiveStartTime(Date startTime, String userId);
  
  /**
   * 保存业务员的达量收入到薪资主表的上月薪资记录里. <br/>
   * 
   * @param userId
   *          业务员id
   * @param achieveIncome
   *          新增加的收益
   * @throws Exception
   * @since JDK 1.8
   */
  void updateAchieveIncome(String userId, double achieveIncome) throws Exception;
  
  /**
   * 保存业务员的叠加收入到薪资主表的上月薪资记录里.<br/>
   * @param userId
   * @param superPositionIncome
   * @throws Exception
   */
  void updateSuperIncome(String userId, double superPositionIncome) throws Exception;
}
