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

public interface SalesManRepository extends JpaRepository<SalesMan,String>{

	List<User> findByRegionId(String regionId);
	
	Page<SalesMan> findAll(Specification<SalesMan> spec, Pageable pageable);

  SalesMan findById(String id);
  
  @Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s")
  List<Object> gainSaojieMan();
  
  @Query("select s.id,s.truename,s.mobile,s.regdate from SalesMan s where s.status=?1")
  List<Object> getSaojieMan(SalesmanStatus status);
  @Query("select s.id from SalesMan s where s.truename = ?1")
  String getIdByTurename(String truename);
  
  /*@Query("ALTER TABLE SalesMan s MODIFY s.region NULL")
  int deleteRegionById(String id);*/
  
  /*
   * 查找某地区下的所有业务
   */
  @Query(value = "select * \n" + "  from sys_salesman s\n" + " where  exists (select 1\n"
      + "       from (select * \n" + "                  from sys_region r\n"
      + "                 start with r.region_id = ?1 \n"
      + "                connect by prior r.region_id = r.parent_id) tmp\n"
      + "         where tmp.region_id = s.region_id and s.status=2)", nativeQuery = true)
  Set<SalesMan> findForTargetByReginId(String regionId);
}
