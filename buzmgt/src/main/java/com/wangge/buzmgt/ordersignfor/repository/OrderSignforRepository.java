package com.wangge.buzmgt.ordersignfor.repository;

import com.wangge.buzmgt.ordersignfor.entity.OrderSignfor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderSignforRepository extends JpaRepository<OrderSignfor, Long>,
JpaSpecificationExecutor<OrderSignfor>,OrderSignforRepositoryCustom{
  public Page<OrderSignfor> findAll(Pageable pageRequest);
  
  public Page<OrderSignfor> findByCustomSignforException(String status,Pageable pageRequest);
  
  public Page<OrderSignfor> findByCustomSignforExceptionAndCreateTimeBetween(
      String status,String startTime,String endTime,Pageable pageRequest);

  public OrderSignfor findByOrderNo(String orderNo);

  public List<OrderSignfor> findAllByOrderNo(String orderNo);

  @Query("select o from OrderSignfor o where o.id = (select max(t.id) from OrderSignfor t where t.fastmailNo = ?1)")
  OrderSignfor findByFastmailNo(String fastMailNo);


//  @Query(value = " select o.order_no,o.shop_name,o.ARREARS,o.ORDER_PAY_TYPE,o.BILL_STATUS,o.IS_PRIMARY_ACCOUNT ,?2 as todayDate" +
//      "from biz_order_signfor  o where o.USER_ID=?1 " +
//      "and to_char(o.creat_time, 'yyyy-mm') = ?2 ", nativeQuery = true)
//  Page<OrderSignfor> findOrderSignforException(String userId,String todayDate,Pageable pageRequest);

}
