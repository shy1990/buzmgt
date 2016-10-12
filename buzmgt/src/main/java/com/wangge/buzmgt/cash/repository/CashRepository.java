package com.wangge.buzmgt.cash.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.cash.entity.Cash;
import com.wangge.buzmgt.cash.entity.Cash.CashStatusEnum;

public interface CashRepository extends JpaRepository<Cash, Long>,
JpaSpecificationExecutor<Cash>{

  List<Cash> findByUserIdAndStatus(String userId,CashStatusEnum status);
	@Query("SELECT c FROM Cash c WHERE c.userId=?1 and c.status= ?2 and c.createDate>=TO_DATE(?3,'YYYY-MM-DD hh24:mi:ss') " +
					"and c.createDate<= TO_DATE(?4,'YYYY-MM-DD hh24:mi:ss')")
  List<Cash> findByUserIdAndStatusCreateDate(String userId,CashStatusEnum status,String startDate, String endDate);

  /**
   * 
  * @Title: findByStatusGroupByUserId 
  * @Description: 查询为结算的用户id
  * @param @param status
  * @param @return    设定文件 
  * @return List<String>    返回类型 
  * @throws
   */
  @Query("SELECT C.userId FROM Cash C WHERE C.status='0' AND C.createDate>=TO_DATE(?1, 'YYYY-MM-DD') GROUP BY C.userId ")
  List<String> findByStatusGroupByUserId(String searchDate);
  /**
   * 
   * @Title: findByStatusGroupByUserId 
   * @Description: 查询为过期未结算的用户id
   * @param @param status
   * @param @return    设定文件 
   * @return List<String>    返回类型 
   * @throws
   */
  @Query("SELECT C.userId FROM Cash C WHERE C.status='0' AND C.createDate <= TO_DATE(?1, 'YYYY-MM-DD hh24:mi:ss') GROUP BY C.userId ")
  List<String> findByStatusGroupByUserIdSceduled(String searchDate);
}
