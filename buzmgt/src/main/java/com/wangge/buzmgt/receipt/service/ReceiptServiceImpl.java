package com.wangge.buzmgt.receipt.service;


import com.wangge.buzmgt.receipt.entity.BillSalesmanVo;
import com.wangge.buzmgt.receipt.entity.BillVo;
import com.wangge.buzmgt.receipt.entity.Receipt;
import com.wangge.buzmgt.receipt.repository.ReceiptRepository;
import com.wangge.buzmgt.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
  public class ReceiptServiceImpl implements ReceiptService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ReceiptRepository receiptRepository;

  @Override
  public Page<BillVo> findAllBill(String date, String salesmanName, int pageNum) {
    //总金额查询计算
    String sql_mall ="(SELECT SUM(t.ACTUAL_PAY_NUM)   AS shouldpaymoneny,\n" +
        " '"+date+"'     AS today,\n" +
        " t.user_id\n" +
        "  FROM BIZ_ORDER_SIGNFOR t ";
    /*    "  WHERE t.createtime BETWEEN trunc( to_date('"+date+"','yyyy-mm-dd')-2)+20/24 AND trunc( to_date('"+date+"','yyyy-mm-dd')-1)+20/24\n" +
        "  AND t.PAY_MENT !='0'\n" +
        "  GROUP BY m.ADMIN_ID\n" +
        "  )p\n" +
        "JOIN SJZAIXIAN.SJ_TB_ADMIN a\n" +
        "ON p.ADMIN_ID=a.ID)sumpay";*/

   /* if(DateUtil.compareDate(date)){
      sql_mall+=" WHERE t.fastmail_time >trunc( to_date('"+date+"','yyyy-mm-dd')-1) ";
    }else{*/
      sql_mall+="WHERE t.fastmail_time >= trunc( to_date('"+date+"','yyyy-mm-dd')-1) AND t.fastmail_time < trunc( to_date('"+date+"','yyyy-mm-dd')) \n" ;
   /* }*/

    sql_mall+=  "  AND t.fastmail_no is not null GROUP BY t.user_id)sumpay ";

    //当天应支付金额
    String sql_todayshouldpay="(select sum(o.ARREARS) as today_shouldPay ,user_id from BIZ_ORDER_SIGNFOR o  \n";
       /* "WHERE o.creat_time BETWEEN trunc( to_date('"+date+"','yyyy-mm-dd')-2)+20/24 AND trunc( to_date('"+date+"','yyyy-mm-dd')-1)+21/24 \n" +
        "and fastmail_no is not null\n" +
        "group by user_id)todyshouldpay";*/


   /* if(DateUtil.compareDate(date)){
      sql_todayshouldpay+=" WHERE o.fastmail_time >trunc( to_date('"+date+"','yyyy-mm-dd')-1) ";
    }else{*/
      sql_todayshouldpay+="WHERE o.fastmail_time >= trunc( to_date('"+date+"','yyyy-mm-dd')-1) AND o.fastmail_time < trunc( to_date('"+date+"','yyyy-mm-dd')) \n" ;
   /* }*/

    sql_todayshouldpay+=  "and o.fastmail_no is not null and o.order_status = '3' \n" +
        "group by user_id)todyshouldpay";
    
    
  /*  String sql_todayshouldpay_unsign = "select sum(o.actual_pay_num) as today_shouldPay from BIZ_ORDER_SIGNFOR o  \n" + 
        " WHERE o.fastmail_time >= trunc( to_date('"+date+"','yyyy-mm-dd')-1) AND o.fastmail_time < trunc( to_date('"+date+"','yyyy-mm-dd')) \n" + 
        " and o.fastmail_no is not null and ( o.order_status = '2' or  o.order_status = '0' ) ";
    */
        

    //历史应付金额

    String sql_historyshouldpay ="(select h.historypay as historypay,h.user_id from  (select sum(o.ARREARS) as historypay,o.user_id from BIZ_ORDER_SIGNFOR o  \n" +
        "where o.fastmail_time <trunc( to_date('"+date+"','yyyy-mm-dd')-1)\n" +
        "group by o.user_id ) h)historyshouldpay\n";
        
    
    String sql_historyshouldpay_unsign = "select sum(actual_pay_num) as paynum from BIZ_ORDER_SIGNFOR o  \n" +
        "where o.fastmail_time <trunc( to_date('"+date+"','yyyy-mm-dd')-1)\n" +
        " and ( ORDER_STATUS ='2' or  ORDER_STATUS ='0')\n" ;
      


    //总算法
    String sql="select s.truename as salemanName,s.user_id as userId ,sumpay.shouldpaymoneny as todayAllShouldPay,sumpay.today as todayDate," +
        "todyshouldpay.today_shouldpay as todayShouldPay,historyshouldpay.historypay as historyShouldPay ," +
        "r.name as regionName from "+sql_mall   +
        " join SYS_SALESMAN s on sumpay.user_id=s.user_id \n" +
        "join "+sql_todayshouldpay  +" on todyshouldpay.user_id=s.user_id\n" +
        "join "+sql_historyshouldpay + " on historyshouldpay.user_id=s.user_id\n" +
        "join sys_region r on s.region_id=r.region_id";

    if(null!=salesmanName&&!"".equals(salesmanName)){
      sql=sql+" where s.truename like '%"+salesmanName+"%'";
    }

    System.out.println(sql);

    Query query =  em.createNativeQuery(sql);
    int count = query.getResultList().size();
    query.setFirstResult(pageNum * 20);
    query.setMaxResults(20);
    
    Query query2 =  em.createNativeQuery(sql_historyshouldpay_unsign);
     Object TodayAllShouldPayUnsign =   query2.getSingleResult();
     
  /*   Query query3 =  em.createNativeQuery(sql_todayshouldpay_unsign);
     Object todayshouldpayunsign =   query3.getSingleResult();
    */
    
    List obj = query.getResultList();
    List<BillVo> billList = new ArrayList<BillVo>();
    if(obj!=null && obj.size()>0){
      Iterator it = obj.iterator();
      while(it.hasNext()){
        Object[] o = (Object[])it.next();
        BillVo bl = new BillVo();

        bl.setSalemanName(o[0]+"");
        bl.setUserId(o[1]+"");
        bl.setTodayAllShouldPay(add(new BigDecimal(o[2]+""), TodayAllShouldPayUnsign!= null ? new BigDecimal(TodayAllShouldPayUnsign+"") : new BigDecimal(0)));
        bl.setTodayDate(o[3]+"");
        if(null==o[4]){
          bl.setTodayShouldPay(new BigDecimal(0) );
        }else{
          bl.setTodayShouldPay(getTodayAllShouldPay(o[4]+"",o[1]+"",date));
        }
        if(null==o[5]){
          bl.setTodayShouldPay(new BigDecimal(0));
        }else{
          bl.setHistoryShouldPay(new BigDecimal(o[5]+""));
        }

        bl.setRegionName(o[6]+"");
        billList.add(bl);
      }
    }
    Page<BillVo> page = new PageImpl<BillVo>(billList, new PageRequest(pageNum, 20), count);
    return page;
  }

  
  private BigDecimal getTodayAllShouldPay(String o,String usetId,String date){
    String sql_todayshouldpay_unsign = "select sum(o.actual_pay_num) as today_shouldPay from BIZ_ORDER_SIGNFOR o  \n" + 
        " WHERE o.fastmail_time >= trunc( to_date('"+date+"','yyyy-mm-dd')-1) AND o.fastmail_time < trunc( to_date('"+date+"','yyyy-mm-dd')) \n" + 
        " and o.fastmail_no is not null and ( o.order_status = '2' or  o.order_status = '0' ) and o.user_id = '"+usetId+"' ";
    
    Query query3 =  em.createNativeQuery(sql_todayshouldpay_unsign);
    Object todayshouldpayunsign =   query3.getSingleResult();
    BigDecimal todayshouldpay = add(new BigDecimal(o), todayshouldpayunsign!= null ? new BigDecimal(todayshouldpayunsign+"") : new BigDecimal(0));
    return todayshouldpay;
  }
  private static BigDecimal add(BigDecimal ActualPayNum,BigDecimal ActualPayNum2){  
    return ActualPayNum.add(ActualPayNum2);   
    }   
  

  @Override
  public Page<BillSalesmanVo> findByUserIdAndCreateTime(String userId, String todayDate, int pageNum) {
    String sql="select o.order_no,o.shop_name,o.ARREARS,o.ORDER_PAY_TYPE,o.BILL_STATUS,o.IS_PRIMARY_ACCOUNT , o.USER_ID, '"+todayDate+"'as todayDate" +
        " from biz_order_signfor  o where " +
        " o.fastmail_time >= trunc( to_date('"+todayDate+"','yyyy-mm-dd')-1) " +
        " and o.fastmail_time < trunc( to_date('"+todayDate+"','yyyy-mm-dd')) and o.order_status = '3' and o.USER_ID='"+userId+"'"+
        "  union " +
        "select o.order_no,o.shop_name,o.ACTUAL_PAY_NUM,o.ORDER_PAY_TYPE,o.BILL_STATUS,o.IS_PRIMARY_ACCOUNT , o.USER_ID, '"+todayDate+"'as todayDate" +
        " from biz_order_signfor  o where " +
        " o.fastmail_time >= trunc( to_date('"+todayDate+"','yyyy-mm-dd')-1) " +
        " and o.fastmail_time < trunc( to_date('"+todayDate+"','yyyy-mm-dd')) and (o.order_status = '0' or o.order_status = '2') and o.USER_ID='"+userId+"' ";
      //  + " and o.CUSTOM_SIGNFOR_TIME is not null";


    Query query =  em.createNativeQuery(sql);
    int count = query.getResultList().size();
    query.setFirstResult(pageNum * 20);
    query.setMaxResults(20);
    //query.unwrap(OrderSignfor.class).setResultTransformer(Transformers.aliasToBean(clazz));
    List obj = query.getResultList();
    List<BillSalesmanVo> orderSignforList = new ArrayList<BillSalesmanVo>();
    if(obj!=null && obj.size()>0){
      Iterator it = obj.iterator();
      while(it.hasNext()){
        Object[] o = (Object[])it.next();
        BillSalesmanVo bs = new BillSalesmanVo();
        bs.setOrderNum(o[0]+"");
        bs.setShopName(o[1]+"");
        if(null!=o[2]){
          bs.setArrears(Double.parseDouble(o[2]+""));

        }else{
          bs.setArrears((double) 0);
        }

        if(null!=o[3]){
          bs.setOrderPayStatus(Integer.parseInt(o[3]+""));
        }else{
          bs.setOrderPayStatus(3);
        }

        if(null!=o[4]){
          bs.setBillStatus(Integer.parseInt(o[4]+""));
        }else{
          bs.setBillStatus(1);
        }

        bs.setIsPrimaryAccount(Integer.parseInt(o[5]+""));
        bs.setTodayDate(todayDate);
        orderSignforList.add(bs);
      }
    }
    Page<BillSalesmanVo> page = new PageImpl<BillSalesmanVo>(orderSignforList, new PageRequest(pageNum, 20), count);
    return page;
  }


    /**
     *
     * @param orderno
     * @return
     */
  @Override
  public List<Receipt> findByOrderno(String orderno) {


    return receiptRepository.findByOrderNo(orderno);
  }
}
