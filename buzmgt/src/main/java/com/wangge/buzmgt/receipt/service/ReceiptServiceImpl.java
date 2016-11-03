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

    if(DateUtil.compareDate(date)){
      sql_mall+=" WHERE t.fastmail_time >trunc( to_date('"+date+"','yyyy-mm-dd')-2) ";
    }else{
      sql_mall+="WHERE t.fastmail_time BETWEEN trunc( to_date('"+date+"','yyyy-mm-dd')-2) AND trunc( to_date('"+date+"','yyyy-mm-dd')-1) \n" ;
    }

    sql_mall+=  "  AND t.fastmail_no is not null GROUP BY t.user_id)sumpay ";

    //当天应支付金额
    String sql_todayshouldpay="(select sum(o.ARREARS) as today_shouldPay ,user_id from BIZ_ORDER_SIGNFOR o  \n";
       /* "WHERE o.creat_time BETWEEN trunc( to_date('"+date+"','yyyy-mm-dd')-2)+20/24 AND trunc( to_date('"+date+"','yyyy-mm-dd')-1)+21/24 \n" +
        "and fastmail_no is not null\n" +
        "group by user_id)todyshouldpay";*/


    if(DateUtil.compareDate(date)){
      sql_todayshouldpay+=" WHERE o.fastmail_time >trunc( to_date('"+date+"','yyyy-mm-dd')-2) ";
    }else{
      sql_todayshouldpay+="WHERE o.fastmail_time BETWEEN trunc( to_date('"+date+"','yyyy-mm-dd')-2) AND trunc( to_date('"+date+"','yyyy-mm-dd')-1) \n" ;
    }

    sql_todayshouldpay+=  "and fastmail_no is not null\n" +
        "group by user_id)todyshouldpay";

    //历史应付金额

    String sql_historyshouldpay ="(select (h.historypay+p.paynum)as historypay,p.user_id from  (select sum(o.ARREARS) as historypay,o.user_id from BIZ_ORDER_SIGNFOR o  \n" +
        "where o.fastmail_time <trunc( to_date('"+date+"','yyyy-mm-dd')-2)\n" +
        "group by o.user_id ) h\n" +
        "join \n" +
        "(select sum(actual_pay_num) as paynum,o.user_id from BIZ_ORDER_SIGNFOR o  \n" +
        "where o.fastmail_time <trunc( to_date('"+date+"','yyyy-mm-dd')-2)\n" +
        "and ORDER_STATUS ='2'\n" +
        "group by o.user_id ) p on h.user_id=p.user_id)historyshouldpay";


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
    List obj = query.getResultList();
    List<BillVo> billList = new ArrayList<BillVo>();
    if(obj!=null && obj.size()>0){
      Iterator it = obj.iterator();
      while(it.hasNext()){
        Object[] o = (Object[])it.next();
        BillVo bl = new BillVo();

        bl.setSalemanName(o[0]+"");
        bl.setUserId(o[1]+"");
        bl.setTodayAllShouldPay(Double.parseDouble(o[2]+""));
        bl.setTodayDate(o[3]+"");
        if(null==o[4]){
          bl.setTodayShouldPay((double) 0);
        }else{
          bl.setTodayShouldPay(Double.parseDouble(o[4]+""));
        }
        if(null==o[5]){
          bl.setTodayShouldPay((double) 0);
        }else{
          bl.setHistoryShouldPay(Double.parseDouble(o[5]+""));
        }

        bl.setRegionName(o[6]+"");
        billList.add(bl);
      }
    }
    Page<BillVo> page = new PageImpl<BillVo>(billList, new PageRequest(pageNum, 20), count);
    return page;
  }


  @Override
  public Page<BillSalesmanVo> findByUserIdAndCreateTime(String userId, String todayDate, int pageNum) {
    String sql="select o.order_no,o.shop_name,o.ARREARS,o.ORDER_PAY_TYPE,o.BILL_STATUS,o.IS_PRIMARY_ACCOUNT ,'"+todayDate+"'as todayDate" +
        " from biz_order_signfor  o where o.USER_ID='" +userId+
        "' and o.fastmail_time = trunc( to_date('"+todayDate+"','yyyy-mm-dd')-1)";
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
