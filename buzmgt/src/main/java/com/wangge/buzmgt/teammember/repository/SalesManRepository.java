package com.wangge.buzmgt.teammember.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wangge.buzmgt.sys.entity.User;
import com.wangge.buzmgt.teammember.entity.SalesMan;
import com.wangge.buzmgt.teammember.entity.SalesmanStatus;

public interface SalesManRepository extends JpaRepository<SalesMan, String> {

	List<User> findByRegionId(String regionId);

	Page<SalesMan> findAll(Specification<SalesMan> spec, Pageable pageable);

  
  @Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s where s.status=?1")
  List<Object> getSaojieMan(SalesmanStatus status);
  @Query("select s.id from SalesMan s where s.truename = ?1")
  String getIdByTurename(String truename);
  @Query("select s.region.id from SalesMan s where s.id =?1")
  String getRegionIdByUserId(String userId);
  
	SalesMan findById(String id);

	//
	/**
	 * 查找某地区下所有的未设置本月月任务的业务员
	 * 
	 * @param regionId
	 * @return
	 */
	@Query(value = "select * \n" + "  from sys_salesman s\n" + " where  exists (select 1\n"
			+ "       from (select * \n" + "                  from sys_region r\n"
			+ "                 start with r.region_id = ?1 \n"
			+ "                connect by prior r.region_id = r.parent_id) tmp\n"
			+ "         where tmp.region_id = s.region_id) and  exists (select  1 \n"
			+ " from sys_month_Task_basicdata d where d.salesman_id=s.user_id and d.used=0 "
			+ " and d.month=to_char(sysdate + interval '1' month,'yyyy-mm'))", nativeQuery = true)
	Set<SalesMan> readAllByRegionIdandMonth_Userd(String regionId);
	
	 /** 
	  * readAllByRegionId:查找一个区域下所有的业务员列表<br/> 
	  * @author yangqc 
	  * @param regionId
	  * @return 
	  * @since JDK 1.8 
	  */  
	@Query(value = "select * \n" + "  from sys_salesman s\n" + " where  exists (select 1\n"
	      + "       from (select * \n" + "                  from sys_region r\n"
	      + "                 start with r.region_id = ?1 \n"
	      + "                connect by prior r.region_id = r.parent_id) tmp\n"
	      + "         where tmp.region_id = s.region_id) ", nativeQuery = true)
  Set<SalesMan> readAllByRegionId(String regionId);
	/**
	 * 通过地区查找主业务员
	 * 
	 * @param regionId
	 * @return
	 */
	@Query("select s from SalesMan s where s.isPrimaryAccount=0 and s.region.id like ?1%")
	List<SalesMan> readByRegionId(String regionId);

	// @Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s where
	// s.SalesmanStatus=1")
	@Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s")
	List<Object> gainSaojieMan();
	
	SalesMan findSaleamanByRegionId(String regionId);
}
