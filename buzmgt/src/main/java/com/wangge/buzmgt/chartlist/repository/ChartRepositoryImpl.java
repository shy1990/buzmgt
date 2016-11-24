package com.wangge.buzmgt.chartlist.repository;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import javassist.expr.NewArray;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.aspectj.apache.bcel.generic.IINC;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ser.std.NumberSerializer;
import com.wangge.buzmgt.dto.ChartDto;
@Repository
public class ChartRepositoryImpl implements ChartListRepository {
  
  @PersistenceContext
  private EntityManager em;
  
  
 
  
  public ChartDto queryByDeelType(String regionId, String date) {
    String dateSql = createTimeSql(date);

    String cashSql = " select count(*),sum(o.order_price)  from biz_order_signfor o "
        + "  where o.order_pay_type = '2'  and " + dateSql;

    String salemanSql = "select  count(*)  from (select count(*),o.user_id from biz_order_signfor o  where o.order_pay_type = '2' "+
          " and o.creat_time > trunc(to_date('" + date +"','yyyy/MM/dd') -1) and o.creat_time < trunc(to_date('" + date +"','yyyy/MM/dd') +1) group by o.user_id) "; 

    Query query = em.createNativeQuery(cashSql);

    Object[] cashObj = (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(createTotalOrder(date));
    
    Object[] totalOrderObj = (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(salemanSql);
    
     Object saleManObj = query.getSingleResult();
     
     query = em.createNativeQuery(createTotalSaleManSql());
     
     Object totalSaleManObj = query.getSingleResult();
     
    String orderPercent = createPrent(cashObj[0], totalOrderObj[0]);
     
     String amountPercent = createPrent(cashObj[1], totalOrderObj[1]);
     
     String personPercent = createPrent(saleManObj,totalSaleManObj);

    ChartDto dto  = new  ChartDto(saleManObj+"", orderPercent, amountPercent, personPercent, totalOrderObj[0]+"", new BigDecimal(totalOrderObj[1]+""));

    return dto;
  }
  
  
 /**
  * 根据时间和区域查询出库订单和总金额的百分比
   * @see com.wangge.buzmgt.chartlist.repository.ChartListRepository#queryByShipStatus(java.lang.String, java.lang.String)
  */
  public ChartDto queryByShipStatus(String regionId, String date){
    String dateSql = createTimeSql(date);
    
   
    String outboundChartSql = " select count(*),sum(o.order_price)  from biz_order_signfor o " +
                "  where o.fastmail_no is not  null  and " + dateSql;
                    
    String totalOrderSql="       select count(*),sum(o.order_price) " +
                  " from biz_order_signfor o  where "+dateSql;
    
    Query query = em.createNativeQuery(outboundChartSql);
    
     Object[] outboundChart =   (Object[]) query.getSingleResult();
     
     query = em.createNativeQuery(totalOrderSql);
     
     Object[] totalOrder =   (Object[]) query.getSingleResult();
     
     
    // Object[]  obj = object;
     String orderP = createPrent(outboundChart[0], totalOrder[0]);
     
     String amountP = createPrent(outboundChart[1], totalOrder[1]);
     
     
     ChartDto dto = new  ChartDto(orderP, amountP, totalOrder[0]+"", new BigDecimal(totalOrder[1]+""));
    
    return dto;
  }
  
  
  @Override
  public ChartDto queryByRefused(String regionId, String date) {
    String dateSql = createTimeSql(date);
    String refusedSql = "select count(*),sum(o.order_price) " +
  " from biz_order_signfor o " +
 " where  o.order_status = '4' and " +dateSql;
    
    String salemanSql = "select count(*)  from ( " +
       " select o.user_id  from biz_order_signfor o " +
         "   where o.order_status = '4'  "+
         " and o.creat_time > trunc(to_date('" + date +"','yyyy/MM/dd') -1) and " +
         " o.creat_time < trunc(to_date('" + date +"','yyyy/MM/dd') +1) " + 
         " group by o.user_id )";
    Query query = em.createNativeQuery(refusedSql);
    
    Object[] refusedOrderObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(createTotalOrder(date));
    
    Object[] totalOrderObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(salemanSql);
    
    Object saleManObj = query.getSingleResult();
    
    query = em.createNativeQuery(createTotalSaleManSql());
    
    Object totalSaleManObj = query.getSingleResult();
    
    
    
   // Object[]  obj = object;
    String orderPercent = createPrent(refusedOrderObj[0], totalOrderObj[0]);
    
    String amountPercent = createPrent(refusedOrderObj[1], totalOrderObj[1]);
    
    String personPercent = createPrent(saleManObj,totalSaleManObj);
    
   // ChartDto dto = new  ChartDto(orderPercent, amountPercent, totalOrder[0]+"", new BigDecimal(totalOrder[1]+""));
    
    ChartDto dto  = new  ChartDto(saleManObj+"", orderPercent, amountPercent, personPercent, totalOrderObj[0]+"", new BigDecimal(totalOrderObj[1]+""));
    return dto;
  }
  
  @Override
  public ChartDto queryByPayStatus(String regionId, String date) {
    String dateSql = createTimeSql(date);
    String unReportsql = "select count(*),sum(o.total_cost) " +
  " from sjzaixian.sj_tb_order o " +
 " where o.pay_status = '0' and " +
  "  o.order_num not in (select t.orderno from biz_unpayment_remark t where t.create_time > " +
    "   trunc(to_date('"+date+"', 'yyyy/MM/dd')-1) " +
      "   and t.create_time < trunc(to_date('"+date+"', 'yyyy/MM/dd'))) and " + 

 " o.createtime > " +
    "  trunc(to_date('"+date+"', 'yyyy/MM/dd') - 1) " +
 " and o.createtime < " +
     " trunc(to_date('"+date+"', 'yyyy/MM/dd'))";
    
    
    String salemanSql = "select count(*)  from ( " +
       " select o.user_id  from biz_order_signfor o " +
       "  where o.order_pay_type is null " +
         "   and o.order_no not in " +
            "   (select t.orderno " +
               "   from biz_unpayment_remark t " +
               "  where t.create_time > " +
                    "   trunc(to_date('"+date+"', 'yyyy/MM/dd')) "+
                  " and t.create_time < " +
                    "   trunc(to_date('"+date+"', 'yyyy/MM/dd') + 1))  "+
                    " and o.creat_time >     trunc(to_date('"+date+"', 'yyyy/MM/dd') - 1) " +
                    "   and o.creat_time <  trunc(to_date('"+date+"', 'yyyy/MM/dd') + 1) "+
         " group by o.user_id )";
    Query query = em.createNativeQuery(unReportsql);
    
    Object[] unReportObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(createTotalOrder(date));
    
    Object[] totalOrderObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(salemanSql);
    
    Object saleManObj = query.getSingleResult();
    
    query = em.createNativeQuery(createTotalSaleManSql());
    
    Object totalSaleManObj = query.getSingleResult();
    
    
    
   // Object[]  obj = object;
    String orderPercent = createPrent(unReportObj[0], totalOrderObj[0]);
    
    String amountPercent = createPrent(unReportObj[1], totalOrderObj[1]);
    
    String personPercent = createPrent(saleManObj,totalSaleManObj);
    
   // ChartDto dto = new  ChartDto(orderPercent, amountPercent, totalOrder[0]+"", new BigDecimal(totalOrder[1]+""));
    
    ChartDto dto  = new  ChartDto(saleManObj+"", orderPercent, amountPercent, personPercent, totalOrderObj[0]+"", new BigDecimal(totalOrderObj[1]+""));
    return dto;
  }
  
  /**
   * 
    * TODO 简单描述该方法的实现功能（报备）. 
    * @see com.wangge.buzmgt.chartlist.repository.ChartListRepository#queryByReport(java.lang.String, java.lang.String)
   */
  @Override
  public ChartDto queryByReport(String regionId, String date) {
    String dateSql = createTimeSql(date);
    String reportsql = "select count(*),sum(o.order_price) " +
  " from biz_order_signfor o " +
 " where  o.order_no  in (select t.orderno from biz_unpayment_remark t where t.create_time > " +
    "   trunc(to_date('"+date+"', 'yyyy/MM/dd')) " +
      "   and t.create_time < trunc(to_date('"+date+"', 'yyyy/MM/dd')+1)) and " + dateSql;
    
    String salemanSql = "select count(*)  from ( " +
       " select o.user_id  from biz_order_signfor o  where " +
         "  o.order_no  in " +
            "   (select t.orderno " +
               "   from biz_unpayment_remark t " +
               "  where t.create_time > " +
                    "   trunc(to_date('"+date+"', 'yyyy/MM/dd')) "+
                  " and t.create_time < " +
                    "   trunc(to_date('"+date+"', 'yyyy/MM/dd') + 1))  "+
                    " and o.creat_time >     trunc(to_date('"+date+"', 'yyyy/MM/dd') - 1) " +
                    "   and o.creat_time <  trunc(to_date('"+date+"', 'yyyy/MM/dd') + 1) " +
         " group by o.user_id )";
    Query query = em.createNativeQuery(reportsql);
    
    Object[] unReportObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(createTotalOrder(date));
    
    Object[] totalOrderObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(salemanSql);
    
    Object saleManObj = query.getSingleResult();
    
    query = em.createNativeQuery(createTotalSaleManSql());
    
    Object totalSaleManObj = query.getSingleResult();
    
    
    
   // Object[]  obj = object;
    String orderPercent = createPrent(unReportObj[0], totalOrderObj[0]);
    
    String amountPercent = createPrent(unReportObj[1], totalOrderObj[1]);
    
    String personPercent = createPrent(saleManObj,totalSaleManObj);
    
   // ChartDto dto = new  ChartDto(orderPercent, amountPercent, totalOrder[0]+"", new BigDecimal(totalOrder[1]+""));
    
    ChartDto dto  = new  ChartDto(saleManObj+"", orderPercent, amountPercent, personPercent, totalOrderObj[0]+"", new BigDecimal(totalOrderObj[1]+""));
    return dto;
  }
  
  
  
  @Override
  public ChartDto queryByStatement(String regionId, String date) {
    String dateSql = createTimeSql(date);
    String statementSql = "select count(os.signid),sum(os.order_price) " +
        "  from sys_cash_record cr " +
         " left join biz_order_signfor os on os.signid = cr.id " +
        "  where cr.create_date > trunc(to_date('"+date+"', 'yyyy/MM/dd')) " +
         "  and cr.create_date < trunc(to_date('"+date+"', 'yyyy/MM/dd')+ 1) ";
    
    String salemanSql = "select count(*)  from (" +
         " select os.user_id  from sys_cash_record cr " +
         " left join biz_order_signfor os on os.signid = cr.id " +
         " where cr.create_date > trunc(sysdate) " +
         "  and cr.create_date < trunc(sysdate + 1) " +
        " group by os.user_id)";
    Query query = em.createNativeQuery(statementSql);
    
    Object[] unReportObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(createTotalOrder(date));
    
    Object[] totalOrderObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(salemanSql);
    
    Object saleManObj = query.getSingleResult();
    
    query = em.createNativeQuery(createTotalSaleManSql());
    
    Object totalSaleManObj = query.getSingleResult();
    
    
    
   // Object[]  obj = object;
    String orderPercent = createPrent(unReportObj[0], totalOrderObj[0]);
    
    String amountPercent = createPrent(unReportObj[1], totalOrderObj[1]);
    
    String personPercent = createPrent(saleManObj,totalSaleManObj);
    
   // ChartDto dto = new  ChartDto(orderPercent, amountPercent, totalOrder[0]+"", new BigDecimal(totalOrder[1]+""));
    
    ChartDto dto  = new  ChartDto(saleManObj+"", orderPercent, amountPercent, personPercent, totalOrderObj[0]+"", new BigDecimal(totalOrderObj[1]+""));
    return dto;
  }

  
  @Override
  public ChartDto queryByStatementAndPaid(String regionId, String date) {
    String dateSql = createTimeSql(date);
    String statementSql = "select count(os.signid),sum(os.order_price) " +
        "  from sys_cash_record cr " +
         " left join biz_order_signfor os on os.signid = cr.id " +
        "  where cr.status = '2' and cr.create_date > trunc(to_date('"+date+"', 'yyyy/MM/dd')) " +
         "  and cr.create_date < trunc(to_date('"+date+"', 'yyyy/MM/dd')+ 1) ";
    
    String salemanSql = "select count(*)  from (" +
         " select os.user_id  from sys_cash_record cr " +
         " left join biz_order_signfor os on os.signid = cr.id " +
         " where  cr.status = '2' and cr.create_date > trunc(sysdate) " +
         "  and cr.create_date < trunc(sysdate + 1) " +
        " group by os.user_id)";
    Query query = em.createNativeQuery(statementSql);
    
    Object[] unReportObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(createTotalOrder(date));
    
    Object[] totalOrderObj =   (Object[]) query.getSingleResult();
    
    query = em.createNativeQuery(salemanSql);
    
    Object saleManObj = query.getSingleResult();
    
    query = em.createNativeQuery(createTotalSaleManSql());
    
    Object totalSaleManObj = query.getSingleResult();
    
    
    
   // Object[]  obj = object;
    String orderPercent = createPrent(unReportObj[0], totalOrderObj[0]);
    
    String amountPercent = createPrent(unReportObj[1], totalOrderObj[1]);
    
    String personPercent = createPrent(saleManObj,totalSaleManObj);
    
   // ChartDto dto = new  ChartDto(orderPercent, amountPercent, totalOrder[0]+"", new BigDecimal(totalOrder[1]+""));
    
    ChartDto dto  = new  ChartDto(saleManObj+"", orderPercent, amountPercent, personPercent, totalOrderObj[0]+"", new BigDecimal(totalOrderObj[1]+""));
    return dto;
  }

  
  
  
  
  /**
   * 
    * createOrderPrent:(计算百分比). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param outbound
    * @param orders
    * @return 
    * @since JDK 1.8
   */
 
  
  private static String createPrent(Object a,Object b){
    if(a ==null){
      
        return "0%";
     
    }else if("0".equals(a.toString())){
      return "0%";
    }
    
    return getNumberFormat().format( new  BigDecimal(a+"").divide(new BigDecimal(b+""), 5,BigDecimal.
        ROUND_HALF_UP).floatValue() * 100)+"%";  
 }

  
  /**
   * 
    * getNumberFormat:(计算百分比的公共方法). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
    * 
    * @author Administrator 
    * @return 
    * @since JDK 1.8
   */
  private static NumberFormat getNumberFormat(){
    NumberFormat numberFormat = NumberFormat.getInstance(); 
    numberFormat.setMaximumFractionDigits(2);  
    return numberFormat;
  }
  
  
  private static String createTotalSaleManSql(){
    
    return  "select count(*)  from sys_salesman t";
  }
  
  
  
  
  
  
  /**
   * 
    * getTotalOrder:(创建订单总数和总金额的sql). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param date
    * @return 
    * @since JDK 1.8
   */
  private static String createTotalOrder(String date){
    return  "select count(*),sum(o.order_price) "
        + " from biz_order_signfor o  where " + createTimeSql(date);

  }
  /**
   * 
    * createTimeSql:(创建sql的时间条件sql). <br/> 
    * TODO(这里描述这个方法适用条件 – 可选).<br/> 
    * TODO(这里描述这个方法的执行流程 – 可选).<br/> 
    * TODO(这里描述这个方法的使用方法 – 可选).<br/> 
    * TODO(这里描述这个方法的注意事项 – 可选).<br/> 
    * 
    * @author Administrator 
    * @param date
    * @return 
    * @since JDK 1.8
   */
  private static String createTimeSql(String date) {

    return "o.creat_time > trunc(to_date('" + date +"','yyyy/MM/dd') -1) and o.creat_time < trunc(to_date('" + date +"','yyyy/MM/dd')+1)";
  }

 private static String  regionSql (String regionId){
   
   return  !StringUtils.isEmpty(regionId) ? " left join sys_salesman s on s.user_id = o.user_id" : "";
   
 }  
 private static String  regionparamSql (String regionId){
     
      return  !StringUtils.isEmpty(regionId) ? "and s.region_id = '"+regionId+"'" : "";
      
 }


}
